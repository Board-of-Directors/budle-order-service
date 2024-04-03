package ru.nsu.fit.directors.orderservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.dto.response.MessageDto;
import ru.nsu.fit.directors.orderservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.orderservice.event.BusinessOrderNotificationEvent;
import ru.nsu.fit.directors.orderservice.event.OrderNotificationEvent;
import ru.nsu.fit.directors.orderservice.event.UserMessageEvent;
import ru.nsu.fit.directors.orderservice.exception.OrderNotFoundException;
import ru.nsu.fit.directors.orderservice.mapper.MessageMapper;
import ru.nsu.fit.directors.orderservice.model.Order;
import ru.nsu.fit.directors.orderservice.repository.MessageRepository;
import ru.nsu.fit.directors.orderservice.repository.OrderRepository;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class MessageServiceImpl implements MessageService {
    private static final String NOTIFICATION_TOPIC = "notificationTopic";
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final OrderRepository orderService;
    private final KafkaTemplate<String, OrderNotificationEvent> notificationKafka;
    private final KafkaTemplate<String, BusinessOrderNotificationEvent> orderNotificationKafka;

    @Override
    public void save(UserMessageEvent userMessageEvent) {
        Order order = orderService.findById(userMessageEvent.orderId())
            .orElseThrow(() -> new OrderNotFoundException(userMessageEvent.orderId()));
        messageRepository.save(messageMapper.toModel(userMessageEvent, order));
        orderNotificationKafka.send(
            NOTIFICATION_TOPIC,
            new BusinessOrderNotificationEvent(
                order.getEstablishmentId(),
                order.getId(),
                "Received message from user"
            )
        );
    }

    @Override
    public void save(BusinessMessageEvent businessMessageEvent) {
        Order order = orderService.findById(businessMessageEvent.orderId())
            .orElseThrow(() -> new OrderNotFoundException(businessMessageEvent.orderId()));
        messageRepository.save(messageMapper.toModel(businessMessageEvent, order));
        notificationKafka.send(
            NOTIFICATION_TOPIC,
            new OrderNotificationEvent(
                "Received message about booking",
                order.getGuestId(),
                order.getId()
            )
        );
    }

    @Nonnull
    @Override
    public List<MessageDto> getByBusiness(Long orderId, Long businessId) {
        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        return messageRepository.findAllByOrder(order).stream()
            .map(messageMapper::toDto)
            .toList();
    }

    @Nonnull
    @Override
    public List<MessageDto> getByUser(Long orderId, Long userId) {
        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        return messageRepository.findAllByOrder(order).stream()
            .map(messageMapper::toDto)
            .toList();
    }
}
