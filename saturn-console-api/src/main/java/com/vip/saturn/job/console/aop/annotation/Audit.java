package com.vip.saturn.job.console.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {

	// API name
	String name() default "";

	// ALLOWED TYPE
	AuditType type() default AuditType.WEB;
}