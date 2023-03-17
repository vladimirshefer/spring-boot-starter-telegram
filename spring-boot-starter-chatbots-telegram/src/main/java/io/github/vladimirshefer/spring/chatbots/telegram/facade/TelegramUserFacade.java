package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class TelegramUserFacade implements UserFacade {

  private final User user;

  @Nullable
  @Override
  public Object getSource() {
    return user;
  }

  @Nullable
  @Override
  public String getId() {
    return user.getId().toString();
  }

  @Nullable
  @Override
  public String getUserName() {
    return user.getUserName();
  }

  @Nullable
  @Override
  public String getDisplayName() {
    return user.getFirstName() + " " + user.getLastName();
  }
}
