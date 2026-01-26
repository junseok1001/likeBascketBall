package com.sourjelly.likebasketball.club.repository;

import com.sourjelly.likebasketball.club.domain.Club;
import com.sourjelly.likebasketball.club.domain.ClubMember;
import com.sourjelly.likebasketball.club.domain.ClubmemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubmemberId> {
}
