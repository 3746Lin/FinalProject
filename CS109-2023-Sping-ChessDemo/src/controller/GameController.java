package controller;
import javax.swing.JOptionPane;


import listener.GameListener;
import model.*;
import view.*;
import view.Component;

import java.awt.*;
import java.util.ArrayList;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private int Initialize=0;
    public int CountRedChess=8;
    public int CountBlueChess=8;
    private Component component1=null;
    private Component component2=null;
    private int m=4;
    private int l=0;
    private ArrayList<CellComponent> FirstCellComponentList=new ArrayList<>();
    private ArrayList<CellComponent> SecondCellComponentList=new ArrayList<>();

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean win() {
        if (CountRedChess==0){
            JOptionPane.showMessageDialog(null, "Blue win!");
            return true;
        }
        if (CountBlueChess==0){
            JOptionPane.showMessageDialog(null, "Red win!");
            return true;
        }
        // TODO: Check the board if there is a winner
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (win()){
            return;
        }
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            for (int i=0;i<FirstCellComponentList.size();i++){
                FirstCellComponentList.get(i).setSelected(false);
                FirstCellComponentList.get(i).repaint();
            }
            if(currentPlayer == PlayerColor.BLUE){
                if(point.equals(new ChessboardPoint(8, 3))){
                    CountBlueChess = 0;
                    win();
                }
            }
            if(currentPlayer == PlayerColor.RED){
                if(point.equals(new ChessboardPoint(0, 3))){
                    CountRedChess = 0;
                    win();
                }
            }
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, Component component) {
        if (win()){
            return;
        }
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                component1=component;
                selectedPoint = point;
                component1.setSelected(true);
                component1.repaint();
                for (int i=0;i<9;i++){
                    for (int j=0;j<7;j++){
                        ChessboardPoint TempPoint=new ChessboardPoint(i,j);
                        if(TempPoint!=point){
                            if (model.isValidMove(point,TempPoint)){
                                view.getGridComponents(i,j).setSelected(true);
                                view.getGridComponents(i,j).repaint();
                                FirstCellComponentList.add(view.getGridComponents(i,j));
                            }if (model.CheckEmptyPoint(TempPoint)!=null&&model.isValidCapture(point,TempPoint)){
                                view.getGridComponents(i,j).setSelected(true);
                                view.getGridComponents(i,j).repaint();
                                FirstCellComponentList.add(view.getGridComponents(i,j));
                            }
                        }

                    }
                }
            }
        }else if(model.getChessPieceOwner(point) != model.getChessPieceOwner(selectedPoint)) {
            if (model.isValidCapture(selectedPoint, point)) {
                if (model.getChessPieceOwner(point).getColor()==Color.RED){
                    CountRedChess--;
                }
                if (model.getChessPieceOwner(point).getColor()==Color.BLUE){
                    CountBlueChess--;
                }
                view.removeChessComponentAtGrid(point);
                model.captureChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                for (int i=0;i<FirstCellComponentList.size();i++){
                    FirstCellComponentList.get(i).setSelected(false);
                    FirstCellComponentList.get(i).repaint();
                }
                win();
            }
        }else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            for (int i=0;i<FirstCellComponentList.size();i++){
                FirstCellComponentList.get(i).setSelected(false);
                FirstCellComponentList.get(i).repaint();
            }
        } else if (model.getChessPieceOwner(point).equals(currentPlayer)) {
            component1.setSelected(false);
            component1.repaint();
            for (int i=0;i<FirstCellComponentList.size();i++){
                FirstCellComponentList.get(i).setSelected(false);
                FirstCellComponentList.get(i).repaint();
            }
            component2=component;
            selectedPoint=point;
            component2.setSelected(true);
            component2.repaint();
            for (int i=0;i<9;i++){
                for (int j=0;j<7;j++){
                    ChessboardPoint TempPoint=new ChessboardPoint(i,j);
                    if(TempPoint!=point){
                        if (model.isValidMove(point,TempPoint)){
                            view.getGridComponents(i,j).setSelected(true);
                            view.getGridComponents(i,j).repaint();
                            SecondCellComponentList.add(view.getGridComponents(i,j));
                        }if (model.CheckEmptyPoint(TempPoint)!=null&&model.isValidCapture(point,TempPoint)){
                            view.getGridComponents(i,j).setSelected(true);
                            view.getGridComponents(i,j).repaint();
                            SecondCellComponentList.add(view.getGridComponents(i,j));
                        }
                    }

                }
            }
            FirstCellComponentList=SecondCellComponentList;
            component1=component2;
        }
        // TODO: Implement capture function
    }
    public int getInitialize(){
        return Initialize;
    }
    public void setInitialize(int i){
        this.Initialize=i;
    }
    public Chessboard getChessboard(){
        return model;
    }

}
