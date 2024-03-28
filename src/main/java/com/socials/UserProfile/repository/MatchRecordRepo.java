package com.socials.UserProfile.repository;

import com.socials.UserProfile.entity.MatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRecordRepo extends JpaRepository<MatchRecord,Integer> {
}
