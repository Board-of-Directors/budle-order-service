package ru.nsu.fit.directors.orderservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import ru.nsu.fit.directors.orderservice.enums.SenderType;

@Entity
@Table(name = "message")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long userId;

    private Long businessId;

    @Enumerated(value = EnumType.STRING)
    private SenderType senderType;

    @Column(name = "message_content")
    private String message;

    @CreationTimestamp
    private LocalDateTime created;

}
