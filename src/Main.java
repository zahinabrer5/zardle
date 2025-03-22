import javax.swing.*;
import java.util.Scanner;

/**
 * <strong><u>Wordle Game Assignment</u></strong>
 * <ul>
 * <li>By: Zahin Abrer</li>
 * <li>Date Submitted: April 3, 2023</li>
 * <li>Class: ICS3U-02</li>
 * <li>Teacher: Mr. Hudson</li>
 * </ul>
 */

public class Main {
    static boolean hard;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREY = "\u001B[30m";

    public static void main(String[] args) throws Exception {
        // the following line enables OpenGL on Linux and Windows
        // it causes glitches on repl.it for some reason
        System.setProperty("sun.java2d.opengl", "true");

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        System.out.printf("""
                Welcome to Zardle!
                ----------------------------------------
                
                Quick Rules:
                Guess a five letter word. You will be given the following hints:
                1. Correct letters in the correct position will be %sg%sreen.
                2. Correct letters in the wrong position will be y%se%sllow.
                3. Letters not in the  word will be dar%sk%s grey.
                
                **Click the question mark icon in the GUI for more info!!!! However,
                for some reason, the help window sometimes takes a while to open. It's
                impossible to gain focus on the main window while the help window is
                rendering the HTML, probably due to some weird Java Swing bug.**
                
                The game includes a GUI which accepts both keyboard input from your
                physical keyboard and from the game's builtin on-screen keyboard.
                
                Please respond to the following prompt to select whether or not you'd
                like to play the game in hard mode. Hard mode includes a larger word
                list and includes more uncommon or rare words.
                %n""", ANSI_GREEN, ANSI_RESET, ANSI_YELLOW, ANSI_RESET, ANSI_GREY, ANSI_RESET);

        Scanner sc = new Scanner(System.in);
        System.out.print("Run as hard mode? Enter Y/y for yes or anything else for no: ");
        hard = sc.nextLine().equalsIgnoreCase("y");

        int waitTime = 3;
        System.out.println("\nA GUI should appear in about "+waitTime+" seconds... good luck!!!!");
        System.out.println("Make sure there's an 'Output' window open if using repl.it");
        Thread.sleep(waitTime*1000);
        new Window();
    }
}
