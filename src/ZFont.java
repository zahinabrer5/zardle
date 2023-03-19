import java.awt.Font;

/**
 * Purpose: I found that I was calling {@code java.awt.Font} often with
 * {@code "SansSerif"} and {@code Font.BOLD} (for the font name and style
 * parameters respectively). So I decided to make a wrapper class {@code ZFont}
 * ("Zardle Font") so I only have to pass the font size with
 * {@code new ZFont(20)}, for example. This will help ensure all the fonts in
 * the program are the same.
 */

public class ZFont extends Font {
    public ZFont(int size) {
        super("SansSerif", Font.BOLD, size);
    }
}
