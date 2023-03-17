package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.object.entity.User;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class DiscordUserFacade implements UserFacade {

  private final User user;

  @Nullable
  @Override
  public Object getSource() {
    return user;
  }

  @Nullable
  @Override
  public String getId() {
    return user.getId().asString();
  }

  @Nullable
  @Override
  public String getUserName() {
    return user.getUsername();
  }

  @Nullable
  @Override
  public String getDisplayName() {
    return user.getUsername();
  }

}
