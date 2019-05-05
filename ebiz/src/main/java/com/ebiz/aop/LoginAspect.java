package com.ebiz.aop;


import com.ebiz.SpringContextUtils;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ResultData;
import com.ebiz.model.ResultState;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ebiz.common.Constant.SESSION_KEY_USER;

@Component
@Aspect
@Order(10)
public class LoginAspect {
    private final static Logger logger = LoggerFactory.getLogger(LoginAspect.class);


    @Around(value = "execution(* com.ebiz.controller..*(..)) " +
            "&& !execution(* com.ebiz.controller.WelcomeController..*(..)) ")
    public Object execute(ProceedingJoinPoint jp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        logger.info("------------------LoginAspect-------------------");
        String name = jp.getSignature().getName();
        logger.info("------访问：" + name + "");
        if (name.equals("index")) {//跳转到登陆页面不拦截
            try {
                logger.info("------访问首页");
                return jp.proceed();
            } catch (Throwable throwable) {
                logger.info("------访问出错");
                throwable.printStackTrace();
                return new ResultData(ResultState.FAIL, throwable.getMessage());
            }
        }

        if (name.equals("getRegisterCode")) {//跳转到登陆页面不拦截
            try {
                logger.info("------访问二维码");
                return jp.proceed();
            } catch (Throwable throwable) {
                logger.info("------访问出错");
                throwable.printStackTrace();
                return new ResultData(ResultState.FAIL, throwable.getMessage());
            }
        }
        EbizUser user = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        if (user != null) {
            if (!"Active".equals(user.getStatus())) {
                logger.info("------已登录 未激活用户");
                return new ResultData(ResultState.REQUIRED_ACTIVE);
            }
            if (name.equals("registerPage")
                    || name.equals("loginPage")
                    || name.equals("login")
                    || name.equals("register")) {
                logger.info("------已登录 访问登录注册");
                return new ResultData(ResultState.FAIL, "已登录");
            }
            try {
                logger.info("------已登录 访问业务相关页面");
                return jp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return new ResultData(ResultState.FAIL, throwable.getMessage());
            }
        } else {
            if (name.equals("registerPage")
                    || name.equals("loginPage")
                    || name.equals("login")
                    || name.equals("register")) {
                try {
                    logger.info("------未登录 访问登录注册");
                    return jp.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return new ResultData(ResultState.FAIL, throwable.getMessage());
                }
            } else {
                logger.info("------未登录 访问业务相关页面");
                return new ResultData(ResultState.REQUIRED_LOGIN);
            }
        }
    }
}
