import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter seed:");
        Map.seed=sc.nextLong();
        new Map();
        new Game();
    }
}
