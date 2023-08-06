package io.github.vladimirshefer.spring.chatbots.core.facade;

import java.util.concurrent.Callable;

public interface FileFacade extends EntityFacade {
  Callable<byte[]> getContent();
}
