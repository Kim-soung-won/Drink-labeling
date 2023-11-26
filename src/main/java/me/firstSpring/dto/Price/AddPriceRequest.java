package me.firstSpring.dto.Price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Price;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPriceRequest {
    private String name;
    private int price;
    private int size;
    private String gro;
    private String brand;

    public Price toEntity(){
        return Price.builder()
                .name(name)
                .price(price)
                .size(size)
                .gro(gro)
                .brand(brand)
                .build();
    }
}
