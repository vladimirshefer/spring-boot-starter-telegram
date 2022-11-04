package io.github.vladimirshefer.demobot.controller;

import io.github.vladimirshefer.spring.chatbots.annotations.RequestMapping;
import io.github.vladimirshefer.spring.chatbots.annotations.TelegramController;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.List;

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

  /**
   * Handle any update with any message
   * @return message;
   */
  @RequestMapping
  public String catchAllHandlers(){
    return "EMPTY";
  }

  /**
   * Handle any message. Receive all information about message to parameter update
   * @param update Receive all information about message
   * @return UserName, who sent the message
   */
  @RequestMapping
  public String getUpdate(Update update){
    return update.getMessage().getFrom().getUserName();
  }

}
