---
nav_order: 0

---

# Spring Boot Starter Chatbots (Telegram, Discord)

Extensible framework for creating chatbots with ease.

This framework is a [Spring Boot Starter](https://www.geeksforgeeks.org/spring-boot-starters/).

Powered by [Spring Boot](https://github.com/spring-projects/spring-boot) and
[TelegramBots](https://github.com/rubenlagus/TelegramBots) libraries.

This framework is designed to be easy for use by [Spring Boot / Web](https://spring.io/guides/gs/spring-boot/) users, who are familiar with `@Controller`-s.

## Easy start

### Simplest bot code

#### BotApplication.java
```java
@SpringBootApplication
@TelegramController
public class BotApplication {

  /**
   * Simple handler to reply the text messages.
   * Handles messages from all chats from all messengers.
   * @param messageText the text of the message, if present.
   * @return Reply message text to be sent by bot.
   */
  @RequestMapping
  public String simpleMessage(String messageText) {
    return "Thank you for your message!";
  }

  /**
   * Handles polls created in telegram chats.
   * If message contains no poll, then this handler will not be invoked.
   * @param poll the poll, if present.
   * @return The reply message, saying the number of options in the poll.
   */
  @Messenger("telegram")
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
# For Telegram
spring.chatbots.telegram.bot.token=123456789:AAfcxQY2FME0UskU1jQE
spring.chatbots.telegram.bot.name=my_demo_bot
# For Discord
spring.chatbots.discord.bot.token=cgnrohmwejmcfmdsdxapweimc
```
- [Where do I get the Telegram bot token?...](https://core.telegram.org/bots#6-botfather)
- [Where do I get the Discord bot token?...](https://discord.com/developers/docs/intro#bots-and-apps)

### Dependencies

#### pom.xml
```xml
<project>
  <dependencies>
    <dependency>
      <groupId>io.github.vladimirshefer</groupId>
      <artifactId>spring-boot-starter-chatbots-telegram</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
  </dependencies>
  <dependencies>
    <dependency>
      <groupId>io.github.vladimirshefer</groupId>
      <artifactId>spring-boot-starter-chatbots-discord</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
  </dependencies>
  <repositories>
    <repository>
      <id>spring-boot-starter-telegram-nexus</id>
      <name>Spring Boot Starter Chatbots Nexus</name>
      <url>https://nexus.hetzner.shefer.dev/repository/spring-boot-starter-telegram/</url>
    </repository>
  </repositories>
</project>
```

## See also

### Other implementations
- [OlegNyr/java-telegram-bot-mvc](https://github.com/OlegNyr/java-telegram-bot-mvc)

  Uses [pengrad/java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api) under the hood


- [kshashov/spring-boot-starter-telegram](https://github.com/kshashov/spring-boot-starter-telegram/) -

  Spring boot starter for Telegram bots, but with another library under the hood.

  Uses [pengrad/java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api) under the hood
  
