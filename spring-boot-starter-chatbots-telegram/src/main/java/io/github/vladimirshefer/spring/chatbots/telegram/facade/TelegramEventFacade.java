package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.function.Function;

@ToString
public class TelegramEventFacade implements EventFacade {

  private final Update update;
  private final TelegramMessageFacade telegramMessageFacade;

  public TelegramEventFacade(Update update, Function<String, Callable<byte[]>> fileGetter) {
    this.update = update;
    telegramMessageFacade = new TelegramMessageFacade(update.getMessage(), fileGetter);
  }

  @NotNull
  @Override
  public Update getSource() {
    return update;
  }

  @Nullable
  @Override
  public MessageFacade getMessage() {
    return telegramMessageFacade;
  }

  @Override
  public String getMessengerName() {
    return "telegram";
  }

}
