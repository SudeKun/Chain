import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.util.Random;
import java.awt.*;

public class Map {
    public static enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard");
    //public static TextAttributes Orange = new TextAttributes(Color.ORANGE);
    //public static TextAttributes Cyan = new TextAttributes(Color.CYAN);
    //public static TextAttributes Brown = new TextAttributes(new Color(92, 64, 51));
    //public static TextAttributes White = new TextAttributes(Color.WHITE);
    //public static TextAttributes Green = new TextAttributes(Color.GREEN);
    //public static TextAttributes Magenta = new TextAttributes(Color.MAGENTA);
    public static Random random = new Random();
    public static char[][] map;

    private void create(int howMany, char what) {
        int count = 0;
        int randomx = 0, randomy = 0;

        while (count < howMany) {
            randomx = random.nextInt(0,31);
            randomy = random.nextInt(0,20);
            if((randomx % 2 == 0 && randomy % 2 == 0) && map[randomy][randomx]==' '){
                    map[randomy][randomx] = what;
                    count++;
            }
        }
    }

    public static void print(int x, int y, char character) {
        cn.getTextWindow().output(x,y,character);
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
