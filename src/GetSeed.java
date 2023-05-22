import enigma.core.Enigma;

public class GetSeed {
    public static enigma.console.Console cn = Enigma.getConsole("Chain",100,40,22,15);
    public static String p_seed;

    GetSeed() throws Exception {
        cn.getTextWindow().setCursorPosition(0,0);
        cn.getTextWindow().output("Please enter seed:");
        cn.getTextWindow().setCursorPosition(19,0);
        p_seed=cn.readLine();
        Map.seed=Long.parseLong(p_seed);
    }
}
