package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Game implements GameInterface {

    private int[][] board;
    private String fillBoard;
    private String playerOne;
    private String playerTwo;
    private int row;
    private int col;
    private int boardRowsLength;
    private int boardColsLength;

    String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Game(int nRows, int nCols) {

        board = new int[nRows][nCols];

        for (row = 0; row < nRows; row++) {
            for (col = 0; col < nCols; col++) {
                board[row][col] = 0;
            }
        }

        boardRowsLength = board[0].length - 1;
        boardColsLength = board.length - 1;
        fillBoard = ".";
        playerOne = "X";
        playerTwo = "O";

    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {

        boolean invalidInput;
        invalidInput = true;

        int playerRow;
        int playerCol;
        int[] playerCoordinates = new int[2];

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        do {
            System.out.println("Player " + player + " coordinates");
            userInput = scanner.nextLine();

            if (userInput.matches("^[A-Za-z][0-9][0-9]?$")) {
                String charInput = userInput.substring(0,1).toUpperCase();
                char[] charArray = abc.toCharArray();

                for (playerRow = 0; playerRow < charArray.length; playerRow++) {

                    String charToString = Character.toString(charArray[playerRow]);
                    if (charToString.equals(charInput)) {
                        playerCoordinates[0] = playerRow;
                    }
                }

                invalidInput = false;
                playerCol = Integer.parseInt(userInput.substring(1))-1;
                playerCoordinates[1] = playerCol;

                try {
                    int playerInputRow = playerCoordinates[0];
                    int playerInputCol = playerCoordinates[1];

                    if (playerInputRow > boardRowsLength || playerInputCol > boardColsLength) {
                        invalidInput = true;
                        System.out.print("Coordinates out of bounds! Try again: \n");

                    } else if (board[playerInputRow][playerInputCol] != 0) {
                        invalidInput = true;
                        System.out.print("Coordinates already taken! Try again: \n");

                    } else {
                        invalidInput = false;
                        return playerCoordinates;
                    }
                } catch (Exception ArrayIndexOutOfBoundsException) {}

            } else {

                System.out.print("That's not a valid coordinate! Try again: \n");
            }

        } while (invalidInput);

        return playerCoordinates;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        if (player == 1) {
            this.board[row][col] = 1;
        } else {
            this.board[row][col] = 2;
        }
    }

    public boolean hasWon(int player, int howMany) {
        int[][] matrix = this.board;

        for( int row = 0; row < matrix.length; row++ )
        {
            for( int col = 0; col < matrix[row].length; col++ )
            {

                int element = player;

                if( col <= matrix[row].length-5 && element == matrix[row][col] && element == matrix[row][col+1] && element == matrix[row][col+2] && element == matrix[row][col+3] && element == matrix[row][col+4])
                    return true;

                if( row <= matrix.length - 5 && element == matrix[row][col] && element == matrix[row+1][col] && element == matrix[row+2][col] && element == matrix[row+3][col] && element == matrix[row+4][col])
                {
                    return true;
                }

                if( row <= matrix.length-5 && col <= matrix[row].length-5 )
                {
                    // If the current element equals each element diagonally to the bottom right
                    if( element == matrix[row][col] && element == matrix[row+1][col+1] && element == matrix[row+2][col+2] && element == matrix[row+3][col+3] && element == matrix[row+4][col+4] )
                        return true;
                }


                if( row <= matrix.length-5 && col >= matrix[row].length-5 )
                {
                    // If the current element equals each element diagonally to the bottom left
                    if( element == matrix[row][col] && element == matrix[row+1][col-1] && element == matrix[row+2][col-2] && element == matrix[row+3][col-3] && element == matrix[row+4][col-4] )
                        return true;
                }

            }
        }

    /* If all the previous return statements failed, then we found no such
       patterns of four identical elements in this matrix, so we return false */
        return false;
    }




    // Checks if the board is full
    public boolean isFull() {
        for (int [] row : board) {
            for (int elem : row) {
                if (elem == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.print("\n");

        for (int i = 0; i < this.board[0].length; i++) {
            System.out.print("   ");
            System.out.print(i+1);
        }

        for (int i = 0; i < this.board.length; i++) {
            System.out.println("");
            System.out.print(abc.charAt(i));

            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print("   ");
                switch (this.board[i][j]) {
                    case 0:
                        System.out.print(this.fillBoard);
                        break;
                    case 1:
                        System.out.print(this.playerOne);
                        break;
                    case 2:
                        System.out.print(this.playerTwo);
                        break;
                }
            }
            System.out.print("  ");
        }
        System.out.print("\n");
    }

    //Prints the result of the ended game
    public void printResult(int player) {
        if(player == 0) {
            System.out.println("It's a tie");
        }
        else {
            System.out.println("Player " + player + " has won");
        }
    }

    public void enableAi(int player) {
    }

    // Game logic
    public void play(int howMany) {
        System.out.print("Hit ENTER to start");
        int currentPlayer;
        int[] playerMoveCoordinates;
        int playerInputRow;
        int playerInputCol;
        int gameOn = 1;

        //  Game loop
        while(gameOn == 1) {
            // Player's 1 turn
            if (!isFull()) {
                //Check if the game has ended then proceeds with player's 1 turn
                if (!hasWon(2, 5)) {
                    printBoard();
                    currentPlayer = 1;
                    playerMoveCoordinates = getMove(currentPlayer);
                    playerInputRow = playerMoveCoordinates[0];
                    playerInputCol = playerMoveCoordinates[1];
                    mark(1, playerInputRow, playerInputCol);
                    printBoard();
                } else {
                    gameOn = 0;
                    printResult(2);

                }
            }
            else {
                gameOn = 0;
                printResult(0);
                break;
            }
            //Player's 2 turn
            if(!isFull()) {
                //Check if the game has ended then proceeds with player's 2 turn
                if (!hasWon(1, 5)) {
                    currentPlayer = 2;
                    playerMoveCoordinates = getMove(currentPlayer);
                    playerInputRow = playerMoveCoordinates[0];
                    playerInputCol = playerMoveCoordinates[1];
                    mark(2, playerInputRow, playerInputCol);
                    printBoard();
                } else {
                    gameOn = 0;
                    printResult(1);
                    break;
                }
            }
            else {
                gameOn = 0;
                printResult(0);
                break;
            }

        }
    }
}
