package com.artlite.pluginmanagerapi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Denotes that a parameter, field or method return value can never be null.
 * <p>
 * This is a marker annotation and it has no specific attributes.
 *
 * @paramDoc This value must never be {@code null}.
 * @returnDoc This value will never be {@code null}.
 * @hide
 */
@Retention(SOURCE)
@Target({METHOD, PARAMETER, FIELD})
public @interface NonNull {
}
