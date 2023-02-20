import MyEnum.Direct;
import MyEnum.TankType;

import java.util.Vector;

public class Tank {

    private int x;
    private int y;
    private Direct direct = Direct.UP;
    private int speed = 1;
    private TankType tankType = TankType.MYHONOR;
    private boolean isAlive = true;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Vector<Bullet> getBulletVector() {
        synchronized (this) {
            return bulletVector;
        }
    }

    public void setBulletVector(Vector<Bullet> bulletVector) {
        synchronized (this) {
            this.bulletVector = bulletVector;
        }
    }

    private Vector<Bullet> bulletVector = new Vector<>();

    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void moveUp() {
        if (y - speed > 0)
            y -= speed;
    }

    public void moveDown() {
        if (y + speed + 60 < 750)
            y += speed;
        System.out.println(y);
    }

    public void moveLeft() {
        if (x - speed > 0)
            x -= speed;
    }

    public void moveRight() {
        if (x + speed + 60 < 1000)
            x += speed;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public Direct getDirect() {
        return direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, TankType tankType) {
        this.x = x;
        this.y = y;
        this.tankType = tankType;
    }

    public void shot() {

        Bullet bullet = null;
        switch (direct) {
            case UP:
                bullet = new Bullet(x + 20, y, direct, tankType);
                break;
            case DOWN:
                bullet = new Bullet(x + 20, y + 60, direct, tankType);
                break;

            case LEFT:
                bullet = new Bullet(x, y + 20, direct, tankType);
                break;

            case RIGHT:
                bullet = new Bullet(x + 60, y + 20, direct, tankType);
                break;
        }
        new Thread(bullet).start();
        bulletVector.add(bullet);
    }
}
