
package org.rookit.dm.artist.factory;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("javadoc")
@BindingAnnotation
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
public @interface ArtistBiStream {

    // Intentionally empty
}
