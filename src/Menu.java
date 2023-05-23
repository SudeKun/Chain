import enigma.core.Enigma;

public class Menu {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",80,40,20,15);
    public static String choice;
    public static String p_seed;
    public static boolean gameon=true;

    Menu() throws Exception {
        while (gameon) {
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
                    for (int i = 0; i < 40; i++) {
                        for (int j = 0; j < 40; j++) {
                            cn.getTextWindow().output(i, j, ' ');
                        }
                    }
                    cn.getTextWindow().setCursorPosition(0,0);
                    cn.getTextWindow().output("Please enter seed:");
                    cn.getTextWindow().setCursorPosition(19,0);
                    p_seed=cn.readLine();
                    Map.seed=Long.parseLong(p_seed);
                    new Map();
                    new Game();
                    break;
                case "2":
                    break;
                case "3":
                    gameon=false;
                    System.exit(0);
                    break;
                default:
                    for (int i = 0; i < 40; i++) {
                        for (int j = 0; j < 40; j++) {
                            cn.getTextWindow().output(i, j, ' ');
                        }
                    }
                    cn.getTextWindow().setCursorPosition(0,0);
                    cn.getTextWindow().output("Please enter a number between 1-3");
                    gameon=true;
            }
        }
        
    }
}
