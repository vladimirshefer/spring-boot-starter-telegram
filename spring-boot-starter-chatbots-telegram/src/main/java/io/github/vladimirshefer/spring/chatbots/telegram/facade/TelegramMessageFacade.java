package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

@RequiredArgsConstructor
public class TelegramMessageFacade implements MessageFacade {

  private final Message message;
  private final Function<String, Callable<byte[]>> fileGetter;

  @Nonnull
  @Override
  public String getMessageText() {
    if (message.getText() != null) return message.getText();
    if (message.getCaption() != null) return message.getCaption();
    return null;
  }

  @Nonnull
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

  @Nonnull
  @Override
  public List<FileFacade> getAttachments() {
    FileFacade file = new TelegramFileFacade(message.getDocument().getFileId(), fileGetter);

    return Collections.singletonList(file);
  }

  @Nonnull
  @Override
  public List<MessageFacade> getReferencedMessages() {
    Message replyToMessage = message.getReplyToMessage();

    if (replyToMessage != null) {
      return Collections.singletonList(new TelegramMessageFacade(replyToMessage, fileGetter));
    }

    return Collections.emptyList();
  }

  @NotNull
  @Override
  public Object getSource() {
    return message;
  }
}
