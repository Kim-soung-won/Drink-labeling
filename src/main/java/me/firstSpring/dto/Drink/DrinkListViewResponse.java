package me.firstSpring.dto.Drink;

import lombok.Getter;
import me.firstSpring.domain.Drink;

@Getter
public class DrinkListViewResponse {
    private final Long id;
    private final String name;
    private final float cal;
    private final float car;
    private final float pro;
    private final float fat;
    private final String gro;
    private final String other;
    public DrinkListViewResponse(Drink drink){
        this.id = drink.getId();
        this.name = drink.getName();
        this.cal = drink.getCal();
        this.car = drink.getCar();
        this.pro = drink.getPro();
        this.fat = drink.getFat();
        this.gro = drink.getGro();
        this.other = drink.getOther();
    }
}
