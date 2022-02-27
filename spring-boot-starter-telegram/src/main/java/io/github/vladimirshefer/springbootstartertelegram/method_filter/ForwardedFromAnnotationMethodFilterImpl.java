package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ForwardedFrom;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class ForwardedFromAnnotationMethodFilterImpl extends SimpleMethodFilter {

  @Override
  public boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ForwardedFrom.class) &&
      !argument.hasAnnotation("Nullable");
  }

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getForwardFrom)
      .isPresent();
  }

}
