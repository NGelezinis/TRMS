package com.revature.trms.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.revature.trms.beans.User;

import io.javalin.http.Context;

@Component
@Aspect
public class SecurityAspect {
	
	@Around("authorizedHook()")
	public void authorizedUser(ProceedingJoinPoint pjp) throws Throwable {
		if (pjp.getArgs().length == 0) {
			throw new Exception("Invalid arguments to adviced method "+pjp.getSignature());
		}
		Context ctx = (Context) pjp.getArgs()[0];
		User user = (User) ctx.sessionAttribute("User");
		if(user != null) {
			pjp.proceed();
			return;
		} else {
			ctx.status(401);
			return;
		}
	}
	
	@Pointcut("@annotation(com.revature.trms.aspects.Authorized)")
	public void authorizedHook() { /* Empty method for Hook */ }

}
