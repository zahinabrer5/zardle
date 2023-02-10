import javax.swing.*;
import java.awt.*;

public class Keyboard extends JPanel {
    public static final int width = 600;
    public static final int height = 175;
    private final int row = 4;
    private final int col = 7;
    public final Font font = new Font("SansSerif", Font.BOLD, 14);
    public final Font fontBig = new Font("SansSerif", Font.BOLD, 20);

    public Keyboard() {
        this.setBackground(new Color(0x121213));
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel

        this.setLayout(new GridLayout(row, col));
        drawButtons();
    }

    private void drawButtons() {
        for (int i = 0; i < 26; i++) {
            JButton letter = new JButton(String.valueOf((char)('A'+i)));
            keyboardBtnFactory(letter, false);
        }
        JButton backspace = new JButton("⌫");
        keyboardBtnFactory(backspace, true);
        JButton enter = new JButton("⏎");
        keyboardBtnFactory(enter, true);
    }

    private void keyboardBtnFactory(JButton btn, boolean special) {
        btn.setPreferredSize(new Dimension(special ? 70 : 50, 50));
        btn.setFont(special ? fontBig : font);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0x818384));
        btn.setFocusPainted(false);
        this.add(btn);
    }
}
