package ar.edu.unq.desapp.grupoa022022.backenddesappapi.aspects.validationToken;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.TokenService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
@Order(1)
public class JWTValidateTokenAspect {

	@Autowired
	private TokenService tokenService;

	static Logger logger = LoggerFactory.getLogger(JWTValidateTokenAspect.class);

	/// CUSTOM POINTCUT////
	@Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.*.*(..))")
	public Object JWTValidateToken(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("/////// START VALIDATION TOKEN //////");
		long startTime = System.currentTimeMillis();
		TokenDTO tokenDTO = tokenService.validate(joinPoint.getArgs()[0].toString());

		if (tokenDTO == null) {
			logger.info("/////// BAD-REQUEST INVALID TOKEN ///////");
			Map<String, Object> body = new LinkedHashMap<>();
			body.put("timestamp: ", new Date());
			body.put("status: ", HttpStatus.BAD_REQUEST.value());
			body.put("error message: ", "invalid token");
			return new ResponseEntity<>(body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
		}
		logger.info("/////// FINISH VALIDATION TOKEN ///////");

		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - startTime;
		logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());

		return proceed;
	}
}