package io.github.vladimirshefer.spring.chatbots.handler;

import io.github.vladimirshefer.spring.chatbots.annotations.RequestMapping;
import io.github.vladimirshefer.spring.chatbots.telegram.util.ReflectionUtil;
import lombok.*;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Full information about handler method - the method from bot controller.
 */
@Getter
@EqualsAndHashCode
@ToString
public class HandlerMethodDefinition {

  /**
   * The bean name of controller
   */
  private final String controllerName;

  /**
   * The method from original controller class before wrapping into proxies.
   */
  private final Method originalMethod;

  /**
   * Original bean class before wrapping into proxies.
   */
  private final Class<?> originalClass;

  /**
   * Method or proxy which should be executed instead of original method.
   */
  private final Method targetMethod;

  /**
   * The class of the controller proxy wrapper.
   */
  private final Class<?> targetClass;

  /**
   * Temp field. Will be removed and only getter will leave.
   */
  private final RequestMapping mappingAnnotation;

  /**
   * Temp field. Will be removed and only getter will leave.
   */
  private final String requestMappingValue;

  /**
   * The controller objec or proxy object wrapped aronud controller (if AOP s used.).
   */
  private final Object controller;

  public HandlerMethodDefinition(
    String controllerName,
    Method originalMethod,
    Class<?> originalClass,
    Object controller
  ) {
    this.controllerName = controllerName;
    this.originalMethod = originalMethod;
    this.originalClass = originalClass;
    this.controller = controller;

    this.targetClass = controller.getClass();
    this.targetMethod = ReflectionUtil.getSameMethod(targetClass, originalMethod);

    this.mappingAnnotation = originalMethod.getAnnotation(RequestMapping.class);
    this.requestMappingValue = mappingAnnotation.value();

    this.arguments = Collections.unmodifiableList(
      IntStream.range(0, this.getOriginalMethod().getParameters().length)
        .mapToObj(index -> new HandlerArgumentDefinition(this, index))
        .collect(Collectors.toList())
    );
  }

  @Getter
  private final List<HandlerArgumentDefinition> arguments;

  public HandlerArgumentDefinition getArgument(int argumentIndex) {
    return this.getArguments().get(argumentIndex);
  }

  public static HandlerMethodDefinition of(
    String controllerName,
    Class<?> originalClass,
    Object controller,
    Method originalMethod
  ) {
    return new HandlerMethodDefinition(controllerName, originalMethod, originalClass, controller);
  }

}
