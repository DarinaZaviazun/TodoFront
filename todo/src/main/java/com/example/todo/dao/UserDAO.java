package com.example.todo.dao;

import com.example.todo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserDAO extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
