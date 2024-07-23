import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.*;

public class DataBaseBollette {//00
    private static Connection connessioneDB;
    private static PreparedStatement statementBollette;
    private static ParametriConfigurazione parametri;
    static {//01 
        if(ValidatoreXML.valida("configurazione.xml", "configurazione.xsd", false))
            parametri = new ParametriConfigurazione("configurazione.xml");
        else 
            parametri = new ParametriConfigurazione();
        String connessione = "jdbc:mysql://" + parametri.IPDataBase + ":" + parametri.portaDataBase + "/bollettemanagerdb";
        try{connessioneDB = DriverManager.getConnection(connessione, parametri.user, parametri.password);
            
        }catch(SQLException e){System.err.println(e.getMessage());}
    }
    public static ArrayList<Bolletta> caricaBollette(String casa){//02 
        ArrayList<Bolletta> listaBollette = new ArrayList<>();
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM bollette WHERE casa = ? ORDER BY scadenza");
            statementBollette.setString(1, casa);
            ResultSet rs = statementBollette.executeQuery();
            while(rs.next()){
                listaBollette.add(new Bolletta( rs.getInt("id"),
                                                casa,
                                                rs.getString("utenza"),
                                                "€ "+rs.getString("importo"),
                                                rs.getString("scadenza"),
                                                (rs.getString("pagata")!=null)?rs.getString("pagata") : "",
                                                rs.getString("QuoteCoinquilini")));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return listaBollette;
    }
    public static ArrayList<String> getScadenzeGrafico(String utenza, String casa){//03 
        ArrayList<String> listaScadenze = new ArrayList<>();
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM bollette WHERE casa = ? AND utenza = ?");
            statementBollette.setString(1, casa); statementBollette.setString(2, utenza);
            ResultSet rs = statementBollette.executeQuery();              
            while(rs.next())
                listaScadenze.add(rs.getString("scadenza"));
        }catch(SQLException e){System.out.println(e.getMessage());}
        return listaScadenze;
    }
    public static ArrayList<Double> getImportiGrafico(String utenza, String casa){//04
        ArrayList<Double> listaImporti = new ArrayList<>();
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM bollette WHERE casa = ? AND utenza = ?");
            statementBollette.setString(1, casa); statementBollette.setString(2, utenza);
            ResultSet rs = statementBollette.executeQuery();              
            while(rs.next())
                listaImporti.add(rs.getDouble("importo"));
        }catch(SQLException e){System.out.println(e.getMessage());}
        return listaImporti;
    }
    public static ArrayList<String> getMax(String casa, String utenza){//05 
        ArrayList<String> list = new ArrayList();
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM bollette b1 inner join (SELECT max(Importo) as importo from bollette b2 where b2.casa = ? and b2.utenza = ?) as b on (b1.importo = b.importo) where casa = ? and utenza = ?");
            statementBollette.setString(1, casa); statementBollette.setString(2, utenza);
            statementBollette.setString(3, casa); statementBollette.setString(4, utenza);
            ResultSet rs = statementBollette.executeQuery();    
            if(rs.next()){
                list.add(rs.getString("scadenza"));
                list.add(rs.getString("importo"));
            }
            return list;
        }catch(SQLException e){System.out.println(e.getMessage());}
        return list;
    }
    public static ArrayList<String> getMin(String casa, String utenza){//06 
        ArrayList<String> list = new ArrayList();
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM bollette b1 inner join (SELECT min(Importo) as importo from bollette b2 where b2.casa = ? and b2.utenza = ?) as b on (b1.importo = b.importo) where casa = ? and utenza = ?");
            statementBollette.setString(1, casa); statementBollette.setString(2, utenza);
            statementBollette.setString(3, casa); statementBollette.setString(4, utenza);
            ResultSet rs = statementBollette.executeQuery();    
            if(rs.next()){
                list.add(rs.getString("scadenza"));
                list.add(rs.getString("importo"));
            }
            return list;
        }catch(SQLException e){System.out.println(e.getMessage());}
        return list; 
    }
    public static String getValorMedio(String casa, String utenza){//07 
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT AVG(importo) as media FROM bollette WHERE casa = ? AND utenza = ?");
            statementBollette.setString(1, casa); statementBollette.setString(2, utenza);
            ResultSet rs = statementBollette.executeQuery();
            rs.next();
            if(rs.getString("media") == null) //07.1 
                return "0";
            else
                return new BigDecimal(rs.getDouble("media")).setScale(2, RoundingMode.DOWN).toString();//07.2
                //return rs.getString("media");
        }catch(SQLException e){System.out.println(e.getMessage());}
        return "";         
    }
    public static void aggiungiBolletta(Bolletta bolletta){//08 
        try{
            statementBollette = connessioneDB.prepareStatement("INSERT INTO bollette (casa,utenza,scadenza,importo,pagata,quotecoinquilini) VALUES(?,?,?,?,?,?) "); 
            statementBollette.setString(1, bolletta.getCasa());
            statementBollette.setString(2, bolletta.getUtenza());
            statementBollette.setString(3, bolletta.getScadenza());
            statementBollette.setDouble(4, Double.parseDouble(bolletta.getImporto().replace("€", ""))); 
            statementBollette.setString(5, bolletta.getDataPagata().equals("")? null : bolletta.getDataPagata());; 
            statementBollette.setString(6, bolletta.getQuoteCoinquilini());            
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }
    public static void modificaBolletta(Bolletta bolletta){//09
        try{
            statementBollette = connessioneDB.prepareStatement("UPDATE bollette SET casa=?, utenza=?, Scadenza=?, Importo=?, Pagata=?, QuoteCoinquilini=? WHERE id = ?"); 
            statementBollette.setString(1, bolletta.getCasa());
            statementBollette.setString(2, bolletta.getUtenza());
            statementBollette.setString(3, bolletta.getScadenza());
            statementBollette.setDouble(4, Double.parseDouble(bolletta.getImporto().replace("€", "")));
            statementBollette.setString(5, bolletta.getDataPagata().equals("")? null : bolletta.getDataPagata());; 
            statementBollette.setString(6, bolletta.getQuoteCoinquilini());   
            statementBollette.setInt(7, bolletta.getId());  
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }
    public static void inserisciCoinquilini(String casa, String coinquilini){//10 
        try{
            statementBollette = connessioneDB.prepareStatement("UPDATE tabella_case SET inquilini = ? WHERE nome = ?"); 
            statementBollette.setString(1, coinquilini);
            statementBollette.setString(2, casa);
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }
    public static void eliminaBolletta(int idBolletta){//11
        try{
            statementBollette = connessioneDB.prepareStatement("DELETE FROM bollette WHERE id =?"); 
            statementBollette.setInt(1, idBolletta);
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}   
    }
    public static void eliminaCasa(String casa){//12
        try{
            statementBollette = connessioneDB.prepareStatement("DELETE FROM tabella_case WHERE nome = ?"); 
            statementBollette.setString(1, casa);
            statementBollette.executeUpdate();
            statementBollette = connessioneDB.prepareStatement("DELETE FROM bollette WHERE casa = ?");
            statementBollette.setString(1, casa);
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }
    public static void creaCasa(String casa){//13
        try{
            statementBollette = connessioneDB.prepareStatement("INSERT INTO tabella_case (nome) VALUES(?) "); 
            statementBollette.setString(1, casa);
            statementBollette.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }     
    public static String getQuoteCoinquilino(int idBolletta){//14
        try{
            statementBollette = connessioneDB.prepareStatement("SELECT quoteCoinquilini FROM bollette WHERE id  = ?"); 
            statementBollette.setInt(1, idBolletta);
            ResultSet rs = statementBollette.executeQuery();
            if(rs.next())
                return rs.getString("quoteCoinquilini");
            return null;
        }catch(SQLException e){System.out.println(e.getMessage());}  
        return null;
    }
    public static String getCoinquilini(String casa){//15
        try{    
            statementBollette = connessioneDB.prepareStatement("SELECT* FROM tabella_case WHERE nome = ?");
            statementBollette.setString(1, casa);
            ResultSet rs = statementBollette.executeQuery();
            if(rs.next())
                return rs.getString("inquilini");         
            return "";
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }
}

//fare i metodi getPagata e getQuotePagate
/*
00) La classe si occupa di comunicare con il DataBase
01) Creo una connessione con il DB 
02) metodo che crea un ArrayList con le bollette di una casa
03) metodo che restituisce le scadenze di tutte le bollette di una specifica utenza relativamente ad una casa
04) metodo che restituisce gli importi di tutte le bollette di una specifica utenza relativamente ad una casa
05) metodo che data una casa e una utenza manda un comando SQL per trovare quella con importo più grande
06) metodo che data una casa e una utenza manda un comando SQL per trovare quella con importo più piccolo
07) metodo che data una casa e una utenza manda un comando SQL per calcora l'importo medio
07.1) Se non ho bellette di quella utenza restituiso 0 come stringa
07.2) Tronca alla seconda cifra decimale il Valor Medio
08) Metodo che, presa una bolletta in ingresso la inserisce nel DB 
09) Metodo che presa una bolletta in ingresso la cerca nel DB e la copia dentro
10) Metodo che modifica i coinquilini in una casa, nel caso in cui aggiungo un coinquilino
11) Elimina una bolletta identificata tramite id
12) Elimina una casa dalla tabella casa e tutte le bollette associate dalla tabella bollette
13) Crea una nuova casa senza coinquilini
14) Restuisce una stringa con le quote dei coinquilini di una bolletta
15) Restituisce i coinquilini di una casa 
*/
