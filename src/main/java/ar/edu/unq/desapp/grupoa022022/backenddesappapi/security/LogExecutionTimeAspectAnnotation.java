package ar.edu.unq.desapp.grupoa022022.backenddesappapi.security;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.TokenDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
@Aspect
@Component
public class LogExecutionTimeAspectAnnotation {

	@Autowired
	private UserService userService;

	static Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation.class);

	/// CUSTOM POINTCUT////
	@Around("execution(* ar.edu.unq.desapp.grupoa022022.backenddesappapi.webservice.*.*(..))")
	public Object logExecutionTimeAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("/////// AROUND START  logExecutionTime annotation //////");
		long start = System.currentTimeMillis();
		/*
		boolean query = joinPoint.getArgs()[0].toString().contains("Bearer");//el primero es el token deberiamos buscar
		// si existe alguno que cumpla esta condicion y si es asi pedirlo y realizar la accion
		*/

		TokenDTO tokenDTO = userService.validate(joinPoint.getArgs()[0].toString());
		if (tokenDTO == null) {
			logger.info("/////// AROUND BAD-REQUEST invalid token ///////");
			return ResponseEntity.badRequest().build();
		}
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms " + proceed.toString());
		logger.info("/////// AROUND FINISH  logExecutionTime annotation ///////");
		return proceed;
	}
}