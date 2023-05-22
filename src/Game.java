import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;

public class Game {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",100,40,25,15);
    public TextMouseListener tmlis;
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int mousepr;          // mouse pressed?
    public int mousex, mousey;   // mouse text coords.
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
    // ----------------------------------------------------




    public static char[][] map = new char[19][31];
    static SingleLinkedList chain = new SingleLinkedList();

    boolean gameover = false;

    Game() throws Exception {   // --- Contructor

        // ------ Standard code for mouse and keyboard ------ Do not change
        tmlis=new TextMouseListener() {
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


        /*
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
                if (rkey == KeyEvent.VK_1 ) {
                    cn.getTextWindow().output(15, 1, (char) rkey);
                }
                else if (rkey == KeyEvent.VK_2){
                    // High Score Table
                }
                else if (rkey == KeyEvent.VK_3){
                    // Quit
                }
                else cn.getTextWindow().output((char) rkey);
                if (rkey == KeyEvent.VK_ENTER) {
                        for (int i = 0; i < 6; i++) {
                            cn.getTextWindow().output(0, i, ' ');
                        }
                        choice=false;
                    }
                }
                keypr=0;
        }*/





        char[] numbers = {'1','2','3','4'};

        // Fill map with 1,2,3,4
        Random rand = new Random(4523);
        for (int i =0; i < 19; i+=2){
            for (int j =0; j<29; j+=2){
                int index = rand.nextInt(0,4);
                map[i][j] =numbers[index];
            }
        }

        int round=0;
        cn.getTextWindow().setCursorPosition(35,0);
        cn.getTextWindow().output("Board Seed: "+ 4523);
        cn.getTextWindow().setCursorPosition(35,1);
        cn.getTextWindow().output("Round: "+round);
        cn.getTextWindow().setCursorPosition(35,2);
        cn.getTextWindow().output("Score: ");
        cn.getTextWindow().setCursorPosition(35,3);
        cn.getTextWindow().output("---------------------------------------");
        cn.getTextWindow().setCursorPosition(35,4);
        cn.getTextWindow().output("Table: ");


        int x=1,y=0;
        cn.getTextWindow().output(x,y,'_');

        printMap();

        while(!gameover) {

            if(keypr==1) {    // if keyboard button pressed
                if(rkey==KeyEvent.VK_LEFT && x > 0 && (map[y][x-1] == 0 || map[y][x-1] == '+' )) x--;
                if(rkey==KeyEvent.VK_RIGHT && x < 32 &&  (map[y][x+1] == 0 || map[y][x+1] == '+' )) x++;
                if(rkey==KeyEvent.VK_UP && y > 0 && (map[y-1][x] == 0 || map[y-1][x] == '+' )) y--;
                if(rkey==KeyEvent.VK_DOWN && y < 20 && (map[y+1][x] == 0 || map[y+1][x] == '+' )) y++;

                char rckey=(char)rkey;
                //        left          right          up            down
                if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') cn.getTextWindow().output(x,y,'_'); // VK kullanmadan test teknigi
                else cn.getTextWindow().output(rckey);

                if(rkey==KeyEvent.VK_SPACE) {

                    if (map[y][x] == '+') {
                        map[y][x] = 0;
                        cn.getTextWindow().output(x, y, ' ');
                        int[] position = {x,y};
                        removePlus(position);
                    }
                    else if (map[y][x] == 0){
                        map[y][x] = '+';
                        cn.getTextWindow().output(x, y, '+');
                        // add to chain
                        int[] position= {x,y};
                        chain.add(position);
                    }
                }
                if (rkey == KeyEvent.VK_ENTER){
                    round++;
                    cn.getTextWindow().setCursorPosition(35,1);
                    cn.getTextWindow().output("Round: "+round);
                    newRound();
                    printMap();
                    cn.getTextWindow().output(x,y,'_');
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

                keypr=0;    // last action
            }
            Thread.sleep(200);
        }
    }



    private void newRound() {

        if (controlChain()){
            // add to table
            updateBoard();
            chain.setHead(null); //resetting head
        }
        else{
            gameover = true;
            cn.getTextWindow().setCursorPosition(40,15);
            cn.getTextWindow().output("Error in chain \n                                     - Game Over - ");
        }
    }

    static void printMap(){
        // change printing for enigma
        for (int i=0; i<19; i++){
            for (int j=0; j<31; j++){
                if (map[i][j]!=0)
                    cn.getTextWindow().output(j,i, map[i][j]);
                else
                    cn.getTextWindow().output(j,i,' ');
            }
        }
    }

    boolean controlChain(){
        boolean flag = true;
        int plusCounter = 0;
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
        map[y][x] = 0;
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
        if (y - 1 > -1 && map[y - 1][x] == '+')    //up
            count++;
        if (y + 1 < 20 && map[y + 1][x] == '+')  //down
            count++;
        if (x - 1 > -1 && map[y][x - 1] == '+')    //left
            count++;
        if (x + 1 < 32 && map[y][x + 1] == '+')   //right
            count++;

        return count;
    }

    public void removePlus(int[] position) {
        Node temp = chain.getHead();
        SingleLinkedList tempSLL = new SingleLinkedList();
        while (temp.getLink() != null){
            int tempX = ((int[]) temp.getData())[0];
            int tempY = ((int[]) temp.getData())[1];
            if (temp.getData() == null ||  tempX ==  position[0] || tempY == position[1]) {
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

}
