package io.github.vladimirshefer.spring.chatbots.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.spec.MessageCreateSpec;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.discord.facade.DiscordMessageCreateEventFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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

    User self = gateway.getSelf().block();

    gateway.on(MessageCreateEvent.class)
      .subscribe(event -> {
        try {
          if (self.getId().equals(event.getMessage().getAuthor().map(User::getId).orElse(null))){
            return;
          }
          List<Object> results = eventListener.handleMessage(new DiscordMessageCreateEventFacade(event));
          List<String> responseTexts = results.stream().filter(it -> it instanceof String).map(it -> (String) it).collect(Collectors.toList());
          for (String responseText : responseTexts) {
            sendSimpleReply(event, responseText);
          }
        } catch (Throwable throwable) {
          log.error("Could not handle event", throwable);
        }
      });

    return gateway;
  }

  private static void sendSimpleReply(MessageCreateEvent event, String responseText) {
    if (responseText != null) {
      MessageCreateSpec responseMessage = sendSimpleMessage(event, responseText);
      event.getMessage().getChannel()
        .flatMap(channel -> channel.createMessage(responseMessage))
        .subscribe();
    }
  }

  private static MessageCreateSpec sendSimpleMessage(MessageCreateEvent event, String result) {
    MessageCreateSpec responseMessage = MessageCreateSpec.builder()
      .content(result)
      .messageReference(event.getMessage().getId())
      .build();
    return responseMessage;
  }

}
