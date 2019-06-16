import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Panel extends JPanel implements ActionListener{
    private Timer timer;
    private int height,width;
    private int M,K,N;
    private Poczekalnia poczekalnia[];
    private Poczekalnia poczekalnia2[];
    private java.util.List<Car> lista_car = new ArrayList<Car>();
    private int image_width;
    private Semaphore tankowanie;
    private Semaphore placenie;
    private Semaphore czekanie,czekanie2;
    private Image progers;
    private java.util.List<Stanowisko> stanowisko = new ArrayList<Stanowisko>();
    private java.util.List<Kasa> kasa = new ArrayList<Kasa>();


    public Panel(int height,int width,int M,int K, int N){
        timer = new Timer(1,this);
        timer.start();
        this.height = height;
        this.width = width;
        this.M = M;
        this.K = K;
        this.N = N;

        this.progers = Toolkit.getDefaultToolkit().getImage("progress.png");

        tankowanie = new Semaphore(M);
        placenie = new Semaphore(K);
        czekanie = new Semaphore(N);
        czekanie2 = new Semaphore(N);

        poczekalnia = new Poczekalnia[N];
        poczekalnia2 = new Poczekalnia[N];

        image_width = width/(2*(Math.max(M,K)));
        if(image_width>50)image_width=50;


        for(int i=0; i<K; i++){
           kasa.add(new Kasa(i*2*image_width,50));
        }


        for(int i=0; i<M; i++){
            stanowisko.add(new Stanowisko(i*2*image_width,300));
        }

        for(int i=0; i<N; i++){
            poczekalnia[i] = new Poczekalnia(i*image_width,150);
            poczekalnia2[i] = new Poczekalnia(i*image_width,500);
        }


    }


    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimesRoman",1,image_width/2));
        //rysowanie kas
        for(int i=0; i<kasa.size(); i++){
            g2d.drawImage(kasa.get(i).getImage(),kasa.get(i).getX(),kasa.get(i).getY(),image_width,image_width,this);
            g2d.drawString(String.valueOf(i+1),kasa.get(i).getX(),kasa.get(i).getY()+10);
        }
        //rysowanie stanowisk
        for(int i=0; i<stanowisko.size(); i++){
            g2d.drawImage(stanowisko.get(i).getImage(), stanowisko.get(i).getX(), stanowisko.get(i).getY(), image_width, image_width, this);
            g2d.drawString(String.valueOf(i + 1), stanowisko.get(i).getX(), stanowisko.get(i).getY() + 10);

        }
        //rysowanie samochodów
        for(int i=0; i<lista_car.size();i++){
            g2d.drawImage(lista_car.get(i).getImage(),lista_car.get(i).getX(),lista_car.get(i).getY(),image_width,image_width,this);
            g2d.drawString(String.valueOf(lista_car.get(i).getId()),lista_car.get(i).getX()+image_width/10,lista_car.get(i).getY()-20);
            g2d.drawImage(this.progers,lista_car.get(i).getX()-image_width/2,lista_car.get(i).getY(),10,(int)(((double)lista_car.get(i).getP2()/lista_car.get(i).getp1()) * image_width),this);
            if(lista_car.get(i).isAlive() == false){
                lista_car.remove(i);
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if((Math.random()*1000+0) < 5){
            if(lista_car.size()<N){
                lista_car.add(new Car(0,0,tankowanie,placenie,stanowisko,kasa,image_width,poczekalnia,czekanie,czekanie2,poczekalnia2));
                lista_car.get(lista_car.size()-1).start();
            }
        }
        repaint();
    }
}