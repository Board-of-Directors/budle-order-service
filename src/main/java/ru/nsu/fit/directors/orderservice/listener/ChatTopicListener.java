package ru.nsu.fit.directors.orderservice.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.orderservice.event.UserMessageEvent;
import ru.nsu.fit.directors.orderservice.service.MessageService;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "chatTopic")
public class ChatTopicListener {
    private final MessageService messageService;

    @KafkaHandler
    void handleUserMessage(UserMessageEvent userMessageEvent) {
        log.info("Receive user message {}", userMessageEvent);
        messageService.save(userMessageEvent);
    }

    @KafkaHandler
    void handleBusinessMessage(BusinessMessageEvent businessMessageEvent) {
        log.info("Receive business message {}", businessMessageEvent);
        messageService.save(businessMessageEvent);
    }
}
