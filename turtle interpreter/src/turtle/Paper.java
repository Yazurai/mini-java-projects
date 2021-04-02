package turtle;

import turtle.util.*;
import java.util.*;

 public class Paper {
     int width;
     int height;

     char[][] content;

     public Paper() {
         this.width = 0;
         this.height = 0;
         this.content = new char[0][0];
     }

     public Paper(int width, int height) {
         this.width = width;
         this.height = height;
         this.content = new char[height][width];
         for (char[] row : content) {
             Arrays.fill(row, ' ');
         }
     }

     public int getHeight() {
         return this.height;
     }

     public int getWidth() {
         return this.width;
     }

     public boolean checkX(int x) {
         return x >= 0 && x < width;
     }

     public boolean checkY(int y) {
         return y >= 0 && y < height;
     }

     public boolean isInbound(int x, int y) {
         return checkX(x) && checkY(y);
     }

     public boolean isInbound(Position pos) {
         return checkX(pos.getX()) && checkY(pos.getY());
     }

     public void mark(int x, int y, char character) {
         if (isInbound(x, y)) {
             content[y][x] = character;
         }
     }

     @Override
     public String toString() {
         StringBuilder sb = new StringBuilder(width * height);
         for (int y = height - 1; y >= 0; y--) {
             for (int x = 0; x < width; x++) {
                 sb.append(content[y][x]);
             }
             //st += "\n"
         }
         return sb.toString();
     }
 }