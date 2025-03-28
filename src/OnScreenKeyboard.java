import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OnScreenKeyboard extends JPanel {
    public static final int width = 600;
    public static final int height = 175;
    private final int row = 4;
    private final int col = 7;
    public static final List<JButton> buttons = new ArrayList<>();
    private final Game game;

    public OnScreenKeyboard(Game game) {
        this.game = game; // set corresponding game panel object
        this.setBackground(Game.themeColours[0]);
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel

        // use GridLayout to make grid of buttons for on-screen keyboard
        this.setLayout(new GridLayout(row, col));
        drawButtons();
    }

    private void drawButtons() {
        for (int i = 0; i < 26; i++) {
            keyboardBtnFactory(String.valueOf((char)('A'+i)), false);
        }
        keyboardBtnFactory("⌫", true);
        keyboardBtnFactory("⏎", true);
    }

    private void keyboardBtnFactory(String text, boolean special) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(special ? 70 : 50, 50));
        btn.setFont(new ZFont(20));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0x818384));
        btn.setFocusable(false);
        btn.setFocusPainted(false);

        // on-screen keyboard inputs are handles in game panel
        btn.addActionListener(game);

        buttons.add(btn);
        this.add(btn);
    }
}
