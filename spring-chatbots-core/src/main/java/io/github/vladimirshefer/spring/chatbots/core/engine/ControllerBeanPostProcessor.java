package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.BotController;
import io.github.vladimirshefer.spring.chatbots.core.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class ControllerBeanPostProcessor implements BeanPostProcessor {

  private final MappingDefinitionsManager mappingDefinitionsManager;

  private final Map<String, Class<?>> telegramControllersClasses = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (ReflectionUtil.hasAnnotation(bean.getClass(), BotController.class)) {
      telegramControllersClasses.put(beanName, bean.getClass());
      log.debug("Registered bot controller " + beanName);
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
