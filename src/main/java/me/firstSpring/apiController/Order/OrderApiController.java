package me.firstSpring.apiController.Order;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Orders;
import me.firstSpring.dto.Order.AddOrderRequest;
import me.firstSpring.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @PostMapping("/api/orders")
    public ResponseEntity<Orders> saveOrder(@RequestBody AddOrderRequest request, Authentication authentication){
        Orders saveOrder = orderService.save(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrder);
    }
}
