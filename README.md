
Extensible framework for easy creating Telegram bots.

Powered by [Spring Boot](https://github.com/spring-projects/spring-boot) and 
[TelegramBots](https://github.com/rubenlagus/TelegramBots).

## Easy start

Simplest bot code:

```java
@SpringBootApplication
@EnableTelegramBots
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

}
```

application.properties
```properties
spring.telegram.bot.token=123456789:AAfcxQY2FME0UskU1jQE
spring.telegram.bot.name=my_demo_bot
```

## See also

https://github.com/OlegNyr/java-telegram-bot-mvc
