import enigma.core.Enigma;

public class Chain {
    public static SingleLinkedList chain = new SingleLinkedList();
    public static char map[][]= Game.map;
    public enigma.console.Console cn = Enigma.getConsole("Chain",80,40,20,15);
    public int plusCounter;

    Chain(){
        SingleLinkedList p_chain = new SingleLinkedList();
        chain=p_chain;
    }

    public SingleLinkedList getChain(){
        return chain;
    }
    public void newRound() {

        if (controlChain()){
            // add to table
            score();
            updateBoard();
            chain.setHead(null); //resetting head
        }
        else{
            Game.gameover = true;
            cn.getTextWindow().setCursorPosition(10,24);
            cn.getTextWindow().output("Error in chain");
            cn.getTextWindow().setCursorPosition(10,25);
            cn.getTextWindow().output("- Game Over -");
        }
    }
    public boolean controlChain(){
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
    public void updateBoard(){
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
    public void changeMap(Node temp) {
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
            Game.score+= ( (chain.size()+1)*(chain.size()+1) );
            cn.getTextWindow().setCursorPosition(35,2);
            cn.getTextWindow().output("Score: "+Game.score);
        }
    }

}
