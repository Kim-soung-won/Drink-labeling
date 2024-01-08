package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "drink_id",  referencedColumnName = "name")
    private Drink drink;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "size", nullable = false)
    private int size;

    @Column(name = "gro", nullable = false, length=10)
    private String gro;

    @Column(name = "brand", nullable = false, length = 10)
    private String brand;

    @Builder
    public Price(String name, int price, int size, String gro, String brand) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.gro = gro;
        this.brand = brand;
    }
    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
