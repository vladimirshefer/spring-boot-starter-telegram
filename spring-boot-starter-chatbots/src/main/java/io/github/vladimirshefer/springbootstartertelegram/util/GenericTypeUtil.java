package io.github.vladimirshefer.springbootstartertelegram.util;

import java.lang.reflect.Type;

public class GenericTypeUtil {

  public static boolean isListOf(Type type, Class<?> elementType){
    return type.getTypeName().equals("java.labg.List<"+elementType.getCanonicalName()+">");
  }

}
