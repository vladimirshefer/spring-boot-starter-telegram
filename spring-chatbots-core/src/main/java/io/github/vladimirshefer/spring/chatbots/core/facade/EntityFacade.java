package io.github.vladimirshefer.spring.chatbots.core.facade;

import javax.annotation.Nullable;

/**
 * Base interface for all entity facades in the library.
 */
public interface EntityFacade {

  /**
   * <p>
   * When implementing facade for specific messenger, you
   * always use messenger-specific entity (DTO) as a source of data.
   * Return this data entity from this method.
   * This value will be used in both core and messenger-specific
   * parts of code as a most full context of the message event.
   * <p/>
   * <p>
   * For example, if this entity is telegram poll, then you should
   * return the instance of Poll class from telegram library.
   * </p>
   * <p>
   * For example, if this entity is telegram message, then uou should
   * return instance of Update class from telegram library.
   * <p/>
   * @return the original object of message or part of the message or another messenger event corresponding to this entity.
   */
  @Nullable
  Object getSource();

}
