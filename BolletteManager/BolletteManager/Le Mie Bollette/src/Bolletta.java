import java.util.*;
import javafx.beans.property.*;

public class Bolletta {//00
    private SimpleIntegerProperty id;
    private final SimpleStringProperty casa;
    private final SimpleStringProperty utenza;
    private final SimpleStringProperty importo;
    private final SimpleStringProperty scadenza;
    private final SimpleStringProperty dataPagata;
    private SimpleStringProperty coinquilino1 = new SimpleStringProperty();
    private SimpleStringProperty coinquilino2 = new SimpleStringProperty();
    private SimpleStringProperty coinquilino3 = new SimpleStringProperty();
    private SimpleStringProperty coinquilino4 = new SimpleStringProperty();
    
    public Bolletta(int id, String casa, String utenza, String importo, String scadenza, String pagata, String QuoteCoinquilini){
        this.id = new SimpleIntegerProperty(id);
        this.casa = new SimpleStringProperty(casa);
        this.utenza = new SimpleStringProperty(utenza);
        this.importo = new SimpleStringProperty(importo);
        this.scadenza = new SimpleStringProperty(scadenza);   
        this.dataPagata = new SimpleStringProperty(pagata);   

        String[] s = new String[4];
        String[] stringa = QuoteCoinquilini.split(",");
        for(int i = 0; i < stringa.length; i++)
            s[i] = stringa[i];          
                    
        if(s[0] != null) this.coinquilino1 = new SimpleStringProperty("€ "+s[0]);
        if(s[1] != null) this.coinquilino2 = new SimpleStringProperty("€ "+s[1]);
        if(s[2] != null) this.coinquilino3 = new SimpleStringProperty("€ "+s[2]);
        if(s[3] != null) this.coinquilino4 = new SimpleStringProperty("€ "+s[3]);
    }
    
    public void setCasa(String casa){ this.casa.setValue(casa);}
    public void setUtenza(String utenza){ this.utenza.setValue(utenza);}    
    public void setImporto(String importo){ importo.replace(",", "."); this.importo.setValue(importo);}
    public void setScadenza(String scadenza){ this.scadenza.setValue(scadenza);}
    public void setDataPagata(String dataPagata){ this.dataPagata.setValue(dataPagata);}
    public void setCoinquilino1(String Coinquilino1) { this.coinquilino1.setValue(Coinquilino1);}
    public void setCoinquilino2(String Coinquilino2) { this.coinquilino2.setValue(Coinquilino2);}
    public void setCoinquilino3(String Coinquilino3) { this.coinquilino3.setValue(Coinquilino3);}
    public void setCoinquilino4(String Coinquilino4) { this.coinquilino4.setValue(Coinquilino4);}
    
    public int getId(){return this.id.getValue();}
    public String getCasa(){return this.casa.getValue();}
    public String getUtenza(){return this.utenza.getValue();}    
    public String getImporto(){return this.importo.getValue();}
    public String getScadenza(){return this.scadenza.getValue();}
    public String getDataPagata(){return this.dataPagata.getValue();}
    public String getCoinquilino1() {return this.coinquilino1.getValue();}
    public String getCoinquilino2() {return this.coinquilino2.getValue();}
    public String getCoinquilino3() {return this.coinquilino3.getValue();}
    public String getCoinquilino4() {return this.coinquilino4.getValue();}
    public String getQuoteCoinquilini(){
        ArrayList<String> al = new ArrayList();
        if(getCoinquilino1() != null) al.add(this.coinquilino1.getValue().replace("€", "")); 
        if(getCoinquilino2() != null) al.add(this.coinquilino2.getValue().replace("€", "")); 
        if(getCoinquilino3() != null) al.add(this.coinquilino3.getValue().replace("€", "")); 
        if(getCoinquilino4() != null) al.add(this.coinquilino4.getValue().replace("€", "")); 
        return al.toString().replace("[", "").replace("]", "").replace(" ", "");//01)
    }
}

/*
00) Classe Bean per creare un Oggetto bolletta da inserire nella tabella, 
    possiede al massimo 4 coinquilini. Se ne servono di meno quelli in eccesso non vengono inizializzati
01) Sistema l'output provienente dalla conversione a String da ArrayList
*/