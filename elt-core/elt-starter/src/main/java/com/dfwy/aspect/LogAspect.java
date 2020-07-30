package com.dfwy.aspect;


import com.dfwy.common.annotation.ClearSession;
import com.dfwy.common.annotation.Log;
import com.dfwy.common.annotation.LogIgnore;
import com.dfwy.common.domain.OperatinLog;
import com.dfwy.common.utils.ELTUtils;
import com.dfwy.common.utils.Result;
import com.dfwy.database.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Resource
    private LogService logService;

    /**
     * PointCut
     */
    @Pointcut("@annotation(com.dfwy.common.annotation.Log)")
    public void logPointCut() {
    }
    @Around("logPointCut()")
    public Object  around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable  {
        //1.修改原来的参数
        long start = System.currentTimeMillis();
        OperatinLog operatinLog = new OperatinLog().setStatus(0);
        Object[] args  = proceedingJoinPoint.getArgs();
        Object result = null ;
        try {
            result =  proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.error("系统异常",throwable);
            operatinLog.setStatus(1);
            throw throwable;
        }
        String businessId = ELTUtils.getELTOperationParam();
        Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
        ClearSession clearSession = method.getAnnotation(ClearSession.class);
        if(clearSession !=null){
            //清除参数缓存
            ELTUtils.cleanELTOperationParam();
        }
        operatinLog.setCostTime(System.currentTimeMillis()-start)
                .setApi(method.getAnnotation(Log.class).api())
                .setBusinessId(businessId);
        logService.insertLog(operatinLog,isIgnore(method)?"":args[0],result);
        return result;
    }
    /**
     * @description : 日志是否忽略参数
     * @author zuoqiwen
     * @date 2020/6/16 15:00
     * @param [method]
     * @return boolean
     */
    public boolean isIgnore(Method method){
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if(parameterAnnotations != null && parameterAnnotations.length > 0 && parameterAnnotations[0].length>0){
            for (Annotation annotation :parameterAnnotations[0]) {
                if(annotation instanceof LogIgnore){
                    return true;
                }
            }
        }
        return false;
    }
}
