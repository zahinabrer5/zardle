import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayAgainWindow extends JFrame implements ActionListener {
    private final JButton playAgainBtn;
    private final JButton exitBtn;
    private final JButton shareBtn;
    private final Game game;
    private boolean won;

    public PlayAgainWindow(Game game, boolean won) {
        this.setTitle("Play Again?");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);

        this.game = game;
        this.won = won;
        String msg = (won ? "Congratulations! You won!!!!" :
                "You lost!!!! :((((") + " Play again?";
        JLabel msgLabel = new JLabel(msg);
        msgLabel.setForeground(Color.white);

        this.playAgainBtn = new JButton("Play Again! :D");
        playAgainBtn.addActionListener(this);
        this.exitBtn = new JButton("Exit >:C");
        exitBtn.addActionListener(this);
        this.shareBtn = new JButton("Share");
        shareBtn.addActionListener(this);

        JPanel btnPanel = new JPanel();
        btnPanel.add(playAgainBtn);
        btnPanel.add(exitBtn);
        btnPanel.add(shareBtn);
        btnPanel.setBackground(Game.themeColours[0]);

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300, 150));
        mainPanel.setBackground(Game.themeColours[0]);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(msgLabel, gbc);
        mainPanel.add(btnPanel, gbc);
        this.getRootPane().setDefaultButton(playAgainBtn);

        this.add(mainPanel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(playAgainBtn)) {
            System.out.println("Play Again!!!!");
            game.reset();
            this.setVisible(false);
            this.dispose();
        }
        else if (e.getSource().equals(exitBtn)) {
            System.out.println("Exit :(((((");
            System.exit(0);
        }
        else if (e.getSource().equals(shareBtn)) {
            StatsWindow.copyToClipboard(shareText());
        }
    }

    private String shareText() {
        String msg = "Zardle "+(won ? Game.currPoints : "X")+"/6\n\n";
        StringBuilder text = new StringBuilder(msg.length()+30);
        text.append(msg);
        for (int i = 0; i < Game.currPoints; i++) {
            for (int j = 0; j < 5; j++) {
                String c = switch (game.colours[i][j]) {
                    case 1 -> "⬛";
                    case 2 -> "\uD83D\uDFE8";
                    case 3 -> "\uD83D\uDFE9";
                    default -> "";
                };
                text.append(c);
            }
            text.append('\n');
        }
        return text.toString();
    }
}
