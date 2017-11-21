package Db.Clients.javafx;

import Db.Components.rmi.iiop.interfaces.ColumnHeader;

// import Db.Components.corba.ColumnHeader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AddRowCorbaDialog extends Dialog {
    public List<String> dataResult;

    public AddRowCorbaDialog(List<ColumnHeader> columns) {
        super();
        String[] defaultResult = new String[columns.size()];
        Arrays.fill(defaultResult, "");
        dataResult = Arrays.asList(defaultResult);
        this.setTitle("Add row");

        GridPane grid = new GridPane();

        int i = 0;
        for (ColumnHeader column: columns) {
            Label nameLabel = new Label(column.name);
            Label typeLabel = new Label(column.type);
            TextField valueText = new TextField("");
            final int index = i;
            valueText.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    dataResult.set(index, newValue);
                }
            });
            grid.add(nameLabel, 1, i + 1);
            grid.add(typeLabel, 2, i + 1);
            grid.add(valueText, 3, i + 1);
            ++i;
        }

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
    }
}
