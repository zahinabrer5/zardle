import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        this.setTitle("Zardle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel panels = new JPanel();
        panels.setLayout(new BorderLayout());
        panels.add(new Navbar(), BorderLayout.PAGE_START);

        Game game = new Game();
        panels.add(game, BorderLayout.CENTER);
        panels.add(new VirtualKeyboard(game), BorderLayout.PAGE_END);

        this.add(panels);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }
}
