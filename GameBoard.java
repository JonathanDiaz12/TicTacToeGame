import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JFrame {
    private JButton[] buttons = new JButton[9];
    private char[] board = new char[9];
    private boolean isPlayerXTurn = true;
    private boolean vsComputer;
    private ComputerPlayer computer = new ComputerPlayer();

    public GameBoard(boolean vsComputer) {
        this.vsComputer = vsComputer;
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        initializeBoard();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            final int index = i;
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleMove(index);
                }
            });
            add(buttons[i]);
        }
    }

    private void handleMove(int index) {
        if (board[index] == ' ') {
            board[index] = isPlayerXTurn ? 'X' : 'O';
            buttons[index].setText(String.valueOf(board[index]));
            buttons[index].setEnabled(false);

            char currentPlayer = board[index];

            if (checkWin(currentPlayer)) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Player " + currentPlayer + " wins! Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    dispose();
                }
                return;
            } else if (checkDraw()) {
                int response = JOptionPane.showConfirmDialog(this,
                        "It's a draw! Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    dispose();
                }
                return;
            }

            isPlayerXTurn = !isPlayerXTurn;

            // Computer's turn
            if (vsComputer && !isPlayerXTurn) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int move = computer.getMove(board);
                    if (move != -1) {
                        handleMove(move);
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "That spot is already taken!", "Invalid Move", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean checkWin(char playerSymbol) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] == playerSymbol &&
                board[condition[1]] == playerSymbol &&
                board[condition[2]] == playerSymbol) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        isPlayerXTurn = true;
    }
}
