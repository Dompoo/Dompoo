package dompoo.todo.repository;

import dompoo.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryJpaImpl implements TodoRepository {
    
    private final TodoDataJpaRepository repository;
    
    @Override
    public List<Todo> findAll() {
        return repository.findAll();
    }
    
    @Override
    public boolean save(Todo todo) {
        repository.save(todo);
        return true;
    }
    
    @Override
    public List<Todo> findAllByDeadlineFromNow(LocalDate deadline) {
        return repository.findByDeadline(deadline);
    }
}
