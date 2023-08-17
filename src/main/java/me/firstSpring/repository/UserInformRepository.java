package me.firstSpring.repository;

import me.firstSpring.domain.Inform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInformRepository extends JpaRepository<Inform, Long> {
    Optional<Inform> findByUserId(Long userId);
}
