package com.ebiz.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
public class JspPageExceptionGlobalAspect {
    private final static Logger logger = LoggerFactory.getLogger(JspPageExceptionGlobalAspect.class);

    @Pointcut("")
    public void pointcut() {

    }

    @Before("pointcut()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        try {
            return pjp.proceed();
        } catch (BindException e) {
            logger.warn("JSP页面访问 数据绑定异常", e);
            return "JSP页面访问 数据绑定异常";
        } catch (DuplicateKeyException e) {
            logger.warn("JSP页面访问 主键冲突异常 请勿录入重复数据", e);
            return "JSP页面访问 主键冲突异常 请勿录入重复数据";
        } catch (DataIntegrityViolationException e) {
            logger.warn("JSP页面访问 数据完整性异常", e);
            return "JSP页面访问 数据完整性异常 插入数据失败 ，请检查数据完整性";
        } catch (Exception e) {
            logger.warn("JSP页面访问 出现未知错误", e);
            if (method.getReturnType().equals(String.class)) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                request.setAttribute("msg", e.getMessage());
                return "500.jsp";
            } else {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("500.jsp");
                mv.addObject("msg", e.getMessage());
            }
            return "JSP页面访问 出现未知错误";
        }
    }
}
