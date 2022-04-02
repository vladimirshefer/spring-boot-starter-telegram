package io.github.vladimirshefer.springbootstartertelegram.argument.resolver;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPhotoOrNull;

@Component
public class PhotoSizeFilteringArgumentResolverImpl extends FilteringArgumentResolver {

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
   * @param argument
   * @param update            The incoming update (i.e. telegram message)
   * @return List of Photo size or
   */
  @Override
  public Object resolve(
    HandlerArgumentDefinition argument,
    Update update
  ) {
    boolean isList = argument.getType().equals(List.class);

    if (isList) {
      boolean hasGeneric = hasGeneric(argument.getGenericType(), PhotoSize.class);
      if (hasGeneric) {
        return getPhotoOrNull(update);
      }
    }

    boolean isPhotoSize = argument.getType().equals(PhotoSize.class);

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

  private boolean hasGeneric(Type parameterType, Type typeArgument) {
    if (parameterType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) parameterType;
      return Arrays.stream(parameterizedType.getActualTypeArguments())
        .map(Type::getTypeName)
        .anyMatch(it -> it.equals(typeArgument.getTypeName()));
    }
    return false;
  }

  @Override
  protected boolean updateHasValue(Update update) {
    return UpdateUtil.getPhotoOrNull(update) != null;
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.getGenericType().getTypeName().contains(PhotoSize.class.getName());
  }

}
