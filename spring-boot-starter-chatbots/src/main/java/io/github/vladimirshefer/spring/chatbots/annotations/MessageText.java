package io.github.vladimirshefer.spring.chatbots.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Puts the contents of the message text to the parameter value.
 *
 * If the annotation is combined with @Nullable annotation, then this parameter is considered as optional.
 * This means that handler method will be invoked, but the value of parameter will be null if no message text present in Update.
 *
 * Same as @RequestBody in Spring web.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageText {

}
