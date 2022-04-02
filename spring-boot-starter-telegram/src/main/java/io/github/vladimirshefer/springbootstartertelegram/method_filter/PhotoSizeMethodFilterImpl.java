package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class PhotoSizeMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    return !hasPhotoSizeListParameter(method) || getPhotos(update) != null;
  }

  private List<PhotoSize> getPhotos(Update update) {
    return update.getMessage().getPhoto();
  }

  private boolean hasPhotoSizeListParameter(HandlerMethodDefinition method) {
    return method.getArguments().stream()
      .map(HandlerArgumentDefinition::getGenericType)
      .map(Type::getTypeName)
      .anyMatch(it -> it.contains(PhotoSize.class.getSimpleName()));
  }

}
