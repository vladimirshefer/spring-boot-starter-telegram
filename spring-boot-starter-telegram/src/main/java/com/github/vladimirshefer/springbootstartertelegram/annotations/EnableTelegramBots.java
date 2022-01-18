package com.github.vladimirshefer.springbootstartertelegram.annotations;

import com.github.vladimirshefer.springbootstartertelegram.SpringBootStarterTelegramConfiguration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.context.annotation.Import;

@Import(SpringBootStarterTelegramConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTelegramBots {

}
