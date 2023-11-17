package me.firstSpring.dto.Drink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Drink;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDrinkRequest {
    private String name;
    private float cal;
    private float car;
    private float pro;
    private float fat;
    private String gro;
    private String other;
    private float cafe;
    private float na;

    public Drink toEntity(){
        return Drink.builder()
                .name(name)
                .cal(cal)
                .car(car)
                .pro(pro)
                .fat(fat)
                .gro(gro)
                .other(other)
                .cafe(cafe)
                .na(na)
                .build();
    }
}
