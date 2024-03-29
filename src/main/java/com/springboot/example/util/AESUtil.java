package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES 加解密工具类
 *
 * @author zhangyonghong
 * @date 2019.6.13
 */
@Slf4j
public class AESUtil {

    // private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String SEED = "zhangyonghong";

    private AESUtil() {
    }

    /**
     * 获取加密器
     *
     * @param mode 加密或者解密模式
     * @return cipher 加密器
     */
    private static Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        // 1.构造密钥生成器，指定为 AES 算法，不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 2.根据 seed 规则初始化密钥生成器，生成一个 128 位的随机源
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(SEED.getBytes());
        keygen.init(128, random);
        // 3.产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();
        // 4.获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();
        // 5.根据字节数组生成 AES 密钥
        SecretKey key = new SecretKeySpec(raw, "AES");
        // 6.根据指定算法 AES 自成密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 7.初始化密码器，第一个参数为加密（Encrypt_mode）或者解密（Decrypt_mode）操作，第二个参数为使用的 KEY
        cipher.init(mode, key);
        return cipher;
    }

    /**
     * 加密
     *
     * @param content 待加密字符串
     * @return 加密后字符串
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            // 将字符串转成原始字节数组
            byte[] byteOriginal = content.getBytes(StandardCharsets.UTF_8);
            // 加密，得到加密后的字节数组
            byte[] byteEncrypt = cipher.doFinal(byteOriginal);
            // 用 Base64 编码加密后的字节数组成字符串
            return Base64.getEncoder().encodeToString(byteEncrypt);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param encrypt 待解密字符串
     * @return 解密后字符串
     */
    public static String decrypt(String encrypt) {
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            // 用 Base64 解码得到加密后的字节数组
            byte[] byteEncrypt = Base64.getDecoder().decode(encrypt);
            // 解密，得到原始字节数组
            byte[] byteOriginal = cipher.doFinal(byteEncrypt);
            // 将原始字节数组构造成解密后的字符串
            return new String(byteOriginal, StandardCharsets.UTF_8);
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return null;
    }

}
