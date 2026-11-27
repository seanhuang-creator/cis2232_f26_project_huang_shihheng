package ca.hccis.penaltyTracking.client;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import org.springframework.web.client.RestClient;

/**
 * Client to call the REST service and print all penalties.
 *
 * @author Huang Shihheng
 * @since 20260925
 */
public class TacklePenaltyClient {

    public static void main(String[] args) {
        RestClient restClient = RestClient.create();
        String url = "http://localhost:8080/api/TacklePenaltyService/v1/penalties";

        TacklePenalty[] penalties = restClient.get()
                .uri(url)
                .retrieve()
                .body(TacklePenalty[].class);

        if (penalties == null || penalties.length == 0) {
            System.out.println("No penalties found.");
            return;
        }

        System.out.println("Found " + penalties.length + " penalties:");
        for (TacklePenalty penalty : penalties) {
            System.out.println(penalty.getId() + " | "
                    + penalty.getHomeTeam() + " vs " + penalty.getAwayTeam() + " | "
                    + penalty.getDate() + " | "
                    + penalty.getPenalty() + " | Quarter: "
                    + penalty.getQuarter() + " | Penalized: "
                    + penalty.getPenalizedTeam() + " | Player: "
                    + penalty.getOffendingPlayer() + " | Division: "
                    + penalty.getAgeDivision() + " | Referee: "
                    + penalty.getReferee() + " | Impact: "
                    + penalty.getImpactScore());
        }
    }
}