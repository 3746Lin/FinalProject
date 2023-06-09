package view;


import controller.GameController;
import model.Chessboard;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> RedDens = new HashSet<>();
    private final Set<ChessboardPoint> BlueDens = new HashSet<>();
    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    switch (grid[i][j].getPiece().getName()){
                        case "Rat":
                            gridComponents[i][j].add(
                                    new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Elephant":
                            gridComponents[i][j].add(
                                    new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Leopard":
                            gridComponents[i][j].add(
                                    new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Lion":
                            gridComponents[i][j].add(
                                    new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Tiger":
                            gridComponents[i][j].add(
                                    new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Wolf":
                            gridComponents[i][j].add(
                                    new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Cat":
                            gridComponents[i][j].add(
                                    new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                        case "Dog":
                            gridComponents[i][j].add(
                                    new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));
                            break;
                    }
                }
            }
        }

    }

    public void paintLandform(){
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new RiverCellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if(trapCell.contains(temp)){
                    cell = new TrapCellComponent(Color.RED, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if(RedDens.contains(temp)){
                    cell = new RedDensCellComponent(Color.GREEN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if(BlueDens.contains(temp)){
                    cell = new BlueDensCellComponent(Color.GREEN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new LandCellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }
    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));
        trapCell.add(new ChessboardPoint(7, 3));

        RedDens.add(new ChessboardPoint(0, 3));
        BlueDens.add(new ChessboardPoint(8, 3));

        paintLandform();
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, Component chess) {
        getGridComponentAt(point).add(chess);
    }

    public void addComponent(ChessboardPoint point, int rank, PlayerColor color){
        int row = point.getRow(), col = point.getCol();
        switch (rank){
            case 0:break;
            case 1:gridComponents[row][col].add(
                    new RatChessComponent(color, CHESS_SIZE));break;
            case 2:gridComponents[row][col].add(
                    new CatChessComponent(color, CHESS_SIZE));break;
            case 3:gridComponents[row][col].add(
                    new DogChessComponent(color, CHESS_SIZE));break;
            case 4:gridComponents[row][col].add(
                    new WolfChessComponent(color, CHESS_SIZE));break;
            case 5:gridComponents[row][col].add(
                    new LeopardChessComponent(color, CHESS_SIZE));break;
            case 6:gridComponents[row][col].add(
                    new TigerChessComponent(color, CHESS_SIZE));break;
            case 7:gridComponents[row][col].add(
                    new LionChessComponent(color, CHESS_SIZE));break;
            case 8:gridComponents[row][col].add(
                    new ElephantChessComponent(color, CHESS_SIZE));break;
        }
    }

    public Component removeChessComponentAtGrid(ChessboardPoint point) {
        if(getGridComponentAt(point).getComponents().length == 0)
            return null;
        // Note re-validation is required after remove / removeAll.
        Component chess = (Component) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public void addChessComponent(String str){
        int i = (int)(str.charAt(0) - 'a'),j = (int)(str.charAt(1) - 'a');
        int rank = (int)(str.charAt(2) - 'a');
        PlayerColor color = str.charAt(3) == '6' ? PlayerColor.BLUE : PlayerColor.RED;
        Component piececomponent = null;
        switch (rank){
            case 1 : piececomponent = new RatChessComponent(color,CHESS_SIZE);break;
            case 2 : piececomponent = new CatChessComponent(color,CHESS_SIZE);break;
            case 3 : piececomponent = new DogChessComponent(color,CHESS_SIZE);break;
            case 4 : piececomponent = new WolfChessComponent(color,CHESS_SIZE);break;
            case 5 : piececomponent = new LeopardChessComponent(color,CHESS_SIZE);break;
            case 6 : piececomponent = new TigerChessComponent(color,CHESS_SIZE);break;
            case 7 : piececomponent = new LionChessComponent(color,CHESS_SIZE);break;
            case 8 : piececomponent = new ElephantChessComponent(color,CHESS_SIZE);break;
        }
        this.setChessComponentAtGrid(new ChessboardPoint(i,j), piececomponent);
    }

    public void addTrap(ChessboardPoint point){
        this.trapCell.add(point);
//        paintLandform();
    }
    public void removeTrap(ChessboardPoint point){
        this.trapCell.remove(point);
//        paintLandform();
    }
    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else if (clickedComponent.getComponentCount() != 0){
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (Component) clickedComponent.getComponents()[0]);
            }
        }
    }

    public GameController getGameController() {
        return gameController;
    }
    public CellComponent getGridComponents(int i,int j){
        return gridComponents[i][j];
    }
}
