package com.group2.capstone.EBPaymentSystem.billing.repository;

import com.group2.capstone.EBPaymentSystem.billing.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
    @Query(value = "SELECT user_profile_id FROM users_profile_properties WHERE properties_id = ?1", nativeQuery = true)
    Long findByPropertyID(long id);

}
