package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ReplyToMessage;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class ReplyToMessageAnnotationMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    boolean hasReplyToMessage = Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getReplyToMessage)
      .isPresent();

    for (HandlerArgumentDefinition argument : method.getArguments()) {
      boolean argumentMatched = !argument.hasAnnotation(ReplyToMessage.class) ||
        argument.hasAnnotation("Nullable") || hasReplyToMessage;

      if (!argumentMatched) {
        return false;
      }
    }

    return true;
  }

}
