import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu {
    public enigma.console.Console cn = Enigma.getConsole("Chain");
    public KeyListener klis;

    public int keypr;   // key pressed?
    public int rkey;   // key   (for press/release)

    Menu() throws Exception {
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
        //-------------------------------------------

        //Main Menu
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
        cn.getTextWindow().setCursorPosition(1,1);

        if (keypr == 1) {
            int number;

            char rckey = (char) rkey;
            //        1             2              3
            if (rckey == 49){
                number=1;
                cn.getTextWindow().output(1, 1, (char) number);
                for(int i=0;i<6;i++){
                    cn.getTextWindow().setCursorPosition(0,i);
                    cn.getTextWindow().output(" ");
                }
                cn.getTextWindow().setCursorPosition(0,0);
                new Map();
                new Game();
                keypr = 0;
            }
            /*else if(rckey == 50){

            }*/

        }
    }
}
