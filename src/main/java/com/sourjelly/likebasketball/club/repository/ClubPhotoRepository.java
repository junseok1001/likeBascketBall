package com.sourjelly.likebasketball.club.repository;


import com.sourjelly.likebasketball.club.domain.ClubPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubPhotoRepository extends JpaRepository<ClubPhoto, Long> {
}
