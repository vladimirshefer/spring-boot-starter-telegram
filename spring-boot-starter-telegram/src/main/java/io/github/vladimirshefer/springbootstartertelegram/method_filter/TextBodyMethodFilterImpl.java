package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    boolean isString = method.getArgument(parameterIndex).getType().equals(String.class);
    boolean isRequired = !method.getArgument(parameterIndex).hasAnnotation("Nullable");
    return isString && isRequired;
  }

}
