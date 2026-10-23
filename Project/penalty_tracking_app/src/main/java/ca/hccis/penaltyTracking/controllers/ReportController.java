package ca.hccis.penaltyTracking.controllers;

import ca.hccis.penaltyTracking.bo.TacklePenaltyBO;
import ca.hccis.penaltyTracking.dao.TacklePenaltyDAO;
import ca.hccis.penaltyTracking.entity.ReportPenalty;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller to administer reports of the project.
 * Includes JDBC report and File I/O export to c:\cis2232\
 * @author Huang Shihheng
 * @since 20260925
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final TacklePenaltyRepository _tpr;

    @Autowired
    public ReportController(TacklePenaltyRepository tpr) {
        _tpr = tpr;
    }

    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    @RequestMapping("/penalty/name")
    public String reportPenaltyName(Model model) {
        logger.info("Running the reports controller penalty name method");
        model.addAttribute("reportInput", new ReportPenalty());
        List<String> refereesList = _tpr.findDistinctReferees();
        if (refereesList != null) {
            refereesList.sort(String.CASE_INSENSITIVE_ORDER);
        }
        model.addAttribute("referees", refereesList);
        return "report/reportPenaltyName";
    }

    @RequestMapping("/penalty/name/submit")
    public String reportPenaltyNameSubmit(Model model, @ModelAttribute("reportInput") ReportPenalty reportPenalty) {

        System.out.println("Name from input form:" + reportPenalty.getName());

        TacklePenaltyBO tacklePenaltyBO = new TacklePenaltyBO();
        ArrayList<TacklePenalty> theList = tacklePenaltyBO.processSelectAllByRefereeTeamName(reportPenalty.getName());
        reportPenalty.setTacklePenalties(theList);

        if (theList != null && theList.isEmpty()) {
            model.addAttribute("message", "No penalties found for that name");
            System.out.println("No data found");
        }

        if (theList != null && !theList.isEmpty()) {
            buildChartData(reportPenalty);
            writeReportToFileNameReport(theList, reportPenalty.getName());
        }

        model.addAttribute("reportInput", reportPenalty);

        List<String> refereesList = _tpr.findDistinctReferees();
        if (refereesList != null) {
            refereesList.sort(String.CASE_INSENSITIVE_ORDER);
        }
        model.addAttribute("referees", refereesList);

        return "report/reportPenaltyName";
    }

    private void buildChartData(ReportPenalty reportPenalty) {
        ArrayList<TacklePenalty> theList = reportPenalty.getTacklePenalties();
        if (theList == null || theList.isEmpty()) {
            return;
        }

        Map<String, Long> penaltyCountMap = theList.stream()
                .filter(p -> p.getPenalty() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getPenalty, Collectors.counting()));
        List<Map.Entry<String, Long>> sortedPenaltyCount = new ArrayList<>(penaltyCountMap.entrySet());
        sortedPenaltyCount.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        List<String> penaltyTypeLabels = sortedPenaltyCount.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> penaltyCountData = sortedPenaltyCount.stream().map(Map.Entry::getValue).collect(Collectors.toList());

        Map<String, Double> penaltyAvgMap = theList.stream()
                .filter(p -> p.getPenalty() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getPenalty,
                        Collectors.averagingDouble(p -> p.getImpactScore() != null ? p.getImpactScore() : 0.0)));
        List<Map.Entry<String, Double>> sortedAvg = new ArrayList<>(penaltyAvgMap.entrySet());
        sortedAvg.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        List<String> penaltyAvgLabels = sortedAvg.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Double> penaltyAvgData = sortedAvg.stream()
                .map(e -> Math.round(e.getValue() * 100.0) / 100.0)
                .collect(Collectors.toList());

        Map<Integer, Long> quarterMap = theList.stream()
                .filter(p -> p.getQuarter() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getQuarter, Collectors.counting()));
        List<String> quarterLabels = Arrays.asList("Q1", "Q2", "Q3", "Q4");
        List<Long> quarterCountData = new ArrayList<>();
        for (int q = 1; q <= 4; q++) {
            quarterCountData.add(quarterMap.getOrDefault(q, 0L));
        }

        Map<String, Long> teamMap = theList.stream()
                .filter(p -> p.getPenalizedTeam() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getPenalizedTeam, Collectors.counting()));
        List<Map.Entry<String, Long>> sortedTeam = new ArrayList<>(teamMap.entrySet());
        sortedTeam.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        List<String> teamLabels = sortedTeam.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> teamCountData = sortedTeam.stream().map(Map.Entry::getValue).collect(Collectors.toList());

        Map<String, Long> dateCountMap = theList.stream()
                .filter(p -> p.getDate() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getDate, Collectors.counting()));
        Map<String, Double> dateImpactMap = theList.stream()
                .filter(p -> p.getDate() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getDate,
                        Collectors.summingDouble(p -> p.getImpactScore() != null ? p.getImpactScore() : 0.0)));
        List<String> dateLabels = new ArrayList<>(dateCountMap.keySet());
        Collections.sort(dateLabels);
        List<Long> dateCountData = dateLabels.stream().map(dateCountMap::get).collect(Collectors.toList());
        List<Double> dateImpactData = dateLabels.stream()
                .map(d -> Math.round(dateImpactMap.getOrDefault(d, 0.0) * 100.0) / 100.0)
                .collect(Collectors.toList());

        Map<String, Long> ageCountMap = theList.stream()
                .filter(p -> p.getAgeDivision() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getAgeDivision, Collectors.counting()));
        Map<String, Double> ageAvgMap = theList.stream()
                .filter(p -> p.getAgeDivision() != null)
                .collect(Collectors.groupingBy(TacklePenalty::getAgeDivision,
                        Collectors.averagingDouble(p -> p.getImpactScore() != null ? p.getImpactScore() : 0.0)));
        List<String> ageDivisionLabels = new ArrayList<>(ageCountMap.keySet());
        ageDivisionLabels.sort(String.CASE_INSENSITIVE_ORDER);
        List<Long> ageDivisionCountData = ageDivisionLabels.stream().map(ageCountMap::get).collect(Collectors.toList());
        List<Double> ageDivisionAvgData = ageDivisionLabels.stream()
                .map(k -> Math.round(ageAvgMap.getOrDefault(k, 0.0) * 100.0) / 100.0)
                .collect(Collectors.toList());

        int total = theList.size();
        double avg = theList.stream().mapToDouble(p -> p.getImpactScore() != null ? p.getImpactScore() : 0.0).average().orElse(0.0);
        double max = theList.stream().mapToDouble(p -> p.getImpactScore() != null ? p.getImpactScore() : 0.0).max().orElse(0.0);
        avg = Math.round(avg * 100.0) / 100.0;
        max = Math.round(max * 100.0) / 100.0;

        reportPenalty.setPenaltyTypeLabels(penaltyTypeLabels);
        reportPenalty.setPenaltyCountData(penaltyCountData);
        reportPenalty.setPenaltyAvgLabels(penaltyAvgLabels);
        reportPenalty.setPenaltyAvgData(penaltyAvgData);
        reportPenalty.setQuarterLabels(quarterLabels);
        reportPenalty.setQuarterCountData(quarterCountData);
        reportPenalty.setTeamLabels(teamLabels);
        reportPenalty.setTeamCountData(teamCountData);
        reportPenalty.setDateLabels(dateLabels);
        reportPenalty.setDateCountData(dateCountData);
        reportPenalty.setDateImpactData(dateImpactData);
        reportPenalty.setAgeDivisionLabels(ageDivisionLabels);
        reportPenalty.setAgeDivisionCountData(ageDivisionCountData);
        reportPenalty.setAgeDivisionAvgData(ageDivisionAvgData);
        reportPenalty.setTotalCount(total);
        reportPenalty.setAverageImpact(avg);
        reportPenalty.setMaxImpact(max);
    }

    @RequestMapping("/penalty/score")
    public String reportPenaltyScore(Model model) {
        logger.info("Running the reports controller penalty score method");
        model.addAttribute("reportInput", new ReportPenalty());
        return "report/reportPenaltyScore";
    }

    @RequestMapping("/penalty/score/submit")
    public String reportPenaltyScoreSubmit(Model model, @ModelAttribute("reportInput") ReportPenalty reportPenalty) {

        System.out.println("Min from input form:" + reportPenalty.getMinScore());
        System.out.println("Max from input form:" + reportPenalty.getMaxScore());

        TacklePenaltyDAO penaltyDAO = new TacklePenaltyDAO();
        ArrayList<TacklePenalty> theList = penaltyDAO.selectAllByImpactScoreMinMax(reportPenalty.getMinScore(), reportPenalty.getMaxScore());
        reportPenalty.setTacklePenalties(theList);

        if (theList != null && theList.isEmpty()) {
            model.addAttribute("message", "No penalties found for that impact score range");
            System.out.println("No data found");
        }

        if (theList != null && !theList.isEmpty()) {
            buildChartData(reportPenalty);
            writeReportToFileScoreReport(theList, reportPenalty.getMinScore(), reportPenalty.getMaxScore());
        }

        model.addAttribute("reportInput", reportPenalty);

        return "report/reportPenaltyScore";
    }

    /**
     * File I/O for Referee/Team report.
     * Creates c:\cis2232\ directory if not exists, then writes report file.
     */
    private void writeReportToFileNameReport(ArrayList<TacklePenalty> theList, String name) {
        try {
            String dirPath = "c:\\cis2232\\";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println("Directory created: " + dirPath + " - " + created);
            }

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String safeName = name != null ? name.replaceAll("[^a-zA-Z0-9]", "") : "Unknown";
            String fileName = "PenaltyByReferee_" + safeName + "_" + timestamp + ".txt";
            File file = new File(dir, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Referee/Team Report for: " + name + "\n");
                writer.write("Generated: " + new Date() + "\n");
                writer.write("Total Records: " + theList.size() + "\n");
                writer.write("--------------------------------------------------\n");
                for (TacklePenalty p : theList) {
                    writer.write(String.format("ID:%d | Date:%s | %s vs %s | Penalty:%s | Q%d | Team:%s | Ref:%s | Score:%.1f\n",
                            p.getId(), p.getDate(), p.getHomeTeam(), p.getAwayTeam(),
                            p.getPenalty(), p.getQuarter(), p.getPenalizedTeam(),
                            p.getReferee(), p.getImpactScore() != null ? p.getImpactScore() : 0.0));
                }
            }
            System.out.println("Report file saved to: " + file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Failed to write name report file", e);
            e.printStackTrace();
        }
    }

    /**
     * File I/O for Impact Score Range report.
     * Creates c:\cis2232\ directory if not exists, then writes report file.
     */
    private void writeReportToFileScoreReport(ArrayList<TacklePenalty> theList, double min, double max) {
        try {
            String dirPath = "c:\\cis2232\\";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println("Directory created: " + dirPath + " - " + created);
            }

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = "PenaltyByScore_" + min + "-" + max + "_" + timestamp + ".txt";
            File file = new File(dir, fileName);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Impact Score Range Report: " + min + " to " + max + "\n");
                writer.write("Generated: " + new Date() + "\n");
                writer.write("Total Records: " + theList.size() + "\n");
                writer.write("--------------------------------------------------\n");
                for (TacklePenalty p : theList) {
                    writer.write(String.format("ID:%d | Date:%s | %s vs %s | Penalty:%s | Q%d | Team:%s | Ref:%s | Score:%.1f\n",
                            p.getId(), p.getDate(), p.getHomeTeam(), p.getAwayTeam(),
                            p.getPenalty(), p.getQuarter(), p.getPenalizedTeam(),
                            p.getReferee(), p.getImpactScore() != null ? p.getImpactScore() : 0.0));
                }
            }
            System.out.println("Report file saved to: " + file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Failed to write score report file", e);
            e.printStackTrace();
        }
    }

}