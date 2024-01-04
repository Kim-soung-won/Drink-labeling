package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Enum.OrderState;
import me.firstSpring.domain.UserDetailBasic;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Orders {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient_address;
    private int count;
    @Column(length = 20)
    private String recipient_contact_number;
    private LocalDateTime order_time;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OrderState order_state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetailBasic userId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Price price;
}
