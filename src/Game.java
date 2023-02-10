import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {
    public static final int width = 600;
    public static final int height = 700-Keyboard.height-Navbar.height;

    public Game() {
        this.setBackground(new Color(0x121213));
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel
        this.setLayout(new GridBagLayout());

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50));
        this.add(textField, new GridBagConstraints());
    }
}
