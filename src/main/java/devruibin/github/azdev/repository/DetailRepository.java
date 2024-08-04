package devruibin.github.azdev.repository;

import devruibin.github.azdev.data.Detail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DetailRepository extends MongoRepository<Detail, String> {
    Optional<Detail> findByPgId(Long pgId);
}
