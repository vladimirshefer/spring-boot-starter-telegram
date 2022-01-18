package com.github.vladimirshefer.springbootstartertelegram.others;

import static com.github.vladimirshefer.springbootstartertelegram.scan.ReflectionUtils.hasAnnotation;

import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TelegramControllerBeanPostProcessor implements BeanPostProcessor {

  private final Map<String, Class<?>> telegramControllersClasses = new HashMap<>();
  private final Map<String, Object> telegramControllersBeans = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (hasAnnotation(bean.getClass(), TelegramController.class)) {
      telegramControllersClasses.put(beanName, bean.getClass());
      System.out.println(beanName);
    }

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (telegramControllersClasses.containsKey(beanName)) {
      telegramControllersBeans.put(beanName, bean);
    }
    return bean;
  }

}
