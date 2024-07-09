package com.gpmall.commons.lock.aspect;


import com.gpmall.commons.lock.DistributedLock;
import com.gpmall.commons.lock.annotation.CustomerLock;
import com.gpmall.commons.lock.extension.ExtensionLoader;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@EnableAspectJAutoProxy
public class DistributedLockAspect {


    public static final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);


    @Pointcut("@annotation(com.gpmall.commons.lock.annotation.CustomerLock)")
    public void distributedLockPointCut(){
    }


    @Around("distributedLockPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final String lockKey = getLockKey(method, joinPoint.getArgs());
        return startLock(lockKey, joinPoint, method);
    }

    private Object startLock(String lockKey, ProceedingJoinPoint joinPoint, Method method) throws Throwable{

        CustomerLock annotation =  method.getAnnotation(CustomerLock.class);
        boolean tryLock = annotation.tryLock();
        if (tryLock){
            return tryLock(joinPoint, annotation, lockKey);
        } else {
            return lock(joinPoint, annotation, lockKey);
        }

    }

    private Object lock(ProceedingJoinPoint joinPoint, CustomerLock annotation, String lockKey) throws Throwable {

        int leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        String type = annotation.lockType();

        DistributedLock distributedLock = getByType(type);
        try {
            distributedLock.lock(lockKey, timeUnit, leaseTime);
            return joinPoint.proceed();
        }finally {
            distributedLock.unlock(lockKey);
        }

    }

    private DistributedLock getByType(String type) {
        return (DistributedLock) ExtensionLoader.getExtensionLoader(DistributedLock.class).getExtension(type);
    }

    private Object tryLock(ProceedingJoinPoint joinPoint, CustomerLock annotation, String lockKey) throws Throwable{

        int leaseTime = annotation.leaseTime();
        int waitTime = annotation.waitTime();
        TimeUnit timeUnit = annotation.timeUnit();
        String type = annotation.lockType();
        DistributedLock distributedLock = getByType(type);

        try {
            if (waitTime == 0){
                if (distributedLock.tryLock(lockKey)){
                    return joinPoint.proceed();
                }
            } else {
                distributedLock.tryLock(lockKey, timeUnit, waitTime, leaseTime);
                return joinPoint.proceed();
            }
        } finally {
            distributedLock.unlock(lockKey);
        }

        return null;

    }


    /**
     * 生成分布式锁key
     * @param method
     * @param args
     * @return
     */
    private String getLockKey(Method method, Object[] args) {

        Objects.requireNonNull(method);
        CustomerLock annotation = method.getAnnotation(CustomerLock.class);
        String lockKey = parseKey(annotation.lockKey(), method, args),
                separator = annotation.separator(),
                prefix = annotation.lockPrefix(),
                suffix = annotation.lockSuffix();

        if (StringUtils.isBlank(lockKey)){
            throw new IllegalArgumentException(String.format("lock [%s] is error", lockKey));
        }
        StringBuilder keyGenerator = new StringBuilder();
        if (StringUtils.isNotBlank(prefix)){
            keyGenerator.append(prefix)
                    .append(separator);
        }
        keyGenerator.append(lockKey.trim());
        if (StringUtils.isNotBlank(suffix)){
            keyGenerator.append(separator)
                    .append(suffix);
        }
        lockKey = keyGenerator.toString().trim();
        if (StringUtils.isBlank(lockKey)){
            throw new IllegalArgumentException("Cannot get or generate lock accurately!");
        }

        logger.info("generate lock_key [" + lockKey + "]");
        return lockKey;
    }


    /**
     * 获取缓存的key
     * @param localKey
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String localKey, Method method, Object[] args){

        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramArr = u.getParameterNames(method);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramArr.length; i++) {
            context.setVariable(paramArr[i], args[i]);
        }
        return parser.parseExpression(localKey).getValue(context, String.class);
    }


}
