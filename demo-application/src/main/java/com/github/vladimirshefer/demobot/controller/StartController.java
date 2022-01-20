package com.github.vladimirshefer.demobot.controller;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

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

  @RequestMapping
  public String pollCheck(Poll poll){
    return "done!";
  }

  @RequestMapping
  public String sentPhoto(PhotoSize photo){
    return "Get Photo";
  }

  @RequestMapping(regex = "[a-zA-z]*")
  public  String getMessageAndPhoto(@MessageBody String body, PhotoSize photo){
    return "I got it";

  }
}
