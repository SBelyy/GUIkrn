package com.company.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton gameButton;

    public GameActionListener(int row, int cell, GameButton button){
        this.row = row;
        this.cell = cell;
        this.gameButton = button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        GameBoard board = gameButton.getBoard();

        if(board.isTurn(row, cell)){

            if(!updateByPlayersData(board)) {
                if (board.isFull()) {
                    board.getGame().showMessage("Ничья!");
                    board.emptyField();
                } else {
                    updateByAiData(board);
                }
            }

        }else {
            board.getGame().showMessage("Неккоректный ход");
        }
    }

    private void updateByAiData(GameBoard board) {
        int x = -1, y = -1;

        Random random = new Random();

//        do {
//            x = random.nextInt(GameBoard.dimension);
//            y = random.nextInt(GameBoard.dimension);
//        }while (!board.isTurn(x, y));

        int sum = -1;
        for (int i = 0; i < GameBoard.dimension; i++){
            for (int j = 0; j < GameBoard.dimension; j++){
                int ind = 0;
                    if(board.isTurn(i, j)){
                    if(!isLimitGameField((i + 1), j)){
                        if(board.checked((i + 1), j)) { ind++; }
                    }
                    if(!isLimitGameField((i - 1), j)){
                        if(board.checked((i - 1), j)) { ind++; }
                    }
                    if(!isLimitGameField(i, (j + 1))){
                        if(board.checked(i, (j + 1))) { ind++; }
                    }
                    if(!isLimitGameField(i, (j - 1))){
                        if(board.checked(i, (j - 1))) { ind++; }
                    }
                    if(!isLimitGameField((i + 1), (j + 1))){
                        if(board.checked((i + 1), (j + 1))) { ind++; }
                    }
                    if(!isLimitGameField((i - 1), (j - 1))){
                        if(board.checked((i - 1), (j - 1))) { ind++; }
                    }
                    if(ind > sum){
                        sum = ind;
                        x = i;
                        y = j;
                    }
                }
            }
        }

        board.updateGameField(x, y);

        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if(board.checkWin()){
            gameButton.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.getGame().passTurn();
            board.emptyField();
        }else {
            board.getGame().passTurn();
        }
    }

    private static boolean isLimitGameField(int x, int y) {
        boolean result = false;

        if(x < 0 || x >= GameBoard.dimension || y < 0 || y >= GameBoard.dimension){
            result = true;
        }

        return result;
    }


    private boolean updateByPlayersData(GameBoard board) {
        board.updateGameField(row, cell);

        boolean result = false;

        gameButton.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if(board.checkWin()){
            board.emptyField();
            result = true;
            gameButton.getBoard().getGame().showMessage("Вы выиграли!");
        }
        else {
            board.getGame().passTurn();
        }

        return result;
    }
}
