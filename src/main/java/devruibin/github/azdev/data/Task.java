package devruibin.github.azdev.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("tasks")
@Builder
@Data
@ToString
public class Task {
    @Id
    private Long id;
    @NotNull
    @Column("content")
    private String content;
    @Column("tags")
    private String tags;
    @NotNull
    @Column("user_id")
    private Long UserId;
    @NotNull
    @Column("is_private")
    @Builder.Default
    private Boolean isPrivate = false;
    @NotNull
    @Column("approach_count")
    @Builder.Default
    private int approachCount = 0;
    @Column("created_at")
    @Builder.Default
    private Instant createdAt = Instant.now();
}
