
package org.rookit.dm.play.factory;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@SuppressWarnings("javadoc")
@Retention(RUNTIME)
@BindingAnnotation
@Target({FIELD, METHOD, PARAMETER})
public @interface PlaylistBiStream {
    
    // intentionally empty
}
