import java.time.*;
import java.time.format.*;
import javafx.event.*;
import javafx.scene.control.*;

public class DatePickerTableCell extends TableCell<Bolletta, String> { //00)
    private DatePicker datePicker;

    public void startEdit() { //01
        if(!isEmpty()){
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    public void cancelEdit() { //02
        super.cancelEdit();

        setText(getItem() == null ? null: getItem());
        setGraphic(null);
    }

    public void updateItem(String item, boolean empty) { //03
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getItem() == null ? null: getItem());
                setGraphic(null);
            }
        }
     }


    private void createDatePicker(){ //04)
        datePicker = new DatePicker(getDate());
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                commitEdit(datePicker.getValue().toString());
            }
        });
    }

    private LocalDate getDate() { //05
        return getItem().equals("")? LocalDate.now() : LocalDate.parse(getItem(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

/* Note
00) La classe permette di modificare la cella della TableView con un DatePicker
01) Quando inizia la modifica alla cella viene creato un DatePicker e visualizzato nella cella
02) Se si annulla la modifica, imposta nuovamente il valore precedente
03) Assegna alla cella il valore selezionato nel datePicker
04) Crea un oggetto DatePicker e, quando quando questo viene modificato, invia il nuovo valore 
    alla cella corrispondente
05) Restituisce il valore della cella come LocalDate o, nel caso la cella sia vuota, la data corrente
*/