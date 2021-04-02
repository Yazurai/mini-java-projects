package turtle;

import turtle.implementation.*;
import turtle.util.Direction;
import turtle.util.Pen;
import turtle.util.Position;
import turtle.util.Rotation;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TurtleInterpreter {
    Scanner scanner;
    PrintStream outStream;

    Paper paper;
    Map<String, Turtle> turtles;


    public TurtleInterpreter(Scanner scanner, PrintStream stream) {
        this.scanner = scanner;
        this.outStream = stream;
        paper = new Paper(10, 10);
        this.turtles = new HashMap<>();
    }

    public void Process() {
        while (scanner.hasNext()) {
            processMethodName();
        }
        scanner.close();
    }

    private void processMethodName() {
        String methodName = scanner.next();
        switch (methodName) {
            case "paper":
                paper();
                break;
            case "new":
                newTurtle("");
                break;
            case "pen":
                pen();
                break;
            case "move":
                move();
                break;
            case "right":
                right();
                break;
            case "left":
                left();
                break;
            case "show":
                show();
                break;
            default:
                break;
        }
    }

    private void paper() {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        paper = new Paper(x, y);
    }

    private Turtle newTurtle(String prefix) {
        Turtle newT = null;
        String type = scanner.next();
        String name = prefix;

        if (type.equals("cluster")) {
            name += scanner.next();
            int tNumb = scanner.nextInt();
            Turtle[] ts = new Turtle[tNumb];

            for (int i = 0; i < tNumb; i++) {
                scanner.next();
                ts[i] = newTurtle(name + ".");
            }

            newT = new ClusterTurtle(ts);
            turtles.put(name, newT);
            return newT;
        } else {
            name += scanner.next();
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            switch (type) {
                case "normal":
                    newT = new NormalTurtle(new Position(x, y), new Direction(0),
                            Pen.DOWN, paper, '*');
                    break;
                case "continuous":
                    newT = new ContinuousTurtle(new Position(x, y), new Direction(0),
                            Pen.DOWN, paper, '*');
                    break;
                case "bouncy":
                    newT = new BouncyTurtle(new Position(x, y), new Direction(0),
                            Pen.DOWN, paper, '*');
                    break;
                case "reflecting":
                    newT = new ReflectingTurtle(new Position(x, y), new Direction(0),
                            Pen.DOWN, paper, '*');
                    break;
                case "wrapping":
                    newT = new WrappingTurtle(new Position(x, y), new Direction(0),
                            Pen.DOWN, paper, '*');
                    break;
                default:
                    break;
            }
            turtles.put(name, newT);
            return newT;
        }
    }

    private void pen() {
        String name = scanner.next();
        String state = scanner.next();
        if (state.equals("up")) {
            turtles.get(name).changePenState(Pen.UP);
        } else {
            if (state.equals("down")) {
                turtles.get(name).changePenState(Pen.DOWN);
            } else {
                turtles.get(name).changeBrush(state.charAt(0));
            }
        }
    }

    private void move() {
        String name = scanner.next();
        int distance = scanner.nextInt();
        turtles.get(name).move(distance);
    }

    private void right() {
        String name = scanner.next();
        int multiplier = scanner.nextInt() / 45;
        turtles.get(name).rotate(Rotation.RIGHT, multiplier);
    }

    private void left() {
        String name = scanner.next();
        int multiplier = scanner.nextInt() / 45;
        turtles.get(name).rotate(Rotation.LEFT, multiplier);
    }

    private void show() {
        String output = paper.toString();
        for (int i = 0; i < paper.getHeight(); i++) {
            outStream.println(output.substring(i * paper.getWidth(),
                    (i + 1) * paper.getWidth()));
        }
        outStream.println();
    }
}