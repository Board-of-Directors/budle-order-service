package ru.nsu.fit.directors.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;

import java.sql.Date;
import java.sql.Time;


@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer guestCount;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Long establishmentId;
    private Long guestId;
    private Long spotId;
    @Transient
    private int duration = 240;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status = OrderStatus.WAITING;

    public Order(Long userId, OrderStatus orderStatus){
        this.guestId = userId;
        this.status = orderStatus;
    }
}
