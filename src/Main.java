import javax.swing.*;

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
    public static void main(String[] args) {
        // the following line enable OpenGL on Linux and Windows
        // it causes glitches in repl.it for some reason
        System.setProperty("sun.java2d.opengl", "true");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        new Window();
    }
}
