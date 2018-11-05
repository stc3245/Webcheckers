package com.webcheckers.model;
import java.lang.Math;

public class BoardGenerator {
    private enum TYPE{
        SPACE{
            @Override
            public String toString() {
                return "*";
            }
        },
        REDPAWN{
            @Override
            public String toString() {
                return "r";
            }
        },
        WHITEPAWN{
            @Override
            public String toString() {
                return "w";
            }
        },
        REDKING{
            @Override
            public String toString() {
                return "R";
            }
        },
        WHITEKING{
            @Override
            public String toString() {
                return "W";
            }
        }
    }

    static int size = 64;
    static int rowSize = 8;

    // board representation
    static String emptyBoard(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i ++){
            sb.append(TYPE.SPACE.toString());
        }
        return sb.toString();
    }

    static int getRow(int idx){
        return Math.floorDiv(idx, 8);
    }

    static int getCol(int idx){
        return idx % rowSize;
    }

    static boolean pieceAllowedAt(int idx){
        int row = getRow(idx);
        int col = getCol(idx);
        if (row % 2 == 0) {
            return col % 2 == 0;
        }else{
            return col % 2 == 1;
        }
    }

    static boolean boardIsValid(String board){
        if (board.length() != 64){
            return false;
        }
        for (int i = 0; i < size; i ++){
            char c = board.charAt(i);
            switch (c) {
                case '*':
                case '@':
                    break;
                case 'r':
                case 'R':
                case'w':
                case 'W':
                    if (!pieceAllowedAt(i)){
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    static String addPieceAt(String board, TYPE type, int row, int col){
        if (!boardIsValid(board)){
            return "";
        }else{
            int idx = row * rowSize + col;
            return board.substring(0, idx) + type.toString() + board.substring(idx + 1);
        }
    }

    static void printBoard(String board){
        board = board.replace(" ",  "");
        for (int i = 0; i < size; i ++){
            System.out.print(board.charAt(i));
            if (i % rowSize == 0 && i != 0){
                System.out.print("\n");
            }
        }
    }

    public static BoardView constructBoardView(String board)
    {
        board = board.replace(" ",  "");
        if (!boardIsValid(board)){
            return null;
        }
        BoardView bv = new BoardView();
        for (int i = 0; i < size; i ++){
            char c = board.charAt(i);
            int row = getRow(i);
            int col = getCol(i);
            Piece pc;
            switch (c) {
                case '*':
                case '@':
                    bv.getTile(row, col).setPiece(null);
                    break;
                case 'r':
                    pc = new Piece(Piece.PieceEnum.SINGLE, Piece.ColorEnum.RED);
                    bv.getTile(row, col).setPiece(pc);
                    break;
                case 'R':
                    pc = new Piece(Piece.PieceEnum.KING, Piece.ColorEnum.RED);
                    bv.getTile(row, col).setPiece(pc);
                    break;
                case 'w':
                    pc = new Piece(Piece.PieceEnum.SINGLE, Piece.ColorEnum.WHITE);
                    bv.getTile(row, col).setPiece(pc);
                    break;
                case 'W':
                    pc = new Piece(Piece.PieceEnum.KING, Piece.ColorEnum.WHITE);
                    bv.getTile(row, col).setPiece(pc);
                    break;
                default:
                    System.out.println("Error!");
                    System.out.println(c);
                    return null;
            }
        }
        return bv;
    }
}
