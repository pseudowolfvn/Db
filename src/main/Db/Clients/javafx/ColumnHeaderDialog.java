package Db.Clients.javafx;

import Db.Components.ColumnHeader;
import Db.Types.DbTypesFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Optional;

public class ColumnHeaderDialog extends Dialog {
    public Optional<String> nameResult = Optional.empty();
    public Optional<String> typeResult = Optional.empty();

    public ColumnHeaderDialog() {
        super();
        this.setTitle("Add column");

        Label nameLabel = new Label("Name: ");
        Label typeLabel = new Label("Type: ");
        TextField nameText = new TextField("column_name");
        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                nameResult = Optional.of(newValue);
            }
        });
        ChoiceBox<String> typeBox
                = new ChoiceBox<>(FXCollections
                    .observableArrayList(DbTypesFactory.supportedTypeNames()));
        typeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                typeResult = Optional.of(newValue);
            }
        });

        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(typeLabel, 1, 2);
        grid.add(nameText, 2, 1);
        grid.add(typeBox, 2, 2);
        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
    }

}
