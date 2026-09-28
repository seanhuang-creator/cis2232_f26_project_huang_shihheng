package ca.hccis.penaltyTracking.dao;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * DAO class to access the db for TacklePenalty records.
 *
 * @author Huang Shihheng
 * @since 20260925
 */
public class TacklePenaltyDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(TacklePenaltyDAO.class);

    public TacklePenaltyDAO() {

        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error(e.toString());
        }

    }

    /**
     * Select all
     *
     * @since 20260925
     * @author Huang Shihheng
     */
    public ArrayList<TacklePenalty> selectAll() {
        ArrayList<TacklePenalty> penalties = null;
        Statement stmt = null;

        //******************************************************************
        //Use the DriverManager to get a connection to our MySql database.
        //******************************************************************
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from TacklePenalty;");

            //******************************************************************
            //Loop through the result set using the next method.
            //******************************************************************
            penalties = loadList(rs);

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return penalties;
    }

    /**
     * Select all by referee or team name
     *
     * @since 20260925
     * @author Huang Shihheng
     */
    public ArrayList<TacklePenalty> selectAllByRefereeTeamName(String name) {
        ArrayList<TacklePenalty> penalties = null;
        Statement stmt = null;

        try {

            stmt = conn.createStatement();
            String sqlStatement = "select * from TacklePenalty " +
                    "where referee = '" + name + "' " +
                    "or homeTeam = '" + name + "' " +
                    "or awayTeam = '" + name + "' " +
                    "or penalizedTeam = '" + name + "';";
            rs = stmt.executeQuery(sqlStatement);

            penalties = loadList(rs);

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return penalties;
    }

    /**
     * Select all by impact score min/max
     *
     * @since 20260925
     * @author Huang Shihheng
     */
    public ArrayList<TacklePenalty> selectAllByImpactScoreMinMax(double min, double max) {
        ArrayList<TacklePenalty> penalties = null;
        Statement stmt = null;

        try {

            stmt = conn.createStatement();
            String sqlStatement = "select * from TacklePenalty " +
                    "where impactScore >= " + min + " and impactScore <= " + max;
            rs = stmt.executeQuery(sqlStatement);

            penalties = loadList(rs);

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing");
            }
        }
        return penalties;
    }

    /**
     * Load the result set into a list of TacklePenalty entities.
     *
     * @param rs the result set
     * @return list of penalties
     * @since 20260925
     * @author Huang Shihheng
     */
    public ArrayList<TacklePenalty> loadList(ResultSet rs) throws SQLException {
        ArrayList<TacklePenalty> penalties = new ArrayList();

        while (rs.next()) {

            TacklePenalty penalty = new TacklePenalty();
            penalty.setId(rs.getInt("id"));
            penalty.setHomeTeam(rs.getString("homeTeam"));
            penalty.setAwayTeam(rs.getString("awayTeam"));
            penalty.setDate(rs.getString("date"));
            penalty.setPenalty(rs.getString("penalty"));
            penalty.setQuarter(rs.getInt("quarter"));
            penalty.setPenalizedTeam(rs.getString("penalizedTeam"));
            int offendingPlayer = rs.getInt("offendingPlayer");
            if (!rs.wasNull()) {
                penalty.setOffendingPlayer(offendingPlayer);
            }
            penalty.setAgeDivision(rs.getString("ageDivision"));
            penalty.setReferee(rs.getString("referee"));
            penalty.setImpactScore(rs.getDouble("impactScore"));

            penalties.add(penalty);
        }
        return penalties;
    }

}
