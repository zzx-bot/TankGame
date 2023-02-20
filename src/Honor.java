import MyEnum.TankType;

public class Honor extends Tank{


    public Honor(int x, int y,int speed) {
        super(x, y,speed);
    }
    public Honor(int x, int y){
        super(x, y, TankType.MYHONOR);
    }


}
