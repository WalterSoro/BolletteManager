import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;



public class BolletteManager extends Application{//00
    public InterfacciaBolletteManager interfaccia;
    private final static String FILE_CONFIGURAZIONE = "configurazione.xml";
    private final static String SCHEMA_CONFIGURAZIONE = "configurazione.xsd";
    private final static String FILE_CACHE = "cache.bin";
    
    public void start(Stage stage){            
        final ParametriConfigurazione configurazione;//01)
        if(ValidatoreXML.valida(FILE_CONFIGURAZIONE, SCHEMA_CONFIGURAZIONE, false))
            configurazione = new ParametriConfigurazione(FILE_CONFIGURAZIONE);
        else 
            configurazione = new ParametriConfigurazione();

        interfaccia = new InterfacciaBolletteManager(configurazione);//02
        
        int screenWidth = (int) (Screen.getPrimary().getBounds().getWidth()*0.8);
        int screenHeight = (int) (Screen.getPrimary().getBounds().getHeight()*0.85);
        
        Scene scene = new Scene(new Group(interfaccia.getContenitore(), interfaccia.getContenitoreGrafico(), //03
                                            interfaccia.getContenitoreValori()),screenWidth, screenHeight );
        stage.setTitle("Bollettemanager");
        stage.setScene(scene);
        GestoreLog.creaLog("AVVIO", configurazione);//04
        stage.show();
        GestoreCache.caricaCache(interfaccia, FILE_CACHE);//05
        stage.setOnCloseRequest((WindowEvent we) -> { 
            GestoreLog.creaLog("TERMINA", configurazione);//06
            GestoreCache.salvaCache(interfaccia, FILE_CACHE);
        });
    }
}
/*
00) Classe principale che estende application e manda a video l'interfaccia
01) Creo un riferimento di tipo ParametriConfigurazione, se la validazione 
    dell'XML va a buon fine allora Chiamo il cotruttore con String in ingresso 
    altrimenti chiamo il costruttore di default
02) Creo una interfaccia
03) Crea la scena con dentro tutti in contenitori della classe interfaccia
04) Prima di mostrare l'interfaccia chiamo il GestoreLog con AVVIO 
05) Chiamo il gestoreCache che mi carica dentro l'interfaccia i dati presi da cache.bin
06) Prima di chuidere l'applicazione chiamo il GestoreLog con TERRMINA e il 
    GestoreCache per salvare i dati su cache.bin
*/