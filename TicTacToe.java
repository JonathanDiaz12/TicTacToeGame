import javax.swing.*;

public class TicTacToe {
    public static void main(String[] args) {
        String[] options = {"Player vs Player", "Player vs Computer"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose game mode:",
            "Tic Tac Toe",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        boolean vsComputer = (choice == 1); // true if PvC mode
        SwingUtilities.invokeLater(() -> new GameBoard(vsComputer));
    }
}
