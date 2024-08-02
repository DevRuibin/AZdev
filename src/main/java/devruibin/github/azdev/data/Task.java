package devruibin.github.azdev.data;

import devruibin.github.azdev.controller.dto.SearchResultItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;

import java.time.Instant;

@Table
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
@Slf4j
public class Task implements SearchResultItemDTO, Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean isNew() {
        log.info("isNew: {}", this.getId() == null);
        return this.getId() == null;
    }
}
