import java.util.*;

public class Main {
    public static void main(String[] args) {
        Tree[] team = new Tree[11];
        for (int i = 0; i < 11; i++) {
            team[i] = new Tree(i);
        }
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        int n = s.nextInt();
        int player1;
        int player2;

        for (int i = 0; i < n; i++) {
            player1 = s.nextInt() - 1;
            player2 = s.nextInt() - 1;
            team[player1].neighbors.add(team[player2]);
            team[player2].neighbors.add(team[player1]);
        }
        Set<Tree> lastRow = new HashSet<Tree>();
        lastRow.add(team[0]);
        team[0].stem = 0;
        int passes = checkNeighbors(lastRow);
        if (passes == -1)
            System.out.println(0);
        else
            System.out.println(90 / (passes * t));
    }

    static int checkNeighbors(Set<Tree> Row) {
        Set<Tree> lastRow = new HashSet<Tree>();
        for (Tree nod :
                Row) {
            for (Tree x :
                    nod.neighbors) {
                if (x.num == 10)
                    return nod.stem + 1;
                if (x.stem == -1) {
                    x.stem = nod.stem + 1;
                    lastRow.add(x);
                }
            }
        }
        if (lastRow.isEmpty())
            return -1;
        return checkNeighbors(lastRow);

    }
}

class Tree {
    Set<Tree> neighbors = new HashSet<Tree>();
    int stem = -1;
    int num;

    public Tree(int num) {
        this.num = num;
    }
}
