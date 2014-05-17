package pl.jakpok.kalkulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;


public class Kalkulator extends JFrame {

	private final Font font = new Font("Helvetica", Font.BOLD, 20);
    private JTextField wyswietlacz;  
    private Liczenie liczenie = new Liczenie(); //kalkulator
    
    //zmienne
    private boolean start = true;
    private String operacja  = "?";
    
    public Kalkulator() {
    	
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception e) {   
        }
        
        //tworzenie wyswietlacza
        wyswietlacz = new JTextField("0", 12);
        wyswietlacz.setHorizontalAlignment(JTextField.RIGHT);
        wyswietlacz.setFont(font);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        wyswietlacz.setBorder(border);
        
        //tworzenie buttonow z cyframi
        ActionListener NumberListener = new NumberListener();

        String cyfry = "789456123 0.";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 3, 4, 4));
        for (int i = 0; i < cyfry.length(); i++) {
            String wycinarka = cyfry.substring(i, i+1);
            JButton b = new JButton(wycinarka);
            if (wycinarka.equals(" ")) {
                //pusty button
                b.setEnabled(false);
            }
            else {
                b.addActionListener(NumberListener);
                b.setFont(font);
            }
            buttonPanel.add(b);
        }
        
        //tworzenie buttonow z operacjami
        ActionListener OperationListner = new OperationListner();
        
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(5, 2, 4, 4));
        
        String[] operacje = {"+", "-", "*", "/", "^", "\u221A","±", "="};//pierwiastek jako escaped string, zeby nie zapisywac klasy w utf-8
        for (int i = 0; i < operacje.length; i++) {
            JButton b = new JButton(operacje[i]);
            b.addActionListener(OperationListner);
            b.setFont(font);
            opPanel.add(b);
        }
        opPanel.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));
        
        //tworzenie buttonu czyszczenia
        
        JPanel clearPanel = new JPanel();
        clearPanel.setLayout(new FlowLayout(0,0,0));

        JButton clearButton = new JButton("Wyczysc");
        clearButton.setFont(font);
        clearButton.addActionListener(new ClearListener());
        clearPanel.add(clearButton);
        
        
        //skladanie wszystkiego w 1 layout
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(5, 5));
        content.add(wyswietlacz, BorderLayout.NORTH );
        content.add(buttonPanel, BorderLayout.CENTER);
        content.add(opPanel, BorderLayout.EAST  );
        content.add(clearPanel, BorderLayout.SOUTH );
        
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setContentPane(content);
        
    }
    
    //listener operacji
    class OperationListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (start) { 
            	//blad
                czysc();
                wyswietlacz.setText("Blad! Nie wpisano liczby");
            } 
            else {            	
            	String akcja = e.getActionCommand();
                String text = wyswietlacz.getText();
                try {
                	//operacje wykonywane na 1 liczbie, np. zmiana znaku lub wyciagniecie pierwiastka
                	if(akcja.equals("±")){
                		wyswietlacz.setText(liczenie.przeciwna(text));
                		return;
                	}
                	if(akcja.equals("\u221A")){
                		wyswietlacz.setText("" + liczenie.pierwiastek(text));
                		return;
                	}
                	//operacje wykonywane na dwoch liczbach, np. mnozenie albo dzielenie
                	if (operacja.equals("=")) {
                        liczenie.ustaw(text);
                    }
                    else if (operacja.equals("+")) {
                        liczenie.dodaj(text);
                    }
                    else if (operacja.equals("-")) {
                        liczenie.odejmij(text);
                    }
                    else if (operacja.equals("*")) {
                        liczenie.pomnoz(text);
                    }
                    else if (operacja.equals("/")) {
                        liczenie.podziel(text);
                    }
                    else if (operacja.equals("^")) {
                        liczenie.potega(text);
                    }  
                    else if (operacja.equals("?")) {
                    	liczenie.ustaw(text);
                    }
                	
                    wyswietlacz.setText("" + liczenie.getWynik());
                    
                } catch (NumberFormatException ex) {
                    czysc();
                    wyswietlacz.setText("Blad! Argument nie jest liczba!");
                }
                
                operacja = akcja;
                start = true; 
            }
        }
    }
    
    
   // listener cyfr
    class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cyfra = e.getActionCommand(); 
            if (start) {
                wyswietlacz.setText(cyfra);
                start = false;
            } 
            else {          
                wyswietlacz.setText(wyswietlacz.getText() + cyfra);
            }
        }
    }
    
    
    //listner czyszczenia
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            czysc();
        }
    }
    
    
    private void czysc() {
        start = true;
        wyswietlacz.setText("0");
        operacja  = "?";
        liczenie.ustaw("0");
    }
    
}