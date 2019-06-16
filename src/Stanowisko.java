import java.awt.*;

public class Stanowisko implements Pozycja{
    private Image image;
    private int X,Y;
    private String stan;

    public Stanowisko(int X, int Y){
        this.image = Toolkit.getDefaultToolkit().getImage("fuel.png");
        this.X = X;
        this.Y = Y;
        this.stan = "wolne";
    }

    public Image getImage() {
        return image;
    }
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    public String getStan() {
        return stan;
    }
    public void zajmij(){
        stan = "zajete";
    }
    public void opusc(){
        stan = "wolne";
    }
}
