
package org.rookit.dm.album.factory;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("javadoc")
@Retention(RUNTIME)
@BindingAnnotation
@Target({FIELD, METHOD, PARAMETER})
public @interface AlbumBiStream {

    // Intentionally empty

}
