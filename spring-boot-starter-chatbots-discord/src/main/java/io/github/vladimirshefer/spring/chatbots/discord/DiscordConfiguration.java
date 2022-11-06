package io.github.vladimirshefer.spring.chatbots.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.discord.facade.DiscordMessageCreateEventFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordConfiguration {

  @Value("${spring.chatbots.discord.token}")
  String token;

  @Bean
  public GatewayDiscordClient discordBot(
    EventListener eventListener
  ) {
    final DiscordClient client = DiscordClient.create(token);
    final GatewayDiscordClient gateway = client.login().block();

    gateway.on(MessageCreateEvent.class)
      .subscribe(event -> eventListener.handleMessage(new DiscordMessageCreateEventFacade(event)));

    gateway.onDisconnect().block();

    return gateway;
  }

}
