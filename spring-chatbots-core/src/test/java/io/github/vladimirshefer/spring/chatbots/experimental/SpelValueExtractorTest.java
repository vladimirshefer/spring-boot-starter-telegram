package io.github.vladimirshefer.spring.chatbots.experimental;

import io.github.vladimirshefer.spring.chatbots.core.util.SpringSpelUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpelValueExtractorTest {

  @Test
  void test() {

    class InnerExample {
      public String value;

      public String getOther() {
        return "other value";
      }

      public List<Integer> getList() {
        return null;
//        return Arrays.asList(1,2,3,4,5,6,7);
      }
    }

    class Example {
      public InnerExample inner;
    }

    Example example = new Example();
    example.inner = new InnerExample();
    example.inner.value = "helloworld";

    assertEquals(SpringSpelUtil.get(example, "inner.value"), "helloworld");
    assertEquals(SpringSpelUtil.get(example, "inner.other"), "other value");
    assertEquals(SpringSpelUtil.get(example, "inner.list?.?[#this>3]"), Arrays.asList(4,5,6,7));
  }

}
