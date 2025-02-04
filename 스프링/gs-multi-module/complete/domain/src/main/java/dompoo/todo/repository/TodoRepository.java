package dompoo.todo.repository;

import dompoo.todo.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository {
    
    List<Todo> findAll();
    
    boolean save(Todo todo);
    
    List<Todo> findAllByDeadlineFromNow(LocalDate deadline);
}
