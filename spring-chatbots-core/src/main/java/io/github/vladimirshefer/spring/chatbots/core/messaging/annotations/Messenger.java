package io.github.vladimirshefer.spring.chatbots.core.messaging.annotations;

import java.lang.annotation.*;

/**
 * Restricts usage only for specific messenger.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Messenger {
  /**
   * List of messenger names.
   * Names are lowercase and in snake-case (i.e. telegram, discord, custom_messenger).
   * Empty list means all.
   */
  String[] value() default {};
}
