import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int width = 600;

    // the height of the game panel will be the total height (700)
    // minus the height of the on-screen keyboard panel and
    // minus the height of the navbar panel

    // this allows the height of the game panel to automatically update
    // according to the heights of the other two panels
    public static final int height = 700-OnScreenKeyboard.height-Navbar.height;

    public static final Color[] themeColours = {
            new Color(0x121213), // general background colour of Zardle
            new Color(0x3A3A3C), // incorrect letter colour
            new Color(0xB59F3B), // correct letter but incorrect position colour
            new Color(0x538D4E)  // correct letter colour
    };

    private final char[][] letters = new char[6][5];
    public final int[][] colours = new int[6][5];
    private int row = 0;
    private int col = 0;
    private String alert = "";
    private boolean disabled = false;

    private final List<String> words = loadWords();
    private final List<String> used = new ArrayList<>();
    private final Random rand = new Random();
    private String word = getRandomWord(words, used, rand);

    public static int points = 0;
    public static int currPoints = 0; // points in current game
    public static int pointsTotal = 6;
    public static int wins = 0;
    public static int losses = 0;
    public static int[] tries = new int[6];

    public Game() {
        this.setBackground(themeColours[0]);
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true); // reduced lag on animations in JPanel
        this.setLayout(new GridBagLayout());
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);

        clearLetterGrid();

//        System.out.println(word); // for debugging
    }

    private void clearLetterGrid() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                letters[i][j] = ' ';
            }
        }
    }

    // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
    private List<String> loadWords() {
        List<String> res = new ArrayList<>();
        String file = Main.hard ? "words_hard.txt" : "words.txt";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(file)))) {
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
        Rectangle rect = new Rectangle(0, 0, width, 40);
        g2.setColor(Color.red);
        drawCenteredString(g2, alert, rect, new ZFont(15));
        alert = "";
    }

    private void drawLetterGrid(Graphics2D g2) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                // the letter grid starts at point (150, 60)
                // width & height of each letter cell will be 60 pixels
                int x = 150+j*60;
                int y = 60+i*60;

                // get colour for current letter cell from colour grid
                Color c = themeColours[colours[i][j]];
                g2.setColor(c);
                g2.fillRect(x, y, 60, 60);

                // get letter for current letter cell from letter grid
                g2.setColor(Color.white);
                Rectangle rect = new Rectangle(x, y, 60, 60);
                drawCenteredString(g2, String.valueOf(letters[i][j]), rect, new ZFont(30));
            }
        }
    }

    // draw borders between letter cells
    private void drawGridBorder(Graphics2D g2) {
        g2.setColor(new Color(0x232325));
        // greater stroke width -> greater border width
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
        // handle letter keys for on-screen keyboard
        for (int i = 0; i < 26; i++) {
            // col < 5 check ensures program doesn't break when
            // user attempts to enter more than 5 letters
            if (e.getSource().equals(OnScreenKeyboard.buttons.get(i)) && col < 5) {
                char letter = OnScreenKeyboard.buttons.get(i).getText().charAt(0);
                letters[row][col++] = letter;
            }
        }
        // handle backspace key for on-screen keyboard
        // col > 0 check ensures program doesn't break when user
        // attempts to hit backspace when there are no letters for
        // the current word
        if (e.getSource().equals(OnScreenKeyboard.buttons.get(26)) && col > 0) {
            letters[row][--col] = ' ';
        }
        // handle enter key for on-screen keyboard
        else if (e.getSource().equals(OnScreenKeyboard.buttons.get(27))) {
            doEnterActions();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // also detect keystrokes from physical keyboard
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

        // convert letters[row] to String and set as enteredWord
        String enteredWord = new String(letters[row]).toLowerCase();

        if (!words.contains(enteredWord)) {
            alert = "Word not in list!!!!";
            return;
        }

        checkWord();

        points++;
        currPoints++;
//        System.out.println("Points:\t\t"+points+"/"+pointsTotal); // for debugging
        if (word.equals(enteredWord)) { // user has won...
            new PlayAgainWindow(this, true, word);
            disabled = true;
            wins++;
//            printWinLossRatio(); // for debugging
            tries[row]++;
            return;
        }

        col = 0;
        if (++row == 6) { // user has lost...
            new PlayAgainWindow(this, false, word);
            disabled = true;
            losses++;
//            printWinLossRatio(); // for debugging
        }
    }

    private void checkWord() {
        StringBuilder sb = new StringBuilder(word);

        // go through user entered word and mark off correct letters
        for (int i = 0; i < 5; i++) {
            char letter = Character.toLowerCase(letters[row][i]);
            if (word.charAt(i) == letter) {
                colours[row][i] = 3;

                // set green colour to letter buttons on on-screen keyboard
                // corresponding to correct letters in entered word
                JButton btn = OnScreenKeyboard.buttons.get(letter-'a');
                btn.setBackground(themeColours[3]);

                // set the corresponding correct letters in sb to a ' '
                // character so that they aren't used for future checks
                sb.setCharAt(i, ' ');
            }
        }

        // go through user entered word again, this
        // time checking for yellow and grey letters
        for (int i = 0; i < 5; i++) {
            if (colours[row][i] == 0) {
                char letter = Character.toLowerCase(letters[row][i]);

                JButton btn = OnScreenKeyboard.buttons.get(letter-'a');
                // get index of colour in themeColours of letter's corresponding button
                int btnColourIdx = Arrays.asList(themeColours).indexOf(btn.getBackground());

                int sbIdx = sb.indexOf(String.valueOf(letter));
                if (sbIdx > -1) { // letter is yellow
                    colours[row][i] = 2;
                    if (btnColourIdx < 2)
                        btn.setBackground(themeColours[2]);

                    // set the corresponding yellow letters in sb to a ' '
                    // character so that they aren't used for future checks
                    sb.setCharAt(sbIdx, ' ');
                }
                else { // final case: letter is grey
                    colours[row][i] = 1;
                    if (btnColourIdx < 1)
                        btn.setBackground(themeColours[1]);
                }
            }
        }
    }

    // for debugging
    private void printWinLossRatio() {
        String ratio = String.valueOf((double)wins/losses);
        if (losses == 0)
            ratio = "--";
        System.out.println("Wins:\t\t"+wins+
                "\nLosses:\t\t"+losses+
                "\nW/L Ratio:\t"+ratio);
    }

    public void reset() {
        clearLetterGrid();

        for (int i = 0; i < 6; i++)
            Arrays.fill(colours[i], 0);

        for (char c = 'a'; c <= 'z'; c++)
            OnScreenKeyboard.buttons.get(c-'a').setBackground(new Color(0x818384));

        row = 0;
        col = 0;
        alert = "";
        word = getRandomWord(words, used, rand); // regenerate word
        disabled = false;
        pointsTotal += 6;
        currPoints = 0;
        repaint();

//        System.out.println(word); // for debugging
    }
}
