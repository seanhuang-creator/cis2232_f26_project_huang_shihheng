package ca.hccis.penaltyTracking.controllers;

import ca.hccis.penaltyTracking.bo.TacklePenaltyBO;
import ca.hccis.penaltyTracking.bo.TacklePenaltyValidationBO;
import ca.hccis.penaltyTracking.entity.TacklePenaltyDto;
import ca.hccis.penaltyTracking.jpa.entity.CodeValue;
import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.CodeValueRepository;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Controller to administer the TacklePenalty crud.
 *
 * @author Huang Shihheng
 * @since 20260925
 */
@Controller
@RequestMapping("/tacklepenalty")
public class TacklePenaltyController {

    private final TacklePenaltyRepository _tpr;
    private final CodeValueRepository _cvr;

    @Autowired
    public TacklePenaltyController(TacklePenaltyRepository tpr, CodeValueRepository cvr) {
        _tpr = tpr;
        _cvr = cvr;
    }

    @Autowired
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(TacklePenaltyController.class);

    @RequestMapping("")
    public String home(Model model, HttpSession session) {

        //Example for dynamic list using CodeValue
        //Work with the CodeValueRepository
        Iterable<CodeValue> codeValues = _cvr.findAll();
        ArrayList<CodeValue> penaltyTypes = new ArrayList<>();
        ArrayList<CodeValue> ageDivisions = new ArrayList<>();
        for (CodeValue codeValue : codeValues) {
            final int CODE_TYPE_ID_PENALTY_TYPES = 2;
            final int CODE_TYPE_ID_AGE_DIVISIONS = 3;
            if (codeValue.getId().getCodeTypeId() == CODE_TYPE_ID_PENALTY_TYPES) {
                penaltyTypes.add(codeValue);
            }
            if (codeValue.getId().getCodeTypeId() == CODE_TYPE_ID_AGE_DIVISIONS) {
                ageDivisions.add(codeValue);
            }
        }
        penaltyTypes.sort((a, b) -> a.getId().getCodeValueSequence().compareTo(b.getId().getCodeValueSequence()));
        ageDivisions.sort((a, b) -> a.getId().getCodeValueSequence().compareTo(b.getId().getCodeValueSequence()));
        session.setAttribute("penaltyTypes", penaltyTypes);
        session.setAttribute("ageDivisions", ageDivisions);

        Iterable<TacklePenalty> penalties = _tpr.findAll();
        model.addAttribute("penalties", penalties);
        model.addAttribute("penalty", new TacklePenalty());
        return "tacklepenalty/list";
    }

    /**
     * Page to delete an entity
     *
     * @param id ID
     * @return redirect to the list page
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        try {
            _tpr.deleteById(id);
            model.addAttribute("messageSuccess", "Penalty deleted");
        } catch (Exception e) {
            //todo what if delete was not successful?
            model.addAttribute("messageError", "Exception deleting penalty");
        }
        Iterable<TacklePenalty> penalties = _tpr.findAll();
        model.addAttribute("penalties", penalties);
        model.addAttribute("penalty", new TacklePenalty());
        return "tacklepenalty/list";
    }

    /**
     * Page to add new entity.
     * @param model
     * @return add
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/add")
    public String add(Model model, HttpSession session) {
        List<String> referees = _tpr.findDistinctReferees();
        model.addAttribute("referees", referees);

        TacklePenalty tacklePenalty = new TacklePenalty();
        model.addAttribute("tacklePenalty", tacklePenalty);

        // Ensure penaltyTypes and ageDivisions are in session
        if (session.getAttribute("penaltyTypes") == null || session.getAttribute("ageDivisions") == null) {
            populateSessionDropdowns(session);
        }

        // Get distinct team names for dropdown (sorted alphabetically)
        List<String> teamNames = _tpr.findAllDistinctTeamNames();
        teamNames.sort(String.CASE_INSENSITIVE_ORDER);
        model.addAttribute("teamNames", teamNames);

        return "tacklepenalty/add";
    }

    /**
     * Helper method to populate session with penalty types and age divisions.
     * @param session HttpSession to store the dropdown data
     */
    private void populateSessionDropdowns(HttpSession session) {
        Iterable<CodeValue> codeValues = _cvr.findAll();
        ArrayList<CodeValue> penaltyTypes = new ArrayList<>();
        ArrayList<CodeValue> ageDivisions = new ArrayList<>();
        for (CodeValue codeValue : codeValues) {
            final int CODE_TYPE_ID_PENALTY_TYPES = 2;
            final int CODE_TYPE_ID_AGE_DIVISIONS = 3;
            if (codeValue.getId().getCodeTypeId() == CODE_TYPE_ID_PENALTY_TYPES) {
                penaltyTypes.add(codeValue);
            }
            if (codeValue.getId().getCodeTypeId() == CODE_TYPE_ID_AGE_DIVISIONS) {
                ageDivisions.add(codeValue);
            }
        }
        penaltyTypes.sort((a, b) -> a.getId().getCodeValueSequence().compareTo(b.getId().getCodeValueSequence()));
        ageDivisions.sort((a, b) -> a.getId().getCodeValueSequence().compareTo(b.getId().getCodeValueSequence()));
        session.setAttribute("penaltyTypes", penaltyTypes);
        session.setAttribute("ageDivisions", ageDivisions);
    }

