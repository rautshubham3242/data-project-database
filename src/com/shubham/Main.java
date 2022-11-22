package com.shubham;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "admin";
    static final String PASS = "admin123";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = conn.createStatement();
        ) {
            statement.executeUpdate("USE ipl_data_set");

            List<Match> matches = getMatchesData(statement);
            List<Delivery> deliveries = getDeliveriesData(statement);

            HashMap<Integer, Integer> matchesPlayedAllYear = findNumberOfMatchesPlayedPerYear(matches);
            HashMap<String, Integer> matchesWonAllTeam = findNumberOfMatchesWonOfAllTeam(matches);
            HashMap<String, Integer> extraRunsConcededPerTeamIn2016 = findExtraRunsConcededPerTeam(matches, deliveries);
            HashMap<String, Integer> mostEconomicalBowlerIn2015 = findTheMostEconomicalBowlerIn2015(matches, deliveries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<Integer, Integer> findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        HashMap<Integer, Integer> matchesPlayedPerYear = new HashMap<>();
        for (int i = 1; i < matches.size(); i++) {
            int season = matches.get(i).getSeason();
            if (!matchesPlayedPerYear.containsKey(season)) {
                matchesPlayedPerYear.put(season, 1);
            }
            else {
                int seasonValue = matchesPlayedPerYear.get(season);
                matchesPlayedPerYear.put(season, seasonValue + 1);
            }
        }
        return matchesPlayedPerYear;
    }

    private static HashMap<String, Integer> findNumberOfMatchesWonOfAllTeam(List<Match> matches) {
        HashMap<String, Integer> matchesWonOfAllTeam = new HashMap<>();
        for (int i = 1; i < matches.size(); i++) {
            if (!matches.get(i).getResult().equals("no result")){
                String winnerTeam = matches.get(i).getWinner();
                if (!matchesWonOfAllTeam.containsKey(winnerTeam)){
                    matchesWonOfAllTeam.put(winnerTeam, 1);
                }
                else{
                    int winnerValue = matchesWonOfAllTeam.get(winnerTeam);
                    matchesWonOfAllTeam.put(winnerTeam, winnerValue+1);
                }
            }
        }
        return matchesWonOfAllTeam;
    }

    private static HashMap<String, Integer> findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
        HashSet<Integer> matchId2016 = new HashSet<>();
        for (int i = 1; i < matches.size(); i++) {
            int season = matches.get(i).getSeason();
            if (season == 2016){
                matchId2016.add(matches.get(i).getId());
            }
        }

        HashMap<String, Integer> extraRunsConcededPerTeam = new HashMap<>();
        for(int j=1; j < deliveries.size(); j++){
            if (matchId2016.contains(deliveries.get(j).getMatchId())){
                int extraRun = deliveries.get(j).getExtraRuns();
                String bowlingTeam = deliveries.get(j).getBowlingTeam();
                if (!extraRunsConcededPerTeam.containsKey(bowlingTeam)){
                    extraRunsConcededPerTeam.put(bowlingTeam, extraRun);
                }
                else{
                    extraRunsConcededPerTeam.put(bowlingTeam, extraRunsConcededPerTeam.get(bowlingTeam) + extraRun);
                }
            }
        }
        return extraRunsConcededPerTeam;
    }

    private static HashMap<String, Integer> findTheMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        HashSet<Integer> matchID2015 = new HashSet<>();
        for(int i=1; i<matches.size(); i++){
            int season = matches.get(i).getSeason();
            if (season == 2015){
                matchID2015.add(matches.get(i).getId());
            }
        }

        HashMap<String, Integer> runs = new HashMap<>();
        HashMap<String, Integer> balls = new HashMap<>();

        for(int j=1; j < deliveries.size(); j++){
            if (matchID2015.contains(deliveries.get(j).getMatchId())){
                int wideRun = deliveries.get(j).getWideRuns();
                int noBallRun = deliveries.get(j).getNoBallRuns();
                int totalRun = deliveries.get(j).getBatsmanRuns();
                int totalRunForBolls = wideRun + noBallRun + totalRun;
                String bowlerName = deliveries.get(j).getBowler();
                if (!runs.containsKey(deliveries.get(j).getBowler())){
                    runs.put(bowlerName, totalRunForBolls);
                }
                else{
                    int previousRun = runs.get(bowlerName);
                    runs.put(bowlerName, previousRun + totalRunForBolls);
                }

                if (wideRun + noBallRun == 0){
                    if (balls.containsKey(bowlerName)){
                        int previousRun = balls.get(bowlerName);
                        balls.put(bowlerName, previousRun + 1);
                    }
                    else{
                        balls.put(bowlerName, 1);
                    }
                }
            }
        }
        HashMap<String, Integer> economicalPlayer = new HashMap<>();
        for (String bowlerRun: runs.keySet()) {
            int playerRun = runs.get(bowlerRun);
            int bowlerBalls = balls.get(bowlerRun);
            economicalPlayer.put(bowlerRun, playerRun * 6/bowlerBalls);
        }
        return economicalPlayer;
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
                matches.add(match);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matches;
    }
    private static List<Delivery> getDeliveriesData(Statement statement){
        ResultSet rs;
        List<Delivery> deliveries = new ArrayList<>();
        try {
            rs = statement.executeQuery("SELECT * FROM deliveries;");
            while (rs.next()) {
                int deliveryID = rs.getInt("match_id");
                int deliveryInning = rs.getInt("inning");
                String deliveryBattingTeam = rs.getString("batting_team");
                String deliveryBowlingTeam = rs.getString("bowling_team");
                int deliveryOvers = rs.getInt("overs");
                int deliveryBalls = rs.getInt("ball");
                String deliveryBatsman = rs.getString("batsman");
                String deliveryNonStriker = rs.getString("non_striker");
                String deliveryBowler = rs.getString("bowler");
                int deliveryIsSuperOver = rs.getInt("is_super_over");
                int deliveryWideRuns = rs.getInt("wide_runs");
                int deliveryByeRuns = rs.getInt("bye_runs");
                int deliveryLegbyeRuns = rs.getInt("legbye_runs");
                int deliveryNoBallRuns = rs.getInt("noball_runs");
                int deliveryPenaltyRuns = rs.getInt("penalty_runs");
                int deliveryBatsmanRuns = rs.getInt("batsman_runs");
                int deliveryExtraRuns = rs.getInt("extra_runs");
                String deliveryPlayerDismissed = rs.getString("player_dismissed");
                String deliveryDismisselKind = rs.getString("dismissal_kind");
                String deliveryFielder = rs.getString("fielder");

                Delivery delivery = new Delivery();
                delivery.setMatchId(deliveryID);
                delivery.setInning(deliveryInning);
                delivery.setBattingTeam(deliveryBattingTeam);
                delivery.setBowlingTeam(deliveryBowlingTeam);
                delivery.setOver(deliveryOvers);
                delivery.setBall(deliveryBalls);
                delivery.setBatsman(deliveryBatsman);
                delivery.setNonStriker(deliveryNonStriker);
                delivery.setBowler(deliveryBowler);
                delivery.setIsSuperOver(deliveryIsSuperOver);
                delivery.setWideRuns(deliveryWideRuns);
                delivery.setByeRuns(deliveryByeRuns);
                delivery.setLegByeRuns(deliveryLegbyeRuns);
                delivery.setNoBallRuns(deliveryNoBallRuns);
                delivery.setPenaltyRuns(deliveryPenaltyRuns);
                delivery.setBatsmanRuns(deliveryBatsmanRuns);
                delivery.setExtraRuns(deliveryExtraRuns);
                delivery.setPlayerDismissed(deliveryPlayerDismissed);
                delivery.setDismissalKind(deliveryDismisselKind);
                delivery.setFielder(deliveryFielder);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deliveries;
    }
}