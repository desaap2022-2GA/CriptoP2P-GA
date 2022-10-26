package ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInfoAspectCustomPointcut {
    static Logger logger = LoggerFactory.getLogger(LogExecutionTime.class);

    /// ANNOTATION POINTCUT////
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTimeAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// AROUND START  logExecutionTime annotation //////");
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());
        logger.info("/////// AROUND FINISH  logExecutionTime annotation ///////");
        return null;
    }
}
