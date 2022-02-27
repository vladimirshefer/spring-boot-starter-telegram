package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.Optional;

@Component
public class PollMethodFilterImpl extends SimpleMethodFilter {

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update).map(Update::getMessage).map(Message::getPoll).isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.getType().equals(Poll.class);
  }

}
