package com.github.vladimirshefer.demobot.controller;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;

@TelegramController
public class StartController {

  @RequestMapping
  public String start(@MessageBody String body) {
    return "Hello, world!";
  }

}
