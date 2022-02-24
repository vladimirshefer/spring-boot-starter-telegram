package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.Arrays;

@Component
public class PollMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    return !Arrays
            .asList(method.getTargetMethod().getParameterTypes())
            .contains(Poll.class) || update.getMessage().getPoll() != null;
  }
}
