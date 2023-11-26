package me.firstSpring.dto.Price;

import lombok.Getter;
import me.firstSpring.domain.Price;

@Getter
public class PriceListViewResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final int size;
    private final String gro;
    private final String brand;
    public PriceListViewResponse(Price price){
        this.id = price.getId();
        this.name = price.getName();
        this.price = price.getPrice();
        this.size = price.getSize();
        this.gro = price.getGro();
        this.brand = price.getBrand();
    }
}
