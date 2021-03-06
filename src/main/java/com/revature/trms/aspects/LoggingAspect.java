package com.revature.trms.aspects;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	private Logger log;
	
	@Around("everything()")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		// ProceedingJoinPoint - Object representation of the method being called.
		Object result = null;
		log = LogManager.getLogger(pjp.getTarget().getClass());
		log.trace("Method with signature: "+pjp.getSignature());
		log.trace("with arguments: "+Arrays.toString(pjp.getArgs()));
		try {
			result = pjp.proceed(); // proceed will call the method with the arguments given to it
			// If you want to change the arguments, you can pass in a new array of arguments.
			// result = pjp.proceed(arr);
		}  catch(Throwable t) {
			log.error("Method threw exception: "+t);
			for(StackTraceElement s : t.getStackTrace()) {
				log.warn(s);
			}
			if(t.getCause() != null) {
				Throwable t2 = t.getCause();
				log.error("Method threw wrapped exception: "+t2);
				for(StackTraceElement s : t2.getStackTrace()) {
					log.warn(s);
				}
			}
			throw t; // we don't want our proxy to have the side-effect of
			// stopping the exception from being thrown (it needs to be handled elsewhere)
			// but we do want to log it for ourselves.
		}
		log.trace("Method returning with: "+result);
		return result;
	}
	
	// hook - a method that only exists as the target for an annotation
	@Pointcut("execution( * com.revature..*(..) )")
	private void everything() { /* Empty method for hook */ }
}
