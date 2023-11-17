package me.firstSpring.dto.Drink;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Drink;

@NoArgsConstructor
@Getter
public class DrinkViewResponse {
    private Long id;
    private String name;
    private float cal;
    private float car;
    private float pro;
    private float fat;
    private String gro;
    private String other;
    private float cafe;
    private float na;
    public DrinkViewResponse(Drink drink){
        this.id = drink.getId();
        this.name = drink.getName();
        this.cal = drink.getCal();
        this.car = drink.getCar();
        this.pro = drink.getPro();
        this.fat = drink.getFat();
        this.gro = drink.getGro();
        this.other = drink.getOther();
        this.cafe = drink.getCafe();
        this.na=drink.getNa();
    }
}
