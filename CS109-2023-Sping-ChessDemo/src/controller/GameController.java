package controller;
import javax.swing.JOptionPane;


import listener.GameListener;
import model.*;
import view.*;
import view.Component;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {

    private List<String> Steps;
    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    public int CountRedChess=8;
    public int CountBlueChess=8;
    private Component component1=null;
    private Component component2=null;
    private int turn=1;
    private ArrayList<CellComponent> FirstCellComponentList=new ArrayList<>();
    private ArrayList<CellComponent> SecondCellComponentList=new ArrayList<>();
    private ArrayList<Component> FirstComponentList=new ArrayList<>();
    private ArrayList<Component> SecondComponentList=new ArrayList<>();

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessGameFrame chessGameFrame;


    public GameController(ChessboardComponent view, Chessboard model,ChessGameFrame chessGameFrame) {
        this.view = view;
        this.model = model;
        this.chessGameFrame=chessGameFrame;
        this.currentPlayer = PlayerColor.BLUE;
        this.Steps = new ArrayList<String>();
        this.Steps.add("000000");

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void clearChessboard() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i,j);
                model.getGrid()[i][j].setPiece(null);
                view.removeChessComponentAtGrid(point);
            }
        }
    }

    public String save(){
        String out = "",temp;
        out += currentPlayer == PlayerColor.BLUE?"BBB": "RRR";

        for(int i=0;i<=8;i++){
            for(int j=0;j<=6;j++){
                ChessPiece temppiece = model.getGrid()[i][j].getPiece();
                if(temppiece != null){
                    temp = "";
                    temp += (char)('a' + i);temp += (char)('a' + j);temp += (char)('a' + temppiece.getRank());
                    temp += temppiece.getOwner() == PlayerColor.BLUE?6:4;
                    out += temp;
                }
            }
        }
        out += "\n";

        for(String i:Steps){
            out += i; out += "\n";
        }
        return out;
    }

    public void load(String path){
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            clearChessboard();
            currentPlayer = line.substring(0,3).equals("BBB")?PlayerColor.BLUE:PlayerColor.RED;
            for(int pos=3;pos<line.length();pos+=4){
                model.addChessPiece(line.substring(pos, pos+4));
                view.addChessComponent(line.substring(pos, pos+4));
            }
            view.repaint();

            while ((line = reader.readLine()) != null) {
                this.Steps.add(line);
            }

            reader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regret(){
        int length = this.Steps.size();
        String temp = this.Steps.get(length-1);
        if(temp == "000000")
            return;

        this.Steps.remove(length-1);
        int old_row = temp.charAt(0) - '0', old_col = temp.charAt(1) - '0';
        int new_row = temp.charAt(2) - '0', new_col = temp.charAt(3) - '0';
        ChessboardPoint old_point = new ChessboardPoint(old_row, old_col), new_point = new ChessboardPoint(new_row, new_col);
        int rank = temp.charAt(5) - '0';
        String name = "";
        switch (rank){
            case 0:break;
            case 1:name = "Rat";break;
            case 2:name = "Cat";break;
            case 3:name = "Dog";break;
            case 4:name = "Wolf";break;
            case 5:name = "Leopard";break;
            case 6:name = "Tiger";break;
            case 7:name = "Lion";break;
            case 8:name = "Elephant";break;
        }

        model.moveChessPiece(new_point, old_point);
        view.setChessComponentAtGrid(old_point, view.removeChessComponentAtGrid(new_point));
        selectedPoint = null;
        switch(temp.charAt(4) - '0'){
            case 0:break;
            case 1://还原陷阱
                model.addTrap(new_point, currentPlayer);
                view.addTrap(new_point);
                break;
            case 2://还原棋子
                ChessPiece chessPiece = new ChessPiece(currentPlayer, name, rank);
                model.getGrid()[new_row][new_col].setPiece(chessPiece);
                view.addComponent(new_point, rank, currentPlayer);
                break;
        }
        view.repaint();

        currentPlayer = currentPlayer == PlayerColor.BLUE? PlayerColor.RED : PlayerColor.BLUE;
        chessGameFrame.addCurrentPlayerLabel();
        if (currentPlayer.getColor()==Color.RED){
            turn--;
            chessGameFrame.addTurnLabel();
        }
        chessGameFrame.repaint();
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

    private String addMovement(ChessboardPoint point){
        /*以一个六位的数字字符串代表移动，其中前两位为起始点的行列，中两位为终点的行列，后两位为特殊事件标记
        特殊事件包含：进入陷阱，吃子。进入陷阱标记为10，吃子标记为2 + 被吃子的等级。
         */
        String temp = "";
        int row = point.getRow(),col = point.getCol();
        temp += selectedPoint.getRow(); temp += selectedPoint.getCol();
        temp += row; temp += col;
        if(model.inTrap(point,currentPlayer)){
            temp += "10";
        } else if(model.getGrid()[row][col].getPiece() != null){
            temp += "2"; temp += model.getGrid()[row][col].getPiece().getRank();
        }else
            temp += "00";
        System.out.println(temp);
        return temp;
    }
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (win()){
            return;
        }
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            Steps.add(addMovement(point));
            if(model.inTrap(selectedPoint, currentPlayer)){
                model.removeTrap(selectedPoint);
                view.removeTrap(selectedPoint);
            }
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            chessGameFrame.addCurrentPlayerLabel();
            if (currentPlayer.getColor()==Color.BLUE){
                turn++;
                chessGameFrame.addTurnLabel();
            }
            chessGameFrame.repaint();
            view.repaint();
            if (FirstCellComponentList.size()!=0) {
                for (CellComponent cellComponent : FirstCellComponentList) {
                    cellComponent.setSelected(false);
                    cellComponent.repaint();
                }
            }
            if (FirstComponentList.size()!=0) {
                for (Component value : FirstComponentList) {
                    value.setCanBeSelected(false);
                    value.repaint();
                }
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
                        if(!TempPoint.equals(point)){
                            if (model.isValidMove(point,TempPoint)){
                                view.getGridComponents(i,j).setSelected(true);
                                view.getGridComponents(i,j).repaint();
                                FirstCellComponentList.add(view.getGridComponents(i,j));
                            }if (model.CheckEmptyPoint(TempPoint)!=null&&model.isValidCapture(point,TempPoint)){
                                if (view.getGridComponents(i,j).getComponent(0)!=null&&view.getGridComponents(i,j).getComponent(0)instanceof Component){
                                    ((Component) view.getGridComponents(i,j).getComponent(0)).setCanBeSelected(true);
                                    ((Component) view.getGridComponents(i,j).getComponent(0)).repaint();
                                    FirstComponentList.add(((Component) view.getGridComponents(i,j).getComponent(0)));
                                }
                            }
                        }

                    }
                }
            }
        }else if(model.getChessPieceOwner(point) != model.getChessPieceOwner(selectedPoint)) {
            if (model.isValidCapture(selectedPoint, point)) {
                if(model.inTrap(selectedPoint, currentPlayer)){
                    model.removeTrap(selectedPoint);
                    view.removeTrap(selectedPoint);
                }
                if(model.inTrap(point, currentPlayer == PlayerColor.BLUE? PlayerColor.RED : PlayerColor.BLUE)){
                    model.removeTrap(selectedPoint);
                    view.removeTrap(selectedPoint);
                }
                Steps.add(addMovement(point));
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
                chessGameFrame.addCurrentPlayerLabel();
                if (currentPlayer.getColor()==Color.BLUE){
                    turn++;
                    chessGameFrame.addTurnLabel();
                }
                chessGameFrame.repaint();
                view.repaint();
                if (FirstCellComponentList.size()!=0) {
                    for (CellComponent cellComponent : FirstCellComponentList) {
                        cellComponent.setSelected(false);
                        cellComponent.repaint();
                    }
                }
                if (FirstComponentList.size()!=0) {
                    for (Component value : FirstComponentList) {
                        value.setCanBeSelected(false);
                        value.repaint();
                    }
                }
                win();
            }
        }else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            if (FirstCellComponentList.size()!=0) {
                for (CellComponent cellComponent : FirstCellComponentList) {
                    cellComponent.setSelected(false);
                    cellComponent.repaint();
                }
            }
            if (FirstComponentList.size()!=0) {
                for (Component value : FirstComponentList) {
                    value.setCanBeSelected(false);
                    value.repaint();
                }
            }
        } else if (model.getChessPieceOwner(point).equals(currentPlayer)) {
            component1.setSelected(false);
            component1.repaint();
            if (FirstCellComponentList.size()!=0) {
                for (CellComponent cellComponent : FirstCellComponentList) {
                    cellComponent.setSelected(false);
                    cellComponent.repaint();
                }
            }
            if (FirstComponentList.size()!=0) {
                for (Component value : FirstComponentList) {
                    value.setCanBeSelected(false);
                    value.repaint();
                }
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
                            if (view.getGridComponents(i,j).getComponent(0)!=null&&view.getGridComponents(i,j).getComponent(0)instanceof Component){
                                ((Component) view.getGridComponents(i,j).getComponent(0)).setCanBeSelected(true);
                                ((Component) view.getGridComponents(i,j).getComponent(0)).repaint();
                                SecondComponentList.add(((Component) view.getGridComponents(i,j).getComponent(0)));
                            }
                        }
                    }

                }
            }
            FirstCellComponentList=SecondCellComponentList;
            FirstComponentList=SecondComponentList;
            component1=component2;
        }
        // TODO: Implement capture function
    }
    public Chessboard getChessboard(){
        return model;
    }
    public PlayerColor getCurrentPlayer(){
        return currentPlayer;
    }
    public int getTurn(){
        return turn;
    }
    public ChessGameFrame getChessGameFrame(){
        return chessGameFrame;
    }
}
