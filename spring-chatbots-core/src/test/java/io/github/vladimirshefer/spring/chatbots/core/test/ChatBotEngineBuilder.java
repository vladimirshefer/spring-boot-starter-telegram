package io.github.vladimirshefer.spring.chatbots.core.test;

import io.github.vladimirshefer.spring.chatbots.core.api.ChatBot;
import io.github.vladimirshefer.spring.chatbots.core.engine.ControllerInvocationArgumentsResolver;
import io.github.vladimirshefer.spring.chatbots.core.engine.ControllerInvocationMethodsResolver;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.core.engine.MappingDefinitionsManager;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.ArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.MethodFilter;

import java.util.Arrays;
import java.util.List;

public class ChatBotEngineBuilder {

  private List<ChatBot> bots;
  private List<Object> controllers;
  private List<MethodFilter> methodFilters;
  private List<ArgumentResolver> argumentResolvers;

  public ChatBotEngineBuilder bots(ChatBot... bots) {
    this.bots = Arrays.asList(bots);
    return this;
  }

  public ChatBotEngineBuilder controllers(Object... controllers) {
    this.controllers = Arrays.asList(controllers);
    return this;
  }

  public ChatBotEngineBuilder methodFilters(MethodFilter... methodFilters) {
    this.methodFilters = Arrays.asList(methodFilters);
    return this;
  }

  public ChatBotEngineBuilder argumentResolvers(ArgumentResolver... argumentResolvers) {
    this.argumentResolvers = Arrays.asList(argumentResolvers);
    return this;
  }

  public void build() {
    MappingDefinitionsManager mappingDefinitionsManager = new MappingDefinitionsManager(
      new ControllerInvocationMethodsResolver(this.methodFilters)
    );

    controllers.forEach(controller -> mappingDefinitionsManager.registerController(
      controller.getClass().getSimpleName(),
      controller.getClass(),
      controller
    ));

    ControllerInvocationArgumentsResolver controllerInvocationArgumentsResolver =
      new ControllerInvocationArgumentsResolver(argumentResolvers);

    EventListener eventListener =
      new EventListener(mappingDefinitionsManager, controllerInvocationArgumentsResolver);

    bots.forEach(bot -> bot.init(eventListener));
  }
}
