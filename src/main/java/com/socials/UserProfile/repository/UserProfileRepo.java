package com.socials.UserProfile.repository;

import com.socials.UserProfile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile,Integer> {
    Optional<UserProfile> findByEmail(String email);

    List<UserProfile> findAllByCity(String city);
}
