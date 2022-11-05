package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class TelegramMessageFacade implements MessageFacade {

  private final Update update;

  @Nullable
  @Override
  public String getMessageText() {
    return update.getMessage().getText();
  }

  @Nullable
  @Override
  public String getId() {
    return null;
  }

}
