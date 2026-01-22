package com.sourjelly.likebasketball.matching.repository;

import com.sourjelly.likebasketball.matching.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    public List<Matching> findByChallengeClub(long challengeClub);

    public List<Matching> findByAwayClub(long awayClub);
}
