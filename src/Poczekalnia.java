public class Poczekalnia implements Pozycja{
    private int X;
    private int Y;
    private String stan;
    public Poczekalnia(int X,int Y){
        this.X =X;
        this.Y =Y;
        this.stan = "wolne";
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getStan() {
        return stan;
    }

    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }
}
