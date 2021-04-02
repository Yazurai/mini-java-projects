package view;

import databases.HighScores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Direction;
import model.Game;

public class MainWindow extends JFrame{
    private static final int GAME_SIZE = 20;
    
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel;    
    private ScheduledExecutorService executor;
    private Future<?> future;
    private HighScores highScores;
    
    public MainWindow() throws IOException{
        game = new Game(GAME_SIZE);
        highScores = new HighScores(5);
        
        setTitle("Labyrinth");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("res/player.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuGameScale = new JMenu("Scale");
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);

        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Leaderboards") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(highScores.getHighScores(), MainWindow.this);
            }
        });
        
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                executor.shutdown();
                System.exit(0);
            }
        });

        JMenuItem item = new JMenuItem(new AbstractAction("New game") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startNewGame();
                    }
                });
        menuGame.add(item);
        menuGame.add(menuGameScale);
        menuGame.add(menuHighScores);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        
        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");

        add(gameStatLabel, BorderLayout.NORTH);
        
        JPanel mazeBorder = new JPanel();
        final int BORDER_SIZE = 20;
        mazeBorder.setBackground(Color.BLACK);
        mazeBorder.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        add(mazeBorder);
                
        try { mazeBorder.add(board = new Board(game), BorderLayout.CENTER); } catch (IOException ex) {}
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                if (!game.isLevelLoaded()) return;
                int kk = ke.getKeyCode();
                Direction d = null;
                switch (kk){
                    case KeyEvent.VK_LEFT:  d = Direction.LEFT; break;
                    case KeyEvent.VK_RIGHT: d = Direction.RIGHT; break;
                    case KeyEvent.VK_UP:    d = Direction.UP; break;
                    case KeyEvent.VK_DOWN:  d = Direction.DOWN; break;
                }
                board.repaint();
                if (d != null && !game.gameFinished()){ 
                    game.stepPlayer(d);
                    checkGameEnd(true);
                } 
            }
        });

        setResizable(false);
        setLocationRelativeTo(null);
        board.refresh();
        startDragon();
        pack();
        setVisible(true);
    }
    
    private void startNewGame(){
        future.cancel(true);
        game.newGame();
        refreshGameStatLabel();
        board.refresh();
        startDragon();
        pack();
    }
    
    private void clear(){
        game.clearLevel();
        refreshGameStatLabel();
        board.refresh();
        startDragon();
        pack();
    }
    
    private void startDragon(){
        Runnable dragonStep = new Runnable() {
            public void run() {
                try{
                    refreshGameStatLabel();
                    board.repaint();
                    game.stepDragon();
                    checkGameEnd(false);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        executor = Executors.newScheduledThreadPool(1);
        future = executor.scheduleAtFixedRate(dragonStep, 0, 500, TimeUnit.MILLISECONDS);
    }
    
    private void checkGameEnd(boolean playerTurn){
        if (game.hasEscaped()){
            future.cancel(true);
            JOptionPane.showMessageDialog(MainWindow.this, "Congratulation, you have escaped!", "Win!", JOptionPane.INFORMATION_MESSAGE);
            clear();
        } else if(game.isDead()){
            if(playerTurn){
                future.cancel(true);
            }
            String name = JOptionPane.showInputDialog(MainWindow.this, "You have died! \n Please enter a name:");
            highScores.putHighScore(name, game.getLevelsCleared());
            startNewGame();
        }
        
    }
    
    private void refreshGameStatLabel(){
        String s = "Levels cleared: " + game.getLevelsCleared();
        gameStatLabel.setText(s);
    }
    
    private void createScaleMenuItems(JMenu menu, double from, double to, double by){
        while (from <= to){
            final double scale = from;
            JMenuItem item = new JMenuItem(new AbstractAction(from + "x") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) pack();
                }
            });
            menu.add(item);
            
            if (from == to) break;
            from += by;
            if (from > to) from = to;
        }
    }
    
    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {}
    }    
}
