package com.github.vladimirshefer.demobot.controller;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramController
public class StartController {

  @RequestMapping("/start")
  public String start(@MessageBody String body, Update update) {
    return "Hello, world!";
  }

  @RequestMapping(regex = "[a-z]{2}")
  public String blabla(@MessageBody String body, Update update){
    return body;
  }
}
