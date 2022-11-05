package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

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

  @NotNull
  @Override
  public Object getSource() {
    return message;
  }
}
