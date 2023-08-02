package com.group2.capstone.EBPaymentSystem.authentication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value="select * from users where user_profile_id is not null",nativeQuery=true)
	List<User> findAllConsumer();
   
}
