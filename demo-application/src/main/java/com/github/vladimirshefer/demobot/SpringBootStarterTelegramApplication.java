package com.github.vladimirshefer.demobot;

import com.github.vladimirshefer.springbootstartertelegram.annotations.EnableTelegramBots;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTelegramBots
@SpringBootApplication
public class SpringBootStarterTelegramApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootStarterTelegramApplication.class, args);
  }

}
