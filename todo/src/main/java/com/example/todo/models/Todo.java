package com.example.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "todoList")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String body;
    private LocalDate finalDate = LocalDate.now() ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TodoList todoList;

    public Todo(String title, String body, LocalDate finalDate) {
        this.title = title;
        this.body = body;
        this.finalDate = finalDate;
    }
}
