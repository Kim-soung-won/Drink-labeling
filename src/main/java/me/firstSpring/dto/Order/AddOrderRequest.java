package me.firstSpring.dto.Order;

import lombok.Getter;
import lombok.Setter;
import me.firstSpring.domain.Enum.OrderState;
import me.firstSpring.domain.Orders;
import me.firstSpring.domain.Price;
import me.firstSpring.domain.UserDetailBasic;

@Getter
@Setter
public class AddOrderRequest {
    private String recipient_address;
    private int count;
    private String recipient_contact_number;
    private OrderState order_state;
    private Long product_id;
    public Orders toEntity(UserDetailBasic user, Price price){
        return Orders.builder()
            .recipient_address(recipient_address)
            .count(count)
            .recipient_contact_number(recipient_contact_number)
            .order_state(order_state)
            .user(user)
            .price(price)
            .build();
    }
}
