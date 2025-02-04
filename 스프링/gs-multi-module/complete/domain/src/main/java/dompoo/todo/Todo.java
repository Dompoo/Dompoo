package dompoo.todo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private String description;
    
    @Column
    private LocalDate deadline;
    
    public static Todo fromNow(String name, String description, int fromNowDays, LocalDate now) {
        return Todo.builder()
                .name(name)
                .description(description)
                .deadline(now.plusDays(fromNowDays))
                .build();
    }
}
