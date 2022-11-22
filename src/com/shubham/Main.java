package com.shubham;
import java.sql.*;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "admin";
    static final String PASS = "admin123";

    private static final String numberOfMatchesPlayedPerYearQuery = "SELECT season, count(season) as matches_played FROM matches GROUP BY season;";
    private static final String NumberOfMatchesWonOfAllTeamQuery = "SELECT winner, count(winner) FROM matches GROUP BY winner;";
    private static final String extraRunsConcededPerTeamIn2016Query = "SELECT bowling_team, sum(extra_runs) FROM deliveries WHERE match_id in ( SELECT id FROM matches WHERE season = 2016) GROUP BY bowling_team;";
    private static final String mostEconomicalBowlerIn2015Query = "SELECT bowler, sum(wide_runs+ noball_runs+batsman_runs)*6/count(all(wide_runs+noball_runs=0))  FROM deliveries WHERE match_id in (SELECT id FROM matches WHERE season=2015) GROUP BY bowler;";
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();
            ){
            statement.executeUpdate("USE ipl_data_set");
            ResultSet matchesPlayedAllYear = findNumberOfMatchesPlayedPerYear(statement);
            ResultSet matchesWonAllTeam = findNumberOfMatchesWonOfAllTeam(statement);
            ResultSet extraRunsConcededPerTeamIn2016 = findExtraRunsConcededPerTeam(statement);
            ResultSet mostEconomicalBowlerIn2015 = findTheMostEconomicalBowlerIn2015(statement);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet findNumberOfMatchesPlayedPerYear(Statement statement) {
        ResultSet rs;
        try {
            rs = statement.executeQuery(Main.numberOfMatchesPlayedPerYearQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    private static ResultSet findNumberOfMatchesWonOfAllTeam(Statement statement) {
        ResultSet rs;
        try {
            rs = statement.executeQuery(Main.NumberOfMatchesWonOfAllTeamQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    private static ResultSet findExtraRunsConcededPerTeam(Statement statement) {
        ResultSet rs;
        try {
            rs = statement.executeQuery(Main.extraRunsConcededPerTeamIn2016Query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    private static ResultSet findTheMostEconomicalBowlerIn2015(Statement statement) {
        ResultSet rs;
        try {
            rs = statement.executeQuery(Main.mostEconomicalBowlerIn2015Query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}