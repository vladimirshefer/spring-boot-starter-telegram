package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.function.Function;

@RequiredArgsConstructor
@ToString
public class TelegramFileFacade implements FileFacade {

  private final String fileId;

  @ToString.Exclude
  private final Function<String, Callable<byte[]>> fileGetter;

  @Nullable
  @Override
  public Object getSource() {
    return fileId;
  }

  @Nullable
  @Override
  public Callable<byte[]> getContent() {
    return fileGetter.apply(fileId);
  }

}
