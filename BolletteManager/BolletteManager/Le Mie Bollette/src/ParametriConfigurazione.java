
import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ParametriConfigurazione {//00
    public final String utenze;
    public final String IPClient;
    public final int portaServerLog;
    public final String IPServerLog;
    public final int portaDataBase;
    public final String IPDataBase;
    public final String user;
    public final String password;
    
    public ParametriConfigurazione(){//01
        utenze = "Luce,Acqua,Gas,Tari,Internet";
        IPClient = "";
        portaServerLog = 8080;
        IPServerLog = ""; 
        portaDataBase = 3360;
        IPDataBase = "localhost";
        user = "root";
        password = "";
    }

    public ParametriConfigurazione(String nomeFile ){//02
        String xml = carica(nomeFile);
        ParametriConfigurazione p = (ParametriConfigurazione)(creaXStream().fromXML(xml));
        utenze = p.utenze;
        IPClient = p.IPClient;
        portaServerLog = p.portaServerLog;
        IPServerLog = p.IPServerLog;
        portaDataBase = p.portaDataBase;
        IPDataBase = p.IPDataBase;
        user = p.user;
        password = p.password;
    }
    
    private static String carica(String nomeFile){//03
        try{
            return new String(Files.readAllBytes(Paths.get(nomeFile)));
        }catch(IOException e){System.err.println(e.getMessage());}
        return null;
    }
    
    private final XStream creaXStream() {//04
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriConfigurazione.class, "IPClient");
        xs.useAttributeFor(ParametriConfigurazione.class, "IPServerLog");
        xs.useAttributeFor(ParametriConfigurazione.class, "portaServerLog");
        xs.useAttributeFor(ParametriConfigurazione.class, "portaDataBase");
        xs.useAttributeFor(ParametriConfigurazione.class, "IPDataBase");
        xs.useAttributeFor(ParametriConfigurazione.class, "user");
        xs.useAttributeFor(ParametriConfigurazione.class, "password");
        return xs;
    }
    
    public String toString() {//05
        return creaXStream().toXML(this);
    }
}
    
/*
00) Classe che contiene i parametri di configurazione letti da file XML, i membri 
    sono pubblici in modo da renderli accessibili a tutti, ma sono final per non farli modificare da nessuno
01) Costruttore di default da usare nel caso in cui la validazione del file XML non vada a buon fine
02) Costruttore che riceve una String XML e la usa per inizializzare i membri della classe, in modo da renderli utilizzabili per tutti
03) Metodo usato per leggere da file una stringa
04) Creo l'oggetto XStream  e imposto i parametri come attributi 
05) Restituisce l'oggetto come String XML
*/