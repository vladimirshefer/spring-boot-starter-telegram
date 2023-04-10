package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.Collections;
import java.util.List;

public class TelegramEventFacade implements EventFacade {

  private final Update update;

  TelegramMessageFacade telegramMessageFacade;

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

  @Nonnull
  @Override
  public List<FileFacade> getAttachments() {
    return Collections.emptyList();
  }

  @Override
  public String getMessengerName() {
    return "telegram";
  }

}
