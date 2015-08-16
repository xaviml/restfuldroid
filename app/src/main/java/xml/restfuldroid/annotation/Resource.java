package xml.restfuldroid.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zenbook on 16/08/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
    String value();
}
