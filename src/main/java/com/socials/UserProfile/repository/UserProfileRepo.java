package com.socials.UserProfile.repository;

import com.socials.UserProfile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile,Integer> {
}
