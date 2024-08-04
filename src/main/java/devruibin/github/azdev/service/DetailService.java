package devruibin.github.azdev.service;

import devruibin.github.azdev.controller.dto.ApproachDetailInputDTO;
import devruibin.github.azdev.controller.dto.Category;
import devruibin.github.azdev.data.Detail;
import devruibin.github.azdev.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    private Detail updateDetail(Detail existingDetail, ApproachDetailInputDTO detail, Category category) {
        return switch (category) {
            case EXPLANATION -> {
                if(existingDetail.getExplanations() == null) {
                    List<String> explanations = new ArrayList<>();
                    explanations.add(detail.content());
                    existingDetail.setExplanations(explanations);
                } else {
                    existingDetail.getExplanations().add(detail.content());
                }
                yield  existingDetail;
            }
            case NOTE -> {
                if(existingDetail.getNotes() == null) {
                    List<String> notes = new ArrayList<>();
                    notes.add(detail.content());
                    existingDetail.setNotes(notes);
                } else {
                    existingDetail.getNotes().add(detail.content());
                }
                yield existingDetail;
            }
            case WARNING -> {
                if(existingDetail.getWarnings() == null) {
                    List<String> warnings = new ArrayList<>();
                    warnings.add(detail.content());
                    existingDetail.setWarnings(warnings);
                } else {
                    existingDetail.getWarnings().add(detail.content());
                }
                yield existingDetail;
            }
            default -> throw new IllegalArgumentException("Invalid category: " + category);
        };
    }

    public void createDetail(ApproachDetailInputDTO detail, Long pgId) {
        Category category = detail.category();
        detailRepository.findByPgId(pgId).ifPresentOrElse(
                existingDetail -> {
                    Detail updatedDetail = updateDetail(existingDetail, detail, category);
                     detailRepository.save(updatedDetail);
                },
                () -> {
                    Detail newDetail = updateDetail(Detail.builder().pgId(pgId).build(), detail, category);
                    log.info("newDetail: {}", newDetail);
                     detailRepository.save(newDetail);
                }
        );

    }
}
