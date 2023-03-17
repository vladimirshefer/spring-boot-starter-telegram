---
title: "Return values. Replying to update."
permalink: /return-values
---

The reaction to the update (message) is defined by method return value.

## Return values

### Simple reply message
```java
  /**
  * Answers with simple text reply message.
  * @return The string containing the text for reply.
  */
  @RequestMapping
  public String simpleReply(){
    return "This is simple text to reply.";
  }
```

### No answer
You could return `void` (`Unit` in Kotlin) or `null` to do nothing.

```java
  @RequestMapping
  public void noReply(){
    log.debug("Some event happened")
    return;
  }
```

## Messenger-specific return values

### Custom answer for Telegram

Return any `BotApiMethod` subsclass to return custom answer.

This is Telegram API class to send any reaction. Unlimited customization.

For example bot will delete incoming message if it has text "delete me". 
For groups bot should have admin permissions to delete messages.

If `BotApiMethod` is returned as a response to event from another 
messenger, then behaviour in undefined. In most cases this answer 
will make no effect (ingnored by handler).

```java
  /**
   * Deletes any incoming message with text "delete me".
   * @param text the text of the message
   * @param update the full information about message
   * @return Command to delete message or null if message is not "delete me".
   */
  @Messenger("telegram")
  @RequestMapping
  public DeleteMessage deleteMessage(String text, Update update) {
    if (text.equalsIgnoreCase("delete me")) {
      String chatId = update.getMessage().getChatId().toString();
      Integer messageId = update.getMessage().getMessageId();
      return new DeleteMessage(chatId, messageId);
    }
    return null;
  }
```
