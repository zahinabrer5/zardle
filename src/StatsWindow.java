import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class StatsWindow extends JFrame {
    public StatsWindow() {
        this.setTitle("Your Statistics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 2));
        statsPanel.setPreferredSize(new Dimension(400, 300));
        statsPanel.setBackground(Game.themeColours[0]);
        statsPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        String ratio = Game.losses == 0 ? "--" :
                String.valueOf((double)Game.wins/Game.losses);
        String[][] data = {
                { "Points", Game.points+"/"+Game.pointsTotal },
                { "Wins", String.valueOf(Game.wins) },
                { "Losses", String.valueOf(Game.losses) },
                { "W/L Ratio", ratio },
        };

        Font dataFont = new WFont(20);
        for (String[] row : data) {
            JLabel key = new JLabel(row[0]);
            key.setForeground(Color.white);
            key.setFont(dataFont);

            JLabel value = new JLabel(row[1], SwingConstants.RIGHT);
            value.setForeground(Color.white);
            value.setFont(dataFont);

            statsPanel.add(key);
            statsPanel.add(value);
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(400, 50));
        btnPanel.setBackground(Game.themeColours[0]);
        String triesData = Arrays.toString(Game.tries).replaceAll(" ", "");
        String triesGraphLink = "https://quickchart.io/chart?c={%20type:%20%27bar%27,%20data:%20{%20labels:%20[1,2,3,4,5,6],%20datasets:%20[{%20label:%20%27Frequency%20of%20Number%20of%20Tries%27,%20data:%20"+
                triesData+"%20}]%20},%20options:%20{%20title:%20{%20display:%20true,%20text:%20%27Distribution%20of%20Number%20of%20Tries%20(until%20win)%27%20},%20scales:%20{%20yAxes:%20[{%20ticks:%20{%20beginAtZero:%20true,%20stepSize:%201,%20}%20}]%20}%20}%20}";
        JButton triesGraphBtn = new JButton("Copy Link to Tries Distribution");
        triesGraphBtn.setFont(new WFont(14));
        triesGraphBtn.addActionListener(e -> {
            copyToClipboard(triesGraphLink);
        });
        btnPanel.add(triesGraphBtn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Game.themeColours[0]);
        mainPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(statsPanel, BorderLayout.PAGE_START);
        mainPanel.add(btnPanel, BorderLayout.PAGE_END);
        this.add(mainPanel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());
        this.setVisible(true);
    }

    // https://stackoverflow.com/a/46353370
    private void copyToClipboard(String triesGraphLink) {
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(triesGraphLink),
                        null
                );
    }
}
