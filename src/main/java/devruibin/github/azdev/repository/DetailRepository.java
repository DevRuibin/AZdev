package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Detail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetailRepository extends MongoRepository<Detail, String> {
}
