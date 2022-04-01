package io.github.vladimirshefer.springbootstartertelegram.handler;

import io.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class HandlerArgumentDefinitionTest {

  @Test
  void testGetGenericTypeName() {
    @SuppressWarnings("unused")
    class TheClass {
      @RequestMapping
      public void theMethod(
        @Nullable List<Map<String, Integer>> argument
      ) {
      }
    }

    HandlerMethodDefinition methodDefinition = new HandlerMethodDefinition(
      "controllerName",
      TheClass.class.getMethods()[0],
      TheClass.class,
      new TheClass()
    );

    HandlerArgumentDefinition argument = new HandlerArgumentDefinition(methodDefinition, 0);

    assertEquals(List.class, argument.getType());
    assertEquals(singletonList(Nullable.class), argument.getAnnotations());
  }

}
