package com.github.vladimirshefer.springbootstartertelegram.scan;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

  public static boolean hasAnnotation(Class<?> targetClass, Class<?> annotation){
    return Arrays.stream(targetClass.getAnnotations())
        .map(Annotation::annotationType)
        .anyMatch(a -> a.equals(annotation));
  }

  public static Method getSameMethod(Class<?> targetClass, Method originalMethod) {
    return Arrays.stream(targetClass.getDeclaredMethods())
        .filter(method -> method.getName().equals(originalMethod.getName()))
        .filter(method -> asList(method.getParameterTypes()).equals(asList(originalMethod.getParameterTypes())))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "Class" + targetClass.getName() + "does not have method like " + originalMethod));
  }

}
