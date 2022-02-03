package com.github.vladimirshefer.demobot.controller;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

@TelegramController
public class StartController {

  /**
   * Simple handler to reply the text messages.
   *
   * @param messageText the text of the message, if present.
   * @return Reply message text to be sent by bot.
   */
  @RequestMapping
  public String simpleMessage(String messageText) {
    return "Thank you for your message!";
  }

  @RequestMapping("[a-z]{2}")
  public String regex(String body, Update update) {
    return body;
  }

  /**
   * Handles polls created in chat. If message contains no poll, then this handler will not be
   * invoked.
   *
   * @param poll the poll, if present.
   * @return The reply message, saying the number of options in the poll.
   */
  @RequestMapping
  public String pollCheck(Poll poll) {
    return "The poll has " + poll.getOptions().size() + " options";
  }

  /**
   * Handles messages with photos attached.
   * @param photos The photos info.
   * @param caption The string parameter is populated with photos description.
   * @return
   */
  @RequestMapping
  public void photos(List<PhotoSize> photos, String caption) {
  }

  @RequestMapping
  public List<PhotoSize> sentPhoto(List<PhotoSize> photo){
    return photo;
  }

  @RequestMapping
  public String bdbfjki(){
    return "EMPTY";
  }

  @RequestMapping
  public String up(Update u){
    return "Update";
  }

}
