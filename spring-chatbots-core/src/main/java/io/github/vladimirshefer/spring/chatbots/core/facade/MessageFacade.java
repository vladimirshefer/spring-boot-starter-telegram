package io.github.vladimirshefer.spring.chatbots.core.facade;

import javax.annotation.Nullable;

public interface MessageFacade {

  /**
   * Most chat messages contain simple text.
   * This method should extract this main text of the message, if present.
   * If text does not present in the message or could not
   * be extracted, then null should be returned.
   * Return empty value is not recommended.
   * This method should never throw any exceptions.
   * @return message text.
   */
  @Nullable
  String getMessageText();

  /**
   * Most messengers assign unique identifier to every message.
   * This method should extract this identifier of the message, if present.
   * If identifier does not present in the message or could not
   * be extracted, then null should be returned.
   * Return empty value is not recommended.
   * This method should never throw any exceptions.
   * @return message identifier.
   */
  @Nullable
  String getId();

}
