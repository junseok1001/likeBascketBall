package com.sourjelly.likebasketball.matching.repository;

import com.sourjelly.likebasketball.matching.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
}
