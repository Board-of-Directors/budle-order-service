package ru.nsu.fit.directors.orderservice;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.orderservice.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByEstablishmentId(Long establishmentId);
}
