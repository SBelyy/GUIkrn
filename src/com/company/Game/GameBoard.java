package com.company.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension  = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButton;

    private Game game;
    private static char nullSymbole = '\u0000';

    public GameBoard(Game currentGame){
        this.game = currentGame;
        initField();
    }

    private void initField() {
        setBounds(dimension * cellSize, dimension * cellSize, 400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Крестики-нолики");

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра!");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize*dimension, cellSize);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(dimension*cellSize, dimension*cellSize);

        gameField = new char[dimension][dimension];
        gameButton = new GameButton[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++){
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButton[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++){
            gameButton[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbole;
        }
    }

    public Game getGame() {
        return game;
    }

    boolean isTurn(int x, int y){
        boolean result = false;

        if(gameField[y][x] == nullSymbole){
            result = true;
        }

        return  result;
    }

    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checked(int x, int y){
        if(gameField[y][x] == game.getCurrentPlayer().getPlayerSign()){
            return true;
        }
        return false;
    }

    public boolean checkWin(){
        boolean result = false;

        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();

        if(checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)){
            result = true;
        }

        return result;
    }

    boolean isFull(){
        boolean result = true;
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if(gameField[i][j] == nullSymbole){
                    return false;
                }
            }
        }
        return result;
    }

    private boolean checkWinLines(char playerSymbol) {
        boolean leftRight, rightLeft, result;

        leftRight = true;
        rightLeft = true;
        result = false;

        for (int i = 0; i < dimension; i++){
            leftRight &= (gameField[i][i] == playerSymbol);
            rightLeft &= (gameField[dimension - i - 1][i] == playerSymbol);
        }

        if (rightLeft || leftRight){
            result = true;
        }

        return result;
    }

    private boolean checkWinDiagonals(char playerSymbol) {
        boolean cols, rows, result = false;

        for (int col = 0; col < dimension; col++) {
            cols = true;
            rows = true;
            for (int row = 0; row < dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }

            if(cols || rows){
                result = true;
            }

            if(result){break;}
        }

        return result;
    }

    public GameButton getButton(int buttonIndex){
        return gameButton[buttonIndex];
    }

}
