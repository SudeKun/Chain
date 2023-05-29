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
             //resetting head
        }
        else{
            Game.gameover = true;
            cn.getTextWindow().setCursorPosition(10,24);
            cn.getTextWindow().output("Error in chain");
            cn.getTextWindow().setCursorPosition(10,25);
            cn.getTextWindow().output("- Game Over -");
        }
        chain.setHead(null);
    }
    public boolean controlChain(){
        boolean flag = true;
        plusCounter = 0;
        Node temp = chain.getHead(); // {x,y}
        if (chain.size()<3) flag=false;

        SingleLinkedList tempSLL = new SingleLinkedList();
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
                int firstValue = Character.getNumericValue(map[y][x-1]);
                int secondValue = Character.getNumericValue(map[y][x+1]);
                tempSLL.add(firstValue);
                tempSLL.add(secondValue);
                int diff = Math.abs(firstValue - secondValue);
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
                int firstValue = Character.getNumericValue(map[y-1][x]);
                int secondValue = Character.getNumericValue(map[y+1][x]);
                tempSLL.add(firstValue);
                tempSLL.add(secondValue);
                int diff = Math.abs(firstValue - secondValue);
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
                int firstValue = Character.getNumericValue(map[y][x-1]);
                int secondValue = Character.getNumericValue(map[y][x+1]);
                tempSLL.add(firstValue);
                tempSLL.add(secondValue);
                diff = Math.abs(firstValue - secondValue);
            }
            else{
                int firstValue = Character.getNumericValue(map[y-1][x]);
                int secondValue = Character.getNumericValue(map[y+1][x]);
                tempSLL.add(firstValue);
                tempSLL.add(secondValue);
                diff = Math.abs(firstValue - secondValue);

            }
            if (diff != 1)
                flag = false;
        }

        if (plusCounter>1){
            flag = false;
        }

        else if (flag){
            String round = Integer.toString(Game.round);
            Game.table.addCoulmn(round);
            Node tempHead = tempSLL.getHead();
            Game.table.addLine(round, tempHead.getData().toString());
            tempHead = tempHead.getLink();
            while (tempHead.getLink() != null) {
                Game.table.addLine(round, tempHead.getData().toString());
                tempHead = tempHead.getLink().getLink();
            }
            Game.table.addLine(round, tempHead.getData().toString());
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
        chain.delete(position);
    }
    public void score(){
        if(plusCounter==1){
            Game.score+= ( (chain.size()+1)*(chain.size()+1) );
            cn.getTextWindow().setCursorPosition(35,2);
            cn.getTextWindow().output("Score: "+Game.score);
        }
    }
   
}
