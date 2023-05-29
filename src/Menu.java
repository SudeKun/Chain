import enigma.core.Enigma;

public class Menu {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",80,40,20,15);
    public static String choice;
    public static String p_seed;
    public static boolean gameon=true;
    public static String username;

    Menu() throws Exception {
        while (gameon) {
            clear_screen();
            cn.getTextWindow().setCursorPosition(0, 0);
            cn.getTextWindow().output("Welcome to Chain Game!");
            cn.getTextWindow().setCursorPosition(0, 1);
            cn.getTextWindow().output("Please enter a number from list:");
            cn.getTextWindow().setCursorPosition(0, 3);
            cn.getTextWindow().output("1-Start Game");
            cn.getTextWindow().setCursorPosition(0, 4);
            cn.getTextWindow().output("2-High Score Table");
            cn.getTextWindow().setCursorPosition(0, 5);
            cn.getTextWindow().output("3-Quit game");
            cn.getTextWindow().setCursorPosition(33, 1);
            choice = cn.readLine();
            switch (choice) {
                case "1":
                    clear_screen();
                    cn.getTextWindow().setCursorPosition(0,0);
                    cn.getTextWindow().output("Please enter seed:");
                    cn.getTextWindow().setCursorPosition(19,0);
                    p_seed=cn.readLine();
                    if (p_seed.isEmpty()) p_seed="0";
                    Map.seed=Long.parseLong(p_seed);
                    cn.getTextWindow().setCursorPosition(0,2);
                    cn.getTextWindow().output("Please enter your name:");
                    cn.getTextWindow().setCursorPosition(23,2);
                    username=cn.readLine();
                    new Map();
                    new Game();
                    Game.round=0;
                    Game.gameover=false;
                    break;
                case "2":
                    clear_screen();
                    if(Game.map!=null) Game.reset();
                	cn.getTextWindow().setCursorPosition(0, 0);
        			Game.hstable.displaytable();
                    cn.readLine();
                    gameon=true;
                    break;
                case "3":
                    gameon=false;
                    System.exit(0);
                    break;
                default:
                    clear_screen();
                    cn.getTextWindow().setCursorPosition(0,0);
                    cn.getTextWindow().output("Please enter a number between 1-3");
                    gameon=true;
                    break;
            }
        }
        
    }
    static public void clear_screen(){
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 40; j++) {
                cn.getTextWindow().output(i, j, ' ');
            }
        }
    }
}