import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

public class InterfacciaBolletteManager {
    private ParametriConfigurazione configurazione;
    private String utenzaAttuale = "";
    private String casaAttuale = null;
    private TabellaBollette tabella; 
    private GraficoAndamentoConsumi grafico;
    private final Button mostra, inserisciCoinquilino, creaCasa, conferma, eliminaRiga, eliminaCasa;
    private final ComboBox utenza;
    private final Label titolo, nomeCasa, leMieBollette, coinquilino, graficoAndamentoConsumi, valorMedio,piccoMinimo, piccoMassimo, medio, max, min, dataMax, dataMin;
    private final TextField casa, nomeCoinquilino;
    
    private final int screenWidth = (int) (Screen.getPrimary().getBounds().getWidth()*0.8);
    private final int screenHeight = (int) (Screen.getPrimary().getBounds().getHeight()*0.85);
    
    private final AnchorPane contenitore;
    private final AnchorPane contenitoreGrafico;
    private final GridPane contenitoreValori;
    
    public InterfacciaBolletteManager(ParametriConfigurazione parametriConfigurazione) {
        configurazione = parametriConfigurazione;        
        tabella = new TabellaBollette(configurazione);
        grafico = new GraficoAndamentoConsumi();
        utenza = new ComboBox();
        mostra = new Button("MOSTRA");
        inserisciCoinquilino = new Button("INSERISCI\nCOINQUILINO");
        creaCasa = new Button("CREA CASA");
        conferma = new Button("  CONFERMA  ");
        eliminaRiga = new Button("ELIMINA RIGA");
        eliminaCasa = new Button("ELIMINA CASA");
        titolo  = new Label("BolletteManager");
        nomeCasa = new Label("NomeCasa");
        leMieBollette  = new Label("Le Mie Bollette");
        coinquilino  = new Label("Coinquilino");
        graficoAndamentoConsumi  = new Label("Grafico Andamento Consumi");
        valorMedio  = new Label("Valor Medio");
        piccoMassimo  = new Label("Picco Massimo");
        piccoMinimo  = new Label("Picco Minimo");
        casa = new TextField(); nomeCoinquilino = new TextField(); 
        medio = new Label(); max = new Label();
        min = new Label(); dataMax = new Label(); dataMin = new Label();
        
        ObservableList<String> options = FXCollections.observableArrayList(configurazione.utenze.split(","));
        utenza.setPromptText("Utenza");
        utenza.getItems().addAll(options); 
        
        contenitore = new AnchorPane();//00
        contenitore.getChildren().addAll(tabella, leMieBollette, coinquilino, nomeCoinquilino, inserisciCoinquilino, titolo, nomeCasa, casa,
                                            mostra, creaCasa, eliminaRiga, conferma, graficoAndamentoConsumi, utenza, eliminaCasa);
        contenitoreGrafico = new AnchorPane();
        contenitoreGrafico.getChildren().add(grafico.getGrafico());
        contenitoreValori = new GridPane();
        contenitoreValori.add(valorMedio, 1, 0); contenitoreValori.add(piccoMassimo, 0, 1); contenitoreValori.add(piccoMinimo, 2, 1);
        contenitoreValori.add(medio, 1, 1); contenitoreValori.add(max, 0, 2); contenitoreValori.add(min, 2, 2);
        contenitoreValori.add(dataMax, 0, 3); contenitoreValori.add(dataMin, 2, 3);
        
        setLayout();//01
        setStyle();    
        impostaAzioni();
    }    
    public void aggiornaDatiGrafico(){ //02
        grafico.riempiGrafico(utenzaAttuale, casaAttuale);
        medio.setText(DataBaseBollette.getValorMedio(casaAttuale, utenzaAttuale));
        ArrayList<String> list = DataBaseBollette.getMax(casaAttuale, utenzaAttuale);
        if(list.size() == 2){ dataMax.setText(list.get(0)); max.setText(list.get(1));} else{ dataMax.setText("0"); max.setText("0");}//03
        list = DataBaseBollette.getMin(casaAttuale, utenzaAttuale);
        if(list.size() == 2){ dataMin.setText(list.get(0)); min.setText(list.get(1));} else{ dataMin.setText("0"); min.setText("0");} 
    }    
    public void aggiornaTabella(){//04
        contenitore.getChildren().remove(tabella);
        tabella = new TabellaBollette(DataBaseBollette.getCoinquilini(casaAttuale),configurazione);
        tabella.aggiornaListaBollette(DataBaseBollette.caricaBollette(casaAttuale), casaAttuale);
        setLayout();
        contenitore.getChildren().add(tabella);
    }
    private void impostaAzioni(){//05
        mostra.setOnAction((ActionEvent)-> { 
            casaAttuale = casa.getText();
            aggiornaTabella();
            utenzaAttuale="";
            utenza.setPromptText("Utenza");
            aggiornaDatiGrafico();
            GestoreLog.creaLog("MOSTRA", configurazione);
        });
        creaCasa.setOnAction((ActionEvent)-> {
            casaAttuale = casa.getText();
            DataBaseBollette.creaCasa(casaAttuale);
            aggiornaTabella();
            grafico.svuota();
            GestoreLog.creaLog("CREA CASA", configurazione);
        });
        eliminaRiga.setOnAction((ActionEvent)-> {
            Bolletta b = tabella.getSelectionModel().getSelectedItem();
            DataBaseBollette.eliminaBolletta(b.getId());
            tabella.aggiornaListaBollette(DataBaseBollette.caricaBollette(b.getCasa()), b.getCasa());
            GestoreLog.creaLog("ELIMINA RIGA", configurazione);
            if(b.getUtenza().equals(utenzaAttuale))//06
                aggiornaDatiGrafico();
        });
        conferma.setOnAction((ActionEvent)-> {
            Bolletta b = tabella.getSelectionModel().getSelectedItem();
            if(b.getId()==-1)
                DataBaseBollette.aggiungiBolletta(b);
            else
                DataBaseBollette.modificaBolletta(b);
            tabella.aggiornaListaBollette(DataBaseBollette.caricaBollette(casaAttuale), casaAttuale);
            GestoreLog.creaLog("CONFERMA", configurazione);
            if(b.getUtenza().equals(utenzaAttuale))//06
                aggiornaDatiGrafico();
        }); 
        inserisciCoinquilino.setOnAction((ActionEvent)-> {
            String coinquilini = DataBaseBollette.getCoinquilini(casaAttuale);
            if(coinquilini == null){//07
                casaAttuale = casa.getText();
                coinquilini = nomeCoinquilino.getText();
            }else{
                coinquilini = DataBaseBollette.getCoinquilini(casaAttuale);
                String str[] = coinquilini.split(",");
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(str));
                if(list.size() < 4){//07
                    list.add(nomeCoinquilino.getText());
                    coinquilini = list.toString().replace("[","").replace("]","").replace(" ", "");
                }else System.err.println("Limite Coinquilini raggiunto");
            }
            DataBaseBollette.inserisciCoinquilini(casaAttuale, coinquilini);
            aggiornaTabella();
            nomeCoinquilino.setText("");
            GestoreLog.creaLog("INERISCI COINQUILINO", configurazione);
        });
        eliminaCasa.setOnAction((ActionEvent)-> {
            DataBaseBollette.eliminaCasa(casa.getText());
            aggiornaTabella();
            casa.clear();
            grafico.svuota();
            GestoreLog.creaLog("ELIMINA CASA", configurazione);
        });
        utenza.setOnAction((ActionEvent)-> {
            utenzaAttuale = utenza.getValue().toString();
            aggiornaDatiGrafico();
            GestoreLog.creaLog(("MOSTRA GRAFICO " + utenzaAttuale).toUpperCase(), configurazione);
        });      
    }
    private void setLayout(){//08
        tabella.setLayoutX(0.02*screenWidth); tabella.setLayoutY(0.12*screenHeight);
        tabella.setFixedCellSize(0.032*screenWidth); tabella.setMinWidth(0.50*screenWidth); 
        tabella.setMaxWidth(0.52*screenWidth);  tabella.setMaxHeight(0.40*screenHeight);
        
        contenitoreGrafico.setLayoutY(0.33*screenWidth);
        contenitoreValori.setLayoutX(0.59*screenWidth); contenitoreValori.setLayoutY(0.65*screenHeight);
        contenitoreValori.setHgap(0.08*screenWidth);  contenitoreValori.setVgap(0.02*screenHeight);
        
        titolo.setLayoutX(0.5*screenWidth);
        nomeCasa.setLayoutX(0.45*screenWidth); nomeCasa.setLayoutY(0.055*screenHeight);
        
        leMieBollette.setLayoutX(0.02*screenWidth); leMieBollette.setLayoutY(0.1*screenHeight);
        graficoAndamentoConsumi.setLayoutX(0.032*screenWidth); graficoAndamentoConsumi.setLayoutY(0.53*screenHeight);
        
        nomeCoinquilino.setLayoutX(0.66*screenWidth); nomeCoinquilino.setLayoutY(0.125*screenHeight);
        
        coinquilino.setLayoutX(0.55*screenWidth); coinquilino.setLayoutY(0.125*screenHeight);
        casa.setLayoutX(0.525*screenWidth); casa.setLayoutY(0.055*screenHeight);
        
        inserisciCoinquilino.setLayoutX(0.55*screenWidth); inserisciCoinquilino.setLayoutY(0.17*screenHeight);
        creaCasa.setLayoutX(0.73*screenWidth); creaCasa.setLayoutY(0.05*screenHeight);
        eliminaCasa.setLayoutX(0.90*screenWidth); eliminaCasa.setLayoutY(0.95*screenHeight);
        conferma.setLayoutX(0.55*screenWidth); conferma.setLayoutY(0.48*screenHeight);
        eliminaRiga.setLayoutX(0.67*screenWidth); eliminaRiga.setLayoutY(0.48*screenHeight);
        mostra.setLayoutX(0.65*screenWidth); mostra.setLayoutY(0.05*screenHeight);
        
        utenza.setLayoutX(0.19*screenWidth); utenza.setLayoutY(0.53*screenHeight);
        
    }
    private void setStyle(){//09
        titolo.setStyle("-fx-font-size:30; -fx-text-fill: #3399FF; -fx-font-weight:bold; -fx-font-family: cursive");
        nomeCasa.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        leMieBollette.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        coinquilino.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        graficoAndamentoConsumi.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        valorMedio.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        piccoMassimo.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        piccoMinimo.setStyle("-fx-font-size:15; -fx-text-fill: #3399FF; -fx-font-weight:bold;");
        medio.setStyle("-fx-font-size:15; -fx-font-weight:bold; -fx-text-fill: #3399FF ");
        min.setStyle("-fx-font-size:15; -fx-font-weight:bold; -fx-text-fill: #3399FF ");
        max.setStyle("-fx-font-size:15; -fx-font-weight:bold; -fx-text-fill: #3399FF ");
        dataMin.setStyle("-fx-font-size:15; -fx-font-weight:bold; -fx-text-fill: #3399FF ");
        dataMax.setStyle("-fx-font-size:15; -fx-font-weight:bold; -fx-text-fill: #3399FF ");
        inserisciCoinquilino.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        creaCasa.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        eliminaRiga.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        //eliminaCasa.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        conferma.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        mostra.setStyle("-fx-font-size: 15px; -fx-background-color: #99CCFF; -fx-font-weight:bold;");
        utenza.setStyle("-fx-background-color: #99CCFF;");
        inserisciCoinquilino.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(medio, HPos.CENTER);
        GridPane.setHalignment(max, HPos.CENTER); GridPane.setHalignment(dataMax, HPos.CENTER);
        GridPane.setHalignment(min, HPos.CENTER); GridPane.setHalignment(dataMin, HPos.CENTER);
    }
    public void setCasa(String c){//10
        casaAttuale = c;
        if(c != null){
            casa.setText(c);
        }else
            casa.setText("");
    }
    public void setUtenza(String u){utenzaAttuale = u; utenza.setValue(u);}
    public String getUtenzaAttuale(){return utenzaAttuale;} 
    public AnchorPane getContenitore() { return contenitore; }
    public AnchorPane getContenitoreGrafico(){return contenitoreGrafico;}
    public GridPane getContenitoreValori(){return contenitoreValori;}
    public TabellaBollette getTabella(){return tabella;}
    public String getCasa(){return casaAttuale!=null? casaAttuale : "";}
}

