import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        this.setTitle("Zardle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // wrapper panel containing navbar, the actual game panel & on-screen keyboard
        JPanel panels = new JPanel();
        // use BorderLayout to arrange panels vertically
        panels.setLayout(new BorderLayout());

        panels.add(new Navbar(), BorderLayout.PAGE_START);
        Game game = new Game();
        panels.add(game, BorderLayout.CENTER);
        panels.add(new OnScreenKeyboard(game), BorderLayout.PAGE_END);

        this.add(panels);
        this.pack();

        this.setLocationRelativeTo(null); // centre the window on the screen
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }
}
