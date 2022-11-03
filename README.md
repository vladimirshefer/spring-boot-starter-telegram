# Spring Boot Starter Telegram

Extensible framework for creating Telegram bots with ease.

This framework is a [Spring Boot Starter](https://www.geeksforgeeks.org/spring-boot-starters/).

Powered by [Spring Boot](https://github.com/spring-projects/spring-boot) and 
[TelegramBots](https://github.com/rubenlagus/TelegramBots) libraries.

This framework is designed to be easy for use by [Spring Boot / Web](https://spring.io/guides/gs/spring-boot/) users, who are familiar with `@Controller`-s.

## Documentation

[See full documentation here.](https://vladimirshefer.github.io/spring-boot-starter-chatbots/)

## Easy start

### Simplest bot code

#### BotApplication.java
```java
@SpringBootApplication
@TelegramController
public class BotApplication {

  /**
   * Simple handler to reply the text messages.
   * @param messageText the text of the message, if present.
   * @return Reply message text to be sent by bot.
   */
  @RequestMapping
  public String simpleMessage(String messageText) {
    return "Thank you for your message!";
  }

  /**
   * Handles polls created in chat.
   * If message contains no poll, then this handler will not be invoked.
   * @param poll the poll, if present.
   * @return The reply message, saying the number of options in the poll.
   */
  @RequestMapping
  public String pollCheck(Poll poll){
    return "The poll has " + poll.getOptions().size() + " options";
  }
  
  public static void main(String[] args) {
    SpringApplication.run(BotApplication.class, args);
  }

}
```

### Configuration

#### application.properties

Minimal configuration to start:
```properties
spring.telegram.bot.token=123456789:AAfcxQY2FME0UskU1jQE
spring.telegram.bot.name=my_demo_bot
```
[Where do I get the bot token?...](https://core.telegram.org/bots#6-botfather)

### Dependencies

#### pom.xml
```xml
<project>
  <dependencies>
    <dependency>
      <groupId>io.github.vladimirshefer</groupId>
      <artifactId>spring-boot-starter-chatbots</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
  </dependencies>
  <repositories>
    <repository>
      <id>spring-boot-starter-chatbots-nexus</id>
      <name>Spring Boot Starter Telegram Nexus</name>
      <url>https://nexus.hetzner.shefer.dev/repository/spring-boot-starter-chatbots/</url>
    </repository>
  </repositories>
</project>
```

## Parsing incoming updates (messages)

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


## See also

### Other implementations
- [OlegNyr/java-telegram-bot-mvc](https://github.com/OlegNyr/java-telegram-bot-mvc)
  
  Uses [pengrad/java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api) under the hood
  
  
- [kshashov/spring-boot-starter-chatbots](https://github.com/kshashov/spring-boot-starter-chatbots/) - 

  Spring boot starter for Telegram bots, but with another library under the hood.
  
  Uses [pengrad/java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api) under the hood
  
