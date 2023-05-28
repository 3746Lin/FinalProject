package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;
import view.PlayMusic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import java.io.File;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    private JLabel statusLabel = new JLabel("当前行棋方:蓝");
    private JLabel TurnLabel = new JLabel("回合数:1");
    private JLabel TimerLabel = new JLabel("回合倒计时:60s");
    private  JLabel ResetStatusLabel;
    private ImageIcon imageIcon1=new ImageIcon("resource/background.jpg");
    private ImageIcon imageIcon2=new ImageIcon("resource/青青草原背景.jpg");
    private ArrayList<JLabel> backgrounds=new ArrayList<JLabel>();
    private int style=1;
    private PlayMusic playMusic=new PlayMusic(0);
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
        addTimeLabel(60);
        addCurrentPlayerLabel();
        addRegretButton();
        addChangeStyleButton();
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
        JLabel statusLabel = new JLabel("菜单");
        statusLabel.setLocation(HEIGTH+70, HEIGTH/10+60);
        statusLabel.setSize(250, 60);
        statusLabel.setFont(new Font("宋体", Font.BOLD, 30));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addCheckRuleButton() {
        JButton button = new JButton("查看规则");
        if (style==1) {
            button.addActionListener((e) -> JOptionPane.showMessageDialog(
                    this, "基本规则:象>狮>虎>豹>狼>狗>猫>鼠(>象)\n" +
                            "狮虎可跳河(如果河里没有老鼠)\n" +
                            "老鼠可以进河，但进河之后只能同级互吃不能吃岸上的象   "));
        }else {
            button.addActionListener((e) -> JOptionPane.showMessageDialog(
                    this, "基本规则:包包大人=黑大帅>喜羊羊=灰太狼>沸羊羊=红太狼>慢羊羊=夜太狼>懒羊羊=灰二太太狼>暖羊羊=巫师狼>美羊羊=蕉太狼>潇洒哥=小灰灰(>包包大人=黑大帅)   "));
        }
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 120);
        button.setSize(250, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);
    }
    private void addInitialButton() {
        JButton button = new JButton("重新开始");
        button.addActionListener(new RestartButtonClickListener());
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 200);
        button.setSize(250, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);
    }
    private class RestartButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(() -> {
                    chessboardComponent.getGameController().clearChessboard();
                    chessboardComponent.getGameController().getBlueTimer().stop();
                    chessboardComponent.getGameController().getRedTimer().stop();
                    chessboardComponent.initiateChessComponent(chessboardComponent.getGameController().getChessboard());
                    chessboardComponent.registerController(new GameController(chessboardComponent,new Chessboard(),chessboardComponent.getGameController().getChessGameFrame()));
                    chessboardComponent.getGameController().resetTurnAndCurrentPlayer();
                    chessboardComponent.getGameController().RepaintAll(style);
                    chessboardComponent.getGameController().getChessGameFrame().repaint();
                    chessboardComponent.repaint();
                });
        }
    }
    private void addBackground(){
        ((JPanel)this.getContentPane()).setOpaque(false);
        ImageIcon image;
        image = imageIcon1;
        JLabel background = new JLabel(image);
        background.setSize(this.getSize());
        background.setVisible(false);
        background.setOpaque(true);
        background.setBounds(0,0,this.getWidth(),this.getHeight());
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
        background.setVisible(true);
    }

    public void addCurrentPlayerLabel() {
        if (chessboardComponent.getGameController() != null) {
            if (chessboardComponent.getGameController().getCurrentPlayer().equals(PlayerColor.BLUE)) {
                ResetStatusLabel = new JLabel("当前行棋方:蓝");
            }else {
                ResetStatusLabel = new JLabel("当前行棋方:红");
            }
            remove(statusLabel);
            statusLabel = ResetStatusLabel;
        }
        statusLabel.setLocation(160, 55);
        statusLabel.setSize(180, 30);
        statusLabel.setFont(new Font("宋体", Font.BOLD, 15));
        add(statusLabel);
    }
    public void addTurnLabel() {
        if (chessboardComponent.getGameController() != null) {
            JLabel ResetTurnLabel = new JLabel("回合数:" + chessboardComponent.getGameController().getTurn());
            remove(TurnLabel);
            TurnLabel = ResetTurnLabel;
        }
        TurnLabel.setLocation(280, 55);
        TurnLabel.setSize(300, 30);
        TurnLabel.setFont(new Font("宋体", Font.BOLD, 15));
        add(TurnLabel);
    }
    public void addTimeLabel(int i) {
        if (chessboardComponent.getGameController() != null) {
            JLabel ResetTimerLabel = new JLabel("回合倒计时:" + i + "s");
            remove(TimerLabel);
            TimerLabel = ResetTimerLabel;
        }
        TimerLabel.setLocation(370, 55);
        TimerLabel.setSize(300, 30);
        TimerLabel.setFont(new Font("宋体", Font.BOLD, 15));
        add(TimerLabel);
    }
    private void addSaveButton() {
        JButton button = new JButton("存档");
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 280);
        button.setSize(250, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);

        button.addActionListener((e) -> {
            String input = JOptionPane.showInputDialog(this, "请输入要保存的文件名");
            String out = this.chessboardComponent.getGameController().save();
            while (input!=null){
                try {
                    if (input.equals("")) {
                        JFrame frame = new JFrame("提示窗口");
                        frame.setSize(400, 300);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        JOptionPane.showMessageDialog(frame, "文件名为空，请重新输入文件名");
                        input = JOptionPane.showInputDialog(this, "请输入要保存的文件名");
                        remove(frame);
                    }else {
                        FileWriter fileWriter = new FileWriter("./txt/" + input + ".txt");
                        BufferedWriter writer = new BufferedWriter(fileWriter);
                        writer.write(out);
                        writer.close();
                        fileWriter.close();
                        break;
                    }
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("读档");
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 360);
        button.setSize(250, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);
        button.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "请输入要载入的文件名");
            while (input!=null) {
                try {
                    File txt = new File("./txt/" + input + ".txt");
                    if (txt.exists()) {
                        this.chessboardComponent.getGameController().load("./txt/" + input + ".txt");
                        break;
                    } else if (input.equals("")) {
                        JFrame frame = new JFrame("提示窗口");
                        frame.setSize(400, 300);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        JOptionPane.showMessageDialog(frame, "文件名为空，请重新输入文件名");
                        input = JOptionPane.showInputDialog(this, "请输入要保存的文件名");
                        remove(frame);
                    } else {
                        throw new FileNotFoundException("文件不存在");
                    }
                } catch (FileNotFoundException exception) {
                    JFrame frame = new JFrame("提示窗口");
                    frame.setSize(400, 300);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JOptionPane.showMessageDialog(frame, "文件不存在，请重新输入文件名");
                    input = JOptionPane.showInputDialog(this, "请输入要保存的文件名");
                    remove(frame);
                }
            }
        });
    }
    private void addChangeStyleButton() {
        JButton button = new JButton("设置风格和背景音乐");
        button.setLocation(HEIGTH-50, HEIGTH / 10 + 520);
        button.setSize(300, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);

        button.addActionListener(e ->  {
            showChangeStyleOptions();
        });

    }
    private void showChangeStyleOptions(){
        JFrame jFrame = new JFrame("设置风格和背景音乐");
        jFrame.setSize(400, 100);
        jFrame.setLocation(600,450);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel jLabel=new JLabel("设置风格和背景音乐");
        JButton normal = new JButton("普通风格");
        JButton happysheep = new JButton("喜羊羊与灰太狼");
        JButton button = new JButton("开关背景音乐");
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        jPanel.add(normal);
        jPanel.add(happysheep);
        jPanel.add(button);
        jFrame.getContentPane().add(jPanel, BorderLayout.CENTER);
        jFrame.setVisible(true);
        normal.addActionListener(e -> {
            style=1;
            chessboardComponent.getGameController().RepaintAll(1);
            chessboardComponent.getGameController().getChessGameFrame().addBackground();
        });
        happysheep.addActionListener(e -> {
            style=2;
            chessboardComponent.getGameController().RepaintAll(2);
            chessboardComponent.getGameController().getChessGameFrame().addBackground();
        });
        button.addActionListener(e -> {
            setBackGroundMusic();
        });
    }
    public int getStyle(){
        return style;
    }
    private void addRegretButton() {
        JButton button = new JButton("悔棋");
        button.setLocation(HEIGTH-25, HEIGTH / 10 + 440);
        button.setSize(250, 60);
        button.setFont(new Font("宋体", Font.BOLD, 24));
        add(button);

        button.addActionListener(e -> {
            this.chessboardComponent.getGameController().regret();
        });
    }
    private void setBackGroundMusic(){
        JFrame jFrame = new JFrame("开关背景音乐");
        jFrame.setSize(400, 100);
        jFrame.setLocation(600,450);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton start = new JButton("开");
        JButton stop = new JButton("关");
        JPanel jPanel = new JPanel();
        jPanel.add(start);
        jPanel.add(stop);
        jFrame.getContentPane().add(jPanel, BorderLayout.CENTER);
        jFrame.setVisible(true);
        start.addActionListener(e -> {
            try {
                addMusic(1);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        stop.addActionListener(e -> {
            try {
                addMusic(0);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void addMusic(int i)throws Exception{
        if (i==1) {
            //if (playMusic.getClip()!=null&&!playMusic.getClip().isRunning()){
                playMusic.PlayExactMusic();
            //}
        }else {
            playMusic.stop();
        }
    }

}
