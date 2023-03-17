package io.github.vladimirshefer.spring.chatbots.core.facade;

import java.util.concurrent.Future;

public interface FileFacade extends EntityFacade {
  Future<byte[]> getContent();
}
