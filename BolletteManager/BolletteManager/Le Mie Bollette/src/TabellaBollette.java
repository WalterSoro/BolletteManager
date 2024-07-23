import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.util.Callback;



public class TabellaBollette extends TableView<Bolletta>{//00 
    private final TableColumn colonnaUtenza;
    private final TableColumn colonnaImporto;
    private final TableColumn colonnaScadenza;
    private final TableColumn colonnaDataPagata;
    private TableColumn colonnaCoinquilino1 = new TableColumn("");//01
    private TableColumn colonnaCoinquilino2 = new TableColumn("");
    private TableColumn colonnaCoinquilino3 = new TableColumn("");
    private TableColumn colonnaCoinquilino4 = new TableColumn("");
    
    private ParametriConfigurazione p;
    private ObservableList<Bolletta>listaBollette;
    public TabellaBollette(ParametriConfigurazione p){//02
        this.p = p;
        colonnaUtenza = new TableColumn("Utenza");
        colonnaImporto = new TableColumn("Importo");
        colonnaScadenza = new TableColumn("Scadenza");
        colonnaDataPagata = new TableColumn("Data Pagamento");
        colonnaUtenza.setCellValueFactory(new PropertyValueFactory("utenza"));colonnaUtenza.setMinWidth(100);
        colonnaImporto.setCellValueFactory(new PropertyValueFactory("importo"));colonnaImporto.setMinWidth(100);
        colonnaScadenza.setCellValueFactory(new PropertyValueFactory("scadenza"));colonnaScadenza.setMinWidth(100);
        colonnaDataPagata.setCellValueFactory(new PropertyValueFactory("dataPagata"));colonnaDataPagata.setMinWidth(100);
        getColumns().addAll(colonnaUtenza, colonnaImporto, colonnaScadenza);
        listaBollette  = FXCollections.observableArrayList();
        this.setItems(listaBollette);
    }
    public TabellaBollette(String nomiCoinquilini, ParametriConfigurazione p){//03
        this(p);
        this.setEditable(true);//04

        if(nomiCoinquilini != null){//05
            String[] s = new String[4];
            String[] stringa = nomiCoinquilini.split(","); 
            for(int i = 0; i < stringa.length; i++)
                s[i] = stringa[i];
            
            if(s[0]!=null) colonnaCoinquilino1.setText(s[0]);//06
            if(s[1]!=null) colonnaCoinquilino2.setText(s[1]);
            if(s[2]!=null) colonnaCoinquilino3.setText(s[2]);
            if(s[3]!=null) colonnaCoinquilino4.setText(s[3]);
        }

        colonnaCoinquilino1.setCellValueFactory(new PropertyValueFactory("coinquilino1"));colonnaCoinquilino1.setMinWidth(100);
        colonnaCoinquilino2.setCellValueFactory(new PropertyValueFactory("coinquilino2"));colonnaCoinquilino2.setMinWidth(100);
        colonnaCoinquilino3.setCellValueFactory(new PropertyValueFactory("coinquilino3"));colonnaCoinquilino3.setMinWidth(100);
        colonnaCoinquilino4.setCellValueFactory(new PropertyValueFactory("coinquilino4"));colonnaCoinquilino4.setMinWidth(100);
        getColumns().clear();
        getColumns().addAll(colonnaUtenza, colonnaImporto, colonnaScadenza, colonnaDataPagata,
                            colonnaCoinquilino1, colonnaCoinquilino2, colonnaCoinquilino3, colonnaCoinquilino4);//07
        
        listaBollette  = FXCollections.observableArrayList();
        this.setItems(listaBollette);
        editTable();
    }
    public void aggiornaListaBollette(ArrayList<Bolletta> bollette, String casa){//08
        Bolletta b = new Bolletta(-1, casa, "<nuova bolletta>", "", "", "", "");
        b.setCoinquilino1(null); //09 
        bollette.add(b);
        listaBollette.clear();
        listaBollette.addAll(bollette);
        editTable();
    }
    private void editTable(){//10
        ObservableList<String> options = FXCollections.observableArrayList(FXCollections.observableArrayList(p.utenze.split(",")));
        colonnaUtenza.setCellFactory(ComboBoxTableCell.forTableColumn(options));//11
        colonnaUtenza.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                Bolletta bolletta = event.getRowValue();
                bolletta.setUtenza(event.getNewValue());
            }
        });       
        colonnaImporto.setCellFactory(TextFieldTableCell.forTableColumn());//12
        colonnaImporto.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                
                Bolletta bolletta = event.getRowValue();
                bolletta.setImporto("€ "+event.getNewValue().replace(",", "."));
            }
        });
        Callback<TableColumn, TableCell<Bolletta, String>> dateCellFactory = //13
            new Callback<TableColumn, TableCell<Bolletta, String>>() {
                public TableCell call(TableColumn p) { return new DatePickerTableCell();}
            };
        
        colonnaScadenza.setCellFactory(dateCellFactory); 
        colonnaScadenza.setCellValueFactory(new PropertyValueFactory<>("scadenza"));
        colonnaScadenza.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bolletta, String>>(){
            public void handle(TableColumn.CellEditEvent<Bolletta, String> event){
                Bolletta bolletta = event.getRowValue();
                bolletta.setScadenza(event.getNewValue());
            }
        });    
    
        colonnaDataPagata.setCellFactory(dateCellFactory); 
        colonnaDataPagata.setCellValueFactory(new PropertyValueFactory<>("dataPagata"));
        colonnaDataPagata.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Bolletta, String>>(){
            public void handle(TableColumn.CellEditEvent<Bolletta, String> event){
                Bolletta bolletta = event.getRowValue();
                System.out.println(event.getRowValue().getQuoteCoinquilini());
                bolletta.setDataPagata(event.getNewValue().replace(",", "."));
            }
        }); 
        colonnaCoinquilino1.setCellFactory(TextFieldTableCell.forTableColumn());//12
        colonnaCoinquilino1.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                Bolletta bolletta = event.getRowValue();
                bolletta.setCoinquilino1("€ "+event.getNewValue().replace(",", "."));
            }
        });        
        colonnaCoinquilino2.setCellFactory(TextFieldTableCell.forTableColumn());//12
        colonnaCoinquilino2.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                Bolletta bolletta = event.getRowValue();
                bolletta.setCoinquilino2("€ "+event.getNewValue().replace(",", "."));
            }
        });        
        colonnaCoinquilino3.setCellFactory(TextFieldTableCell.forTableColumn());//12
        colonnaCoinquilino3.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                Bolletta bolletta = event.getRowValue();
                bolletta.setCoinquilino3("€ "+event.getNewValue().replace(",", "."));
            }
        });        
        colonnaCoinquilino4.setCellFactory(TextFieldTableCell.forTableColumn());//12
        colonnaCoinquilino4.setOnEditCommit(new EventHandler<CellEditEvent<Bolletta, String>>(){
            public void handle(CellEditEvent<Bolletta, String> event) {
                Bolletta bolletta = event.getRowValue();
                bolletta.setCoinquilino4("€ "+event.getNewValue().replace(",", "."));
            }
        });        
    }
}

