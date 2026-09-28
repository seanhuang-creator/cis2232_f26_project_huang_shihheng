package ca.hccis.penaltyTracking.rest;

import ca.hccis.penaltyTracking.bo.TacklePenaltyBO;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for accessing using REST.
 *
 * @author Huang Shihheng
 * @since 20260925
 */
@RestController
@RequestMapping("/api/TacklePenaltyService/v1/penalties")
public class TacklePenaltyService {

    private final TacklePenaltyRepository repo;

    @Autowired
    public TacklePenaltyService(TacklePenaltyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<TacklePenalty>> getAll() {

        Iterable<TacklePenalty> penaltiesIterable = repo.findAll();
        List<TacklePenalty> penalties = new ArrayList<>();
        penaltiesIterable.forEach(penalties::add);

        if (penalties == null || penalties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(penalties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacklePenalty> getById(@PathVariable Integer id) {

        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        return repo.findById(id)
                .map(entity -> {
                    repo.delete(entity);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<TacklePenalty> create(
            @RequestBody TacklePenalty obj) {

        TacklePenaltyBO.calculateImpactScore(obj);
        TacklePenalty saved = repo.save(obj);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacklePenalty> update(
            @PathVariable Integer id,
            @RequestBody TacklePenalty obj) {

        obj.setId(id);
        TacklePenaltyBO.calculateImpactScore(obj);

        return ResponseEntity.ok(repo.save(obj));
    }
}
