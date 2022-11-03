package io.github.vladimirshefer.springbootstartertelegram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If applied to parameter of type User then will set the author of the original message.
 * If author hides the link on forward (via telegram account settings)
 * then this annotation will not work.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForwardedFrom {
}
