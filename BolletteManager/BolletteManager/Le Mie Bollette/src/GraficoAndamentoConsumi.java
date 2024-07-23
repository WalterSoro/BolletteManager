
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.*;
import javafx.stage.Screen;

public class GraficoAndamentoConsumi{//00
    private final List<Double> importi;
    private final List<String> date;

    private final LineChart<String,Number> lineChart;
    private final XYChart.Series series;

    private final int screenWidth = (int) (Screen.getPrimary().getBounds().getWidth()*0.8);
    private final int screenHeight = (int) (Screen.getPrimary().getBounds().getHeight()*0.85);
    public  GraficoAndamentoConsumi(){
        importi = new ArrayList();
        date = new ArrayList();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        lineChart = new LineChart<String,Number>(xAxis,yAxis);//01 
        series = new XYChart.Series();
        lineChart.getData().add(series);//02
        lineChart.setAnimated(false); //03
        
        lineChart.setMaxHeight(0.45*screenHeight);
        lineChart.setPrefWidth(0.53*screenWidth);
        lineChart.setMaxWidth(screenWidth*0.95);

    }
    public void svuota(){   //04
        series.getData().clear(); 
        series.setName("");
    }
    
    public void riempiGrafico(String utenza, String casa){//05
        series.getData().clear();
        series.setName(utenza);
        
        ArrayList<String> date = DataBaseBollette.getScadenzeGrafico(utenza, casa);
        ArrayList<Double> importi = DataBaseBollette.getImportiGrafico(utenza, casa);
        for(int i = 0; i < importi.size(); i++){
            series.getData().add(new XYChart.Data(date.get(i), importi.get(i)));
        }
    }
    public LineChart <String,Number> getGrafico(){return lineChart; }
}

/*
00) Creo un grafico che mostra tutti gli importi delle bollette di una utenza 
    per una casa
01) Creo un LineChart con asse X e Y
02) Aggiungo al grafico una serie di dati vuoti
03) Toglie le animazioni del grafico per migliorare le prestazioni
04) Metodo che svuota i dati del grafico e mette come nome una stringa vuota
05) Metodo che rieme il grafico in base all'utenza e alla casa passati come 
    argomenti, in particolare preleva tramite DataBaseBollette le scadenze 
    e gli importi delle bollette necessarie a costruire il grafico 
    e le inserisce una per volta dentro series
*/
