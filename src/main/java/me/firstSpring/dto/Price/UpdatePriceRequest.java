package me.firstSpring.dto.Price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Price;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdatePriceRequest {
    private Long id;
    private String name;
    private int price;
    private String brand;
    public UpdatePriceRequest(Price price){
        this.id = price.getId();
        this.name = price.getName();
        this.price = price.getPrice();
        this.brand = price.getBrand();
    }
}
