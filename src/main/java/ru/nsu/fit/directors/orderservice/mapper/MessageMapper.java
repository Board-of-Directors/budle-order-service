package ru.nsu.fit.directors.orderservice.mapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.dto.response.MessageDto;
import ru.nsu.fit.directors.orderservice.enums.SenderType;
import ru.nsu.fit.directors.orderservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.orderservice.event.UserMessageEvent;
import ru.nsu.fit.directors.orderservice.model.Message;
import ru.nsu.fit.directors.orderservice.model.Order;

@Component
@ParametersAreNonnullByDefault
public class MessageMapper {
    @Nonnull
    public Message toModel(UserMessageEvent userMessageEvent, Order order) {
        return new Message()
            .setUserId(order.getGuestId())
            .setOrder(order)
            .setSenderType(SenderType.USER)
            .setBusinessId(order.getEstablishmentId())
            .setMessage(userMessageEvent.message());
    }

    @Nonnull
    public Message toModel(BusinessMessageEvent businessMessageEvent, Order order) {
        return new Message()
            .setBusinessId(order.getEstablishmentId())
            .setOrder(order)
            .setSenderType(SenderType.BUSINESS)
            .setUserId(order.getGuestId())
            .setMessage(businessMessageEvent.message());
    }

    @Nonnull
    public MessageDto toDto(Message message) {
        return MessageDto.builder()
            .message(message.getMessage())
            .timestamp(message.getCreated())
            .senderType(message.getSenderType())
            .build();
    }
}
