package io.github.vladimirshefer.spring.chatbots.core.test;

import io.github.vladimirshefer.spring.chatbots.core.api.ChatBot;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.UserFacade;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@Getter
public class MockChatBot implements ChatBot {

  @Setter
  private String name = "mockBot";

  @Setter
  private String type = "mock";

  @Setter
  private boolean autoInit = true;

  private MockMessengerClient mockMessengerClient;

  @Override
  public boolean autoInit() {
    return autoInit;
  }

  @Override
  public void init(EventListener eventListener) {
    mockMessengerClient = new MockMessengerClient(eventListener, type);
  }

  public static class MockMessengerClient {
    private final EventListener eventListener;
    private String messengerName;

    public MockMessengerClient(EventListener eventListener, String messengerName) {
      this.eventListener = eventListener;
      this.messengerName = messengerName;
    }

    public void sendMessage(EventFacade eventFacade) {
      eventListener.handleMessage(eventFacade);
    }

    public void sendSimpleTextMessage(String text) {
      sendMessage(new EventFacade(){
        @Nullable
        @Override
        public Object getSource() {
          return null;
        }

        @Nullable
        @Override
        public MessageFacade getMessage() {
          return new MessageFacade() {
            @Nullable
            @Override
            public String getMessageText() {
              return text;
            }

            @Nullable
            @Override
            public String getId() {
              return null;
            }

            @Nullable
            @Override
            public UserFacade getAuthor() {
              return null;
            }

            @Nullable
            @Override
            public String getChatId() {
              return null;
            }

            @Nonnull
            @Override
            public List<FileFacade> getAttachments() {
              return Collections.emptyList();
            }

            @Nullable
            @Override
            public Object getSource() {
              return null;
            }
          };
        }

        @Override
        public String getMessengerName() {
          return messengerName;
        }
      });
    }
  }
}
