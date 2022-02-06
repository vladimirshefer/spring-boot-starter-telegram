package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPhotoOrNull;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.lang.reflect.Type;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PhotoSizeArgumentResolverImpl implements ArgumentResolver {

  @Override
  public void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index) {
    Type[] genericParameterTypes = mappingDefinition.getOriginalMethod().getGenericParameterTypes();
    if (genericParameterTypes[index].getTypeName().contains("PhotoSize")) {
      result[index] = getPhotoOrNull(update);
    }

  }
}
