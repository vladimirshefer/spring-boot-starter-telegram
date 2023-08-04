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

  /**
   * The user or bot, which has sent this message.
   * Throwing exceptions from this method is not recommended.
   * @return author (creator) of the message.
   */
  @Nullable
  UserFacade getAuthor();


  /**
   *
   * @return
   */
  @Nullable
  String getChatId();


  /**
   * Some messengers support attaching files (photos, documents, archives, etc.) to the message.
   * Returns these attachments, if present and possible to get.
   * If mwssage has no attachments or private/unavailable attachments, then return empty list.
   * @return
   */
  @Nonnull
  List<FileFacade> getAttachments();

  /**
   * If this message is reply to another message, or references other messages (i.e. via link)
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
