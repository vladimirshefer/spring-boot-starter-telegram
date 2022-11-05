package io.github.vladimirshefer.spring.chatbots.core.messaging.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Main annotation applied to class. Marks the class as handler
 * for messages or messenger events.
 * Usage is similar to Spring Framework @Controller/@RestController annotations.
 * Methods inside the class marked as controller could be marked with @RequestMapping
 * annotation to enable them as message handlers.
 */
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
  String[] messengers() default {};
}
