package io.github.vladimirshefer.springbootstartertelegram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If applied to parameter of type String then will set the text of the message which this update is reply to.
 * If applied to parameter of type User then will set the author of the message which this update is reply to.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplyToMessage {
}
