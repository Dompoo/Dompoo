package dompoo.api;

import dompoo.todo.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoResponse {
    
    private final String name;
    private final String description;
    private final LocalDate deadline;
    
    private static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .name(todo.getName())
                .description(todo.getDescription())
                .deadline(todo.getDeadline())
                .build();
    }
    
    public static List<TodoResponse> fromList(List<Todo> todos) {
        return todos.stream()
                .map(TodoResponse::from)
                .toList();
    }
}
