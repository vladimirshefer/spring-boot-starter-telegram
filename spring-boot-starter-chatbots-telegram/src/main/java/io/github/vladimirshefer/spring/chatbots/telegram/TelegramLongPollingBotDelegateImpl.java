package io.github.vladimirshefer.spring.chatbots.telegram;

import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.telegram.facade.TelegramEventFacade;
import io.github.vladimirshefer.spring.chatbots.telegram.util.UpdateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * This is the adapter for TelegramLongPollingBot,
 * which delegates updates (messages) handling to framework's core EventListener.
 */
@Slf4j
class TelegramLongPollingBotDelegateImpl extends TelegramLongPollingBot {
  private final EventListener eventListener;
  private final String botName;
  private final String botToken;

  public TelegramLongPollingBotDelegateImpl(
    EventListener eventListener,
    String botName,
    String botToken
  ) {
    this.eventListener = eventListener;
    this.botName = botName;
    this.botToken = botToken;
  }

  @SneakyThrows
  private Callable<byte[]> getFile(String id) {
    return () -> {
      File file = this.sendApiMethod(new GetFile(id));
      String fileUrl = file.getFileUrl(this.getBotToken());

      ByteBuffer buffer;
      try (ReadableByteChannel channel = Channels.newChannel(new URL(fileUrl).openStream())) {
        buffer = ByteBuffer.allocate(Math.toIntExact(file.getFileSize()));
        channel.read(buffer);
      }

      return buffer.array();
    };
  }

  @SneakyThrows
  @Override
  public void onUpdateReceived(Update update) {
    final TelegramEventFacade telegramEventFacade = new TelegramEventFacade(update, this::getFile);
    List<Object> result = eventListener.handleMessage(telegramEventFacade);
    List<BotApiMethod<?>> answers = result
      .stream()
      .map(it -> resultToApiMethod(update, it))
      .collect(Collectors.toList());

    for (BotApiMethod<?> answer : answers) {
      if (answer != null) {
        this.sendApiMethod(answer);
      }
    }
  }

  @Override
  public void onRegister() {
    super.onRegister();
    log.info("Telegram bot registered");
  }

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }


  public static BotApiMethod<?> resultToApiMethod(Update update, Object methodInvocationResult) {
    if (methodInvocationResult instanceof BotApiMethod) {
      return (BotApiMethod<?>) methodInvocationResult;
    }

    return sendSimpleMessage(update, methodInvocationResult.toString());
  }

  private static SendMessage sendSimpleMessage(Update update, String result) {
    SendMessage sendMessage = new SendMessage(UpdateUtil.getChatId(update), result);
    if (update.getMessage() != null && update.getMessage().getMessageId() != null) {
      sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
    }
    return sendMessage;
  }
}
