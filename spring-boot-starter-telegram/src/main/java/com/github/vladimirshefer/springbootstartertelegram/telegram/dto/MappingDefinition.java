package com.github.vladimirshefer.springbootstartertelegram.telegram.dto;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.scan.ReflectionUtils;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MappingDefinition {

  private String controllerName;
  private Method originalMethod;
  private Class<?> originalClass;
  private Method targetMethod;
  private Class<?> targetClass;
  private RequestMapping mappingAnnotation;
  private String requestMappingValue;
  private Object controller;

  public static MappingDefinition of(String controllerName, Class<?> originalClass,
      Object controller, Method originalMethod) {
    RequestMapping mappingAnnotation = originalMethod.getAnnotation(RequestMapping.class);

    Class<?> targetClass = controller.getClass();
    return MappingDefinition.builder()
        .controller(controller)
        .originalClass(originalClass)
        .originalMethod(originalMethod)
        .targetClass(targetClass)
        .targetMethod(ReflectionUtils.getSameMethod(targetClass, originalMethod))
        .mappingAnnotation(mappingAnnotation)
        .requestMappingValue(mappingAnnotation.value())
        .controllerName(controllerName)
        .build();
  }

}
