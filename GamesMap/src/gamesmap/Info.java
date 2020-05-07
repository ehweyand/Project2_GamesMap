package gamesmap;

import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class Info {

    public int number_reviews;
    public int number_mediocre;
    public int number_action;
    public double percent_mediocre;
    public double total_scores;
    public ArrayList<Double> list_scores;
    public String best_game;
    public Double best_score;
    public String worst_game;
    public Double worst_score;

    public Info() {
        this.list_scores = new ArrayList<>();
    }

    public double getMeanScores() {
        return this.total_scores / this.number_reviews;
    }

    public double getScoresStdDeviation() {
        double sum = 0;
        double stdDeviation = 0;
        int size = this.list_scores.size();
        for (Double d : this.list_scores) {
            stdDeviation += Math.pow(d - this.getMeanScores(), 2);
        }
        return Math.sqrt(stdDeviation / size);
    }
}
