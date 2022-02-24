package io.github.vladimirshefer.springbootstartertelegram.handler;

import io.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.ReflectionUtil;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Full information about handler method - the method from bot controller.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HandlerMethodDefinition {

  /**
   * The bean name of controller
   */
  private String controllerName;

  /**
   * The method from original controller class before wrapping into proxies.
   */
  private Method originalMethod;

  /**
   * Original bean class before wrapping into proxies.
   */
  private Class<?> originalClass;

  /**
   * Method or proxy which should be executed instead of original method.
   */
  private Method targetMethod;

  /**
   * The class of the controller proxy wrapper.
   */
  private Class<?> targetClass;

  /**
   * Temp field. Will be removed and only getter will leave.
   */
  private RequestMapping mappingAnnotation;

  /**
   * Temp field. Will be removed and only getter will leave.
   */
  private String requestMappingValue;

  /**
   * The controller objec or proxy object wrapped aronud controller (if AOP s used.).
   */
  private Object controller;


  public static HandlerMethodDefinition of(
    String controllerName,
    Class<?> originalClass,
    Object controller,
    Method originalMethod
  ) {
    RequestMapping mappingAnnotation = originalMethod.getAnnotation(RequestMapping.class);

    Class<?> targetClass = controller.getClass();
    return HandlerMethodDefinition.builder()
      .controller(controller)
      .originalClass(originalClass)
      .originalMethod(originalMethod)
      .targetClass(targetClass)
      .targetMethod(ReflectionUtil.getSameMethod(targetClass, originalMethod))
      .mappingAnnotation(mappingAnnotation)
      .requestMappingValue(mappingAnnotation.value())
      .controllerName(controllerName)
      .build();
  }

}
