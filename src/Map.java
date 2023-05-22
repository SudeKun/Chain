import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.util.Random;
import java.awt.*;

public class Map {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",100,40,25,15);
    public static TextAttributes White = new TextAttributes(Color.WHITE);
    public static TextAttributes Orange = new TextAttributes(Color.ORANGE);
    public static TextAttributes Cyan = new TextAttributes(Color.CYAN);
    public static TextAttributes Green = new TextAttributes(Color.GREEN);
    public static TextAttributes Magenta = new TextAttributes(Color.MAGENTA);
    public static Random random = new Random();
    public static Random random_seed = new Random();
    public static long seed;
    public static char[][] map;

    private void create(int howMany, char what) {
        int count = 0;
        int x, y;
        if(seed==0){
            seed=random_seed.nextLong(0,10000);
        }
        random.setSeed(seed);
        while (count < howMany) {
            x = random.nextInt(0,31);
            y = random.nextInt(0,20);
            if((x % 2 == 0 && y % 2 == 0) && map[y][x]==' '){
                map[y][x] = what;
                count++;
            }
        }
    }

    public static void print(int x, int y, char character) {
        var color=White;
        if(character=='1') color=Orange;
        else if(character=='2') color=Cyan;
        else if(character=='3') color=Green;
        else if(character=='4') color=Magenta;
        cn.getTextWindow().output(x,y,character,color);
    }

    public Map() {
        map = new char[20][31];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = ' ';
            }
        }

        create(40,'1');
        create(40,'2');
        create(40,'3');
        create(40,'4');

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                print(j,i,map[i][j]);
            }
        }

    }
}
