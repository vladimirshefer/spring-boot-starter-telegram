package io.github.vladimirshefer.springbootstartertelegram.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class HandlerArgumentDefinition {
  @Getter
  private final HandlerMethodDefinition handlerMethodDefinition;
  @Getter
  private final int parameterIndex;

  @Getter
  private final List<Class<?>> annotations =
    Collections.unmodifiableList(
      Arrays
        .stream(
          handlerMethodDefinition
            .getOriginalMethod()
            .getParameterAnnotations()[parameterIndex]
        )
        .map(Annotation::annotationType)
        .collect(Collectors.toList())
    );

  @Getter
  private final Class<?> type =
    handlerMethodDefinition
      .getOriginalMethod()
      .getParameterTypes()[parameterIndex];

}
