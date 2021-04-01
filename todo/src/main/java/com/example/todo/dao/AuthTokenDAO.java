package com.example.todo.dao;

import com.example.todo.models.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AuthTokenDAO extends JpaRepository<AuthToken, Integer> {
    AuthToken findByToken(String token);
}
