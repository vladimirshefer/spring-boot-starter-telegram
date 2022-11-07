package io.github.vladimirshefer.spring.chatbots.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.discord.facade.DiscordMessageCreateEventFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan
@Slf4j
public class DiscordConfiguration {

  @Bean
  public GatewayDiscordClient gatewayDiscordClient(
    @Value("${spring.chatbots.discord.bot.token:}")
    String token,
    EventListener eventListener
  ) {
    GatewayDiscordClient gateway = DiscordClient.create(token)
      .gateway()
      .login()
      .block();

    gateway.on(MessageCreateEvent.class)
      .subscribe(event -> {
        try {
          List<Object> results = eventListener.handleMessage(new DiscordMessageCreateEventFacade(event));

        } catch (Throwable throwable) {
          log.error("Could not handle event", throwable);
        }
      });

//    gateway.getChannelById(Snowflake.of())

    return gateway;
  }

}
