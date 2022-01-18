package com.github.vladimirshefer.springbootstartertelegram.scan;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ReflectionUtils {

  public static boolean hasAnnotation(Class<?> targetClass, Class<?> annotation){
    return Arrays.stream(targetClass.getAnnotations())
        .map(Annotation::annotationType)
        .anyMatch(a -> a.equals(annotation));
  }

}
