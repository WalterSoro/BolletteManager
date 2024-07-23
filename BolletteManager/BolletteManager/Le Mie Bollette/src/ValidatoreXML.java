import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML { //01)

    public static boolean valida(String nomeFile, String fileSchema, boolean file) { // 02)
        String xml = carica(nomeFile);
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //03)
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); //04)
            Document doc;
            if(file) //05)
                doc = documentBuilder.parse(new File(xml)); //06)
            else //07)
                doc = documentBuilder.parse(new InputSource(new StringReader(xml))); //08)

            Schema schema = schemaFactory.newSchema( //09)
                                new StreamSource(new File(fileSchema)));
            schema.newValidator().validate(new DOMSource(doc)); //10)
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    public static String carica(String nomeFile){
        try{
            return new String(Files.readAllBytes(Paths.get(nomeFile)));
        }catch(IOException e){System.err.println(e.getMessage());}
        return null;
    }
}


/* Note

01) Classe che gestisce la validazione di file o stringhe XML.
    Ritorna un booleano il cui valore e' true se la validazione avviene con
    successo, e viceversa.
02) Valida un file o una stringa XML secondo uno schema xsd
    specificato da fileSchema. Il terzo parametro distingue il caso di
    validazione di un file XML o direttamente di una stringa XML
03) Definisce le API per ottenere documenti DOM da piu' tipi di sorgenti XML,
    come in questo caso da un file o da una stringa.
04) Factory di oggetti Schema che funge da compilatore di schema XML
05) Se file==true l'input XML e' da prelevare in un file.
06) Effettua il parse del file XML e restituisce un oggetto DOM.
07) Se file==false l'input XML è passato direttamente come stringa (primo parametro)
08) Effettuo il parsing da stringa XML (e non da file)
09) Oggetto Schema creato dallo SchemaFactory, ottenuto prelevando il file
    di schema XSD..
10) Viene creato un oggetto validatore che valida il documento DOM derivante
    dal documento XML, sullo Schema appena creato.
*/