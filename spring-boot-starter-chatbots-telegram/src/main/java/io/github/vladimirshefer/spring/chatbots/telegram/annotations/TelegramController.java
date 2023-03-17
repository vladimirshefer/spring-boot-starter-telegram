package io.github.vladimirshefer.spring.chatbots.telegram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.BotController;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.Messenger;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Messenger("telegram")
@BotController
public @interface TelegramController {

}
