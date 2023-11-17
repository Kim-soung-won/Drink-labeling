package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "cal", nullable = false)
    private float cal;

    @Column(name = "car", nullable = false)
    private float car;

    @Column(name = "pro", nullable = false)
    private float pro;

    @Column(name = "fat", nullable = false)
    private float fat;

    @Column(name = "gro", nullable = true)
    private String gro;

    @Column(name = "other", nullable = true)
    private String other;

    @Column(name = "cafe", nullable = true)
    private float cafe;

    @Column(name = "na", nullable = true)
    private float na;

    @Builder
    public Drink(String name, float cal, float car, float pro, float fat, String gro, String other, float cafe,float na) {
        this.name = name;
        this.cal = cal;
        this.car = car;
        this.pro = pro;
        this.fat = fat;
        this.gro = gro;
        this.other = other;
        this.cafe = cafe;
        this.na=na;
    }
    public void update(String name, float cal, float car, float pro, float fat, String gro, String other, float cafe,float na) {
        this.name = name;
        this.cal = cal;
        this.car = car;
        this.pro = pro;
        this.fat = fat;
        this.gro = gro;
        this.other = other;
        this.cafe = cafe;
        this.na=na;
    }
}
