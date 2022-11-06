---
title: "Parsing updates."
permalink: /parsing-updates
---

## Parsing incoming events (messages)

### Message text
The message text will be passed to the parameter of type `String`.

If the message does not contain text (e.g. photo or sticker message),
then this method will not be invoked.

It is ok to miss parameter for message text. This means that handler does not require such and information.

Parameter should not have any annotations (except for documented below)
to avoid conflicts with other parameter resolvers.
```java
  /**
  * Handles all messages with text.
  */
  @RequestMapping
  public void textMessage(String messageText) {
  }
```

If you need to have custom annotation on message text parameter,
then you should also mark the parameter with `@MessageText` annotation

```java
  /**
  * Handles all messages with text.
  */
  @RequestMapping
  public void textMessage(@SomeAnnotaion @MessageText String messageText) {
  }
```

You could make the message body parameter to be optional if you apply `@Nullable`.
Then this method will be invoked even if message has no text (e.g. photo or sticker message).
You could use any `@Nullable` annotation e.g.
`@javax.annotation.Nullable` from [JSR305](https://mvnrepository.com/artifact/com.google.code.findbugs/jsr305).

```java
  /**
  * Handles all messages and puts the message text to messageText parameter if present.
  * @param messageText Contains the text of the incoming message 
  *                    or null if no text present (i.e. sticker message).
  */
  @RequestMapping
  public void optionalTextMessage(
      @Nullable String messageText,
      Update update
  ) {
    // handling code
  }
```

### Poll
You could simply get the poll from the message by adding `Poll` parameter.
If the message does not contain poll, then this method will not be invoked.
```java
  /**
  * Handles all poll messages.
  */
  @RequestMapping
  public void simpleMessage(Poll poll) {
  }
```

### Update
`Update` is the object with **all** information about **any** incoming event
(e.g. incoming message, poll created, media shared, message forwarded etc.).
It is like "HTTP request" in terms of Spring Web and REST API applications.

You can get it by simply adding parameter with `Update` type.

Since the update is the root object of all telegram events, then the method from the example below will be invoked for **each** event
```java
  /**
  * Handles all updates (e.g. messages, polls, media, etc.).
  */
  @RequestMapping
  public void simpleMessage(Update update) {
  }
```
