import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    private static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {
        int playerNo = myScanner.nextInt();
        int teamNo = myScanner.nextInt();
        myScanner.nextLine(); // Receiving the ENTER
        Map<String, String> playerMap = new HashMap<>();
        inputPlayerName(playerNo, playerMap);
        Set<String> badTeamSet = findBadTeams(teamNo, playerMap);
        badTeamSet.forEach(System.out::println);
    }

    private static void inputPlayerName(int playerNo, Map<String, String> playerMap) {
        for (int i = 0; i < playerNo; i++) {
            String name = myScanner.nextLine();
            playerMap.put(name, null);
        }
    }

    private static Set<String> findBadTeams(int teamNo, Map<String, String> playerMap) {
        Set<String> badTeamSet = new TreeSet<>();
        for (int i = 0; i < teamNo; i++) {
            String teamName = myScanner.nextLine();
            int playerNo = myScanner.nextInt();
            myScanner.nextLine(); // Receiving the ENTER
            for (int j = 0; j < playerNo; j++) {
                String playerName = myScanner.nextLine();
                if (!playerMap.containsKey(playerName)) {
                    badTeamSet.add(teamName);
                } else {
                    if (playerMap.get(playerName) == null) {
                        playerMap.put(playerName, teamName);
                    } else {
                        badTeamSet.add(teamName);
                        badTeamSet.add(playerMap.get(playerName));
                    }
                }
            }
        }
        return badTeamSet;
    }

}