import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(answer(scanner.nextLine()));
    }

    public static int answer(String inputString) {
        Pattern p = Pattern.compile("@#([a-z]*),x=([\\d]*),y=([\\d]*),distance=([\\d]*)#@");
        Matcher m = p.matcher(inputString);
        int lastEnd = 0, lastDistance = 0, numberOfGoals = 0;
        boolean getBall = false;
        while (m.find()) {
            if (m.start() - lastEnd > 200) {
                break;
            }
            int x = Integer.parseInt(m.group(2));
            int y = Integer.parseInt(m.group(3));
            int distance = Integer.parseInt(m.group(4));
            if (m.group(1).equals("forward")) {
                if (lastDistance > distance) {
                    getBall = false;
                }
                if (Integer.parseInt(m.group(4)) < 10) {
                    getBall = true;
                }
                if (getBall && Math.sqrt(x * x + y * y) - distance < 10) {
                    numberOfGoals += 1;
                }
            }
            lastEnd = m.end();
            lastDistance = distance;
        }
        return numberOfGoals;
    }
}