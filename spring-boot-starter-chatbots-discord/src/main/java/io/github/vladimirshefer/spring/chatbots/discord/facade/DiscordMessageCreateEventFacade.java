package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class DiscordMessageCreateEventFacade implements EventFacade {

  @Getter
  private final MessageCreateEvent source;

  @Nullable
  @Override
  public MessageFacade getMessage() {
    return new DiscordMessageFacade(source.getMessage());
  }

  @Nonnull
  @Override
  public List<FileFacade> getAttachments() {
    return Collections.emptyList();
  }

  @Override
  public String getMessengerName() {
    return "discord";
  }

}
