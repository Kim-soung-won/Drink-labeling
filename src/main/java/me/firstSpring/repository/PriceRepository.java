package me.firstSpring.repository;

import java.util.Optional;
import me.firstSpring.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByName(String name);
    Optional<Price> findByBrand(String brand);
}
