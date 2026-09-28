package ca.hccis.penaltyTracking.repositories;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TacklePenalty entity database operations.
 *
 * @author Huang Shihheng
 * @since 20260925
 */
@Repository
public interface TacklePenaltyRepository extends CrudRepository<TacklePenalty, Integer> {

    List<TacklePenalty> findByHomeTeamContaining(String name);
    List<TacklePenalty> findByAwayTeamContaining(String name);
    List<TacklePenalty> findByPenalizedTeamContaining(String name);
    List<TacklePenalty> findByRefereeContaining(String name);

    @Query("SELECT DISTINCT t.homeTeam FROM TacklePenalty t UNION " +
            "SELECT DISTINCT t.awayTeam FROM TacklePenalty t UNION " +
            "SELECT DISTINCT t.penalizedTeam FROM TacklePenalty t ORDER BY 1 ASC")
    List<String> findAllDistinctTeamNames();

    @Query("SELECT DISTINCT t.referee FROM TacklePenalty t WHERE t.referee IS NOT NULL AND t.referee != '' ORDER BY t.referee ASC")
    List<String> findDistinctReferees();

    /**
     * Find all penalties with date greater than or equal to the specified date.
     *
     * @param startDate The start date in 'yyyy-MM-dd' format
     * @return List of penalties on or after the start date
     * @author Huang Shihheng
     * @since 20260927
     */
    List<TacklePenalty> findByDateGreaterThanEqual(String startDate);

}