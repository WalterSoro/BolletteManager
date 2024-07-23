import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerLogXML { //00)
    private final static int PORTA = 8080;
    private final static String FILE_LOG = "log.xml";
    private final static String SCHEMA_LOG = "log.xsd";
    private final static String PATH = "C:\\prg\\myapps\\ServerLogXML\\";

    public static void main(String[] args) {
        System.out.println("Server Log avviato");
        try ( ServerSocket servsock = new ServerSocket(PORTA) ){ //01)
            while(true) { //02)
                try ( Socket socket = servsock.accept(); 
                      ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
                ){
                    String log = (String) oin.readObject(); //03)
                    if(ValidatoreXML.valida(log, PATH+SCHEMA_LOG, false))
                        Files.write(Paths.get(PATH+FILE_LOG), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);                    
                } catch (IOException | ClassNotFoundException ex) { System.err.println(ex.getMessage()); System.out.println(ex.getClass());}
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
/* Note

00) Server che riceve ciclicamente log XML, e se validati correttamente, li
    aggiunge ad un file di log.
01) Crea un server socket, specificandone la porta.
02) L'applicazione aspetta finchè viene creata una connessione, e la accetta.
03) Viene letta la stringa XML di log
04) Stampa la stringa XML di log a video.
05) Salva su file la stringa XML
*/