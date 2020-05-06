
package gamesmap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import projeto1.SimpleReader;

public class GameReviewAnalyser {
    
    private static class GameReview {
        
        private String name;
        private String platform;
        private String rating;
        private BigDecimal score;
        private String genre;
        private String recommended;
        private Integer yearOfRelease;

        public GameReview() {}

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

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
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

        public Integer getYearOfRelease() {
            return yearOfRelease;
        }

        public void setYearOfRelease(Integer yearOfRelease) {
            this.yearOfRelease = yearOfRelease;
        }
        
    }
    
    public static void main(String[] args) {
        
        Map<String, Integer> map = new TreeMap<>();
        
        SimpleReader file = new SimpleReader("C:\\Users\\T-Gamer\\Downloads\\game-reviews\\game-reviews.csv");
        
        String line = file.readLine();
        
        List<GameReview> gameReviews = new ArrayList<>();
        
        while ( line != null ) {
            String[] fields = line.split(";");
            
            GameReview gameReview = new GameReview();
                
            gameReview.setName(fields[0]);
            gameReview.setPlatform(fields[1]);
            gameReview.setRating(fields[2]);
            gameReview.setScore(new BigDecimal(fields[3]));
            gameReview.setGenre(fields[4]);
            gameReview.setRecommended(fields[5]);
            gameReview.setYearOfRelease(Integer.valueOf(fields[6]));
            
            gameReviews.add(gameReview);
            
            System.out.println(line);
            
            line = file.readLine();
        }
        
        file.close();
        
        List<GameReview> yearReviews = gameReviews.stream().filter(g -> g.getYearOfRelease() == Calendar.getInstance().get(Calendar.YEAR)).collect(Collectors.toList());
        
        List<Integer> idList = gameReviews.stream().map(GameReview::getYearOfRelease).collect(Collectors.toSet());
    }
    
}
