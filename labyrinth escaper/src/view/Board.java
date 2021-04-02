package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import model.Game;
import model.Position;
import res.ResourceLoader;

public class Board extends JPanel {

    private Game game;
    private final Image dragon, destination, player;
    private double scale;
    private int scaledSize;
    private final int CELL_SIZE = 32;

    public Board(Game g) throws IOException {
        game = g;
        scale = 1.0;
        scaledSize = (int) (scale * CELL_SIZE);
        setBackground(Color.BLACK);
        dragon = ResourceLoader.loadImage("res/dragon.png");
        destination = ResourceLoader.loadImage("res/destination.png");
        player = ResourceLoader.loadImage("res/player.png");
    }

    public boolean setScale(double scale) {
        this.scale = scale;
        scaledSize = (int) (scale * CELL_SIZE);
        return refresh();
    }

    public boolean refresh() {
        if (!game.isLevelLoaded()) {
            return false;
        }
        Dimension dim = new Dimension(game.getLevelCols() * scaledSize + 1, game.getLevelRows() * scaledSize + 1);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }

    private void printEntireMap(Graphics2D gr, Position p, Position d, Position gPos) {
        for (int y = 0; y < game.getLevelRows(); y++) {
            for (int x = 0; x < game.getLevelCols(); x++) {
                game.getCell(x, y).draw(gr, scaledSize);
                if (d.x == x && d.y == y) {
                    gr.drawImage(dragon, d.x * scaledSize, d.y * scaledSize, scaledSize, scaledSize, null);
                }
                if (gPos.x == x && gPos.y == y) {
                    gr.drawImage(destination, gPos.x * scaledSize, gPos.y * scaledSize, scaledSize, scaledSize, null);
                }
            }
        }
    }

    private void printWithFog(Graphics2D gr, Position p, Position d, Position gPos) {
        for (int y = p.y - 3; y <= p.y + 3; y++) {
            for (int x = p.x - 3; x <= p.x + 3; x++) {
                if (0 <= y && y < game.getLevelRows() && 0 <= x && x < game.getLevelCols()) {
                    game.getCell(x, y).draw(gr, scaledSize);
                    if (d.x == x && d.y == y) {
                        gr.drawImage(dragon, d.x * scaledSize, d.y * scaledSize, scaledSize, scaledSize, null);
                    }
                    if (gPos.x == x && gPos.y == y) {
                        gr.drawImage(destination, gPos.x * scaledSize, gPos.y * scaledSize, scaledSize, scaledSize, null);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!game.isLevelLoaded()) {
            return;
        }
        Graphics2D gr = (Graphics2D) g;
        Position p = game.getPlayerPos();
        Position d = game.getDragonPos();
        Position gPos = game.getGoalPos();

        //printEntireMap(gr, p, d, gPos);
        printWithFog(gr, p, d, gPos);

        gr.drawImage(player, p.x * scaledSize, p.y * scaledSize, scaledSize, scaledSize, null);
    }
}
