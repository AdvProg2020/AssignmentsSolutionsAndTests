import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int n = scanner.nextInt();
        int limit = 2 * n + 1;
        for (int i = 0; i < limit; i++) {
            StringBuilder currentLineBuilder = new StringBuilder();
            for (int j = 0; j < limit; j++) {
                char currentChar = ' ';
                int position = Math.abs(i - n) + Math.abs(j - n);
                if (position <= n && position > (n - k))
                    currentChar = '*';
                currentLineBuilder.append(currentChar);
            }
            System.out.println(currentLineBuilder);
        }
    }
}