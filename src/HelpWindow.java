import javax.swing.*;
import java.io.IOException;

public class HelpWindow extends JFrame {
    final int width = 500;
    final int height = 600;

    public HelpWindow() {
        this.setTitle("How to Play Zardle");
        // hide window instead of disposing
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);

        // use JEditorPane to render & display HTML file
        // it usually takes a long while to render the HTML
        // when the HelpWindow object is created and I don't
        // know how to make it load faster
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        jep.setContentType("text/html");
        try {
            jep.setPage(getClass().getResource("help_page.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jep.setFocusable(false);
        jep.setBackground(Game.themeColours[0]);

        // a JScrollPane object must be created using the
        // JEditorPane object, but since we don't want a
        // scrollbar, we disable the border
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
