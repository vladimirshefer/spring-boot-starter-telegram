package io.github.vladimirshefer.spring.chatbots.core.facade;

public interface FileFacade extends EntityFacade {

  /**
   * Lazy if possible.
   * Cached if possible.
   * @return
   */
  byte[] getContentStream();

}
