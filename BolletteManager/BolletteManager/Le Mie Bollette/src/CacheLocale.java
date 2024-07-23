
import java.io.*;


public class CacheLocale implements Serializable {//00
    public int id;
    public String utenzaGrafico;
    public String casa;
    public String utenza;
    public String importo;
    public String scadenza;
    public String dataPagata;
    public String coinquilino1;
    public String coinquilino2;
    public String coinquilino3;
    public String coinquilino4;
    public int indiceModifica = -1;
    
    public CacheLocale(InterfacciaBolletteManager interfaccia){
        casa = interfaccia.getCasa();
        utenzaGrafico = interfaccia.getUtenzaAttuale();
        Bolletta b = interfaccia.getTabella().getSelectionModel().getSelectedItem();
        if(b!=null){
            indiceModifica = interfaccia.getTabella().getSelectionModel().getSelectedIndex(); // prendo l' indice della modifica che si stava effettuando prima della chiusura
            id = b.getId();
            utenza = b.getUtenza();
            importo = b.getImporto();
            scadenza = b.getScadenza();
            dataPagata = b.getDataPagata();
            coinquilino1 = b.getCoinquilino1();
            coinquilino2 = b.getCoinquilino2();
            coinquilino3 = b.getCoinquilino3();
            coinquilino4 = b.getCoinquilino4();
        }
    }
}

/*
00) La classe salva le informazioni relative alla cache. Salva la casa e le 
    informazioni relative all'ultima bolletta modificata e l'utenza selezionata
01) Prendo l'indice del punto della riga della tabella in cui stavo effettuando la 
    modifica prima della chiusura
*/