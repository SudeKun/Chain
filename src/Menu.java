import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu {
    public enigma.console.Console cn = Enigma.getConsole("Chain",100,40,25,15);
    public TextMouseListener tmlis;
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int mousepr;          // mouse pressed?
    public int mousex, mousey;   // mouse text coords.
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
    // ----------------------------------------------------


    Menu() throws Exception {   // --- Contructor

        // ------ Standard code for mouse and keyboard ------ Do not change
        /*tmlis=new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {}
            public void mousePressed(TextMouseEvent arg0) {
                if(mousepr==0) {
                    mousepr=1;
                    mousex=arg0.getX();
                    mousey=arg0.getY();
                }
            }
            public void mouseReleased(TextMouseEvent arg0) {}
        };
        cn.getTextWindow().addTextMouseListener(tmlis);*/

        klis=new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if(keypr==0) {
                    keypr=1;
                    rkey=e.getKeyCode();
                }
            }
            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);

        // ----------------------------------------------------
        cn.getTextWindow().setCursorPosition(0,0);
        cn.getTextWindow().output("Welcome to Chain Game!");
        cn.getTextWindow().setCursorPosition(0,1);
        cn.getTextWindow().output("Please enter a number from list:");
        cn.getTextWindow().setCursorPosition(0,2);
        cn.getTextWindow().output("1-Start Game");
        cn.getTextWindow().setCursorPosition(0,3);
        cn.getTextWindow().output("2-High Score Table");
        cn.getTextWindow().setCursorPosition(0,4);
        cn.getTextWindow().output("3-Quit game");
        boolean choice =true;
        while(choice) {
            if (keypr == 1) {    // if keyboard button pressed
                char rckey = (char) rkey;
                //        left          right          up
                if (rckey == '1' || rckey == '2' || rckey == '3')
                    cn.getTextWindow().output(33, 1, rckey); // VK kullanmadan test teknigi
                else cn.getTextWindow().output(rckey);
                if (rkey == 1) {
                    if (rkey == KeyEvent.VK_ENTER) {
                        for (int i = 0; i < 6; i++) {
                            cn.getTextWindow().output(0, i, ' ');
                        }
                        new Map();
                        choice=false;
                    }
                }
                keypr=0;
            }
        }


    }
                }
