package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.validation_token;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils.ResponseEntityBadRequest;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
@Component
@Order(1)
public class JWTValidateTokenAspect {

    @Autowired
    private TokenService tokenService;

    static Logger logger = LoggerFactory.getLogger(JWTValidateTokenAspect.class);

    @Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.*.*(..))")
    public Object jwtValidateToken(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// START VALIDATION TOKEN ");
        TokenDTO tokenDTO = tokenService.validate(joinPoint.getArgs()[0].toString());

        if (tokenDTO == null) {
            logger.info(" BAD-REQUEST INVALID TOKEN ///////");
            return (new ResponseEntityBadRequest("invalid token")).getResponseEntity();
        }
        logger.info(" FINISH VALIDATION TOKEN ///////");
        return joinPoint.proceed();
    }
}