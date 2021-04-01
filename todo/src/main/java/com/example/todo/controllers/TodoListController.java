package com.example.todo.controllers;

import com.example.todo.dao.TodoListDAO;
import com.example.todo.models.TodoList;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TodoListController {

    private TodoListDAO todoListDAO;

    @GetMapping("/todolists")
    public List<TodoList> getLists(){
        return todoListDAO.findAll();
    }

    @PostMapping("/todolists/search")
    public List<TodoList> searchLists(@RequestBody String word){
        List<TodoList> all = todoListDAO.findAll();
        return all.stream().filter(el -> el.getTitle().toLowerCase().contains(word.toLowerCase())).collect(Collectors.toList());
    }

    @PostMapping("/todolist/save")
    public void saveList(@RequestBody String title){
        System.out.println(title);
        todoListDAO.save(new TodoList(title));
    }

    @PutMapping("/todolist/{id}/update")
    public void updateList(@PathVariable int id,
                           @RequestBody String title){
        TodoList one = todoListDAO.getOne(id);
        one.setTitle(title);
        one.setUpdatedAt(LocalDateTime.now());
        todoListDAO.save(one);
    }

    public void updateListTime(int id) {
        TodoList one = todoListDAO.getOne(id);
        one.setUpdatedAt(LocalDateTime.now());
        todoListDAO.save(one);
    }

    @DeleteMapping("/todolist/{id}/delete")
    public void deleteList(@PathVariable int id){
        todoListDAO.deleteById(id);
    }
}
