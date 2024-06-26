package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * HttpURLConnection 工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class HttpURLConnectionUtilTest {

    // private static Logger logger = LoggerFactory.getLogger(HttpURLConnectionUtilTests.class);

    @Test
    public void httpGet() throws Exception {
        String url = "https://www.baidu.com";
        String result = HttpURLConnectionUtil.httpGet(url, null);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void httpPost() throws Exception {
        // String url = "http://127.0.0.1:9527/hello";
        String url = "https://sample:9527/hello";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("-", ""));
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        String param = "foo=bar&bar=barz";
        String result = HttpURLConnectionUtil.httpPost(url, headerMap, param);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void httpPostJson() throws Exception {
        // String url = "http://127.0.0.1:9527/helloJSON";
        String url = "https://127.0.0.1:9527/helloJSON";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("-", ""));
        headerMap.put("Content-Type", "application/json");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String param = JSON.toJSONString(paramMap);
        String result = HttpURLConnectionUtil.httpPost(url, headerMap, param);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void upload() throws Exception {
        // String url = "http://127.0.0.1:9527/upload?filename=test.csv";
        // String url = "https://127.0.0.1:9527/upload?filename=test.csv";
        String url = "https://nbiot-smart-glass.eliteei.com/app/v1/upload?filename=1589771849250.png";
        String filePath = "D:\\1589771849250.png";
        String result = HttpURLConnectionUtil.upload(url, filePath);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void multipartUpload() throws Exception {
        // String url = "http://127.0.0.1:9527/multipartUpload";
        String url = "https://127.0.0.1:9527/multipartUpload";
        List<String> filePathList = new ArrayList<>();
        filePathList.add("D:\\test.csv");
        filePathList.add("D:\\test2.csv");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String result = HttpURLConnectionUtil.multipartUpload(url, filePathList, paramMap);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void download() throws Exception {
        // 1.请求动态资源
        // String url = "http://127.0.0.1:9527/download?foo=bar";
        String url = "https://127.0.0.1:9527/download?foo=bar";
        // 2.请求静态资源
        // String url = "https://127.0.0.1:8800/TeamViewerHost.apk";
        String downloadDir = "D:/";
        HttpURLConnectionUtil.download(url, downloadDir);
        log.info(">>>>> DOWNLOAD SUCCESS");
    }

}
