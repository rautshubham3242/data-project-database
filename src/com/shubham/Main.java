package com.shubham;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "admin";
    static final String PASS = "admin123";

    static final String numberOfMatchesPlayedPerYearQuery = "SELECT season, count(season) FROM matches GROUP BY season;";
    static final String NumberOfMatchesWonOfAllTeamQuery = "SELECT winner, count(winner) FROM matches GROUP BY winner;";
    static final String extraRunsConcededPerTeamIn2016Query = "SELECT bowling_team, sum(extra_runs) FROM deliveries WHERE match_id in ( SELECT id FROM matches WHERE season = 2016) GROUP BY bowling_team;";
    static final String mostEconomicalBowlerIn2015Query = "SELECT bowler, sum(wide_runs+ noball_runs+batsman_runs)*6/count(all(wide_runs+noball_runs=0))  FROM deliveries WHERE match_id in (SELECT id FROM matches WHERE season=2015) GROUP BY bowler;";
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();
            ){
            try {
                HashMap<String, Integer> matchesPlayedAllYear = findNumberOfMatchesPlayedPerYear(statement);
                HashMap<String, Integer> matchesWonAllTeam = findNumberOfMatchesWonOfAllTeam(statement);
                HashMap<String, Integer> extraRunsConcededPerTeamIn2016 = findExtraRunsConcededPerTeam(statement, deliveries);
                HashMap<String, Integer> mostEconomicalBowlerIn2015 = findTheMostEconomicalBowlerIn2015(statement, deliveries);
                HashMap<String, Integer> teamWhoWonTheTossAndMatch = findTheTeamWhoWonTheTossAndWonTheMatch(statement);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}