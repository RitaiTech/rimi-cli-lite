package rimi.ritsai.cli.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Documented
@Retention(RUNTIME)
@Target({ TYPE })
@Component
public @interface Command {

	String name() default ""; // the command name

	String description() default "";

	String manual() default "";

	String usage() default "";

	String group() default "";

}
