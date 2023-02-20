import MyEnum.Direct;
import MyEnum.TankType;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;


public class GamePanel extends JPanel implements KeyListener, Runnable {
    private static GamePanel gamePanel = new GamePanel();
    Honor honor = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;


    private Vector<Bomb> bombs = new Vector<>();

    private Image image1 = null;
    private Image image2 = null;
    private Image image3 = null;


    private GamePanel() {
        honor = new Honor(100, 100);
        honor.setSpeed(10);
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirect(Direct.DOWN);
            enemyTank.shot();
            enemyTanks.add(enemyTank);
            new Thread(enemyTank).start();
        }
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1000, 750);
        if (honor.isAlive()) {
            drawTank(g, honor.getX(), honor.getY(), honor.getDirect(), honor.getTankType());
        }

        for (int bulletIndex = 0; bulletIndex < honor.getBulletVector().size(); bulletIndex++) {
            Bullet bullet = honor.getBulletVector().get(bulletIndex);
            if (bullet != null && bullet.isAlive()) {
                g.fill3DRect(bullet.getX() - 2, bullet.getY() - 2, 4, 4, false);
            } else {
                honor.getBulletVector().remove(bullet);
            }
        }

        for (int tankIndex = 0; tankIndex < enemyTanks.size(); tankIndex++) {
            EnemyTank enemyTank = enemyTanks.get(tankIndex);
            if (!enemyTank.isAlive()) {
//                enemyTanks.remove(enemyTank);
                continue;
            }
            drawTank(g, enemyTank.getX(), enemyTank.getY(), enemyTank.getDirect(), enemyTank.getTankType());

            try {
                for (int i = 0; i < enemyTank.getBulletVector().size(); i++) {
                    Bullet bullet = enemyTank.getBulletVector().get(i);

                    if (bullet == null) break;
                    if (bullet.isAlive())
                        g.fill3DRect(bullet.getX() - 1, bullet.getY() - 1, 2, 2, false);
                    else
                        enemyTank.getBulletVector().remove(bullet);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDec();
            if (bomb.life <= 0)
                bombs.remove(bomb);
        }
    }

    public void drawTank(Graphics g, int x, int y, Direct direct, TankType type) {
        switch (type) {
            case MYHONOR:
                g.setColor(Color.cyan);
                break;
            case ENEMY:
                g.setColor(Color.yellow);
                break;
        }
        switch (direct) {
            case UP:
                g.fill3DRect(x, y, 10, 60, false);//左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//主体正方形
                g.fillOval(x + 10, y + 20, 20, 20);//圆
                g.drawLine(x + 20, y, x + 20, y + 20);//炮管
                break;
            case DOWN:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 60, x + 20, y + 40);
                break;

            case LEFT:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x, y + 20, x + 20, y + 20);
                break;

            case RIGHT:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 60, y + 20, x + 40, y + 20);
                break;
        }
    }

    public void hitTank(Bullet bullet, Tank tank) {
        if (!bullet.isAlive() || !tank.isAlive()) return;
        if (bullet.getSourceType() == tank.getTankType()) return;

        switch (tank.getDirect()) {
            case UP:
            case DOWN:
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX() + 40 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY() + 60) {
                    bullet.setAlive(false);
                    tank.setAlive(false);
                    this.bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
            case LEFT:
            case RIGHT:
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX() + 60 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY() + 40) {
                    bullet.setAlive(false);
                    tank.setAlive(false);
                    this.bombs.add(new Bomb(tank.getX(), tank.getY()));
                }
                break;
        }
    }

    /**
     * 有字符输出时，触发
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            honor.setDirect(Direct.UP);
            honor.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            honor.setDirect(Direct.DOWN);
            honor.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            honor.setDirect(Direct.LEFT);
            honor.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            honor.setDirect(Direct.RIGHT);
            honor.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            honor.shot();
        }
        this.repaint();

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (Bullet bullet : honor.getBulletVector()) {
                if (bullet.isAlive())
                    for (EnemyTank enemyTank : enemyTanks)
                        hitTank(bullet, enemyTank);
            }

            for (EnemyTank enemyTank : enemyTanks) {
                for (int i = 0; i < enemyTank.getBulletVector().size(); i++) {
                    Bullet bullet = enemyTank.getBulletVector().get(i);
                    hitTank(bullet, honor);
                }
            }

            this.repaint();
        }
    }
}
