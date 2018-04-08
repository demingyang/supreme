package com.richgo.aspect;

import com.richgo.annotation.SensitiveParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Date: 2017/6/20
 * Time: 13:33
 * User: Kayle
 */
public class Aspect {

    private final static Logger logger = LoggerFactory.getLogger(Aspect.class);

    private final static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Object methodExecuteTime (ProceedingJoinPoint joinPoint) {
        Object object;
        Date startDate = new Date();
        long start = System.currentTimeMillis();
        try {
            //访问目标方法的参数：
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("throwable ",throwable);
            return null;
        }
        long end = System.currentTimeMillis();
        String time = formatExecuteTime(end - start);

        Signature signature = joinPoint.getSignature();
        MethodSignature ms = (MethodSignature)signature;
        Method method = ms.getMethod();
        Class<?>[] paramTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramTypes.length; i++) {
            Annotation[] paramAnnotations = parameterAnnotations[i];
            for (Annotation annotation: paramAnnotations) {
                if (annotation instanceof SensitiveParam && ((SensitiveParam) annotation).required() && args[i] != null) {
                    args[i] = ((SensitiveParam) annotation).type().unSensitive(args[i].toString());
                }
            }
        }
        logger.info(String.format("方法签名:%s, 入参:%s, 开始执行时间:%s, 执行时间:%s", signature.toString(), Arrays.toString(args), sdf.format(startDate), time));
        return object;
    }

    private String formatExecuteTime(long executeTime) {
        long min = (executeTime % 3600000) / 60000;
        long sec = (executeTime % 60000) / 1000;
        long msec = executeTime % 10000;
        StringBuilder sb = new StringBuilder();
        if (min > 0) {
            sb.append(min).append("m ");
        }
        if (sec > 0) {
            sb.append(sec).append("s ");
        }
        sb.append(msec).append("ms");
        return sb.toString();
    }
}
