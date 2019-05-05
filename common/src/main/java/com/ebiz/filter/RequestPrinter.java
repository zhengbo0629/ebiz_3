package com.ebiz.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class RequestPrinter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestPrinter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String method = request.getMethod();
        String remoteIP = request.getRemoteAddr();  //获取客户端IP地址
        StringBuffer requestURL = request.getRequestURL();
        logger.info("[ " + remoteIP + "]");
        logger.info("[ " + method.toUpperCase() + ": " + requestURL + "]");
        if (!method.equalsIgnoreCase("post")) {
            Map<String, String[]> parameterMap = req.getParameterMap();
            if (!parameterMap.isEmpty()) {
                String json = JSONObject.toJSONString(parameterMap);
                logger.info("[ " + json + "]");
            }
            chain.doFilter(req, resp);
        } else {
            Map<String, String[]> parameterMap = req.getParameterMap();
            if (!parameterMap.isEmpty()) {
                String json = JSONObject.toJSONString(parameterMap);
                logger.info("[ " + json + "]");
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
