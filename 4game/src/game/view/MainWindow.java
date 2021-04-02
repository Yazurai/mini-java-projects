package game.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MainWindow extends BaseWindow {
    
    /**
     * Creates a main window where the user can choose from a set of different game sizes. 
     * Clicking on these buttons open a new window with a game initialized.
     */
    public MainWindow() {
        JButton small = new JButton();
        small.setText("3 x 3");
        small.addActionListener(getPlayButtonListener(TableSize.SMALL));
        
        JButton medium = new JButton();
        medium.setText("5 x 5");
        medium.addActionListener(getPlayButtonListener(TableSize.MEDIUM));

        JButton large = new JButton();
        large.setText("7 x 7");
        large.addActionListener(getPlayButtonListener(TableSize.LARGE));
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(small);
        getContentPane().add(medium);
        getContentPane().add(large);
    }
    
    /**
     * An action listener that creates a new game window with the given game table size
     * @param size The size of the game table/area
     */
    private ActionListener getPlayButtonListener(final TableSize size) {
        return new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow window = new GameWindow(size);
                window.setVisible(true);
            }
        };
    }
    
    /**
     * Close the main window instead of disposing it. (unlike the game windows)
     */
    @Override
    protected void doUponExit() {
        System.exit(0);
    }
}
