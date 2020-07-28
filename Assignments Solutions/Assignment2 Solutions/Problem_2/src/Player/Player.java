package Player;

import Game.Pieces.Piece;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> allPlayers = new ArrayList<>();
    private String username;
    private String password;
    private long score, wins, losess, draws;
    private int undoLeft;
    private ArrayList<String> killedPieces = new ArrayList<>();
    private ArrayList<String> moves = new ArrayList<>();

    //Constructor
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.wins = 0;
        this.losess = 0;
        this.draws = 0;
        allPlayers.add(this);
    }

    //Getters
    public int getUndoLeft() {
        return undoLeft;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getScore() {
        return score;
    }

    //Setters
    public void setWins(long wins) {
        this.wins = wins;
    }

    public void setLosess(long losess) {
        this.losess = losess;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getWins() {
        return wins;
    }

    public long getLosess() {
        return losess;
    }

    public long getDraws() {
        return draws;
    }

    public void addToMoves(String thisMove) {
        moves.add(thisMove);
    }

    public void removeFromMoves() {
        moves.remove(moves.size() - 1);
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public void setUndoLeft(int undoLeft) {
        this.undoLeft = undoLeft;
    }

    //Other Methods
    public static String[] getAllPlayers() {
        String[] allNames = new String[allPlayers.size()];
        for(int i = 0 ; i < allNames.length ; i++)
            allNames[i] = allPlayers.get(i).getUsername();
        return allNames;
    }

    public int compareTo(Player anotherPlayer) {
        if(this.getScore() > anotherPlayer.getScore())
            return 1;
        else if(this.getScore() == anotherPlayer.getScore()) {
            if(this.wins > anotherPlayer.wins)
                return 1;
            else if(this.wins == anotherPlayer.wins) {
                if (this.draws > anotherPlayer.draws)
                    return 1;
                else if(this.draws == anotherPlayer.draws) {
                    if (this.losess < anotherPlayer.losess)
                            return 1;
                    else if (this.losess == anotherPlayer.losess) {
                            if (this.username.compareTo(anotherPlayer.username) < 0)
                                return 1;
                    }
                }
            }
        }
        return -1;
    }

    public static void sortPlayers() {
        for (int i = 0 ; i < allPlayers.size() ; i ++)
            for (int j = i + 1 ; j < allPlayers.size() ; j ++)
                if (allPlayers.get(i).compareTo(allPlayers.get(j)) < 0) {
                    Player tmp1 = allPlayers.get(i);
                    Player tmp2 = allPlayers.get(j);
                    allPlayers.remove(i);
                    allPlayers.add(i, tmp2);
                    allPlayers.remove(j);
                    allPlayers.add(j, tmp1);
                }
    }

    @Override
    public String toString() {
        return this.username+" "+this.score+" "+this.wins+" "+this.draws+" "+this.losess;
    }

    public static void printPlayers() {
        for (Player player : allPlayers) {
            System.out.println(player);
        }
    }

    public static boolean validateUsernameAndPassword(String username, String password) {
        Player player = Player.getPlayerByUsername(username);
        if(player.getPassword().equals(password))
            return true;
        else
            return false;
    }

    public static void removePlayer(String username) {
        allPlayers.remove(Player.getPlayerByUsername(username));
    }

    public static Player getPlayerByUsername(String username) {
        for (Player player : allPlayers) {
            if(player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public void addToKilledPieces(String thisKill) {
        killedPieces.add(thisKill);
    }

    public void removeFromKilledPieces() {
        killedPieces.remove(killedPieces.size() - 1);
    }

    public ArrayList<String> getKilledPieces() {
        return killedPieces;
    }

    public void resetPlayer() {
        this.undoLeft = 2;
        this.killedPieces = new ArrayList<>();
        this.moves = new ArrayList<>();
    }
}
