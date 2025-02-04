package dompoo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    
    private final TodoService todoService;
    
    @GetMapping("/all")
    public ResponseEntity<List<TodoResponse>> getTodoList() {
        return ResponseEntity.ok(todoService.getTodoList());
    }
    
    @GetMapping("/todo")
    public ResponseEntity<List<TodoResponse>> getTodoListFromNow() {
        return ResponseEntity.ok(todoService.getTodoListFromNow());
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@RequestBody TodoCreateRequest request) {
        return ResponseEntity.ok(todoService.addTodo(request));
    }
}
