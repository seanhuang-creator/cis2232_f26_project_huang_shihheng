package ca.hccis.penaltyTracking.bo;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Business validation for the Tackle Penalty entity.
 */
public class TacklePenaltyValidationBO {

    /**
     * Validate the business rules for a penalty record.
     *
     * @param tacklePenalty the penalty to validate
     * @return list of validation error messages (empty if valid)
     * @author Huang Shihheng
     * @since 20260925
     */
    public static ArrayList<String> validateTacklePenalty(TacklePenalty tacklePenalty) {

        ArrayList<String> errors = new ArrayList<>();

        String date = tacklePenalty.getDate();
        if (date == null || date.length() != 10) {
            errors.add("Game date must be 10 length");
        } else {
            try {
                LocalDate localDateGameDate = LocalDate.parse(date);
                LocalDate currentDate = LocalDate.now();
                int compareValue = localDateGameDate.compareTo(currentDate);

                if (compareValue > 0) { //current date is less than game date
                    errors.add("Game date can not be in the future");
                }
            } catch (Exception e) {
                errors.add("Could not parse game date");
            }
        }

        Integer quarter = tacklePenalty.getQuarter();
        if (quarter == null || quarter < 1 || quarter > 4) {
            errors.add("Quarter must be between 1 and 4");
        }

        return errors;
    }

}
