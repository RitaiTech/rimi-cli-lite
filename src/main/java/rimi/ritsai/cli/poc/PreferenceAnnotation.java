package rimi.ritsai.cli.poc;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;

@Retention(RUNTIME)
@Repeatable(Preferences.class)
public @interface PreferenceAnnotation {

	String name() default "";

	String description() default "";

	String defaultValue() default "";

	boolean required() default false;

}
