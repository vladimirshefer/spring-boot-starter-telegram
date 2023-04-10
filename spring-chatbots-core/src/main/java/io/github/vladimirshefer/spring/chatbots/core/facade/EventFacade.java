package io.github.vladimirshefer.spring.chatbots.core.facade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface EventFacade extends EntityFacade {

  @Nullable
  MessageFacade getMessage();

  @Nonnull
  List<FileFacade> getAttachments();

  /**
   * Returns the name of the messenger, where from has the event arrived.
   * Value should be non-null, non-blank snake cased string.
   * Examples: "telegram", "discord"
   * Another code could make assumptions about source value based
   * on this name, (i.e. for telegram getSource will always return Update)
   *
   * @return messenger simple name.
   */
  String getMessengerName();

  /**
   * Returns the name of the messenger, where from has the event arrived.
   * Value should be non-null, non-blank string.
   * If bot has no id, then you could return "default" string.
   * @return bot identifier
   */
//  String botId();

}
