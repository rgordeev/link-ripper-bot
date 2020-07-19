package io.tver.telegram.bot;

import io.tver.telegram.config.ApplicationProperties;
import io.tver.telegram.rebrandly.RebrandlyClient;
import io.tver.telegram.rebrandly.model.LinkRequest;
import io.tver.telegram.rebrandly.model.LinkResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class TverIOBot extends TelegramLongPollingBot {

    private final ApplicationProperties applicationProperties;
    private final RebrandlyClient rebrandlyClient;

    @Autowired
    public TverIOBot(ApplicationProperties applicationProperties, RebrandlyClient rebrandlyClient) {
        this.applicationProperties = applicationProperties;
        this.rebrandlyClient = rebrandlyClient;
    }

    @Override
    public String getBotToken() {
        return applicationProperties.getBotToken();
    }

    @Override
    public String getBotUsername() {
        return applicationProperties.getBotUserName();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(final Update update) {
        if (update.getMessage().isCommand()) {
            final String command = update.getMessage().getEntities().stream().findFirst().map(MessageEntity::getText).orElse("");
            switch (command) {
                case "/start" :
                    showHelp(update);
                    break;
                case "/help" :
                    showHelp(update);
                    break;
                case "/link" :
                    showShortLink(update);
                    break;
                default: showHelp(update);
            }
        }

    }

    private void showHelp(final Update update) throws TelegramApiException {
        String helpText =
                "/help - show this info \n" +
                "/link <url> <tag> - create short link from url with tag (optional)";
        execute(new SendMessage(update.getMessage().getChatId(), helpText));
    }

    private void showShortLink(final Update update) throws TelegramApiException {
        final LinkResponse response = createShortLink(update);
        if (Objects.isNull(response.getShortUrl())) {
            execute(new SendMessage(update.getMessage().getChatId(), "Something went wrong! Try /link <url> <tag>* (optional)"));
        } else {
            execute(new SendMessage(update.getMessage().getChatId(), response.getShortUrl()));
        }
    }

    private LinkResponse createShortLink(Update update) {
        final String text = update.getMessage().getText();
        final String[] args = Strings.split(text, ' ');
        final Optional<LinkRequest.Domain> domain = Optional.ofNullable(applicationProperties.getRebrandlyDomain())
                .filter(s -> !StringUtils.isEmpty(s))
                .map(s -> new LinkRequest.Domain(s));

        if (args.length < 2 || args.length > 3)
            return new LinkResponse();
        else if (args.length == 2) {
            final String url = args[1];
            LinkRequest request = LinkRequest.builder()
                    .domain(domain)
                    .destination(url)
                    .build();
            log.info("REQUEST {}", request);
            LinkResponse response = rebrandlyClient.links(request, applicationProperties.redrandlyHeaders());
            log.info("RESPONSE {}", response);
            return response;
        }
        else {
            final String url = args[1];
            final String tag = args[2];
            LinkRequest request = LinkRequest.builder()
                    .domain(domain)
                    .slashtag(tag)
                    .destination(url)
                    .build();
            log.info("REQUEST {}", request);
            LinkResponse response = rebrandlyClient.links(request, applicationProperties.redrandlyHeaders());
            log.info("RESPONSE {}", response);
            return response;
        }
    }
}
