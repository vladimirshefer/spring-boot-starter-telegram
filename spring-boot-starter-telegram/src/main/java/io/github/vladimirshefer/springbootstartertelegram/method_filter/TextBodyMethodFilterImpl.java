package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class TextBodyMethodFilterImpl implements MethodFilter {

  /**
   * Filters out controller method if update mas no text and method has String parameter without
   * Nullable annotation.
   *
   * @param update the telegram message
   * @param method the controller method information
   * @return false if this update should not be handled by this method. True if you don't mind.
   */
  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    return getMessageText(update) != null || !isHasRequiredStringParameter(method);
  }

  private boolean isHasRequiredStringParameter(HandlerMethodDefinition method) {
    boolean hasRequiredStringParameter = false;

    for (int i = 0; i < getParametersCount(method); i++) {
      if (isRequiredString(method, i)) {
        hasRequiredStringParameter = true;
        break;
      }
    }
    return hasRequiredStringParameter;
  }

  private String getMessageText(Update update) {
    return update.getMessage() != null ? update.getMessage().getText() : null;
  }

  private int getParametersCount(HandlerMethodDefinition method) {
    return method.getTargetMethod().getParameters().length;
  }

  private boolean isRequiredString(HandlerMethodDefinition method, int parameterIndex) {
    boolean isString = isParameterTypeEquals(method.getTargetMethod(), parameterIndex,
      String.class);
    boolean isRequired = !hasParameterAnnotation(method, parameterIndex, "Nullable");
    return isString && isRequired;
  }

  private boolean isParameterTypeEquals(Method targetMethod, int index, Class<String> b) {
    return Objects.equals(targetMethod.getParameterTypes()[index], b);
  }

  private boolean hasParameterAnnotation(HandlerMethodDefinition method, int parameterIndex,
    String annotationName) {
    return Arrays.stream(getParameterAnnotations(method.getTargetMethod(), parameterIndex))
      .anyMatch(it -> it.annotationType().getSimpleName().equals(annotationName));
  }

  private Annotation[] getParameterAnnotations(Method targetMethod, int index) {
    return targetMethod.getParameterAnnotations()[index];
  }

}
