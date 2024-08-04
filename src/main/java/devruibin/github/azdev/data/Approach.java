package devruibin.github.azdev.data;

import devruibin.github.azdev.controller.dto.SearchResultItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.Instant;

@Entity(name = "approaches")
@Table
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Approach implements SearchResultItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    @NotNull
    private String content;
    @Column(name = "user_id")
    @NotNull
    private Long userId;
    @Column(name = "task_id")
    @NotNull
    private Long taskId;
    @Column(name = "vote_count")
    @Builder.Default
    @NotNull
    private Integer voteCount = 0;
    @Column(name = "created_at")
    @Builder.Default
    @NotNull
    private Instant createdAt = Instant.now();


}
