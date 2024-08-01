package devruibin.github.azdev.data;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "approachDetails")
@Data
@ToString
@Builder
public class Detail {
    @Id
    private String id;
    @Column(name = "pgId")
    @NotNull
    private Long pgId;

    @Field("explanations")
    private List<String> explanations;

    @Field("notes")
    private List<String> notes;

    @Field("warnings")
    private List<String> warnings;

}

