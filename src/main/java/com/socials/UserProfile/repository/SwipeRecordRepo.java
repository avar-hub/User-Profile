package com.socials.UserProfile.repository;

import com.socials.UserProfile.entity.SwipeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwipeRecordRepo extends JpaRepository<SwipeRecord,Integer> {


    Optional<SwipeRecord> findByUserEmailAndUserEmailLiked(String userEmail, String userEmailLiked);

}
