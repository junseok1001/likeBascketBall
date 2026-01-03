package com.sourjelly.likebasketball.user.repository;

import com.sourjelly.likebasketball.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
