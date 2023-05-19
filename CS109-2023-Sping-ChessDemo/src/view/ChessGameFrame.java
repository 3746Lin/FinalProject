package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("WHZ and LJZ's project"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addBackground();
        addChessboard();
        addLabel();
        addHelloButton();
        addInitialButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Options");
        statusLabel.setLocation(HEIGTH+40, HEIGTH/10+60);
        statusLabel.setSize(250, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Check Rule Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Basic Rule:Elephant>Lion>Tiger>Wolf>Dog>Cat>Rat(>Elephant)   "));
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 120);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addInitialButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(new RestartButtonClickListener());
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 180);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public class RestartButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                    mainFrame.setVisible(true);
                });
        }
    }
    public void addBackground(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon image;
        image = new ImageIcon("D:/ljz/Java/FinalProject/CS109-2023-Sping-ChessDemo/resource/background1.jpg");
        JLabel background = new JLabel(image);
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
        background.setBounds(0,0,this.getWidth(),this.getHeight());
    }



//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
