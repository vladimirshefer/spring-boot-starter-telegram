package io.github.vladimirshefer.spring.chatbots.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.BotController;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@BotController(messengers = {"telegram"})
public @interface TelegramController {

}
