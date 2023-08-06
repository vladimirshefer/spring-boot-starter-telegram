package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.object.entity.Attachment;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.concurrent.Callable;

@ToString
@RequiredArgsConstructor
class DiscordFileFacade implements FileFacade {
    private final Attachment attachment;

    @Override
    public Callable<byte[]> getContent() {
        return () -> IOUtils.toByteArray(new URI(attachment.getUrl()));
    }

    @Nullable
    @Override
    public Object getSource() {
        return attachment;
    }
}
