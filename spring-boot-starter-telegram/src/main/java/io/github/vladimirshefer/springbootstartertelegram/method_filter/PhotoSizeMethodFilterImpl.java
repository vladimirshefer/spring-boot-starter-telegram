package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class PhotoSizeMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return !hasPhotoSizeListParameter(method) || getPhotos(update) != null;
  }

  private List<PhotoSize> getPhotos(Update update) {
    return update.getMessage().getPhoto();
  }

  private boolean hasPhotoSizeListParameter(MappingDefinition method) {
    return Arrays
      .stream(method.getTargetMethod().getGenericParameterTypes())
      .anyMatch(p -> p.getTypeName().contains("PhotoSize"));
  }
}
