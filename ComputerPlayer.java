import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer {
    private Random rand = new Random();

    public int getMove(char[] board) {
        ArrayList<Integer> available = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                available.add(i);
            }
        }

        if (available.size() > 0) {
            int choice = rand.nextInt(available.size());
            return available.get(choice);
        }

        return -1; // No valid moves
    }
}
