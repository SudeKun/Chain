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
         //Add +
         if (rkey == KeyEvent.VK_SPACE) {
            if (map[py][px] == '+') {
               cn.getTextWindow().output(px, py, ' ');
               map[py][px] = ' ';
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
}

