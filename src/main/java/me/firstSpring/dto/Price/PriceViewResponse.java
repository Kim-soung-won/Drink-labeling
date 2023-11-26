package me.firstSpring.dto.Price;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.domain.Price;

@NoArgsConstructor
@Getter
public class PriceViewResponse {
    private Long id;
    private String name;
    private int price;
    private int size;
    private String gro;
    private String brand;
    public PriceViewResponse(Price price){
        this.id = price.getId();
        this.name = price.getName();
        this.price = price.getPrice();
        this.size = price.getSize();
        this.gro = price.getGro();
        this.brand = price.getBrand();
    }
}
