package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.api.ChatBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatBotInitializer {

  private final List<ChatBot> chatBots;
  private final io.github.vladimirshefer.spring.chatbots.core.engine.EventListener eventListener;

  @EventListener(ContextRefreshedEvent.class)
  public void init() {
    chatBots.stream()
      .filter(ChatBot::autoInit)
      .forEach(chatBot -> chatBot.init(eventListener));
  }

}
