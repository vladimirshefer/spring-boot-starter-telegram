package io.github.vladimirshefer.spring.chatbots.core.facade;

import javax.annotation.Nullable;

public interface EventFacade extends EntityFacade {

  @Nullable
  MessageFacade getMessage();

}
