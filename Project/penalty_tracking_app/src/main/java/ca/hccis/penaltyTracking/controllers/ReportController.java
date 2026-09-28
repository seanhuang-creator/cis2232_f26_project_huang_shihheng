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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller to administer reports of the project.
 *
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

    /**
     * Send the user to list of reports view.
     *
     * @param model
     * @param session
     * @return To the appropriate view
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }

    /**
     * Method to send user to the referee/team name report.
     *
     * @param model Model object for the view
     * @return view for list
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/penalty/name")
    public String reportPenaltyName(Model model) {
        logger.info("Running the reports controller penalty name method");
        model.addAttribute("reportInput", new ReportPenalty());

        // 使用跟 TacklePenaltyController 完全一樣的方式取得並排序 referees
        List<String> refereesList = _tpr.findDistinctReferees();
        if (refereesList != null) {
            refereesList.sort(String.CASE_INSENSITIVE_ORDER);
        }
        model.addAttribute("referees", refereesList);

        return "report/reportPenaltyName";
    }

    /**
     * Process the report - name
     * @param model Model object for the view
     * @param reportPenalty Object containing inputs for the report
     * @return view to show report
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/penalty/name/submit")
    public String reportPenaltyNameSubmit(Model model, @ModelAttribute("reportInput") ReportPenalty reportPenalty) {

        System.out.println("Name from input form:" + reportPenalty.getName());

        //Go to the db and get the appropriate penalties using jdbc (BO)
        TacklePenaltyBO tacklePenaltyBO = new TacklePenaltyBO();
        ArrayList<TacklePenalty> theList = tacklePenaltyBO.processSelectAllByRefereeTeamName(reportPenalty.getName());
        reportPenalty.setTacklePenalties(theList);

        //Add a message in case the report does not contain any data
        if (theList != null && theList.isEmpty()) {
            model.addAttribute("message", "No penalties found for that name");
            System.out.println("No data found");
        }

        //Put object in model so it can be used on the view (html)
        model.addAttribute("reportInput", reportPenalty);

        // 提交後重新載入畫面也必須帶入排序好的 referees，否則下拉選單會變空白
        List<String> refereesList = _tpr.findDistinctReferees();
        if (refereesList != null) {
            refereesList.sort(String.CASE_INSENSITIVE_ORDER);
        }
        model.addAttribute("referees", refereesList);

        return "report/reportPenaltyName"; //Send user to another view.
    }

    /**
     * Method to send user to the impact score range report.
     *
     * @param model
     * @return view for list
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/penalty/score")
    public String reportPenaltyScore(Model model) {
        logger.info("Running the reports controller penalty score method");
        model.addAttribute("reportInput", new ReportPenalty());
        return "report/reportPenaltyScore";
    }

    /**
     * Process the report - impact score range
     * @param model
     * @param reportPenalty Object containing inputs for the report
     * @return view to show report
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/penalty/score/submit")
    public String reportPenaltyScoreSubmit(Model model, @ModelAttribute("reportInput") ReportPenalty reportPenalty) {

        System.out.println("Min from input form:" + reportPenalty.getMinScore());

        //Go to the db and get the appropriate penalties using jdbc (DAO)
        TacklePenaltyDAO penaltyDAO = new TacklePenaltyDAO();
        ArrayList<TacklePenalty> theList = penaltyDAO.selectAllByImpactScoreMinMax(reportPenalty.getMinScore(), reportPenalty.getMaxScore());
        reportPenalty.setTacklePenalties(theList);

        //Add a message in case the report does not contain any data
        if (theList != null && theList.isEmpty()) {
            model.addAttribute("message", "No penalties found for that impact score range");
            System.out.println("No data found");
        }

        //Put object in model so it can be used on the view (html)
        model.addAttribute("reportInput", reportPenalty);

        return "report/reportPenaltyScore"; //Send user to another view.
    }

}