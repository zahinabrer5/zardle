import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HelpWindow extends JFrame {
    final int width = 500;
    final int height = 600;

    public HelpWindow() {
        this.setTitle("How to Play Wardle");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);

        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        jep.setContentType("text/html");
        try {
            jep.setPage(getClass().getResource("help_page.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jep.setFocusable(false);
        jep.setBackground(new Color(0x121213));

        JScrollPane scrollPane = new JScrollPane(jep);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        this.getContentPane().add(scrollPane);
        this.setSize(width, height);

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }
}
