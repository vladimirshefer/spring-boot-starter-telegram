package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.Nullable;

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

  @Override
  public String getMessengerName() {
    return "discord";
  }

}
