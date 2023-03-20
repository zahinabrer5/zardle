import javax.swing.*;
import java.util.Scanner;

/**
 * <strong><u>Wordle Game Assignment</u></strong>
 * <ul>
 * <li>By: Zahin Abrer</li>
 * <li>Due Date: March 9, 2023</li>
 * <li>Class: ICS3U-02</li>
 * <li>Teacher: Mr. Hudson</li>
 * </ul>
 * <em>Note: I recommend opening the project in an IDE like IntelliJ so
 * the Javadoc can be rendered without having to generate the HTML.</em>
 */

public class Main {
    static boolean hard;

    public static void main(String[] args) throws Exception {
        // the following line enable OpenGL on Linux and Windows
        // it causes glitches in repl.it for some reason
        System.setProperty("sun.java2d.opengl", "true");

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        Scanner sc = new Scanner(System.in);
        System.out.println("""
                Welcome to Zardle!
                ----------------------------------------
                
                Please respond to the following prompt to select whether or not you'd
                like to play the game in hard mode. Hard mode includes a larger word
                list and includes more uncommon or rare words.
                """);
        System.out.print("Run as hard mode? Enter Y/y for yes or anything else for no: ");
        hard = sc.nextLine().equalsIgnoreCase("y");
        System.out.println("\nA GUI should appear in about 3 seconds... ");
        System.out.println("**Click the question mark icon in the GUI to see rules!!!!**");
        System.out.println("Good Luck!!!!");
        Thread.sleep(3000);
        new Window();
    }
}
