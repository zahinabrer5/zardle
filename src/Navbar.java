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
    private final JButton helpBtn;
    private HelpWindow helpWindow;

    public Navbar() {
        this.setBackground(Game.themeColours[0]);
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel

        // create JButton with icon only (no button decorations)
        // kind of like an <a href="..."><img src="..." height=30></a> in HTML
        ImageIcon helpIcon = new ImageIcon(getClass().getResource("help.png"));
        helpBtn = new JButton();
        helpBtn.setPreferredSize(new Dimension(25, 25));
        helpBtn.setFocusable(false);
        helpBtn.setFocusPainted(false);
        helpBtn.setBorder(null);
        helpBtn.setBorderPainted(false);
        helpBtn.setMargin(new Insets(0, 0, 0, 0));
        helpBtn.setContentAreaFilled(false);
        helpBtn.setIcon(helpIcon);
        helpBtn.addActionListener(this);

        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new BorderLayout());
        this.add(helpBtn, BorderLayout.LINE_END);
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
        if (e.getSource().equals(helpBtn)) {
            if (helpWindow == null) {
                helpWindow = new HelpWindow();
            }
            else if (!helpWindow.isVisible()) {
                helpWindow.setVisible(true);
            }
        }
    }
}