/*
00) Creo 3 diversi contenitori per i vari oggetti della mia interfaccia. 
    Il GridPane lo uso solo per i valori del grafico in modo da poterli 
    inserire in modo ordinato
01) Richiamo i metodio per inizalizzarmi il layout, lo stile e le azioni degli elementi dell'interfaccia
02) Metodo che aggiorna il grafico e gli elementi dell'interfaccia associati, come ValorMedio, Max e Min
03) Se la lista contiene almeno 2 elementi, vuol dire che esiste la bolletta che mi serve
04) Aggiorna la tabella, in particolare elimina la tabella dal contenitore, 
    crea un nuovo oggetto tabella con i coinquilini, aggiorna la lista 
    della tabella e poi la inserisce nel contenitore. Non faccio semplicemente 
    solo aggiornaListaTabella perché altrimenti non mi creerebbe le colonne coinquilino
05) Metodo che imposta azioni per i pulsanti, e per ogni azione richiama GestoreeLog.
06) Se l'utenza della bolletta eliminata o aggiunta è la stessa mostrata nel grafico, 
    lo aggiorno in modo tale che il cambiamento avvenga in tempo reale
07) Se non ho coinquilini inserisco direttamente il nuovo coinquilino nel database, 
    altrimenti prelevo dal database i coinquilini, li metto dentro una lista e, se ho meno di 4 coinquilini, 
    aggiungo il nuovo coinquilino e aggiorno il database
08) Metodo che imposta la posizione degli elementi dell'interfaccia in base alle dimensioni dello schermo
09) Imposta uno stile agli elementi dell'interfaccia
10) Metodo set per la casa, se l'ingresso è null, allora setta la casa come stringa vuota
*/