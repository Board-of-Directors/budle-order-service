package ru.nsu.fit.directors.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.orderservice.model.Message;
import ru.nsu.fit.directors.orderservice.model.Order;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderAndBusinessId(Order order, Long businessId);

    List<Message> findAllByOrderAndUserId(Order order, Long userId);
}
