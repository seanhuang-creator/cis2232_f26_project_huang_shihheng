package ca.hccis.penaltyTracking.graphql;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import ca.hccis.penaltyTracking.bo.TacklePenaltyBO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class TacklePenaltyGraphQLController {

    private final TacklePenaltyRepository repo;

    public TacklePenaltyGraphQLController(TacklePenaltyRepository repo) {
        this.repo = repo;
    }

    @QueryMapping
    public List<TacklePenalty> penalties() {
        List<TacklePenalty> all = new ArrayList<>();
        repo.findAll().forEach(all::add);
        return all;
    }

    @QueryMapping
    public TacklePenalty penaltyById(@Argument Integer id) {
        Optional<TacklePenalty> opt = repo.findById(id);
        return opt.orElse(null);
    }

    @QueryMapping
    public List<TacklePenalty> findByTeam(@Argument String name) {
        //Union of the three team fields to find any team matching the name
        HashSet<TacklePenalty> set = new HashSet<>();
        set.addAll(repo.findByHomeTeamContaining(name));
        set.addAll(repo.findByAwayTeamContaining(name));
        set.addAll(repo.findByPenalizedTeamContaining(name));
        return new ArrayList<>(set);
    }

    @QueryMapping
    public List<TacklePenalty> findByReferee(@Argument String name) {
        return repo.findByRefereeContaining(name);
    }

    @MutationMapping
    public TacklePenalty createPenalty(@Argument TacklePenaltyInput input) {
        TacklePenalty entity = input.toEntity();
        TacklePenaltyBO.calculateImpactScore(entity);
        return repo.save(entity);
    }

    @MutationMapping
    public TacklePenalty updatePenalty(@Argument Integer id, @Argument TacklePenaltyInput input) {
        TacklePenalty entity = input.toEntity();
        entity.setId(id);
        TacklePenaltyBO.calculateImpactScore(entity);
        return repo.save(entity);
    }

    @MutationMapping
    public Boolean deletePenalty(@Argument Integer id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
