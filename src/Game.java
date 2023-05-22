import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
    public enigma.console.Console cn = Enigma.getConsole("Chain");
    public static TextAttributes Red = new TextAttributes(Color.RED);
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
// ----------------------------------------------------


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

        char[][] map = Map.map;
        int round=0;
        cn.getTextWindow().setCursorPosition(35,0);
        cn.getTextWindow().output("Board Seed: "+Map.seed);
        cn.getTextWindow().setCursorPosition(35,1);
        cn.getTextWindow().output("Round: "+round);
        cn.getTextWindow().setCursorPosition(35,2);
        cn.getTextWindow().output("Score: ");
        cn.getTextWindow().setCursorPosition(35,3);
        cn.getTextWindow().output("---------------------------------------");
        cn.getTextWindow().setCursorPosition(35,4);
        cn.getTextWindow().output("Table: ");

        int px=1,py=0;
        map[py][px]='|';
        cn.getTextWindow().output(px, py, '|');
        boolean gameover=false;
        while(!gameover) {
            if (keypr == 1) {    // if keyboard button pressed
                if(map[py][px] == ' ' || map[py][px]=='|') {
                    map[py][px] = ' ';
                    cn.getTextWindow().output(px, py, ' ');
                }
                else if(map[py][px]=='+'){
                    map[py][px]='+';
                    cn.getTextWindow().output(px,py,'+',Red);
                }
                if (rkey == KeyEvent.VK_LEFT && px>0) {
                    if(py%2!=0 && px-1==0){
                        px--;
                    }
                    else if(px-2>=0){
                        px-=2;
                    }
                }
                if (rkey == KeyEvent.VK_RIGHT) {
                    if(py%2!=0 && px<30 && px+1==30) {
                        px++;
                    }
                    else if (px+2<=30) px+=2;
                }
                if (rkey == KeyEvent.VK_UP && py>0) {
                    if (px % 2 == 0 && map[py - 1][px] != ' ') {
                        if (px - 1 > -1) {
                            px--;
                        } else px++;
                    } else if (py - 1 % 2 != 0 && px % 2 != 0) {
                        if (px - 1 > -1) {
                            px++;
                        } else px--;
                    }
                    py--;
                }
                if (rkey == KeyEvent.VK_DOWN && py<18) {
                    if(px%2==0 && map[py+1][px]!=' ') {
                        if (px - 1 > -1) {
                            px--;
                        } else px++;
                    }
                    else if(py+1%2!= 0 && px%2!=0){
                        if (px-1 > -1) {
                            px++;
                        } else px --;
                    }
                    py++;
                }
                int correctness = 0;
                int x= 0;
                int y = 0;
                //Add +
                if (rkey == KeyEvent.VK_SPACE) {
                    if (map[py][px] == '+') {
                        cn.getTextWindow().output(px, py, ' ');
                        map[py][px] = ' ';
                        if (correctness==0){
                            x= px;
                            y= py;
                            correctness++;
                        }
                    }
                    else {
                        map[py][px] = '+';
                        cn.getTextWindow().output(px, py, '+',Red);
                    }
                }

                //Enter
                if (rkey == KeyEvent.VK_ENTER){
                    round++;
                    cn.getTextWindow().setCursorPosition(35,1);
                    cn.getTextWindow().output("Round: "+round);
                    if (!ChainTruth(x,y)){
                        gameover = true;
                        cn.getTextWindow().setCursorPosition(35,8);
                        cn.getTextWindow().output("Game over!");
                    }
                }

                //Quit
                if (rkey == KeyEvent.VK_E) {
                    for(int i=0;i<31;i++){
                        for(int j=0;j<20;j++){
                            map[j][i]=' ';
                            cn.getTextWindow().output(i, j,' ');
                        }
                    }
                    cn.getTextWindow().setCursorPosition(0,0);
                    cn.getTextWindow().output("Quited");
                    Thread.sleep(1000);
                    gameover=true;
                }

                char rckey = (char) rkey;
                //        left          right          up            down
                if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {
                    cn.getTextWindow().output(px, py, '|');
                }

                //Output for +
                if(map[py][px]=='+') {
                    cn.getTextWindow().output(px, py, '|');
                }
                else{
                    map[py][px] = '|';
                    cn.getTextWindow().output(px, py, '|');
                }


                keypr = 0;    // last action
            }
            Thread.sleep(20);
        }
    }

    public boolean ChainTruth (int x, int y) {
        SingleLinkedList sll = new SingleLinkedList();
        boolean flag = true;
        int correctnes = 0;
        int whichturn=0;
        if (y%2 == 0){
            if (y>=2 && x >= 1 && x <= 29){
                if (Map.map[y-1][x-1] == '+') {
                    if (Math.abs(Map.map[y - 2][x - 1] - Map.map[y][x - 1]) == 1) {
                        sll.add(Map.map[y][x + 1]);
                        sll.add(Map.map[y][x - 1]);
                        whichturn = 0;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (y>=2 && x >= 1 && x <= 29 ){
                if (Map.map[y-1][x+1] == '+') {
                    if (Math.abs(Map.map[y - 2][x + 1] - Map.map[y][x + 1]) == 1) {
                        sll.add(Map.map[y][x - 1]);
                        sll.add(Map.map[y][x + 1]);
                        whichturn = 1;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (x >= 3 && x <= 29){
                if (Map.map[y][x-2] == '+') {
                    if (Math.abs(Map.map[y][x - 3] - Map.map[y][x - 1]) == 1) {
                        sll.add(Map.map[y][x + 1]);
                        sll.add(Map.map[y][x - 1]);
                        whichturn = 2;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (x >= 1 && x <= 27){
                if (Map.map[y][x+2] == '+' ) {
                    if (Math.abs(Map.map[y][x + 3] - Map.map[y][x + 1]) == 1) {
                        sll.add(Map.map[y][x - 1]);
                        sll.add(Map.map[y][x + 1]);
                        whichturn = 3;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (y<=16 && x >= 1 && x <= 29){
                if (Map.map[y+1][x-1] == '+') {
                    if (Math.abs(Map.map[y + 2][x - 1] - Map.map[y][x + 1]) == 1) {
                        sll.add(Map.map[y][x + 1]);
                        sll.add(Map.map[y][x - 1]);
                        whichturn = 4;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if ( y>=16 && x >= 1 && x <= 29) {
                if (Map.map[y + 1][x + 1] == '+') {
                    if (Math.abs(Map.map[y + 2][x + 1] - Map.map[y][x + 1]) == 1) {
                        sll.add(Map.map[y][x - 1]);
                        sll.add(Map.map[y][x + 1]);
                        whichturn = 5;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
        }
        if (y%2 == 1){
            if (y>=3 && x >= 1 && y <= 17){
                if (Map.map[y-2][x] == '+') {
                    if (Math.abs(Map.map[x][y - 3] - Map.map[y - 1][x]) == 1) {
                        sll.add(Map.map[y + 1][x]);
                        sll.add(Map.map[y - 1][x]);
                        whichturn = 6;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if ( y>=1 && x >= 2 && y <= 17){
                if (Map.map[y-1][x-1] == '+') {
                    if (Math.abs(Map.map[y - 1][x] - Map.map[y - 1][x - 2]) == 1) {
                        sll.add(Map.map[y + 1][x]);
                        sll.add(Map.map[y - 1][x]);
                        whichturn = 7;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (y>=1 && x <= 28 && y <= 17){
                if (Map.map[y-1][x+1] == '+') {
                    if (Math.abs(Map.map[y - 1][x] - Map.map[y - 1][x + 2]) == 1) {
                        sll.add(Map.map[y + 1][x]);
                        sll.add(Map.map[y - 1][x]);
                        whichturn = 8;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (y>=1 && x >= 2 && y <= 17){
                if (Map.map[y+1][x-1] == '+') {
                    if (Math.abs(Map.map[y + 1][x] - Map.map[y + 1][x - 2]) == 1) {
                        sll.add(Map.map[y - 1][x]);
                        sll.add(Map.map[y + 1][x]);
                        whichturn = 9;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if (y>=1 && x >= 28 && y <= 17){
                if (Map.map[y+1][x+1] == '+') {
                    if (Math.abs(Map.map[y + 1][x] - Map.map[y + 1][x + 2]) == 1) {
                        sll.add(Map.map[y - 1][x]);
                        sll.add(Map.map[y + 1][x]);
                        whichturn = 10;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
            if ( y>=1 && y <= 15){
                if (Map.map[y+2][x] == '+') {
                    if (Math.abs(Map.map[y + 3][x] - Map.map[y + 1][x]) == 1) {
                        sll.add(Map.map[y - 1][x]);
                        sll.add(Map.map[y + 1][x]);
                        whichturn = 11;
                    } else {
                        flag = false;
                    }
                    correctnes++;
                }
            }
        }
        if (correctnes != 1){
            flag = false;
        }
        if (whichturn == 0){
            x = x-1;
            y = y-1;
        }
        if (whichturn == 1){
            x = x+1;
            y = y-1;
        }
        if (whichturn == 2){
            x = x-2;
        }
        if (whichturn == 3){
            x = x+2;
        }
        if (whichturn == 4){
            x = x-1;
            y = y+1;
        }
        if (whichturn == 5){
            x = x+1;
            y = y+1;
        }
        if (whichturn == 6){
            y = y-2;
        }
        if (whichturn == 7){
            x = x-1;
            y = y-1;
        }
        if (whichturn == 8){
            x = x+1;
            y = y-1;
        }
        if (whichturn == 9){
            x = x-1;
            y = y+1;
        }
        if (whichturn == 10){
            x = x+1;
            y = y+1;
        }
        if (whichturn == 11){
            y = y-2;
        }

        while(true){
            if (whichturn == 0 || whichturn == 1 || whichturn == 6){
                if (y>=3 && x >= 1 && y <= 17){
                    if (Map.map[y-2][x] == '+') {
                        if (Math.abs(Map.map[x][y - 3] - Map.map[y - 1][x]) == 1) {
                            sll.add(Map.map[y + 1][x]);
                            sll.add(Map.map[y - 1][x]);
                            whichturn = 6;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if ( y>=1 && x >= 2 && y <= 17){
                    if (Map.map[y-1][x-1] == '+') {
                        if (Math.abs(Map.map[y - 1][x] - Map.map[y - 1][x - 2]) == 1) {
                            sll.add(Map.map[y + 1][x]);
                            sll.add(Map.map[y - 1][x]);
                            whichturn = 7;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (y>=1 && x <= 28 && y <= 17){
                    if (Map.map[y-1][x+1] == '+') {
                        if (Math.abs(Map.map[y - 1][x] - Map.map[y - 1][x + 2]) == 1) {
                            sll.add(Map.map[y + 1][x]);
                            sll.add(Map.map[y - 1][x]);
                            whichturn = 8;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (whichturn == 6){
                    y = y-2;
                }
                if (whichturn == 7){
                    x = x-1;
                    y = y-1;
                }
                if (whichturn == 8){
                    x = x+1;
                    y = y-1;
                }
            }
            else if (whichturn == 2 || whichturn == 7 || whichturn == 9){
                if (y>=2 && x >= 1 && x <= 29){
                    if (Map.map[y-1][x-1] == '+') {
                        if (Math.abs(Map.map[y - 2][x - 1] - Map.map[y][x - 1]) == 1) {
                            sll.add(Map.map[y][x + 1]);
                            sll.add(Map.map[y][x - 1]);
                            whichturn = 0;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (x >= 3 && x <= 29){
                    if (Map.map[y][x-2] == '+') {
                        if (Math.abs(Map.map[y][x - 3] - Map.map[y][x - 1]) == 1) {
                            sll.add(Map.map[y][x + 1]);
                            sll.add(Map.map[y][x - 1]);
                            whichturn = 2;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (y<=16 && x >= 1 && x <= 29){
                    if (Map.map[y+1][x-1] == '+') {
                        if (Math.abs(Map.map[y + 2][x - 1] - Map.map[y][x + 1]) == 1) {
                            sll.add(Map.map[y][x + 1]);
                            sll.add(Map.map[y][x - 1]);
                            whichturn = 4;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (whichturn == 0){
                    x = x-1;
                    y = y-1;
                }
                if (whichturn == 2){
                    x = x-2;
                }
                if (whichturn == 4){
                    x = x-1;
                    y = y+1;
                }
            }
            else if (whichturn == 3|| whichturn == 8 || whichturn == 10){
                if (y>=2 && x >= 1 && x <= 29 ){
                    if (Map.map[y-1][x+1] == '+') {
                        if (Math.abs(Map.map[y - 2][x + 1] - Map.map[y][x + 1]) == 1) {
                            sll.add(Map.map[y][x - 1]);
                            sll.add(Map.map[y][x + 1]);
                            whichturn = 1;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (x >= 1 && x <= 27){
                    if (Map.map[y][x+2] == '+' ) {
                        if (Math.abs(Map.map[y][x + 3] - Map.map[y][x + 1]) == 1) {
                            sll.add(Map.map[y][x - 1]);
                            sll.add(Map.map[y][x + 1]);
                            whichturn = 3;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if ( y>=16 && x >= 1 && x <= 29) {
                    if (Map.map[y + 1][x + 1] == '+') {
                        if (Math.abs(Map.map[y + 2][x + 1] - Map.map[y][x + 1]) == 1) {
                            sll.add(Map.map[y][x - 1]);
                            sll.add(Map.map[y][x + 1]);
                            whichturn = 5;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (whichturn == 1){
                    x = x+1;
                    y = y-1;
                }
                if (whichturn == 3){
                    x = x+2;
                }
                if (whichturn == 5){
                    x = x+1;
                    y = y+1;
                }
            }
            else if (whichturn == 4 || whichturn == 5 || whichturn == 11){
                if (y>=1 && x >= 2 && y <= 17){
                    if (Map.map[y+1][x-1] == '+') {
                        if (Math.abs(Map.map[y + 1][x] - Map.map[y + 1][x - 2]) == 1) {
                            sll.add(Map.map[y - 1][x]);
                            sll.add(Map.map[y + 1][x]);
                            whichturn = 9;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (y>=1 && x >= 28 && y <= 17){
                    if (Map.map[y+1][x+1] == '+') {
                        if (Math.abs(Map.map[y + 1][x] - Map.map[y + 1][x + 2]) == 1) {
                            sll.add(Map.map[y - 1][x]);
                            sll.add(Map.map[y + 1][x]);
                            whichturn = 10;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if ( y>=1 && y <= 15){
                    if (Map.map[y+2][x] == '+') {
                        if (Math.abs(Map.map[y + 3][x] - Map.map[y + 1][x]) == 1) {
                            sll.add(Map.map[y - 1][x]);
                            sll.add(Map.map[y + 1][x]);
                            whichturn = 11;
                        } else {
                            flag = false;
                        }
                        correctnes++;
                    }
                }
                if (whichturn == 9){
                    x = x-1;
                    y = y+1;
                }
                if (whichturn == 10){
                    x = x+1;
                    y = y+1;
                }
                if (whichturn == 11){
                    y = y-2;
                }
            }
            if (correctnes != 0 || correctnes!= 1){
                flag = false;
            }
            if (correctnes == 0){
                break;
            }
        }
        return flag;
    }
}


