package databases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import java.util.Scanner;

public class HighScores {
    int maxscores;
    ArrayList<HighScore> highscores;

    public HighScores(int maxscores){
        this.maxscores = maxscores;
        this.highscores = new ArrayList<>();
        readHighScores();
    }

    private void readHighScores(){
        highscores.clear();
        try{
            File f = new File("leaderboard.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                highscores.add(new HighScore(data[0], Integer.parseInt(data[1])));
             }
        } catch(IOException e) {
            
        }
    }
    
    public ArrayList<HighScore> getHighScores() {
        return highscores;
    }

    public void putHighScore(String name, int score) {
        if (highscores.size() < maxscores) {
            insertScore(name, score);
        } else {
            int worstScore = highscores.get(highscores.size() - 1).getScore();
            if (worstScore < score) {
                highscores.remove(highscores.size() - 1);
                insertScore(name, score);
            }
        }
    }

    private void sortHighScores() {
        Collections.sort(highscores, (HighScore t, HighScore t1) -> t1.getScore() - t.getScore());
    }

    private void insertScore(String name, int score) {
        highscores.add(new HighScore(name, score));
        sortHighScores();
        
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("leaderboard.txt", false));
            for(int i=0; i<highscores.size(); i++){
                HighScore sc = highscores.get(i);
                writer.append(sc.getName() + "\t" + String.valueOf(sc.getScore()) + "\n");
            }
            writer.close();
        } catch (IOException e){
            System.err.println("Cannot open the file");
        }
    }
}
