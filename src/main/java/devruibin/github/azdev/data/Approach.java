package devruibin.github.azdev.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("approaches")
@Builder
@Data
@ToString
public class Approach {
    @Id
    Long id;
    @Column("content")
    @NotNull
    String content;
    @Column("user_id")
    @NotNull
    Long userId;
    @Column("task_id")
    @NotNull
    Long taskId;
    @Column("vote_count")
    @Builder.Default
    @NotNull
    Integer voteCount = 0;
    @Column("created_at")
    @Builder.Default
    @NotNull
    private Instant createdAt = Instant.now();


}
