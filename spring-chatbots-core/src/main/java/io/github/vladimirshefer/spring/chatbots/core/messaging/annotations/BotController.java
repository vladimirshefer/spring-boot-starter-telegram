package io.github.vladimirshefer.spring.chatbots.core.messaging.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface BotController {
  /**
   * Snake case names of messengers which are handled by this controller.
   * Examples: "telegram", "discord", "slack".
   * If empty array, then controller is considered for all messengers.
   */
  public String[] messengers() default {};
}
