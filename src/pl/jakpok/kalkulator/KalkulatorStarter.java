package pl.jakpok.kalkulator;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class KalkulatorStarter {

    public static void main(String[] arguments) {
        EventQueue.invokeLater(new Runnable(){
           public void run(){
              Kalkulator k = new Kalkulator();
              k.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              k.pack();
              k.setTitle("Kalkulator by Jakpok");
              k.setResizable(false);
              k.setLocationRelativeTo(null);
              k.setVisible(true);
           }
        });
    }

}
