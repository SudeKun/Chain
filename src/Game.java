import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
public enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard");
public TextMouseListener tmlis;
public KeyListener klis;
public char[][] map = Map.map;

// ------ Standard variables for mouse and keyboard ------
public int mousepr;          // mouse pressed?
public int mousex, mousey;   // mouse text coords.
public int keypr;   // key pressed?
public int rkey;    // key   (for press/release)
// ----------------------------------------------------


Game() throws Exception {   // --- Contructor

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
   };*/
   cn.getTextWindow().addTextMouseListener(tmlis);

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



   int px=1,py=0;

   map[py][px]='|';
   cn.getTextWindow().output(px, py, '|');

   while(true) {
      if (keypr == 1) {    // if keyboard button pressed
         boolean plus=false;
         if(map[py][px] == ' ' || map[py][px]=='|') {
            map[py][px] = ' ';
            cn.getTextWindow().output(px, py, ' ');
         }
         //Can be deleted
         /*else if(map[py][px]=='1') {
            map[py][px]='1';
            cn.getTextWindow().output(px,py,'1');
         }
         else if(map[py][px]=='2') {
            map[py][px]='2';
            cn.getTextWindow().output(px,py,'2');
         }
         else if(map[py][px]=='3') {
            map[py][px]='3';
            cn.getTextWindow().output(px,py,'3');
         }
         else if(map[py][px]=='4') {
            map[py][px]='4';
            cn.getTextWindow().output(px,py,'4');
         }*/
         else if(map[py][px]=='+'){
            map[py][px]='+';
            cn.getTextWindow().output(px,py,'+');
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
         if (rkey == KeyEvent.VK_SPACE) {
            if (map[py][px] == '+') {
               cn.getTextWindow().output(px, py, ' ');
               map[py][px] = ' ';
            }
            else {
               map[py][px] = '+';
               cn.getTextWindow().output(px, py, '+');
            }
         }
         //Enter
         if (rkey == KeyEvent.VK_ENTER){

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
            System.exit(0);
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

