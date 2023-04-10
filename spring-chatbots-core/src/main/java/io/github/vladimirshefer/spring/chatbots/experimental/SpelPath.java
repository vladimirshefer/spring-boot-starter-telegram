package io.github.vladimirshefer.spring.chatbots.experimental;

public @interface SpelPath {
  String value() default "@source";
}
