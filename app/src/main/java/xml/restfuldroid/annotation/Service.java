package xml.restfuldroid.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zenbook on 16/08/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String value() default "";
    String method() default "GET";
    Class<?> response() default Void.class;
}
