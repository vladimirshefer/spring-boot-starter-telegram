package io.github.vladimirshefer.spring.chatbots.core.facade;

import javax.annotation.Nullable;

public interface UserFacade extends EntityFacade {

  @Nullable
  String getId();

  @Nullable
  String getUserName();

  @Nullable
  String getDisplayName();

}
