package me.firstSpring.dto.Drink;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.Drink;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class DrinkViewResponse {
    private Long id;
    private String name;
    private float cal;
    private float car;
    private float pro;
    private float fat;
    private String group;
    private String other;

    public DrinkViewResponse(Drink drink){
        this.id = drink.getId();
        this.name = drink.getName();
        this.cal = drink.getCal();
        this.car = drink.getCar();
        this.pro = drink.getPro();
        this.fat = drink.getFat();
        this.group = drink.getGroup();
        this.other = drink.getOther();
    }
}
