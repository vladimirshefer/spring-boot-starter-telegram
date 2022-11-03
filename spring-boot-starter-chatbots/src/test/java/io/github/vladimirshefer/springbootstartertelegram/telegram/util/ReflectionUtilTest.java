package io.github.vladimirshefer.springbootstartertelegram.telegram.util;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class ReflectionUtilTest {

  @SneakyThrows
  @Test
  void getSameMethod() {

    class Original {
      public String theMethod(String str, int number, Object d){
        return null;
      }
    }

    class Target {
      public String theMethod(String mystr, int mynumber, Object p){
        return "rigel";
      }
    }

    Method theMethod = Original.class.getMethod("theMethod", String.class, int.class, Object.class);
    Method targetMethod = ReflectionUtil.getSameMethod(Target.class, theMethod);
    assertEquals("rigel", targetMethod.invoke(new Target(), "hhh", 13, new Object()));
  }
}
