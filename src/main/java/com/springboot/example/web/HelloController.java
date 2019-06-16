package com.springboot.example.web;

import com.springboot.example.domain.User;
import com.springboot.example.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    // GET 请求
    @GetMapping("/hello")
    // 不加 @ResponseBody 就会被 thymeleaf 视图解析器解析到 html 页面
    @ResponseBody
    public Object helloGET(HttpServletRequest request) {
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        logger.info(">>>>> PARAM_MAP: {}", map);
        return map;
    }


    // POST（application/x-www-form-urlencoded） 请求
    @PostMapping("/hello")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request) {
        logger.info(">>>>> REQUEST_URI: {}", request.getRequestURI());
        getHeaderMap(request).forEach((key, value) ->logger.info(">>>>> HEADER_MAP: {} = {}", key, value));
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        logger.info(">>>>> PARAM_POST: {}", map);
        return map;
    }

    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            map.put(name, request.getHeader(name));
        }
        return map;
    }

    // POST（application/x-www-form-urlencoded） 请求
    @PostMapping("/helloUSER")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request, User user) {
        getHeaderMap(request).forEach((key, value) ->logger.info(">>>>> HEADER_MAP: {} = {}", key, value));
        logger.info(">>>>> PARAM_POST_USER {}", user);
        return user;
    }

    // POST（application/json） 请求
    @PostMapping("/helloJSON")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        getHeaderMap(request).forEach((key, value) ->logger.info(">>>>> HEADER_MAP: {} = {}", key, value));
        logger.info(">>>>> PARAM_POST_JSON: {}", map);
        return map;
    }

}
