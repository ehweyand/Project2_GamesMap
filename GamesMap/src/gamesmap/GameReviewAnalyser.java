
package gamesmap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import projeto1.SimpleReader;


public class GameReviewAnalyser {
    
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    
    public static void main(String[] args) {
        
        Map<String, ReviewTotals> totais = new TreeMap<>();
        
        SimpleReader file = new SimpleReader("C:\\Users\\T-Gamer\\Downloads\\game-reviews\\game-reviews.csv");
        
        String line = file.readLine();
        line = file.readLine();
        
        while ( line != null ) {
            String[] fields = line.split(";");
            
            GameReview gameReview = new GameReview(fields);
            ReviewTotals reviewTotal;
            
            //System.out.println(line);
            
            if ( !totais.containsKey(gameReview.getYearOfRelease()) ) {
                reviewTotal = new ReviewTotals();
                
                reviewTotal.setReviews(1);
                reviewTotal.setTotalScores(gameReview.getScore());
                reviewTotal.setBestGame(gameReview.getName());
                reviewTotal.setBestScore(gameReview.getScore());
                reviewTotal.setWorstGame(gameReview.getName());
                reviewTotal.setWorstScore(gameReview.getScore());
                reviewTotal.setScores(new ArrayList<>());
                reviewTotal.getScores().add(gameReview.getScore());
                
                if ( gameReview.getRating().equals("Mediocre") ) {
                    reviewTotal.setMediocreReviews(1);
                } else {
                    reviewTotal.setMediocreReviews(0);
                }
                
                if ( gameReview.getGenre().equals("Action") ) {
                    reviewTotal.setActionGames(1);
                } else {
                    reviewTotal.setActionGames(0);
                }
                
                totais.put(gameReview.getYearOfRelease(), reviewTotal);
            } else {
                reviewTotal = totais.get(gameReview.getYearOfRelease());
                
                reviewTotal.setReviews(reviewTotal.getReviews() + 1);
                reviewTotal.setTotalScores(reviewTotal.getTotalScores() + gameReview.getScore());
                reviewTotal.getScores().add(gameReview.getScore());
                
                if ( gameReview.getRating().equals("Mediocre") ) {
                    reviewTotal.setMediocreReviews(reviewTotal.getMediocreReviews() + 1);
                }
                
                if ( gameReview.getGenre().equals("Action") ) {
                    reviewTotal.setActionGames(reviewTotal.getActionGames()+ 1);
                }
                
                if ( gameReview.getScore().compareTo(reviewTotal.getBestScore()) > 0 ) {
                    reviewTotal.setBestGame(gameReview.getName());
                    reviewTotal.setBestScore(gameReview.getScore());
                }
                
                if ( gameReview.getScore().compareTo(reviewTotal.getWorstScore()) < 0 ) {
                    reviewTotal.setWorstGame(gameReview.getName());
                    reviewTotal.setWorstScore(gameReview.getScore());
                }
                        
                totais.put(gameReview.getYearOfRelease(), reviewTotal);
            }
            
            line = file.readLine();
        }
        
        file.close();
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Relatório:");
        System.out.println();
        
        for ( String year : totais.keySet() ) {
            ReviewTotals reviewTotal = totais.get(year);
            
            System.out.println("Ano: " + year);
            System.out.println("Total de reviews: " + reviewTotal.getReviews());
            System.out.println("% de reviews mediocres: " + df2.format(reviewTotal.getPercentualMediocre()));
            System.out.println("Média: " + df2.format(reviewTotal.getMedia()));
            System.out.println("Desvio padrão: " + df2.format(reviewTotal.getDesvioPadrao()));
            System.out.println("Melhor jogo: " + reviewTotal.getBestGame() + " - " + reviewTotal.getBestScore());
            System.out.println("Pior jogo: " + reviewTotal.getWorstGame() + " - " + reviewTotal.getWorstScore());
            System.out.println("Jogos de ação: " + reviewTotal.getActionGames());
            System.out.println();
            System.out.println();
        }
        
        System.out.println("Ano com mais jogos de ação: " + Collections.max(totais.entrySet(), (entry1, entry2) -> entry1.getValue().getActionGames() - entry2.getValue().getActionGames()).getKey());
        System.out.println();
        
    }
    
    private static class GameReview {
        
        private String name;
        private String platform;
        private String rating;
        private Double score;
        private String genre;
        private String recommended;
        private String yearOfRelease;

        public GameReview() {}
        
        public GameReview(String[] fields) {
            name = fields[0];
            platform = fields[1];
            rating = fields[2];
            score = Double.parseDouble(fields[3]);
            genre = fields[4];
            recommended = fields[5];
            yearOfRelease = fields[6];
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getRecommended() {
            return recommended;
        }

        public void setRecommended(String recommended) {
            this.recommended = recommended;
        }

        public String getYearOfRelease() {
            return yearOfRelease;
        }

        public void setYearOfRelease(String yearOfRelease) {
            this.yearOfRelease = yearOfRelease;
        }
        
    }
    
    public static class ReviewTotals {
        
        private Integer reviews;
        private Integer mediocreReviews;
        private Double totalScores;
        private String bestGame;
        private Double bestScore;
        private String worstGame;
        private Double worstScore;
        private Integer actionGames;
        private List<Double> scores;

        public ReviewTotals() {}

        public Integer getReviews() {
            return reviews;
        }

        public void setReviews(Integer reviews) {
            this.reviews = reviews;
        }

        public Integer getMediocreReviews() {
            return mediocreReviews;
        }

        public void setMediocreReviews(Integer mediocreReviews) {
            this.mediocreReviews = mediocreReviews;
        }

        public Double getTotalScores() {
            return totalScores;
        }

        public void setTotalScores(Double totalScores) {
            this.totalScores = totalScores;
        }

        public String getBestGame() {
            return bestGame;
        }

        public void setBestGame(String bestGame) {
            this.bestGame = bestGame;
        }

        public Double getBestScore() {
            return bestScore;
        }

        public void setBestScore(Double bestScore) {
            this.bestScore = bestScore;
        }

        public String getWorstGame() {
            return worstGame;
        }

        public void setWorstGame(String worstGame) {
            this.worstGame = worstGame;
        }

        public Double getWorstScore() {
            return worstScore;
        }

        public void setWorstScore(Double worstScore) {
            this.worstScore = worstScore;
        }

        public Integer getActionGames() {
            return actionGames;
        }

        public void setActionGames(Integer actionGames) {
            this.actionGames = actionGames;
        }

        public List<Double> getScores() {
            return scores;
        }

        public void setScores(List<Double> scores) {
            this.scores = scores;
        }
        
        public Double getPercentualMediocre() {
            return ((double)mediocreReviews / reviews ) * 100;
        }
        
        public Double getMedia() {
            return totalScores / reviews;
        }
        
        public Double getDesvioPadrao() {
            Double desvioPadrao = scores.stream().mapToDouble(s -> Math.pow(s - getMedia(), 2)).average().orElse(0);
            
            return Math.sqrt(desvioPadrao);
        }
        
    }
    
}
