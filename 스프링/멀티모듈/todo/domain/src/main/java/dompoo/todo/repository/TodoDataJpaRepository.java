package dompoo.todo.repository;

import dompoo.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoDataJpaRepository extends JpaRepository<Todo, Long> {
    
    List<Todo> findByDeadline(LocalDate deadline);
}
