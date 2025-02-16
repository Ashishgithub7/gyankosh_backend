package com.example.gyankosh.Repository;

import com.example.gyankosh.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long aLong);
    Optional<User> findByEmail(String email);

}
