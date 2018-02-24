package org.rookit.dm.artist.factory;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@SuppressWarnings("javadoc")
@BindingAnnotation
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
public @interface ArtistBiStream {}
