package ar.edu.unq.desapp.grupoa022022.backenddesappapi.validationAspect;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
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
@Order(0)
public class LogExecutionTimeAspectAnnotation {

	@Autowired
	private UserService userService;

	static Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation.class);

	/// CUSTOM POINTCUT////
	@Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.*.*(..))")
	public Object logExecutionTimeAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("/////// AROUND START validation token //////");
		long start = System.currentTimeMillis();
		/*
		boolean query = joinPoint.getArgs()[0].toString().contains("Bearer");//el primero es el token deberiamos buscar
		// si existe alguno que cumpla esta condicion y si es asi pedirlo y realizar la accion
		*/
		TokenDTO tokenDTO = userService.validate(joinPoint.getArgs()[0].toString());
		if (tokenDTO == null) {
			logger.info("/////// AROUND BAD-REQUEST invalid token ///////");
			Map<String, Object> body = new LinkedHashMap<>();
			body.put("timestamp: ", new Date());
			body.put("status: ", HttpStatus.BAD_REQUEST.value());
			body.put("error message: ", "invalid token");
			return new ResponseEntity<>(body, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
		}
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());
		logger.info("/////// AROUND FINISH validation token ///////");
		return proceed;
	}
}