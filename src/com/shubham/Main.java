package com.shubham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "admin";
    static final String PASS = "admin123";

//    public static final int MATCH_ID = 0;
//    public static final int MATCH_SEASON = 1;
//    public static final int MATCH_CITY = 2;
//    public static final int MATCH_DATE = 3;
//    public static final int MATCH_TEAM1 = 4;
//    public static final int MATCH_TEAM2 = 5;
//    public static final int MATCH_TOSS_WINNER = 6;
//    public static final int MATCH_TOSS_DECISION = 7;
//    public static final int MATCH_RESULT = 8;
//    public static final int MATCH_DL_APPLIED = 9;
//    public static final int MATCH_WINNER = 10;
//    public static final int MATCH_WIN_BY_RUNS = 11;
//    public static final int MATCH_WIN_BY_WICKETS = 12;
//    public static final int MATCH_PLAYER_OF_MATCH = 13;
//    public static final int MATCH_VENUE = 14;
//
//    public static final int DELIVERY_MATCH_ID = 0;
//    public static final int DELIVERY_INNING = 1;
//    public static final int DELIVERY_BATTING_TEAM = 2;
//    public static final int DELIVERY_BOWLING_TEAM = 3;
//    public static final int DELIVERY_OVER = 4;
//    public static final int DELIVERY_BALL = 5;
//    public static final int DELIVERY_BATSMAN = 6;
//    public static final int DELIVERY_NON_STRIKER = 7;
//    public static final int DELIVERY_BOWLER = 8;
//    public static final int DELIVERY_IS_SUPER_OVER = 9;
//    public static final int DELIVERY_WIDE_RUNS = 10;
//    public static final int DELIVERY_BYE_RUNS = 11;
//    public static final int DELIVERY_LEG_BYE_RUNS = 12;
//    public static final int DELIVERY_NO_BALL_RUNS = 13;
//    public static final int DELIVERY_PENALTY_RUNS = 14;
//    public static final int DELIVERY_BATSMAN_RUNS = 15;
//    public static final int DELIVERY_EXTRA_RUNS = 16;
//    public static final int DELIVERY_TOTAL_RUNS = 17;

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate("USE ipl_data_set");

            List<Match> matches = getMatchesData(statement);
            List<Delivery> deliveries = getDeliveriesData(statement);
            ResultSet matchesPlayedAllYear = findNumberOfMatchesPlayedPerYear(matches);
            ResultSet matchesWonAllTeam = findNumberOfMatchesWonOfAllTeam(matches);
            ResultSet extraRunsConcededPerTeamIn2016 = findExtraRunsConcededPerTeam(matches, deliveries);
            ResultSet mostEconomicalBowlerIn2015 = findTheMostEconomicalBowlerIn2015(matches, deliveries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet findNumberOfMatchesPlayedPerYear(List<Match> matches) {
    }

    private static ResultSet findNumberOfMatchesWonOfAllTeam(List<Match> matches) {
    }

    private static ResultSet findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
    }

    private static ResultSet findTheMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
    }


    private static List<Match> getMatchesData(Statement statement){
        ResultSet rs;
        List<Match> matches = new ArrayList<>();


        try {
            rs = statement.executeQuery("SELECT * FROM matches;");
            while (rs.next()){
                int matchID = rs.getInt("id");
                int matchSeason = rs.getInt("season");
                String matchCity = rs.getString("city");
                String matchDate = rs.getString("dates");
                String matchTeam1 = rs.getString("team1");
                String matchTeam2 = rs.getString("team2");
                String matchTossWinner = rs.getString("toss_winner");
                String matchTossDecision = rs.getString("toss_decision");
                String matchResult = rs.getString("result");
                int matchDlApplied = rs.getInt("dl_applied");
                String matchWinner = rs.getString("winner");
                int matchWinByRuns = rs.getInt("win_by_runs");
                int matchWinByWickets = rs.getInt("win_by_wickets");
                String matchPlayer = rs.getString("player_of_match");
                String matchVenue = rs.getString("venue");
                String matchUmpire1 = rs.getString("umpire1");
                String matchUmpire2 = rs.getString("umpire2");
                String matchUmpire3 = rs.getString("umpire3");

                Match match = new Match();
                match.setId(matchID);
                match.setSeason(matchSeason);
                match.setCity(matchCity);
                match.setDate(matchDate);
                match.setTeam1(matchTeam1);
                match.setTeam2(matchTeam2);
                match.setTossWinner(matchTossWinner);
                match.setTossDecision(matchTossDecision);
                match.setResult(matchResult);
                match.setDlApplied(matchDlApplied);
                match.setWinner(matchWinner);
                match.setWinByRuns(matchWinByRuns);
                match.setWinByWickets(matchWinByWickets);
                match.setPlayerOFMatch(matchPlayer);
                match.setVenue(matchVenue);
                match.setUmpire1(matchUmpire1);
                match.setUmpire2(matchUmpire2);
                match.setUmpire3(matchUmpire3);
                matches.add(match)
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matches;
    }
    private static List<Delivery> getDeliveriesData(Statement statement) throws SQLException {
        statement.executeQuery("SELECT * FROM deliveries;")

    }



}