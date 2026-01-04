package com.sourjelly.likebasketball.user.repository;

import com.sourjelly.likebasketball.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByLoginId(String loginId);

    User findByLoginId(String loginId);
}
