package ru.nsu.fit.directors.orderservice.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.orderservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.orderservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.orderservice.model.Order;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class OrderMapper {
    private final ModelMapper mapper;

    @Autowired
    public OrderMapper(ModelMapper mapper) {
        final int bookingDurationMinutes = 240;
        final Converter<LocalDate, Date> converterDate = (src) -> Date.valueOf(src.getSource());

        final Converter<LocalTime, Time> converterToStartTime = (src) ->
            Time.valueOf(src.getSource());

        final Converter<LocalTime, Time> converterTime = (src) ->
            Time.valueOf(src.getSource().plusMinutes(bookingDurationMinutes));

        mapper.createTypeMap(OrderCreatedEvent.class, Order.class)
            .addMappings(mapping -> mapping.using(converterToStartTime)
                .map(
                    OrderCreatedEvent::getTime,
                    Order::setStartTime
                ))
            .addMappings(mapping -> mapping.using(converterTime)
                .map(OrderCreatedEvent::getTime, Order::setEndTime))
            .addMappings(mapping -> mapping.using(converterDate)
                .map(OrderCreatedEvent::getDate, Order::setDate));
        this.mapper = mapper;
    }

    public Order toEntity(OrderCreatedEvent dto) {
        return mapper.map(dto, Order.class);
    }

    public ResponseOrderDto toResponse(Order order) {
        return mapper.map(order, ResponseOrderDto.class);
    }
}
