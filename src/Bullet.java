import MyEnum.Direct;
import MyEnum.TankType;

public class Bullet implements Runnable {

    private boolean isAlive = true;
    private int x;
    private int y;
    private Direct direct = Direct.UP;
    private int speed = 1;
    private TankType sourceType = TankType.MYHONOR;

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSourceType(TankType sourceType) {
        this.sourceType = sourceType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direct getDirect() {
        return direct;
    }

    public int getSpeed() {
        return speed;
    }

    public TankType getSourceType() {
        return sourceType;
    }

    @Override
    public void run() {
        while (true) {

            switch (direct) {
                case UP: {
                    y -= speed;
                    break;
                }
                case DOWN: {
                    y += speed;
                    break;
                }
                case LEFT: {
                    x -= speed;
                    break;
                }
                case RIGHT: {
                    x += speed;
                    break;
                }
            }

            if (x < 0 || x > 1000 || y < 0 || y > 750 && isAlive) {
                isAlive = false;
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("子弹线程退出了");
    }

    public Bullet(int x, int y, Direct direct, TankType sourceType) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.sourceType = sourceType;
    }

}
