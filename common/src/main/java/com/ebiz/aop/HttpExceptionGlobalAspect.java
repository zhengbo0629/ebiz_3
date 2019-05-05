package com.ebiz.aop;

import com.ebiz.model.ResultData;
import com.ebiz.model.ResultState;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;

@Aspect
public class HttpExceptionGlobalAspect {
    private final static Logger logger = LoggerFactory.getLogger(HttpExceptionGlobalAspect.class);

    @Pointcut("")
    public void pointcut() {

    }

    @Before("pointcut()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (BindException e) {
            logger.warn("HTTP异步调用 数据绑定异常", e);
            return new ResultData(ResultState.FAIL, "HTTP异步调用 数据绑定异常");
        } catch (DuplicateKeyException e) {
            logger.warn("HTTP异步调用 主键冲突异常 请勿录入重复数据", e);
            return new ResultData(ResultState.FAIL, "HTTP异步调用 主键冲突异常 请勿录入重复数据");
        } catch (DataIntegrityViolationException e) {
            logger.warn("HTTP异步调用 数据完整性异常", e);
            return new ResultData(ResultState.FAIL, "HTTP异步调用 数据完整性异常 插入数据失败 ，请检查数据完整性");
        } catch (Exception e) {
            logger.warn("HTTP异步调用 出现未知错误", e);
            return new ResultData(ResultState.FAIL, "HTTP异步调用 出现未知错误");
        }
    }
}
