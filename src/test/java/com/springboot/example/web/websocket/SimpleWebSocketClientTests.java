package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSON;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.SignUtil;
import org.java_websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleWebSocketClientTests {

    private static Logger logger = LoggerFactory.getLogger(SimpleWebSocketClientTests.class);

    private static WebSocketClient webSocketClient;

    private static final int BUFFER_LENGTH = 1024 * 8;

    @Before
    public void connect() {
        new Thread(() -> {
            try {
                URI uri = new URI("ws://127.0.0.1:9527/websocket");
                Map<String, String> httpHeaders = new HashMap<>();
                httpHeaders.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
                httpHeaders.put("foo", "bar");
                httpHeaders.put("sign", SignUtil.generateSignature(httpHeaders, "sign"));
                webSocketClient = new SimpleWebSocketClient(uri, httpHeaders);
                webSocketClient.connect();
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(logger, e);
            }
        }).start();
    }

    @Test
    public void sendTextMessage() throws InterruptedException {
        String message = "HELLO";
        boolean notSend = true;
        while (notSend) {
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(message);
                notSend = false;
            }
            Thread.sleep(10);
        }
        // 让主线程阻塞住不要退出，不然长连接守护线程也会退出
        Thread.currentThread().join();
    }

    @Test
    public void sendBinaryMessage() throws InterruptedException, IOException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\paypal.png";
        Map<String, String> map = new HashMap<>();
        map.put("filename", filePath.substring(filePath.lastIndexOf("\\") + 1));
        map.put("md5", DigestUtils.md5DigestAsHex(new FileInputStream(filePath)));
        String message = JSON.toJSONString(map);
        boolean notSend = true;
        while (notSend) {
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(message);
                RandomAccessFile accessFile = new RandomAccessFile(filePath, "r");
                byte[] buf = new byte[BUFFER_LENGTH];
                int len;
                while ((len = accessFile.read(buf)) != -1) {
                    if (len == BUFFER_LENGTH) {
                        webSocketClient.send(buf);
                    } else {
                        byte[] temp = new byte[len];
                        System.arraycopy(buf, 0, temp, 0, len);
                        webSocketClient.send(temp);
                    }
                }
                accessFile.read(buf);
                notSend = false;
            }
            // 每次获取 websocket 客户端状态后线程需要 sleep 一下，不然 cpu 一直不停地执行此任务，没有时间更新 websocket 客户端状态
            Thread.sleep(10);
        }
        // 让主线程阻塞住不要退出，不然长连接守护线程也会退出
        Thread.currentThread().join();
    }

}