import MyEnum.Direct;
import MyEnum.TankType;

public class EnemyTank extends Tank implements Runnable {

    public EnemyTank(int x, int y, int speed) {
        super(x, y, speed);
        super.setTankType(TankType.ENEMY);
    }

    public EnemyTank(int x, int y) {
        super(x, y, TankType.ENEMY);
    }

    @Override
    public void run() {

        while (isAlive()) {
            shot();
            setDirect(Direct.randomDirect());
            for (int i = 0; i < 30; i++) {
                switch (getDirect()) {
                    case UP: {
                        moveUp();
                        break;
                    }
                    case DOWN: {
                        moveDown();
                        break;
                    }
                    case LEFT: {
                        moveLeft();
                        break;
                    }
                    case RIGHT: {
                        moveRight();
                        break;
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        System.out.println("坦克线程退出;");
    }
}
