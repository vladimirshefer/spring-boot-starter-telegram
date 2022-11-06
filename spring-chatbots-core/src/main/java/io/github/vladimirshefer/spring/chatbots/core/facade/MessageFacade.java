package io.github.vladimirshefer.spring.chatbots.core.facade;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public interface MessageFacade extends EntityFacade {

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

  @Nullable
  UserFacade getAuthor();

  @Nullable
  String getChatId();

  /**
   * If this message if reply to another message, or references other messages (i.e. via link)
   * then this value should be references to these messages.
   * This method never throws any exceptions.
   * If no messages are referenced, then return empty list.
   * The list could be immutable.
   *
   * @return another messages, which are referenced by this message. or empty list.
   */
  @Nonnull
  default List<MessageFacade> getReferencedMessages() {
    return Collections.emptyList();
  }

}
