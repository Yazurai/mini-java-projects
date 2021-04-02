package turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = null;
        PrintStream ps = null;

        if (args.length > 0) {
            if (args[0] != null) {
                try {
                    sc = new Scanner(new File(args[0]));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                sc = new Scanner(System.in);
            }
        } else {
            sc = new Scanner(System.in);
        }

        if (args.length > 1) {
            if (args[1] != null) {
                ps = new PrintStream(new File(args[1]));
            } else {
                ps = new PrintStream(System.out);
            }
        } else {
            ps = new PrintStream(System.out);
        }

        TurtleInterpreter ti = new TurtleInterpreter(sc, ps);
        ti.Process();
    }
}
