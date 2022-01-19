package com.github.vladimirshefer.springbootstartertelegram.scan;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.ReflectionUtil.hasAnnotation;

import com.github.vladimirshefer.springbootstartertelegram.annotations.TelegramController;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TelegramControllerBeanPostProcessor implements BeanPostProcessor {

  private final MappingDefinitionsManager mappingDefinitionsManager;

  private final Map<String, Class<?>> telegramControllersClasses = new HashMap<>();

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
      mappingDefinitionsManager.registerController(beanName, telegramControllersClasses.get(beanName), bean);
    }
    return bean;
  }

}
