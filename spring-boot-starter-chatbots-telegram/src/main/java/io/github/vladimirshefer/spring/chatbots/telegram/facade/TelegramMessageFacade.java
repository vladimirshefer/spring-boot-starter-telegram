package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class TelegramMessageFacade implements MessageFacade {

  private final Message message;

  @NotNull
  @Override
  public String getMessageText() {
    return message.getText();
  }

  @NotNull
  @Override
  public String getId() {
    return message.getMessageId().toString();
  }

  @Nullable
  @Override
  public UserFacade getAuthor() {
    User author = message.getFrom();
    if (author == null) return null;

    return new TelegramUserFacade(author);
  }

  @NotNull
  @Override
  public Object getSource() {
    return message;
  }
}
