package jackie.learn.daily;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= {ElementType.METHOD,ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	public static enum ProcessType{EXECUTE,JUMP};
	
	public ProcessType process() default ProcessType.EXECUTE;
}
