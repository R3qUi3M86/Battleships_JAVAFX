package com.battleships.model;

public class Board {
    BoardField[][] boardFields;

    public Board(int boardSize){
        boardFields = new BoardField[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                boardFields[i][j] = new BoardField();
            }
        }
    }

    public BoardField[][] getBoardFields() {
        return boardFields;
    }

    public BoardField getBoardField(int[] coordinate){
        return boardFields[coordinate[0]][coordinate[1]];
    }
}
