package io.github.vladimirshefer.springchat.core.arguments.util;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Allows converting repeatable fields (list, array, single value) to required single or collection type.
 */
public class RepeatableArgumentConverter<T> {

  /**
   * Should be collection or array or plain value object.
   */
  private final Object value;
  private Class<T> elementType;

  public RepeatableArgumentConverter(@Nullable Object value, Class<T> elementType) {
    this.value = value;
    this.elementType = elementType;
  }

  private boolean isNull() {
    return value == null;
  }

  public boolean isList() {
    return value != null && List.class.isAssignableFrom(value.getClass());
  }

  public boolean isSet() {
    return value != null && Set.class.isAssignableFrom(value.getClass());
  }

  public boolean isCollection() {
    return value != null && Collection.class.isAssignableFrom(value.getClass());
  }

  public boolean isPlainValue() {
    return value != null && elementType.isAssignableFrom(value.getClass());
  }

  public boolean isArray() {
    return value != null && value.getClass().isArray();
  }

  @Nullable
  public List<T> toListOrNull() {
    if (isNull()) {
      return null;
    }

    if (isList()) {
      return castAndGet();
    }

    if (isCollection()) {
      return new ArrayList<>(castAngGetAsCollection());
    }

    if (isPlainValue()) {
      return Collections.singletonList(castAndGetAsT());
    }

    if (isArray()) {
      return Arrays.asList(castAndGetAsArray());
    }

    throw new IllegalStateException("Can not convert unknown type " + value.getClass().getName() +
      " to List of " + elementType.getName());
  }

  @Nullable
  public Set<T> toSetOrNull() {
    if (isNull()) {
      return null;
    }

    if (isSet()) {
      return castAndGet();
    }

    if (isCollection()) {
      return new HashSet<>(castAngGetAsCollection());
    }

    if (isPlainValue()) {
      return Collections.singleton(castAndGetAsT());
    }

    if (isArray()) {
      return new HashSet<>(Arrays.asList(castAndGetAsArray()));
    }

    throw new IllegalStateException("Can not convert unknown type " + value.getClass().getName() +
      " to Set of " + elementType.getName());
  }

  @Nullable
  public T getBestOrNull(Comparator<T> comparator) {
    return toListOrEmpty().stream().max(comparator).orElse(null);
  }

  public List<T> toListOrEmpty() {
    return Optional.ofNullable(toListOrNull()).orElse(new ArrayList<>());
  }

  @SuppressWarnings("unchecked")
  private <P> P castAndGet() {
    return (P) value;
  }

  public Collection<T> castAngGetAsCollection() {
    return castAndGet();
  }

  public T castAndGetAsT() {
    return castAndGet();
  }

  private T[] castAndGetAsArray() {
    return castAndGet();
  }

}
