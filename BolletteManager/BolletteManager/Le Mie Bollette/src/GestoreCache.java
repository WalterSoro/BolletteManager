
import java.io.*;


public class GestoreCache {//00
    public static void salvaCache(InterfacciaBolletteManager interfaccia, String nomeFile){
        try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(nomeFile))){//01
            oout.writeObject(new CacheLocale(interfaccia));//02
            
        }catch (IOException e){System.err.println(e.getMessage());}
    }
    public static void caricaCache(InterfacciaBolletteManager interfaccia, String nomeFile){
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(nomeFile))){//03
            CacheLocale c = (CacheLocale) oin.readObject();
            if(c!=null){//04 
                interfaccia.setCasa(c.casa);
                interfaccia.aggiornaTabella();
                if(c.utenzaGrafico != null){
                    interfaccia.setUtenza(c.utenzaGrafico);
                    interfaccia.aggiornaDatiGrafico();
                }
                if(c.indiceModifica != -1){//05
                    String quote = "";
                   
                    if(c.coinquilino1!=null)   quote += c.coinquilino1.substring(2);//06
                    if(c.coinquilino2!=null)   quote += ","+c.coinquilino2.substring(2);
                    if(c.coinquilino3!=null)   quote += ","+c.coinquilino3.substring(2);
                    if(c.coinquilino4!=null)   quote += ","+c.coinquilino4.substring(2);
     
                    Bolletta b = new Bolletta(c.id, c.casa, c.utenza, c.importo, c.scadenza,c.dataPagata, quote);
                    interfaccia.getTabella().getItems().set(c.indiceModifica, b);                
                }
            }else
                interfaccia.setCasa("");
        }catch (IOException | ClassNotFoundException e){System.err.println(e.getMessage());}
    }
}

/*
00) Classe che si occupate di salvare e caricare un oggetto di tipo CacheLocale
01) Creo un ObjectOutputStream per scrivere sul file
02) Scrivo su file un oggetto di tipo CacheLocale che salva i contenuti 
    dell'oggetto InterfacciaBolleteManager che gli sto passando
03) Tramite uno stram in input leggo da file un oggetto di tipo CacheLocale
    e lo assegno ad un riferimento
04) Se la casa salvata nella cache non è null allora aggiorno l'interfaccia e la
    tabella con i dati che ho letto, se poi anche l'utenza del grafico è not null
    allora aggiorno utenza e grafico
05) Se indiceModifica=!1 vuol dire che prima della chiusura stavo effettuando 
    una modifica e quindi aggiorno le quote dei coinquilini in base a quelle 
    che ho salvato nella cache, creando un oggetto Bolletta e inserendolo
    nell'indice in cui si trovava la bolletta in questione
06) Il substring serve per non salvare in cache il simbolo dell' € 
*/