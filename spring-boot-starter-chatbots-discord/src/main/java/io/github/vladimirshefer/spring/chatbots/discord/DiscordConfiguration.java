package io.github.vladimirshefer.spring.chatbots.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.discord.facade.DiscordMessageCreateEventFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class DiscordConfiguration {

  @Value("${spring.chatbots.discord.bot.token:}")
  String token;

  @Bean
  public GatewayDiscordClient discordBot(
    EventListener eventListener
  ) {
    if (token.isEmpty()) {
      return null;
    }
    final DiscordClient client = DiscordClient.create(token);
    final GatewayDiscordClient gateway = client.login().block();

    gateway.on(MessageCreateEvent.class)
      .subscribe(event -> {
        List<Object> response = eventListener.handleMessage(new DiscordMessageCreateEventFacade(event));
        log.info("Response to the message: " + response);
      });

    return gateway;
  }

}
