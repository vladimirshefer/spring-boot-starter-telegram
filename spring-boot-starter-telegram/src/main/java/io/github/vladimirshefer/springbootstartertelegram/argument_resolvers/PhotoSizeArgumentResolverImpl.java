package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPhotoOrNull;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PhotoSizeArgumentResolverImpl implements ArgumentResolver {

  /**
   * If parameter is
   * <pre>{@code List<PhotoSize>}</pre>
   * then all PhotoSize-s will be returned as list.
   *
   * If parameter type is PhotoSize, then the biggest PhotoSize will be returned.
   *
   * If parameter has another type then null will be returned.
   *
   * If update has no PhotoSize-s then null will be returned.
   *
   * @param method The full information about handler method.
   * @param update            The incoming update (i.e. telegram message)
   * @param index             The index of the handler method parameter to resolve.
   * @return List of Photo size or
   */
  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    Type[] genericParameterTypes = method.getOriginalMethod().getGenericParameterTypes();

    boolean isList = method.getArgument(index).getType().equals(List.class);

    if (isList) {
      boolean hasGeneric = hasGeneric(genericParameterTypes[index], PhotoSize.class);
      if (hasGeneric) {
        return getPhotoOrNull(update);
      }
    }

    boolean isPhotoSize = method.getArgument(index).getType().equals(PhotoSize.class);

    if (isPhotoSize) {
      return getBiggestPhotoSize(update);
    }

    return null;
  }

  private PhotoSize getBiggestPhotoSize(Update update) {
    return UpdateUtil.getPhotoOrEmpty(update)
      .stream()
      .max(Comparator.comparing(PhotoSize::getHeight))
      .orElse(null);
  }

  private boolean hasGeneric(Type parameterType, Class<?> genericType) {
    if (parameterType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) parameterType;
      return Arrays.stream(parameterizedType.getActualTypeArguments())
        .map(Type::getTypeName)
        .anyMatch(it -> it.equals(genericType.getTypeName()));
    }
    return false;
  }

}
