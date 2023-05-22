import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
    public enigma.console.Console cn = Enigma.getConsole("Chain",100,40,25,15);
    public static TextAttributes Red = new TextAttributes(Color.RED);
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
// ----------------------------------------------------

    public static char[][] map = Map.map;
    static SingleLinkedList chain = new SingleLinkedList();

    boolean gameover=false;
    public int score=0;

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
        cn.getTextWindow().output("Score: "+score);
        cn.getTextWindow().setCursorPosition(35,3);
        cn.getTextWindow().output("---------------------------------------");
        cn.getTextWindow().setCursorPosition(35,4);
        cn.getTextWindow().output("Table: ");

        int x=1,y=0;
        map[y][x]='|';
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
                        removePlus(position);
                    }
                    else {
                        map[y][x] = '+';
                        cn.getTextWindow().output(x, y, '+',Red);
                        int[] position= {x,y};
                        chain.add(position);
                    }
                }

                //Enter
                if (rkey == KeyEvent.VK_ENTER){
                    round++;
                    cn.getTextWindow().setCursorPosition(35,1);
                    cn.getTextWindow().output("Round: "+round);
                    newRound();
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
                    cn.getTextWindow().output(x, y, '|');
                }

                //Output for +
                if(map[y][x]=='+') {
                    cn.getTextWindow().output(x, y, '|');
                }
                else{
                    map[y][x] = '|';
                    cn.getTextWindow().output(x, y, '|');
                }


                keypr = 0;    // last action
            }
            Thread.sleep(20);
        }
    }
    public int plusCounter;

    private void newRound() {

        if (controlChain()){
            // add to table
            score();
            updateBoard();
            chain.setHead(null); //resetting head
        }
        else{
            gameover = true;
            cn.getTextWindow().setCursorPosition(10,24);
            cn.getTextWindow().output("Error in chain");
            cn.getTextWindow().setCursorPosition(10,25);
            cn.getTextWindow().output("- Game Over -");
        }
    }
    boolean controlChain(){
        boolean flag = true;
        plusCounter = 0;
        Node temp = chain.getHead(); // {x,y}

        // for more than 1 plus
        while(temp.getLink()!=null){
            if (temp.getData() == null ) {
                temp = temp.getLink();
                continue;
            }
            int x = ((int[])  temp.getData())[0];
            int y = ((int[])  temp.getData())[1];
            if (y % 2 == 0){
                // numbers are on right and left side.

                int diff = Math.abs(Character.getNumericValue(map[y][x-1]) - Character.getNumericValue(map[y][x+1]));
                if (diff != 1)
                    flag = false;
                if (plusCount(y,x-1) != 2){
                    plusCounter++;
                }
                if (plusCount(y,x+1) != 2){
                    plusCounter++;
                }

            }
            else{
                int diff = Math.abs(Character.getNumericValue(map[y-1][x]) - Character.getNumericValue(map[y+1][x]));
                if (diff != 1)
                    flag = false;
                if (plusCount(y-1,x) != 2 ){
                    plusCounter++;
                }
                if (plusCount(y+1,x) != 2){
                    plusCounter++;
                }
            }
            temp=temp.getLink();
        }

        // checking last (or only) plus
        if (temp.getLink() == null && temp.getData() != null){
            int x = ((int[])  temp.getData())[0];
            int y = ((int[])  temp.getData())[1];
            int diff;
            if (y % 2 == 0){
                // numbers are on right and left side.
                diff = Math.abs(Character.getNumericValue(map[y][x - 1]) - Character.getNumericValue(map[y][x + 1]));
            }
            else{

                diff = Math.abs(Character.getNumericValue(map[y - 1][x]) - Character.getNumericValue(map[y + 1][x]));

            }
            if (diff != 1)
                flag = false;
        }

        if (plusCounter>1){
            flag = false;
        }

        return flag;
    }
    void updateBoard(){
        Node temp = chain.getHead(); // {x,y}

        while (temp.getLink() != null){
            if (temp.getData() == null ) {
                temp = temp.getLink();
                continue;
            }
            changeMap(temp);
            temp = temp.getLink();
        }
        if (temp.getLink() == null && temp.getData() != null){
            changeMap(temp);
        }
    }
    private void changeMap(Node temp) {
        int x = ((int[])  temp.getData())[0];
        int y = ((int[])  temp.getData())[1];
        map[y][x] = ' ';
        cn.getTextWindow().output(x,y,' ');
        if (y % 2 == 0){
            // numbers are on right and left side.
            map[y][x-1] = '.';
            map[y][x+1] = '.';
            cn.getTextWindow().output(x-1,y,'.');
            cn.getTextWindow().output(x+1,y,'.');

        }
        else{
            map[y-1][x] = '.';
            map[y+1][x] = '.';
            cn.getTextWindow().output(x,y-1,'.');
            cn.getTextWindow().output(x,y+1,'.');

        }
    }
    public static int plusCount(int y, int x){
        int count = 0;
        if (y - 1 > 0 && map[y - 1][x] == '+')    //up
            count++;
        if (y + 1 < 21 && map[y + 1][x] == '+')  //down
            count++;
        if (x - 1 > 0 && map[y][x - 1] == '+')    //left
            count++;
        if (x + 1 < 31 && map[y][x + 1] == '+')   //right
            count++;
        return count;
    }
    public void removePlus(int[] position) {
        Node temp = chain.getHead();
        SingleLinkedList tempSLL = new SingleLinkedList();
        while (temp.getLink() != null){
            int temx = ((int[]) temp.getData())[0];
            int temy = ((int[]) temp.getData())[1];
            if (temp.getData() == null ||  temx ==  position[0] || temy == position[1]) {
                temp = temp.getLink();
                continue;
            }
            else{
                tempSLL.add(temp.getData());
            }

            temp = temp.getLink();
        }
        if (temp.getLink() == null)
        {
            if (temp.getData() != position ){
                tempSLL.add(temp.getData());
            }

        }
        chain = tempSLL;
    }
    public void score(){
        if(plusCounter==1){
            score+= ( (chain.size()+1)*(chain.size()+1) );
            cn.getTextWindow().setCursorPosition(35,2);
            cn.getTextWindow().output("Score: "+score);
        }
    }
}


