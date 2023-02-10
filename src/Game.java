import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int width = 600;
    public static final int height = 700-VirtualKeyboard.height-Navbar.height;
    private final JLabel label;
    private boolean disabled = false;

    public Game() {
        this.setBackground(new Color(0x121213));
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel
        this.setLayout(new GridBagLayout());
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        label = new JLabel();
        label.setPreferredSize(new Dimension(300, 50));
        label.setForeground(Color.WHITE);
        label.setFont(VirtualKeyboard.font);
        this.add(label, new GridBagConstraints());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!disabled) {
            for (int i = 0; i < 26; i++) {
                if (e.getSource().equals(VirtualKeyboard.buttons.get(i))) {
                    label.setText(label.getText().concat(VirtualKeyboard.buttons.get(i).getText()));
                }
            }
            if (e.getSource().equals(VirtualKeyboard.buttons.get(26))) {
                label.setText(removeLastChar(label.getText()));
            }
        }
        if (e.getSource().equals(VirtualKeyboard.buttons.get(27))) {
            label.setEnabled(disabled);
            disabled = !disabled;
            label.setText(label.getText().concat("n"));
        }
    }

    private String removeLastChar(String s) {
        if (s != null && s.length() > 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (!disabled) {
            if (Character.isLetter(c)) {
                label.setText(label.getText().concat(String.valueOf(Character.toUpperCase(c))));
            }
            if (c == KeyEvent.VK_BACK_SPACE) {
                label.setText(removeLastChar(label.getText()));
            }
        }
        if (c == KeyEvent.VK_ENTER) {
            label.setEnabled(disabled);
            disabled = !disabled;
            label.setText(label.getText().concat("n"));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
