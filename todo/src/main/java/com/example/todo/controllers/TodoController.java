package com.example.todo.controllers;

import com.example.todo.dao.TodoDAO;
import com.example.todo.dao.TodoListDAO;
import com.example.todo.models.Todo;
import com.example.todo.models.TodoList;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

    private TodoDAO todoDAO;
    private TodoListDAO todoListDAO;

    @GetMapping("/todolist/{id}/todos")
    public List<Todo> getTodos(@PathVariable int id) {
        TodoList byId = todoListDAO.getOne(id);
        return byId.getTodos();
    }

    @PostMapping("/todolist/{id}/todos/search")
    public List<Todo> searchTodos(@PathVariable int id,
                                      @RequestBody String word){
        List<Todo> all = todoListDAO.getOne(id).getTodos();
        return all.stream().filter(el -> el.getTitle().toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
    }

    @PostMapping("/todolist/{id}/todo/save")
    public void saveTodo(@PathVariable int id,
                         @RequestParam String title,
                         @RequestParam String body,
                         @RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date, formatter);
        TodoList byId = todoListDAO.getOne(id);
        List<Todo> todos = byId.getTodos();
        todos.add(new Todo(title, body, date1));
        byId.setTodos(todos);
        byId.setUpdatedAt(LocalDateTime.now());
        todoListDAO.save(byId);
    }

    @PutMapping("/todolist/{id}/todo/{id2}/update")
    public void updateTodo(@PathVariable int id,
                           @PathVariable int id2,
                           @RequestParam String title,
                           @RequestParam String body,
                           @RequestParam String date) {
        TodoList one = todoListDAO.getOne(id);
        List<Todo> todos = one.getTodos();
        Todo todo = new Todo();
        for(Todo todo2 : todos){
            if (todo2.getId() == id2)
                todo = todo2;
        }
        todo.setTitle(title);
        todo.setBody(body);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date, formatter);
        todo.setFinalDate(date1);
        one.setTodos(todos);
        one.setUpdatedAt(LocalDateTime.now());
        todoListDAO.save(one);
    }

    @DeleteMapping("/todolist/{id}/todo/{id2}/delete")
    public void deleteTodo(@PathVariable int id,
                           @PathVariable int id2){
        TodoList one = todoListDAO.getOne(id);
        List<Todo> todos = one.getTodos();
        Todo todo = new Todo();
        for(Todo todo2 : todos){
            if (todo2.getId() == id2)
                todo = todo2;
        }
        todos.remove(todo);
        todoDAO.deleteById(todo.getId());
        one.setTodos(todos);
        one.setUpdatedAt(LocalDateTime.now());
        todoListDAO.save(one);
    }
}
