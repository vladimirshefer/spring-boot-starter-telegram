package com.github.vladimirshefer.springbootstartertelegram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Component
public @interface TelegramController {

}
