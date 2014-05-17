package pl.jakpok.kalkulator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Liczenie {
    
	//big decimal jest dokladniejszy niz double czy float, ktore dziwnie zachowuja sie z liczbami o duzej liczbie miejsc po przecinku
    private BigDecimal liczba = new BigDecimal(0);  
    
    private MathContext context = new MathContext(12,RoundingMode.HALF_UP);
    
    public String getWynik() {
    	//sprawdzic czy nie za duze
        liczba = liczba.round(context);
        liczba = liczba.stripTrailingZeros();
        return liczba.toPlainString();
    }
    
    public void ustaw(String n) {
        liczba = new BigDecimal(n);
    }
    
    public void dodaj(String n) {
        liczba = liczba.add(new BigDecimal(n));
    }
    
    public void odejmij(String n) {
    	liczba = liczba.subtract(new BigDecimal(n));
    }
    
    public void pomnoz(String n) {
    	liczba = liczba.multiply(new BigDecimal(n));
    }
    
    public void podziel(String n) {
    	if(n.equals("0")){
    		ustaw("0");
    		return;
    	}
    	liczba = liczba.divide(new BigDecimal(n), 12, RoundingMode.HALF_UP);
    }
    
    public void potega(String n) {
    	//dodany context, aby nie rozsadzilo komputera by liczeniu wielkiej potegi
    	liczba = liczba.pow((int) stringToInt(n), context);
    }
    
    //metody wykonywane na 1 liczbie
    
    public double pierwiastek(String n) {
    	//liczba = new BigDecimal(Math.sqrt(stringToInt(n)));
    	return Math.sqrt(stringToInt(n));
    }
    
    public String przeciwna(String text) {
    	if(text.startsWith("-")){
    		text = text.replace("-", "");
    	}
    	else{
    		text = "-" + text;
    	}
		return text;
    }
    
    private int stringToInt(String n) {
        return Integer.parseInt(n);
    }
}