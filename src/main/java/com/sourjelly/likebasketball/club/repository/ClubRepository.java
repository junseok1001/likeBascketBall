package com.sourjelly.likebasketball.club.repository;


import com.sourjelly.likebasketball.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByUserId(long userId);
}
