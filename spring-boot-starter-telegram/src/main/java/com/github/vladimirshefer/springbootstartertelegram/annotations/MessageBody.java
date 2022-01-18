package com.github.vladimirshefer.springbootstartertelegram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Puts the contents of the message text to the parameter value.
 * Same as @RequestBody in Spring web.
 */
@Target({ElementType.PARAMETER})
public @interface MessageBody {

}
