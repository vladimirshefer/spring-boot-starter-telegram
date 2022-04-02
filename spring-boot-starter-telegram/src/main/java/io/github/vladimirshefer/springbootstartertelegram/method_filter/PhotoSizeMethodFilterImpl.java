package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PhotoSizeMethodFilterImpl extends SimpleMethodFilter {

  @Override
  protected boolean updateHasValue(Update update) {
    return UpdateUtil.getPhotoOrNull(update) != null;
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.getGenericType().getTypeName().contains(PhotoSize.class.getName());
  }

}
