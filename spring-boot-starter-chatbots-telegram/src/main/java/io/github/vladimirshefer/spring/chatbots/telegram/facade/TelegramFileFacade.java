package io.github.vladimirshefer.spring.chatbots.telegram.facade;

import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.File;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
public class TelegramFileFacade implements FileFacade {

  private final File file;
//  private final byte[] fileBytes;

  @Nullable
  @Override
  public Object getSource() {
    return file;
  }

  @Override
  public Future<byte[]> getContent() {
    System.out.println("getContent");
    System.out.println(file);
//    System.out.println(file.getFilePath());

    return null;
  }
}
