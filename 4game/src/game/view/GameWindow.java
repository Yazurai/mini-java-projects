package game.view;

import game.model.Field;
import game.model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GameWindow extends BaseWindow {

    private Model model;

    private JButton[][] grid;

    private JLabel score;

    /**
     * Creates a new window with a scoreboard and a table with the given size
     * @param tableSize The size of the game area
     */
    public GameWindow(TableSize tableSize) {
        int size = tableSize.getSize();
        setTitle("4 game - " + size + "x" + size);
        setSize(tableSize.getWidth(), tableSize.getHeight());
        
        score = new JLabel();
        
        model = new Model(size);
        grid = new JButton[size][size];
        
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        score = new JLabel();
        
        JButton newGameButton = new JButton();
        newGameButton.setText("New game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        
        top.add(score);
        top.add(newGameButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }
        
        setLayout(new BorderLayout(10,10));
        add(top, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        updateScreen();
    }

    /**
     * Creates a new game, or in other words resets the current play field.
     */
    private void newGame(){
        model.setup(model.getSize());
        updateScreen();
    }
    
    /**
     * Initialize a new button in the grid
     * @param panel The current game panel
     * @param x The horizontal index                                                                                                                                                                                                                                                                                                                                                                                                                                        he horizontal index
     * @param y The vertical index
     */
    private void addButton(JPanel panel, int x, int y) {
        grid[x][y] = new JButton();
        panel.add(grid[x][y]);

        grid[x][y].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (model == null) {
                    return;
                }

                model.stepOnField(x, y);
                updateScreen();
                
                int remaining = model.getRemaining();

                if (remaining == 0) {
                    if(model.getBluePoint() > model.getRedPoint()){
                        JOptionPane.showMessageDialog(GameWindow.this, "Blue won!");
                    } else {
                        JOptionPane.showMessageDialog(GameWindow.this, "Red won!");
                    }
                    newGame();
                }
            }
        });
    }

    /**
     * Update the entire play field. Goes through every single button and updates them.                                                                                                                                                                         
     */
    private void updateScreen(){
        for (int i = 0; i < model.getSize(); ++i) {
            for (int j = 0; j < model.getSize(); ++j) {
                updateTextOfButton(i, j);
            }
        }
        updateScore();
    }
    
    /**
     * Updates the scoreboard with the blue and red scores and current player.
     */
    private void updateScore(){
        String currentPlayer = model.getPlayer() ? "<span style='color:blue'>Blue</span>" : "<span style='color:red'>Red</span>";
        score.setText("<html><span style='color:blue'>Blue</span>: " + model.getBluePoint() + "<br>" 
                      + "<span style='color:red'>Red</span>: " + model.getRedPoint() + "<br>"
                      + "Current player: " + currentPlayer);
    }
    
    /**
     * Updates a single button in the grid.
     * @param x The horizontal index                                                                                                                                                                                                                                                                                                                                                                                                                                        he horizontal index
     * @param y The vertical index
     */
    private void updateTextOfButton(int x, int y) {
        Field f = model.getField(x, y);
        int num = f.getValue();
        if (num == 0) {
            grid[x][y].setText("");
        } else {
            grid[x][y].setText(String.valueOf(num));
        }
        switch(f.getState()){
            case EMPTY:
                grid[x][y].setEnabled(true);
                grid[x][y].setBorder(UIManager.getBorder("Button.border"));
                break;
            case BLUE:
                grid[x][y].setEnabled(false);
                grid[x][y].setBorder(BorderFactory.createLineBorder(Color.blue, 3)); 
                break;
            case RED:
                grid[x][y].setEnabled(false);
                grid[x][y].setBorder(BorderFactory.createLineBorder(Color.red, 3)); 
                break;
            default:
                break;
        }
        
    }
}
