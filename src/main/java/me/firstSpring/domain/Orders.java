package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Enum.OrderState;

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
//    private LocalDateTime order_time;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private OrderState order_state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetailBasic user_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Price product_id;

    @Builder
    public Orders(String recipient_address, int count, String recipient_contact_number,
                  OrderState order_state, UserDetailBasic user, Price price) {
        this.recipient_address = recipient_address;
        this.count = count;
        this.recipient_contact_number = recipient_contact_number;
        this.order_state = order_state;
        this.user_id = user;
        this.product_id = price;
//        this.order_time = order_time;
    }
}
