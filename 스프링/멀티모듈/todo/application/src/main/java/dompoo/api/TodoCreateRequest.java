package dompoo.api;

import dompoo.todo.Todo;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class TodoCreateRequest {
    
    private final String name;
    private final String description;
    private final int deadlineDays;
    
    public Todo toDomain(LocalDate now) {
        return Todo.fromNow(name, description, deadlineDays, now);
    }
}
