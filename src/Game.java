import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int width = 600;
    public static final int height = 700-VirtualKeyboard.height-Navbar.height;
    private final char[][] letters = new char[6][5];
    private final int[][] colours = new int[6][5];
    private int row = 0;
    private int col = 0;
    private String alert = "";
    private final List<String> words = loadWords();
    private final List<String> used = new ArrayList<>();
    private final Random rand = new Random();
    private String word = getRandomWord(words, used, rand);
    private boolean disabled = false;

    public Game() {
        this.setBackground(new Color(0x121213));
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel
        this.setLayout(new GridBagLayout());
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        System.out.println(word);

        // initially fill letters grid with spaces to avoid tofu
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                letters[i][j] = ' ';
            }
        }
    }

    // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
    private List<String> loadWords() {
        List<String> res = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("words.txt")))) {
            for (String line; (line = br.readLine()) != null; ) {
                res.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     *
     * @param words List of words to search in
     * @param used List of used words (expected to contain only elements
     *             in {@code words})
     * @param rand {@code Random} instance to use
     * @return A pseudorandomly selected word from {@code words}, or
     * {@code null} if {@code used} contains all the elements of {@code words}
     */
    private String getRandomWord(List<String> words, List<String> used, Random rand) {
        // words.size() will probably never equal used.size()
        // if it does, that means the user played thousands of times
        // without closing the window, and that's absurd if done by hand
        if (words.size() == used.size()) {
            return null;
        }
        String res;
        do {
            res = words.get(rand.nextInt(words.size()));
        } while (used.contains(res));
        used.add(res);
        return res;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // enable antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawAlert(g2);
        drawLetterGrid(g2);
        drawGridBorder(g2);
    }

    private void drawAlert(Graphics2D g2) {
        Rectangle rect = new Rectangle(0, 0, 600, 40);
        g2.setColor(Color.red);
        drawCenteredString(g2, alert, rect, new WFont(15));
        alert = "";
    }

    private void drawLetterGrid(Graphics2D g2) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                int x = 150+j*60;
                int y = 60+i*60;
                Color c = switch (colours[i][j]) {
                    case 1 -> new Color(0x3A3A3C);
                    case 2 -> new Color(0xB59F3B);
                    case 3 -> new Color(0x538D4E);
                    default -> new Color(0x121213);
                };
                g2.setColor(c);
                g2.fillRect(x, y, 60, 60);
                g2.setColor(Color.white);
                Rectangle rect = new Rectangle(x, y, 60, 60);
                drawCenteredString(g2, String.valueOf(letters[i][j]), rect, new WFont(30));
            }
        }
    }

    private void drawGridBorder(Graphics2D g2) {
        g2.setColor(new Color(0x232325));
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                g2.drawRect(150+j*60, 60+i*60, 60, 60);
            }
        }
    }

    // https://stackoverflow.com/a/27740330
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (disabled) return;
        for (int i = 0; i < 26; i++) {
            if (e.getSource().equals(VirtualKeyboard.buttons.get(i)) && col < 5) {
                char letter = VirtualKeyboard.buttons.get(i).getText().charAt(0);
                letters[row][col++] = letter;
            }
        }
        if (e.getSource().equals(VirtualKeyboard.buttons.get(26)) && col > 0) {
            letters[row][--col] = ' ';
        }
        else if (e.getSource().equals(VirtualKeyboard.buttons.get(27))) {
            doEnterActions();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (disabled) return;
        char c = e.getKeyChar();
        if (Character.isLetter(c) && col < 5) {
            letters[row][col++] = Character.toUpperCase(c);
        }
        else if (c == KeyEvent.VK_BACK_SPACE && col > 0) {
            letters[row][--col] = ' ';
        }
        else if (c == KeyEvent.VK_ENTER) {
            doEnterActions();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void doEnterActions() {
        if (col < 5) {
            alert = "Write more letters!!!!";
            return;
        }
        String enteredWord = new String(letters[row]).toLowerCase();
        if (!words.contains(enteredWord)) {
            alert = "Word not in list!!!!";
            return;
        }
        for (int i = 0; i < 5; i++) {
            char letter = Character.toLowerCase(letters[row][i]);
            colours[row][i] = 1;
            if (word.indexOf(letter) > -1) colours[row][i] = 2;
            if (word.charAt(i) == letter) colours[row][i] = 3;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            paintImmediately(0, 0, width, height);
        }
        if (word.equals(enteredWord)) {
            new PlayAgainWindow(true);
            disabled = true;
            return;
        }
        col = 0;
        if (++row == 6) {
            new PlayAgainWindow(false);
            disabled = true;
        }
    }
}
