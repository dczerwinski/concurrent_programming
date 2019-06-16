import java.awt.*;
import java.util.concurrent.Semaphore;

public class Car extends  Thread{
    private Image image;
    private int X,Y;
    private Semaphore tankowanie,placenie,czekanie,czekanie2;
    private java.util.List<Stanowisko> stanowisko;
    private java.util.List<Kasa> kasa;
    private Poczekalnia poczekalnia[];
    private int image_width;
    private Poczekalnia poczekalnia2[];
    private int p1,p2;



    public Car(int X, int Y, Semaphore tankowanie, Semaphore placenie, java.util.List<Stanowisko> stanowisko,java.util.List<Kasa> kasa,int image_width,Poczekalnia poczekalnia[],Semaphore czekanie,Semaphore czekanie2,Poczekalnia poczekalnia2[]){
        this.image = Toolkit.getDefaultToolkit().getImage("car.png");
        this.X = X;
        this.Y = Y;
        this.tankowanie = tankowanie;
        this.placenie = placenie;
        this.kasa = kasa;
        this.stanowisko = stanowisko;
        this.image_width = image_width;
        this.poczekalnia = poczekalnia;
        this.czekanie = czekanie;
        this.czekanie2 = czekanie2;
        this.poczekalnia2 = poczekalnia2;
        this.p1 = 1;
        this.p2 = 0;
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

    public int getp1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    private double funkcja_liniowa(double xa, double xb, double ya, double yb, double y){

        double x;
        double a;
        a = (yb - ya)/(xb - xa);
        x = (y-ya)/a;
        x = x + xa;

        return x;
    }

    private void move(Object object){
        int _y = this.Y;
        int _x = this.X;
        while(Y != ((Pozycja) object).getY()){
            this.X = (int) funkcja_liniowa(((Pozycja) object).getX()+image_width,_x,((Pozycja) object).getY(),_y,Y);
            this.Y--;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run(){
        //czekanie na wolny dystrybutor
        int temp = -1;
        try {
            czekanie2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i<poczekalnia2.length; i++){
            if(poczekalnia2[i].getStan() == "wolne"){
                temp = i;
                poczekalnia2[i].setStan("zajete");
                break;
            }
        }
        this.X = poczekalnia2[temp].getX();
        this.Y = poczekalnia2[temp].getY();

        //jazda do dystrybutora
        try {
            tankowanie.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        czekanie2.release();
        poczekalnia2[temp].setStan("wolne");


        for(int i=0; i<stanowisko.size();i++){
            if(stanowisko.get(i).getStan() == "wolne"){
                temp = i;
                stanowisko.get(i).zajmij();
                break;
            }
        }

        move(stanowisko.get(temp));
        //tankowanie
        int losowa = (int) (Math.random()*5000 + 100);
        this.p1 = losowa;

        try {
            for(int i=0; i<losowa; i++){
                sleep(1);
                this.p2 = i;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.p2 = 0;


        stanowisko.get(temp).opusc();
        tankowanie.release();


        //czekanie na wolne miesjce przed dystrybutorem
        int temp2=-1;
        try {
            czekanie.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0; i<poczekalnia.length;i++){
            if(poczekalnia[i].getStan() == "wolne"){
                temp2 = i;
                poczekalnia[i].setStan("zajete");
                break;
            }
        }

        //podjechanie na wolne miejsce
        move(poczekalnia[temp2]);

        //czekanie na wolna kase
        try {
            placenie.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<kasa.size();i++){
            if(kasa.get(i).getStan() == "wolne"){
                temp = i;
                kasa.get(i).zajmij();
                break;
            }
        }
        poczekalnia[temp2].setStan("wolne");
        czekanie.release();
        move(kasa.get(temp));


        //placenie
        try {
            sleep((long) (Math.random()*5000+0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kasa.get(temp).opusc();
        placenie.release();
    }


}
