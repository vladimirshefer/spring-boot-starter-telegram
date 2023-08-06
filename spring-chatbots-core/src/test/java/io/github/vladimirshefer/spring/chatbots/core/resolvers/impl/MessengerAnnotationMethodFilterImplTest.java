package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.Messenger;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.RequestMapping;
import io.github.vladimirshefer.spring.chatbots.core.test.ChatBotEngineBuilder;
import io.github.vladimirshefer.spring.chatbots.core.test.MockChatBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class MessengerAnnotationMethodFilterImplTest {

  private static class TestController {

    @Messenger("nonexisting")
    @RequestMapping
    public void m1(String text){

    }

    @Messenger("mock")
    @RequestMapping
    public void m2(String text){

    }

    @RequestMapping
    public void m3(String text){

    }

  }

  @Test
  public void testMessengerAnnotationWorks() {
    MockChatBot mockChatBot = new MockChatBot();
    TestController controller = Mockito.spy(new TestController());
    new ChatBotEngineBuilder()
      .bots(mockChatBot)
      .controllers(controller)
      .methodFilters(new MessengerAnnotationMethodFilterImpl())
      .argumentResolvers(new StringArgumentResolverImpl())
      .build();

    mockChatBot.getMockMessengerClient().sendSimpleTextMessage("hello");

    // Should not be invoked because target messenger does not match ("mock" != "nonexisting")
    verify(controller, never()).m1(any());
    // Should be invoked because target messenger matches ("mock")
    verify(controller).m2("hello");
    // Should be invoked because no target messenger specified
    verify(controller).m3("hello");
  }

}
