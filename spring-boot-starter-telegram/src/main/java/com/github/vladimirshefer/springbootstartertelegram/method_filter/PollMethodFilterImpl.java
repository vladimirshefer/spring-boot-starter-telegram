package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PollMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return update.getMessage().getPoll() != null &&
      Arrays
        .stream(method.getTargetMethod().getParameterTypes())
        .collect(Collectors.toList())
        .contains(Poll.class);

  }
}