/*
00) Classe che crea una tabella con 3 colonne fisse e 4 colonne che vengono 
    istanziate in base al numero di coinquilini
01) Istanzio qua le colonne coinquilini perché nel caso in cui avessi meno
    di 4 coinqulini allora rischierei di non istanziare tutte le colonne
02) Costruttore di default che crea solo 3 oggetti di tipo TableColumn, non crea quinid le colonne per  i coinquilini
03) Costruttore che prende in ingresso una Stringa con tutti i coinquilini
04) Rende editabile la tabella
05) Crea una stringa in cui inserisco i nomi dei coinquilini
06) Aggiorno gli header delle colonne con i nomi dei coinquilini, 
    se ho meno di 4 coinquilini le colonne rimarranno con header stringa vuota
07) Aggiorno le colonne della tabella
08) Aggiorno la lista di esami della tabella con la lista che mi viene passata come parametro
09) Serve per non mettere il simbolo dell'euro nella riga editabile per inserire una nuova colonna
10) Rendo editabile la tabella richiamando per ogni colonna il metodo setCellFactory e il metodo 
    setOnEditCommit che specifica le azioni da eseguire quando avviene la modifica
11) Consente la modifica della cella tramite una ComboBox (insieme di valori della lista options)
    quando si fa click sulla cella
12) Consente la modifica della cella tramite un TextField quando si fa doppio click sulla cella
13) Creo un Callback che crea un oggetto DatePickerTableCell e faccio in modo che quando faccio doppio click
    sulla cella la modifica avvenga tramite un DatePicker
*/
