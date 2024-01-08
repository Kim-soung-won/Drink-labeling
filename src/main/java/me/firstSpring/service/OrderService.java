package me.firstSpring.service;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Orders;
import me.firstSpring.domain.Price;
import me.firstSpring.domain.UserDetailBasic;
import me.firstSpring.dto.Order.AddOrderRequest;
import me.firstSpring.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserDetailService userDetailService;
    private final UserService userService;
    private final PriceService priceService;
    public Orders save(AddOrderRequest request, Authentication authentication){
        String email = authentication.getName();
        System.out.println("getName : "+ email);
        UserDetailBasic user = userDetailService.findByUser(userService.findByEmail(email));
        return orderRepository.save(request.toEntity(user,priceService.findById(request.getProduct_id())));
    }
}
