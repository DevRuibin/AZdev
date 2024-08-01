package devruibin.github.azdev.service;

import devruibin.github.azdev.data.Detail;
import devruibin.github.azdev.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DetailService {
    private final DetailRepository detailRepository;
    public Iterable<Detail> getDetails() {
        Iterable<Detail> details = detailRepository.findAll();
        details.forEach(detail -> log.info("detail: {}", detail));
        return details;
    }
}
