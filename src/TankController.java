import MyEnum.Direct;

import java.util.List;
import java.util.Vector;

public class TankController {
    public static boolean canMove(Tank tank, List<Tank> tankList) {
        for (int tankIndex = 0; tankIndex < tankList.size(); tankIndex++) {
            Tank otherTank = tankList.get(tankIndex);
            if (testTankCross(tank, otherTank))
                return true;
        }
        return false;
    }

    public static boolean testTankCross(Tank tank, Tank otherTank) {
        switch (tank.getDirect()) {
            case UP:
                return (testCross(tank.getX(), tank.getY(), otherTank) ||
                        testCross(tank.getX() + 40, tank.getY(), otherTank));
            case DOWN:
                return (testCross(tank.getX(), tank.getY() + 60, otherTank) ||
                        testCross(tank.getX() + 40, tank.getY() + 60, otherTank));
            case LEFT:
                return (testCross(tank.getX(), tank.getY(), otherTank) ||
                        testCross(tank.getX(), tank.getY() + 40, otherTank));
            case RIGHT:
                return (testCross(tank.getX() + 60, tank.getY(), otherTank) ||
                        testCross(tank.getX() + 60, tank.getY() + 40, otherTank));
        }
        return false;
    }


    public static boolean testCross(int x, int y, Tank targetTank) {
        switch (targetTank.getDirect()) {
            case UP:
            case DOWN:
                if (x > targetTank.getX() && x < targetTank.getX() + 40 && y > targetTank.getY() && y < targetTank.getY() + 60) {
                    return true;
                }
                break;
            case LEFT:
            case RIGHT:
                if (x > targetTank.getX() && x < targetTank.getX() + 60 && y > targetTank.getY() && y < targetTank.getY() + 40) {
                    return true;
                }
                break;
        }
        return false;
    }

}
