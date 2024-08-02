package devruibin.github.azdev.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.springframework.data.domain.Persistable;


import java.time.Instant;

@Table
@Data
@Builder
@ToString
@AllArgsConstructor
@Entity(name = "users")
@NoArgsConstructor
public class User implements Persistable<Long> {
    @Id
    private Long id;
    @NotNull
    @Size(min=4, max = 30, message="Name must be at least 5 characters long, and no more than 30 characters")
    private String username;
    @NotNull
    @Column(name = "hashed_password")
    private String password;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column( name = "hashed_auth_token")
    private String authToken;
    @Column(name = "created_at")
    @Builder.Default
    @NotNull
    private Instant createdAt = Instant.now();

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
