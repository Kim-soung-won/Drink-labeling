package me.firstSpring.repository;

import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailBasicRepository extends JpaRepository<UserDetailBasic, Long> {
    UserDetailBasic findByUser(User user_id);
}
