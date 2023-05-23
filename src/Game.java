import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
    public enigma.console.Console cn = Enigma.getConsole("Chain",80,40,20,15);
    public static TextAttributes Red = new TextAttributes(Color.RED);
    public KeyListener klis;
    public Chain chain = new Chain();

    // ------ Standard variables for mouse and keyboard ------
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
// ----------------------------------------------------

    public static char[][] map = Map.map;


    static boolean gameover=false;
    static public int score=0, x,y;

    Game() throws Exception {
        klis=new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
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

   /*cn.getTextWindow().output("Do you want to enter a seed?");
   if(keypr == 1){
      if(rkey==KeyEvent.VK_Y){
         while(rkey!=KeyEvent.VK_ENTER) {
            Map.seed;
         }
      }
      keypr=0;
   }*/
        int round=0;
        cn.getTextWindow().setCursorPosition(35,0);
        cn.getTextWindow().output("Board Seed: "+Map.seed);
        cn.getTextWindow().setCursorPosition(35,1);
        cn.getTextWindow().output("Round: "+round);
        cn.getTextWindow().setCursorPosition(35,2);
        cn.getTextWindow().output("Score: "+ score);
        cn.getTextWindow().setCursorPosition(35,3);
        cn.getTextWindow().output("---------------------------------------");
        cn.getTextWindow().setCursorPosition(35,4);
        cn.getTextWindow().output("Table: ");

        x = 1;
        y = 0;
        map[y][x] = '|';
        cn.getTextWindow().output(x, y, '|');

        while(!gameover) {
            if (keypr == 1) {    // if keyboard button pressed
                if(map[y][x] == ' ' || map[y][x]=='|') {
                    map[y][x] = ' ';
                    cn.getTextWindow().output(x, y, ' ');
                }
                else if(map[y][x]=='+'){
                    map[y][x]='+';
                    cn.getTextWindow().output(x,y,'+',Red);
                }
                if (rkey == KeyEvent.VK_LEFT && x>0) {
                    if(y%2!=0 && x-1==0){
                        x--;
                    }
                    else if(x-2>=0){
                        x-=2;
                    }
                }
                if (rkey == KeyEvent.VK_RIGHT) {
                    if(y%2!=0 && x<30 && x+1==30) {
                        x++;
                    }
                    else if (x+2<=30) x+=2;
                }
                if (rkey == KeyEvent.VK_UP && y>0) {
                    if (x % 2 == 0 && map[y - 1][x] != ' ') {
                        if (x - 1 > -1) {
                            x--;
                        } else x++;
                    } else if (y - 1 % 2 != 0 && x % 2 != 0) {
                        if (x - 1 > -1) {
                            x++;
                        } else x--;
                    }
                    y--;
                }
                if (rkey == KeyEvent.VK_DOWN && y<18) {
                    if(x%2==0 && map[y+1][x]!=' ') {
                        if (x - 1 > -1) {
                            x--;
                        } else x++;
                    }
                    else if(y+1%2!= 0 && x%2!=0){
                        if (x-1 > -1) {
                            x++;
                        } else x --;
                    }
                    y++;
                }
                //Add +
                if (rkey == KeyEvent.VK_SPACE) {
                    if (map[y][x] == '+') {
                        cn.getTextWindow().output(x, y, ' ');
                        map[y][x] = 0;
                        int[] position = {x,y};
                        chain.removePlus(position);
                    }
                    else {
                        map[y][x] = '+';
                        cn.getTextWindow().output(x, y, '+',Red);
                        int[] position= {x,y};
                        chain.getChain().add(position);
                    }
                }
                //Enter
                if (rkey == KeyEvent.VK_ENTER){
                    round++;
                    cn.getTextWindow().setCursorPosition(35,1);
                    cn.getTextWindow().output("Round: "+round);
                    chain.newRound();
                }
                //Quit
                if (rkey == KeyEvent.VK_E) {
                    gameover=true;
                }

                char rckey = (char) rkey;
                //        left          right          up            down
                if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {
                    cn.getTextWindow().output(x, y, '|');
                }

                //Output for +
                if(map[y][x]=='+'){
                    cn.getTextWindow().output(x, y, '|');
                }
                else{
                    map[y][x] = '|';
                    cn.getTextWindow().output(x, y, '|');
                }
                keypr = 0;    // last action
            }
            Thread.sleep(200);
        }
        if (gameover){
            reset();
            cn.getTextWindow().setCursorPosition(0,0);
            cn.getTextWindow().output("Game over");
            Thread.sleep(2000);
        }
    }

    public void reset(){
        for(int i=35;i<75;i++){
            for(int j=0;j<35;j++){
                cn.getTextWindow().output(i, j,' ');
            }
        }
        for(int i=0;i<31;i++){
            for(int j=0;j<20;j++){
                map[j][i]=' ';
                cn.getTextWindow().output(i, j,' ');
            }
        }
    }

}


