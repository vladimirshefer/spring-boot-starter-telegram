package io.github.vladimirshefer.springbootstartertelegram.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class HandlerArgumentDefinition {

  private final HandlerMethodDefinition handlerMethodDefinition;
  private final int parameterIndex;

  @Getter
  private final List<Class<? extends Annotation>> annotations;

  @Getter
  private final Class<?> type;

  public HandlerArgumentDefinition(HandlerMethodDefinition handlerMethodDefinition, int parameterIndex) {
    this.handlerMethodDefinition = handlerMethodDefinition;
    this.parameterIndex = parameterIndex;
    this.type = handlerMethodDefinition
      .getOriginalMethod()
      .getParameterTypes()[parameterIndex];
    this.annotations = Collections.unmodifiableList(
      Arrays
        .stream(
          handlerMethodDefinition
            .getOriginalMethod()
            .getParameterAnnotations()[parameterIndex]
        )
        .map(Annotation::annotationType)
        .collect(Collectors.toList())
    );
  }
}
