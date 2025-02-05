package dompoo.api;

import dompoo.date.DateProvider;
import dompoo.todo.Todo;
import dompoo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    
    private final DateProvider dateProvider;
    private final TodoRepository todoRepository;
    
    public List<TodoResponse> getTodoList() {
        List<Todo> todos = todoRepository.findAll();
        return TodoResponse.fromList(todos);
    }
    
    public List<TodoResponse> getTodoListFromNow() {
        List<Todo> todos = todoRepository.findAllByDeadlineFromNow(dateProvider.now());
        return TodoResponse.fromList(todos);
    }
    
    public String addTodo(TodoCreateRequest request) {
        boolean result = todoRepository.save(request.toDomain(dateProvider.now()));
        if (!result) throw new RuntimeException();
        return "저장되었습니다!";
    }
}
