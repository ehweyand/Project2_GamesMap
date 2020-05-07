/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamesmap;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author evand
 */
public class GamesMap {

    public static void main(String[] args) {
        Map<String, Info> map = new TreeMap<>();
        DecimalFormat df = new DecimalFormat("0.00");
        SimpleReader file = new SimpleReader("src/gamesmap/game-reviews.csv");
        String line = file.readLine();
        line = file.readLine();

        while (line != null) {
            String[] col = line.split(";");
            String title = col[0];
            String platform = col[1];
            String score_phrase = col[2];
            double score = Double.parseDouble(col[3]);
            String genre = col[4];
            String year = col[6];

            if (!map.containsKey(year)) { // INSERT
                Info i = new Info();
                i.number_reviews = 1;

                if (score_phrase.equals("Mediocre")) {
                    i.number_mediocre = 1;
                }
                if (genre.equals("Action")) {
                    i.number_action = 1;
                }
                i.total_scores = score;
                i.list_scores.add(score);

                i.best_game = title;
                i.best_score = score;
                i.worst_game = title;
                i.worst_score = score;

                map.put(year, i);
            } else { // UPDATE
                Info i = map.get(year);
                i.number_reviews += 1;
                if (score_phrase.equals("Mediocre")) {
                    i.number_mediocre += 1;
                }
                if (genre.equals("Action")) {
                    i.number_action += 1;
                }

                i.percent_mediocre = ((double) i.number_mediocre / i.number_reviews) * 100;
                i.total_scores += score;
                i.list_scores.add(score);

                if (score > i.best_score) {
                    i.best_game = title;
                    i.best_score = score;
                }
                if (score < i.worst_score) {
                    i.worst_game = title;
                    i.worst_score = score;
                }
            }
            line = file.readLine();
        }

        //impressão       
        for (String y : map.keySet()) {
            Info i = map.get(y);
            System.out.println("------------------------------------");
            System.out.println("Ano: " + y + "\nNº Reviews: " + i.number_reviews + "\nMedíocre: " + df.format(i.percent_mediocre) + "% "
                    + "\nScore médio: " + df.format(i.getMeanScores()) + "\nDesvio padrão: " + df.format(i.getScoresStdDeviation()) + " "
                    + "\nMelhor Jogo: " + i.best_game + " | Score: " + i.best_score + " "
                    + "\nPior Jogo: " + i.worst_game + " | Score: " + i.worst_score);
            System.out.println("------------------------------------\n\n");
        }
        int actions = 0;
        String ano = "";
        for (String y : map.keySet()) {
            Info i = map.get(y);
            if (i.number_action > actions) {
                actions = i.number_action;
                ano = y;
            }
        }
        System.out.println("Qual o ano em que foi lançado um maior número de jogos do gênero Action");
        System.out.println("Resposta: " + ano);
    }

}
