package com.moon.demo01.repository;

import com.moon.demo01.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);

    @Query("{ 'email': ?0 }")
    User findByEmail(String email);
}
