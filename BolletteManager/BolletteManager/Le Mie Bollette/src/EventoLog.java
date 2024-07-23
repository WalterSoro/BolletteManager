import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.text.*;
import java.util.*;


public class EventoLog implements Serializable {//00
    private String nomeApplicazione = "BolletteManager"; 
    private String nomeEvento;
    private String indirizzoIpClient;
    private String istanteEvento;
    
    public EventoLog(String evento, String indirizzoIpClient){
        this.nomeEvento = evento;
        this.indirizzoIpClient = indirizzoIpClient;
        this.istanteEvento = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());//01
    }
    
    @Override
    public String toString(){//02
        XStream xs = new XStream();
        xs.useAttributeFor(EventoLog.class, "nomeApplicazione");
        xs.useAttributeFor(EventoLog.class, "nomeEvento");
        xs.useAttributeFor(EventoLog.class, "indirizzoIpClient");
        xs.useAttributeFor(EventoLog.class, "istanteEvento");
        return xs.toXML(this) + "\n";
    }
}

/*
00) La classe contiene le informazioni da salvare relative al log da 
    inviare al ServerLog
01) Creo un oggetto Date che di default prende l'istante in cui viene istanziato,
    e tramite il metodo di un oggetto SimpleDateFormat lo converto in String 
    con il formato che desidero
02) Dato che sto lavorando con semplici String metto tutti i membri 
    della classe come attributi
*/