package ru.nsu.fit.directors.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;
import ru.nsu.fit.directors.orderservice.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByEstablishmentId(Long establishmentId);

    @Query("select o from Order o " +
        "where o.establishmentId = :establishmentId and (o.status = :status or :status is null)")
    List<Order> findAllByEstablishmentAndStatus(Long establishmentId, OrderStatus status);

    @Query("select o from Order o " +
        "where o.guestId = :guestId and (o.status = :status or :status is null)")
    List<Order> findAllByUserAndStatus(Long guestId, OrderStatus status);
}
