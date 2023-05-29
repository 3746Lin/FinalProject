package model;

import view.*;
import view.Component;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private HashSet<ChessboardPoint> redTrapCell = new HashSet<>();
    private HashSet<ChessboardPoint> blueTrapCell = new HashSet<>();

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }
    public boolean inRiver(ChessboardPoint point){
        return ( ( (point.getCol() >= 1 && point.getCol() <= 2) || (point.getCol() >= 4 && point.getCol() <= 5) )
                &&  (point.getRow() >= 3 && point.getRow() <= 5) );
    }

    public boolean inTrap(ChessboardPoint point,PlayerColor color){
        if(color == PlayerColor.RED)
            return blueTrapCell.contains(point);
        else
            return redTrapCell.contains(point);
    }
    public void addTrap(ChessboardPoint point, PlayerColor color){
        if(color == PlayerColor.RED)
            blueTrapCell.add(point);
        if(color == PlayerColor.BLUE)
            redTrapCell.add(point);
    }
    public void removeTrap(ChessboardPoint point){
        if(blueTrapCell.contains(point))
            blueTrapCell.remove(point);
        if(redTrapCell.contains(point))
            redTrapCell.remove(point);
    }
    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
        redTrapCell.add(new ChessboardPoint(0, 2));
        redTrapCell.add(new ChessboardPoint(0, 4));
        redTrapCell.add(new ChessboardPoint(1, 3));
        blueTrapCell.add(new ChessboardPoint(8, 2));
        blueTrapCell.add(new ChessboardPoint(8, 4));
        blueTrapCell.add(new ChessboardPoint(7, 3));
    }

    private void initPieces() {
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)&&!isValidCapture(src,dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }else{
            moveChessPiece(src,dest);
        }
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public void addChessPiece(int rank, int i,int j,PlayerColor color){
        String name = "";
        switch (rank){
            case 1 : name = "Rat";break;
            case 2 : name = "Cat";break;
            case 3 : name = "Dog";break;
            case 4 : name = "Wolf";break;
            case 5 : name = "Leopard";break;
            case 6 : name = "Tiger";break;
            case 7 : name = "Lion";break;
            case 8 : name = "Elephant";break;
        }
        this.getGrid()[i][j].setPiece(new ChessPiece(color, name, rank));
    }
    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {

        if(Objects.equals(getChessPieceAt(src).getName(), "Lion") || Objects.equals(getChessPieceAt(src).getName(), "Tiger")){
            int col = src.getCol(), row = src.getRow(), col2 = dest.getCol(), row2 = dest.getRow();
            if(col == col2)
                if(col == 1 || col == 2 || col == 4 || col == 5)
                    if((row == 2 && row2 == 6 ) || (row == 6 && row2 == 2)){
                        for(int i = 3;i <= 5;i++){
                            if(getChessPieceAt(new ChessboardPoint(i,col)) != null)
                                return false;
                        }
                        return true;
                    }

            if(row == row2)
                if(row == 3 || row == 4 || row == 5)
                    if((col == 0 && col2 == 3) || (col == 3 && col2 == 0)){
                        for(int i = 1;i <= 2;i++){
                            if(getChessPieceAt(new ChessboardPoint(row,i)) != null)
                                return false;
                        }
                        return true;
                    }else if((col == 6 && col2 == 3) || (col == 3 && col2 == 6)){
                        for(int i = 4;i <= 5;i++){
                            if(getChessPieceAt(new ChessboardPoint(row,i)) != null)
                                return false;
                        }
                        return true;
                    }

        }
        if (Objects.equals(getChessPieceAt(src).getName(), "Rat")){
            if (calculateDistance(src,dest)!=1||getChessPieceAt(dest)!=null) {
                return false;
            }else {
                return true;
            }
        }
        if (getChessPieceAt(src) == null ||calculateDistance(src,dest)!=1||getChessPieceAt(dest)!=null||inRiver(dest)) {
            return false;
        }if (getChessPieceAt(src).getOwner().getColor()==Color.BLUE&&dest.getRow()==8&&dest.getCol()==3){
            return false;
        }if (getChessPieceAt(src).getOwner().getColor()==Color.RED&&dest.getRow()==0&&dest.getCol()==3){
            return false;
        } else {
            return true;
        }
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if(inRiver(dest) ^ inRiver(src))
            return false;
        ChessPiece caping = this.getChessPieceAt(src), caped = this.getChessPieceAt(dest);

        if(Objects.equals(getChessPieceAt(src).getName(), "Lion") || Objects.equals(getChessPieceAt(src).getName(), "Tiger")){
            int SpecialRow= src.getRow();
            int SpecialCol= src.getCol();
            if (SpecialRow==3||SpecialRow==4||SpecialRow==5){
                if ((SpecialRow==dest.getRow()&&calculateDistance(src,dest)==3&&getChessPieceAt(src).canCapture(getChessPieceAt(dest))&&(getChessPieceAt(src).getOwner()!=getChessPieceAt(dest).getOwner())&&isValidMove(src,dest))){
                    return true;
                }
            } else if (SpecialCol==1||SpecialCol==2||SpecialCol==4||SpecialCol==5) {
                if (SpecialRow==2||SpecialRow==6) {
                    if ((SpecialCol == dest.getCol() && calculateDistance(src,dest) == 4 && getChessPieceAt(src).canCapture(getChessPieceAt(dest))&&(getChessPieceAt(src).getOwner()!=getChessPieceAt(dest).getOwner())&&isValidMove(src,dest))) {
                        return true;
                    }
                }
            }
        }
        if (Objects.equals(getChessPieceAt(src).getName(), "Rat")){
            if (inRiver(src)&&inRiver(dest)&&calculateDistance(src,dest)==1){
                return true;
            }if (!inRiver(src)&&!inRiver(dest)&&getChessPieceAt(src).canCapture(getChessPieceAt(dest))&&calculateDistance(src,dest)==1&&(getChessPieceAt(src).getOwner()!=getChessPieceAt(dest).getOwner())){
                return true;
            }
            else {
                return false;
            }
        }

        if(calculateDistance(src,dest) != 1)
            return false;
        if(inRiver(dest) && inRiver(src))
            return true;
        PlayerColor current = this.getChessPieceOwner(src);
        if(current == PlayerColor.RED){
            for(ChessboardPoint temp:redTrapCell){
                if(dest.equals(temp)&&getChessPieceOwner(src)!=getChessPieceOwner(dest)){
                    return true;
                }
            }
        } else {
            for(ChessboardPoint temp:blueTrapCell){
                if(dest.equals(temp)&&getChessPieceOwner(src)!=getChessPieceOwner(dest)){
                    return true;
                }
            }
        }

        return caping.canCapture(caped);
    }
    public ChessPiece CheckEmptyPoint(ChessboardPoint point){
        return getChessPieceAt(point);
    }
}
