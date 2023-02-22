import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayAgainWindow extends JFrame {
    public PlayAgainWindow(boolean won) {
        this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());

        String msg = (won ? "Congratulations! You won!!!!" :
                "You lost!!!! :((((") + " Play again?";

        int optionChosen = JOptionPane.showOptionDialog(this,
                msg,
                "Play Again?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        System.out.println(optionChosen);
    }
}
