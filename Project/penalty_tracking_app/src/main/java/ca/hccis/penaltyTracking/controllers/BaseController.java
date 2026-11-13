package ca.hccis.penaltyTracking.controllers;

import ca.hccis.penaltyTracking.dao.TacklePenaltyDAO;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.util.CisUtility;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base controller which control general functionality in the app.
 *
 * @since 20220624
 * @author BJM
 */
@Controller
public class BaseController {

    @Autowired
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Send the user to the welcome view with team-aggregated impact scores.
     *
     * @param timeRange Filter: "all", "1year", "6months", "3months", "1month"
     * @since 20220624
     * @author BJM, Huang Shihheng
     */
    @RequestMapping("/")
    public String home(HttpSession session, Model model,
                       @RequestParam(defaultValue = "all") String timeRange) {

        // BJM 20200602 Issue#1 Set the current date in the session
        String currentDate = CisUtility.getCurrentDate("yyyy-MM-dd");
        session.setAttribute("currentDate", currentDate);

        // Calculate the start date based on timeRange
        String startDate = calculateStartDate(timeRange, currentDate);

        // Fetch penalties using DAO (JDBC) only, no JPA / _tpr
        TacklePenaltyDAO penaltyDAO = new TacklePenaltyDAO();
        ArrayList<TacklePenalty> allPenalties = penaltyDAO.selectAll();
        if (allPenalties == null) {
            allPenalties = new ArrayList<>();
        }

        List<TacklePenalty> penalties;
        if ("all".equals(timeRange)) {
            penalties = allPenalties;
        } else {
            penalties = new ArrayList<>();
            for (TacklePenalty penalty : allPenalties) {
                String date = penalty.getDate();
                if (date != null && date.compareTo(startDate) >= 0) {
                    penalties.add(penalty);
                }
            }
        }

        // Aggregate impact scores by penalizedTeam
        Map<String, Double> teamImpactMap = new LinkedHashMap<>();
        for (TacklePenalty penalty : penalties) {
            String team = penalty.getPenalizedTeam();
            if (team != null && !team.isEmpty()) {
                double currentTotal = teamImpactMap.getOrDefault(team, 0.0);
                teamImpactMap.put(team, currentTotal + penalty.getImpactScore());
            }
        }

        // Sort by total impact score descending
        List<Map.Entry<String, Double>> sortedTeams = new ArrayList<>(teamImpactMap.entrySet());
        sortedTeams.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Convert to lists for chart
        List<String> teamNames = new ArrayList<>();
        List<Double> impactScores = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedTeams) {
            teamNames.add(entry.getKey());
            impactScores.add(entry.getValue());
        }

        model.addAttribute("teamNames", teamNames);
        model.addAttribute("impactScores", impactScores);
        model.addAttribute("timeRange", timeRange);
        model.addAttribute("startDate", startDate);

        return "index";
    }

    /**
     * Calculate the start date based on time range selection.
     *
     * @param timeRange  The selected time range
     * @param currentDate Current date in 'yyyy-MM-dd' format
     * @return Start date in 'yyyy-MM-dd' format
     */
    private String calculateStartDate(String timeRange, String currentDate) {
        if ("all".equals(timeRange)) {
            return "1900-01-01"; // Effectively no filter
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.parse(currentDate, formatter);

        switch (timeRange) {
            case "1year":
                return today.minusYears(1).format(formatter);
            case "6months":
                return today.minusMonths(6).format(formatter);
            case "3months":
                return today.minusMonths(3).format(formatter);
            case "1month":
                return today.minusMonths(1).format(formatter);
            default:
                return "1900-01-01";
        }
    }

    /**
     * Send the user to the about view.
     *
     * @since 20220624
     * @author BJM
     */
    @RequestMapping("/about")
    public String about() {
        return "other/about";
    }
}