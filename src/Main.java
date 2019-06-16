import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        JFrame obj = new JFrame();

        int M,N,K;
        String temp = JOptionPane.showInputDialog(obj, "Podaj M (liczbe stanowisk z dystrybutorami):");
        M = Integer.parseInt(temp);
        temp = JOptionPane.showInputDialog(obj, "Podaj K (liczbe kas):");
        K = Integer.parseInt(temp);
        temp = JOptionPane.showInputDialog(obj, "Podaj N (maksymalna liczba samochodów, które mogą przebywać na stacji):");
        N = Integer.parseInt(temp);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Panel panel = new Panel(screenSize.height,screenSize.width,M,K,N);
        obj.setBounds(0,0,screenSize.width,screenSize.height);
        obj.setBackground(Color.WHITE);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(panel);
    }
}
