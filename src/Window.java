import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        this.setTitle("Wardle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Game game = new Game();
        this.add(game);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }
}
