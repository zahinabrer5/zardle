import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Navbar extends JPanel {
    public static final int width = 600;
    public static final int height = 80;

    public Navbar() {
        this.setBackground(new Color(0x121213));
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel
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
}