    /**
     * Page to edit
     *
     * @param id    ID
     * @param model
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session) {

        // Ensure penaltyTypes and ageDivisions are in session
        if (session.getAttribute("penaltyTypes") == null || session.getAttribute("ageDivisions") == null) {
            populateSessionDropdowns(session);
        }

        Optional<TacklePenalty> tacklePenalty = _tpr.findById(id);
        if (tacklePenalty.isPresent()) {
            model.addAttribute("tacklePenalty", tacklePenalty.get());
            // Get distinct team names for dropdown (sorted alphabetically)
            List<String> teamNames = _tpr.findAllDistinctTeamNames();
            teamNames.sort(String.CASE_INSENSITIVE_ORDER);
            model.addAttribute("teamNames", teamNames);

            List<String> referees = _tpr.findDistinctReferees();
            model.addAttribute("referees", referees);

            return "tacklepenalty/add";
        }

        //todo How can we best communicate this to the view.
        model.addAttribute("messageError", "Could not load the penalty");
        Iterable<TacklePenalty> penalties = _tpr.findAll();
        model.addAttribute("penalties", penalties);
        model.addAttribute("penalty", new TacklePenalty());
        return "tacklepenalty/list";
    }

    /**
     * Submit method that processes add and edit and any form submission
     *
     * @param model
     * @param request
     * @param tacklePenalty what is being added or modified
     * @param bindingResult Result of validation
     * @return add with errors or tacklepenalty list
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/submit")
    public String submit(Model model, HttpServletRequest request, @Valid @ModelAttribute("tacklePenalty") TacklePenalty tacklePenalty, BindingResult bindingResult) {
        boolean valid = true;

        //Business validation
        ArrayList<String> validationErrors = TacklePenaltyValidationBO.validateTacklePenalty(tacklePenalty);
        if (validationErrors.size() > 0) {
            valid = false;
        }

        if (!valid || bindingResult.hasErrors()) {
            System.out.println("--------------------------------------------");
            System.out.println("Validation error");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + "-" + error.toString() + "-" + error.getDefaultMessage());
            }
            System.out.println("--------------------------------------------");
            tacklePenalty.setImpactScore(0.0);
            model.addAttribute("tacklePenalty", tacklePenalty);

            // Ensure penaltyTypes and ageDivisions are in session
            if (request.getSession().getAttribute("penaltyTypes") == null || request.getSession().getAttribute("ageDivisions") == null) {
                populateSessionDropdowns(request.getSession());
            }

            // Get distinct team names for dropdown (sorted alphabetically)
            List<String> teamNames = _tpr.findAllDistinctTeamNames();
            teamNames.sort(String.CASE_INSENSITIVE_ORDER);
            model.addAttribute("teamNames", teamNames);

            List<String> referees = _tpr.findDistinctReferees();
            model.addAttribute("referees", referees);

            model.addAttribute("businessValidationErrors", validationErrors);
            return "tacklepenalty/add";
        }

        //Calculate the impact score before saving.
        TacklePenaltyBO.calculateImpactScore(tacklePenalty);
        _tpr.save(tacklePenalty);
        return "redirect:/tacklepenalty";
    }

    /**
     * Search for a team or referee name
     *
     * @param model
     * @param penalty
     * @return view for list
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute("penalty") TacklePenalty penalty) {

        //**********************************************************************
        //Use repository methods created to find any entities which contain
        //the name entered on the list page.
        //**********************************************************************
        String name = penalty.getSearchName();
        if (name == null) {
            name = "";
        }

        List<TacklePenalty> penaltiesHome = _tpr.findByHomeTeamContaining(name);
        List<TacklePenalty> penaltiesAway = _tpr.findByAwayTeamContaining(name);
        List<TacklePenalty> penaltiesPenalized = _tpr.findByPenalizedTeamContaining(name);
        List<TacklePenalty> penaltiesReferee = _tpr.findByRefereeContaining(name);

        //put in set to eliminate duplicates
        HashSet<TacklePenalty> penaltiesSet = new HashSet<>();
        penaltiesSet.addAll(penaltiesHome);
        penaltiesSet.addAll(penaltiesAway);
        penaltiesSet.addAll(penaltiesPenalized);
        penaltiesSet.addAll(penaltiesReferee);

        model.addAttribute("penalties", penaltiesSet);
        logger.debug("searched for name:" + name);
        return "tacklepenalty/list";
    }

    /**
     * Page to edit all records at once
     *
     * @param model
     * @return view for list edit
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/list/edit")
    public String showCreateForm(Model model) {
        TacklePenaltyDto tacklePenaltyForm = new TacklePenaltyDto();

        Iterable<TacklePenalty> penalties = _tpr.findAll();
        ArrayList<TacklePenalty> tempList = new ArrayList<>();
        for (TacklePenalty current : penalties) {
            tempList.add(current);
        }
        tacklePenaltyForm.setPenalties(tempList);
        model.addAttribute("form", tacklePenaltyForm);
        return "tacklepenalty/listedit";
    }

    /**
     * Submit all the edited records
     *
     * @param form
     * @param model
     * @return view for list edit
     * @author Huang Shihheng
     * @since 20260925
     */
    @RequestMapping("/list/edit/submit")
    public String listEditSubmit(@ModelAttribute TacklePenaltyDto form, Model model) {

        //Recalculate all impact scores before saving
        form.getPenalties().forEach(TacklePenaltyBO::calculateImpactScore);

        _tpr.saveAll(form.getPenalties());

        TacklePenaltyDto tacklePenaltyForm = new TacklePenaltyDto();

        Iterable<TacklePenalty> penalties = _tpr.findAll();
        ArrayList<TacklePenalty> tempList = new ArrayList<>();
        for (TacklePenalty current : penalties) {
            tempList.add(current);
        }
        tacklePenaltyForm.setPenalties(tempList);
        model.addAttribute("form", tacklePenaltyForm);
        return "tacklepenalty/listedit";

    }
}