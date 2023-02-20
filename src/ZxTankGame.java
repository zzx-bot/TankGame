import javax.swing.*;

public class ZxTankGame extends JFrame {
    //    定义绘图区域
    GamePanel gamePanel;

    public static void main(String[] args) {
        ZxTankGame zxTankGame = new ZxTankGame();

    }

    public ZxTankGame() {
        gamePanel = GamePanel.getGamePanel();
        new Thread(gamePanel).start();
        this.add(gamePanel);
        this.addKeyListener(gamePanel);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
