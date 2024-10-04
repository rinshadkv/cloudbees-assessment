package com.cloudbees.trainbooking.Repository;

import com.cloudbees.trainbooking.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
