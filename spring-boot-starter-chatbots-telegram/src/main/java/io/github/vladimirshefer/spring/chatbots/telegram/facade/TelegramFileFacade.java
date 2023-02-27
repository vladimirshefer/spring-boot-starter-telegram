package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

@RequiredArgsConstructor
public class TelegramFileFacade implements FileFacade {

  private final String fileId;
  private final Function<String, Callable<byte[]>> fileGetter;

  @Nullable
  @Override
  public Object getSource() {
    return fileId;
  }

  @Nullable
  @Override
  public Future<byte[]> getContent() {
    return new FutureTask<>(fileGetter.apply(fileId));
  }

}
