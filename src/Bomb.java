import java.awt.*;

public class Bomb {
    int x, y;

    public int life = 9;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDec() {
        if (life > 0)
            life--;
    }
}
