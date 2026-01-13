package com.sourjelly.likebasketball.club.repository;


import com.sourjelly.likebasketball.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
