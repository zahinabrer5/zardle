import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Navbar extends JPanel implements ActionListener {
    public static final int width = 600;
    public static final int height = 80;
    private final JButton statsBtn;
    private final JButton helpBtn;

    // only one HelpWindow instance is needed so HTML
    // has to be loaded only once
    // just hide & unhide the window afterwards
    private HelpWindow helpWindow;

    public Navbar() {
        this.setBackground(Game.themeColours[0]);
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel

        statsBtn = iconOnlyBtn("stats.png", 25, 25);
        statsBtn.addActionListener(this);

        helpBtn = iconOnlyBtn("help.png", 25, 25);
        helpBtn.addActionListener(this);

        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // use BorderLayout to arrange buttons horizontally
        this.setLayout(new BorderLayout());
        this.add(statsBtn, BorderLayout.LINE_START);
        this.add(helpBtn, BorderLayout.LINE_END);
    }

    // create JButton with icon only (no button decorations)
    // kind of like an <a href="..."><img src="..." height=30></a> in HTML
    private JButton iconOnlyBtn(String iconFile, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconFile));
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFocusable(false);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setIcon(icon);
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // enable antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawFullLogo(g2);
    }

    private void drawFullLogo(Graphics2D g2) {
        Image fullLogo = getImage(getClass().getResource("full_logo_240.png"));
        // draw centered
        g2.drawImage(fullLogo, width/2-fullLogo.getWidth(null)/2, 20, null);
    }

    private Image getImage(URL resource) {
        BufferedImage img;
        try {
            img = ImageIO.read(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(statsBtn)) {
            new StatsWindow();
        }
        else if (e.getSource().equals(helpBtn)) {
            // if helpWindow has not been assigned...
            if (helpWindow == null) {
                // create HelpWindow object, loading HTML
                helpWindow = new HelpWindow();
            }
            else if (!helpWindow.isVisible()) {
                // otherwise just show the window
                helpWindow.setVisible(true);
            }
        }
    }
}
