package devruibin.github.azdev.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Table
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
public class Task {
    @Id
    private Long id;
    @NotNull
    @Column(name = "content")
    private String content;
    @Column(name = "tags")
    private String tags;
    @NotNull
    @Column(name = "user_id")
    private Long UserId;
    @NotNull
    @Column(name = "is_private")
    @Builder.Default
    private Boolean isPrivate = false;
    @NotNull
    @Column(name = "approach_count")
    @Builder.Default
    private int approachCount = 0;
    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
