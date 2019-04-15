package sample;

public class Game {

    private Cell[][] board;

    public Game() {
        board = new Cell[50][50];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                board[i][j]= new Cell(Math.random());
            }

        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }
}
