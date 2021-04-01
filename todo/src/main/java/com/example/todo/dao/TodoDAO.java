package com.example.todo.dao;

import com.example.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TodoDAO extends JpaRepository<Todo, Integer> {
}
