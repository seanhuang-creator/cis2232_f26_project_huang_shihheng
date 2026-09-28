package ca.hccis.penaltyTracking.entity;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO to hold a list of TacklePenalty records for the list edit page.
 */
public class TacklePenaltyDto {
    private List<TacklePenalty> penalties;

    // default and parameterized constructor

    public TacklePenaltyDto() {
        penalties = new ArrayList<TacklePenalty>();
    }

    public TacklePenaltyDto(List<TacklePenalty> penalties) {
        this.penalties = penalties;
    }

    public void addTacklePenalty(TacklePenalty tacklePenalty) {
        this.penalties.add(tacklePenalty);
    }

    public List<TacklePenalty> getPenalties() {
        return penalties;
    }

    public void setPenalties(List<TacklePenalty> penalties) {
        this.penalties = penalties;
    }
}
