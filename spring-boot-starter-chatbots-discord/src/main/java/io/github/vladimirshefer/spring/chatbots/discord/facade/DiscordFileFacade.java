package io.github.vladimirshefer.spring.chatbots.discord.facade;

import discord4j.core.object.entity.Attachment;
import io.github.vladimirshefer.spring.chatbots.core.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import sun.misc.IOUtils;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.concurrent.Callable;

@ToString
@RequiredArgsConstructor
class DiscordFileFacade implements FileFacade {
    private final Attachment attachment;

    @Override
    public Callable<byte[]> getContent() {
        return () -> {
            BufferedInputStream in = new BufferedInputStream(new URL(attachment.getUrl()).openStream());
            return IOUtils.readAllBytes(in);
        };
    }

    @Nullable
    @Override
    public Object getSource() {
        return attachment;
    }
}
