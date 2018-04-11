package raf.kalendar.calendar_componentrs.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Event type anotated with this anotation can be can run simultaneously with other tasks in scheduler
 * 
 * 
 * 
 * @author comex
 *
 */



@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Background {
	
}
