package me.firstSpring.repository;

import java.util.Optional;
import me.firstSpring.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findByName(String name);
    Optional<Drink> findByGroup(String group);
}
