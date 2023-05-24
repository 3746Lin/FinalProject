package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;
import model.Chessboard;
import model.ChessboardPoint;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    private JLabel statusLabel = new JLabel("CurrentPlayer:BLUE");
    private JLabel TurnLabel = new JLabel("Turn:1");
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
        addCheckRuleButton();
        addSaveButton();
        addLoadButton();
        addInitialButton();
        addTurnLabel();
        addCurrentPlayerLabel();
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
        statusLabel.setLocation(HEIGTH+30, HEIGTH/10+60);
        statusLabel.setSize(250, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addCheckRuleButton() {
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
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 200);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private class RestartButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(() -> {
                    chessboardComponent.getGameController().clearChessboard();
                    chessboardComponent.registerController(new GameController(chessboardComponent, new Chessboard(),chessboardComponent.getGameController().getChessGameFrame()));
                    chessboardComponent.repaint();
                });
        }
    }
    private void addBackground(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon image;
        image = new ImageIcon("CS109-2023-Sping-ChessDemo/resource/background.jpg");
        JLabel background = new JLabel(image);
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
    }

    public void addCurrentPlayerLabel() {
        if (chessboardComponent.getGameController() != null) {
            JLabel ResetStatusLabel = new JLabel("CurrentPlayer:"+chessboardComponent.getGameController().getCurrentPlayer().toString());
            remove(statusLabel);
            statusLabel = ResetStatusLabel;
        }
        statusLabel.setLocation(160, 55);
        statusLabel.setSize(180, 30);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(statusLabel);
    }
    public void addTurnLabel() {
        if (chessboardComponent.getGameController() != null) {
            JLabel ResetTurnLabel = new JLabel("Turn:" + chessboardComponent.getGameController().getTurn());
            remove(TurnLabel);
            TurnLabel = ResetTurnLabel;
        }
        TurnLabel.setLocation(320, 55);
        TurnLabel.setSize(300, 30);
        TurnLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(TurnLabel);
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 280);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener((e) -> {
            String out = this.chessboardComponent.getGameController().save();
            try {
                FileWriter fileWriter = new FileWriter("save.txt");
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(out);
                writer.close();
                fileWriter.close();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 360);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            this.chessboardComponent.getGameController().load("save.txt");
        });
    }

}
