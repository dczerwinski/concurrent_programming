public interface Pozycja {
    int X=0;
    int Y=0;
    public default int getX(){
        return this.X;
    }

    public default int getY(){
        return this.Y;
    }
}
