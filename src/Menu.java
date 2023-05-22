import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.*;

public class Menu {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",100,40,25,15);
    public static String p_seed;
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
// ----------------------------------------------------

    Menu() throws Exception {
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        cn.getTextWindow().addKeyListener(klis);
        // ----------------------------------------------------

        cn.getTextWindow().setCursorPosition(0,0);
        cn.getTextWindow().output("Please enter seed:");
        cn.getTextWindow().setCursorPosition(19,0);
        p_seed=cn.readLine();
        Map.seed=Long.parseLong(p_seed);
    }
}
