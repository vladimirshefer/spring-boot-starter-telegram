package io.github.vladimirshefer.spring.chatbots.core.engine;

import lombok.Getter;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class HandlerArgumentDefinition {

  private final HandlerMethodDefinition handlerMethodDefinition;

  private final int parameterIndex;

  @Getter
  private final List<Class<? extends Annotation>> annotations;

  @Getter
  private final Class<?> type;

  @Getter
  private final Type genericType;

//  private final MethodParameter springMethodPatameter;

  public HandlerArgumentDefinition(HandlerMethodDefinition handlerMethodDefinition, int parameterIndex) {
    this.handlerMethodDefinition = handlerMethodDefinition;
    this.parameterIndex = parameterIndex;
    this.type = handlerMethodDefinition
      .getOriginalMethod()
      .getParameterTypes()[parameterIndex];
    this.genericType = handlerMethodDefinition.getOriginalMethod().getGenericParameterTypes()[parameterIndex];
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

  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    Annotation[] parameterAnnotations = handlerMethodDefinition
      .getOriginalMethod()
      .getParameterAnnotations()[parameterIndex];

    return Arrays.stream(parameterAnnotations)
      .anyMatch(annotation -> annotation.annotationType().equals(annotationClass));
  }

  public boolean hasAnnotation(String annotationClassName) {
    Annotation[] parameterAnnotations = handlerMethodDefinition
      .getOriginalMethod()
      .getParameterAnnotations()[parameterIndex];

    return Arrays.stream(parameterAnnotations)
      .anyMatch(annotation ->
        annotation.annotationType().getSimpleName().equals(annotationClassName)
          || annotation.annotationType().getCanonicalName().equals(annotationClassName)
          || annotation.annotationType().getName().equals(annotationClassName)
      );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HandlerArgumentDefinition that = (HandlerArgumentDefinition) o;

    if (parameterIndex != that.parameterIndex) return false;
    return handlerMethodDefinition.equals(that.handlerMethodDefinition);
  }

  @Override
  public int hashCode() {
    int result = handlerMethodDefinition.hashCode();
    result = 31 * result + parameterIndex;
    return result;
  }

}
