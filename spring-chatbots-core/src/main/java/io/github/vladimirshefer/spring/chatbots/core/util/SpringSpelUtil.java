package io.github.vladimirshefer.spring.chatbots.core.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

public class SpringSpelUtil {

  private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
  private static final EvaluationContext SIMPLE_EVALUATION_CONTEXT =
    SimpleEvaluationContext.forReadOnlyDataBinding().build();

  /**
   * Extracts value from object. Mechanism is similar to JsonPath.
   * Example: get(someObject, "foo.bar") is similar to someObject.getFoo().getBar();
   * This method can access only public fields and getters.
   * Powered by SPEL - Spring Expression Language.
   * See: <a href="https://docs.spring.io/spring-framework/docs/4.3.10.RELEASE/spring-framework-reference/html/expressions.html#expressions-bean-references">SpEL Bean references</a>
   *
   * @param rootObject root
   * @param spel path to desired field in object.
   * @return
   */
  public static Object get(Object rootObject, String spel) {
    return EXPRESSION_PARSER.parseExpression(spel).getValue(SIMPLE_EVALUATION_CONTEXT, rootObject);
  }
}
