package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class TelegramMessageFacade implements MessageFacade {

  private final Message message;

  @NotNull
  @Override
  public String getMessageText() {
    if (message.getText() != null) return message.getText();
    if (message.getCaption() != null) return message.getCaption();
    return null;
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

  @Nullable
  @Override
  public String getChatId() {
    return message.getChatId().toString();
  }

  @Nullable
  @Override
  public List<MessageFacade> getReferencedMessages() {
    Message replyToMessage = message.getReplyToMessage();

    if (replyToMessage != null) {
      return Arrays.asList(new TelegramMessageFacade(replyToMessage));
    }

    return null;
  }

  @NotNull
  @Override
  public Object getSource() {
    return message;
  }
}
