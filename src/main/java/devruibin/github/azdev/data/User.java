package devruibin.github.azdev.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("users")
@Data
@Builder
@ToString
public class User {
    @Id
    private Long id;
    @NotNull
    @Size(min=5, max = 30, message="Name must be at least 5 characters long, and no more than 30 characters")
    private String username;
    @NotNull
    @Column("hashed_password")
    private String password;
    @Column("first_name")
    private String firstname;
    @Column("last_name")
    private String lastname;
    @Column("hashed_auth_token")
    private String authToken;
    @Column("created_at")
    @Builder.Default
    @NotNull
    private Instant createdAt = Instant.now();
}
