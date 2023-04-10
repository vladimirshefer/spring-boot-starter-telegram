package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

@RequiredArgsConstructor
public class DiscordMessageFacade implements MessageFacade {

  private final Message message;

  @Nullable
  @Override
  public Object getSource() {
    return message;
  }

  @Nullable
  @Override
  public String getMessageText() {
    return message.getContent();
  }

  @Nullable
  @Override
  public String getId() {
    return message.getId().asString();
  }

  @Nullable
  @Override
  public UserFacade getAuthor() {
    User author = message.getAuthor().orElse(null);
    return new DiscordUserFacade(author);
  }

  @Nullable
  @Override
  public String getChatId() {
    return message.getChannelId().asString();
  }

  @Nonnull
  @Override
  public List<FileFacade> getAttachments() {
    return null;
  }

  @Override
  public List<MessageFacade> getReferencedMessages() {
    Message referencedMessage = message.getReferencedMessage().orElse(null);

    if (referencedMessage == null) {
      return Collections.emptyList();
    }

    DiscordMessageFacade messageFacade = new DiscordMessageFacade(referencedMessage);

    return singletonList(messageFacade);
  }

}
