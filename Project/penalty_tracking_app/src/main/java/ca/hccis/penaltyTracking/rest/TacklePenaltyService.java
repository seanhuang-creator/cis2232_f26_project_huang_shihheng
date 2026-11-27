package ca.hccis.penaltyTracking.rest;

import ca.hccis.penaltyTracking.bo.TacklePenaltyBO;
import ca.hccis.penaltyTracking.bo.TacklePenaltyValidationBO;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    public TacklePenaltyService(TacklePenaltyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<TacklePenalty>> getAll() {
        Iterable<TacklePenalty> penaltiesIterable = repo.findAll();
        List<TacklePenalty> penalties = new ArrayList<>();
        penaltiesIterable.forEach(penalties::add);
        return ResponseEntity.ok(penalties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacklePenalty> getById(@PathVariable Integer id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TacklePenalty penalty, BindingResult bindingResult) {
        List<String> errors = collectErrors(penalty, bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        penalty.setId(null);
        TacklePenaltyBO.calculateImpactScore(penalty);
        TacklePenalty saved = repo.save(penalty);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody TacklePenalty penalty, BindingResult bindingResult) {
        List<String> errors = collectErrors(penalty, bindingResult);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        penalty.setId(id);
        TacklePenaltyBO.calculateImpactScore(penalty);
        TacklePenalty saved = repo.save(penalty);
        return ResponseEntity.ok(saved);
    }

    private List<String> collectErrors(TacklePenalty penalty, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
        }
        errors.addAll(TacklePenaltyValidationBO.validateTacklePenalty(penalty));
        return errors;
    }
}