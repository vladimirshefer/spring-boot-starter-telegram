package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.RequestMapping;
import io.github.vladimirshefer.spring.chatbots.core.test.ChatBotEngineBuilder;
import io.github.vladimirshefer.spring.chatbots.core.test.MockChatBot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class RequestMappingAnnotationPatternMatchesMethodFilterImplTest {

  private static class TestController {

    @RequestMapping("[A-Z]+")
    public void m1(String text){

    }

    @RequestMapping("[a-z]+")
    public void m2(String text){

    }

    @RequestMapping
    public void m3(String text){

    }

  }

  @Test
  public void testRegex() {
    MockChatBot mockChatBot = new MockChatBot();
    TestController controller = Mockito.spy(new TestController());
    new ChatBotEngineBuilder()
      .bots(mockChatBot)
      .controllers(controller)
      .methodFilters(new RequestMappingAnnotationPatternMatchesMethodFilterImpl())
      .argumentResolvers(new StringArgumentResolverImpl())
      .build();

    mockChatBot.getMockMessengerClient().sendSimpleTextMessage("hello");

    // Should not be invoked because regex does not match ("[A-Z]+" !~ "hello")
    verify(controller, never()).m1(any());
    // Should be invoked because regex matches ("[a-z]+" ~ "hello")
    verify(controller).m2("hello");
    // Should be invoked because no message regex specified
    verify(controller).m3("hello");
  }
}
