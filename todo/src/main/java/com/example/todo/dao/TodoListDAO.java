package com.example.todo.dao;

import com.example.todo.models.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TodoListDAO extends JpaRepository<TodoList, Integer> {
}
