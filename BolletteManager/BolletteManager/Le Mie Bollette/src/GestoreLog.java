
import java.io.*;
import java.net.*;


public class GestoreLog {//00 
    private static void invia(EventoLog evento, String IPServerLog, int portaServerLog){//01
        try(Socket sock = new Socket(IPServerLog, portaServerLog);
            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream())    
        ){
            out.writeObject(evento.toString());//02
        }catch(IOException e){System.err.println(e.getMessage());}
    }
    
    public static void creaLog(String nomeEvento, ParametriConfigurazione xml){
        invia(  
                new EventoLog(nomeEvento, xml.IPClient),
                xml.IPServerLog,
                xml.portaServerLog
        );
    }
}

/*
00) Gestisce l'invio di dati con il ServerLog
01) Metodo che invia un oggetto EventoLog ad un server di indirizzo ip e porta 
    dati. Il metodo è private perchè verrà usato solo da creaLog
02) Invia un oggetto EventoLog come stringa xml
03) Metodo che crea un oggetto EventoLog tramite un oggetto ParametriConfigurazione
    e invoca il metodo invia
*/