package Db.Clients.javafx;

import Db.Components.Database;
import Db.Components.DatabaseManager;
import Db.Components.Row;
import Db.Components.Table;
import Db.Types.DbType;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DesktopGUI extends Application {

    private Database db = null;

    private final String DB_PATH = "/home/pseudowolf/dev/Projects/KNU/IT/Db/out/db";
    public static void main(String[] args) {
        launch(args);
    }

    public void showDatabaseNames(Stage primaryStage) {
        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                DatabaseManager.getDatabaseNames()
        );
        names.setItems(items);


        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2)
                    showTableNames(primaryStage, names.getSelectionModel().getSelectedItem());
            }
        });

        Button addDatabase = new Button("+");
        addDatabase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("database name");
                dialog.setTitle("Enter name of the new database");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    db = new Database(DB_PATH, result.get());
                    names.getItems().add(result.get());
                }
            }
        });

        Button deleteDatabase = new Button("-");
        deleteDatabase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<String> items = names.getSelectionModel().getSelectedItems();
                List<String> removeNames = new ArrayList<>();
                for (String item: items) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to delete " + item + "?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        DatabaseManager.deleteDatabase(item);
                        removeNames.add(item);
                    }
                }
                names.getItems().removeAll(removeNames);
            }
        });

        VBox root = new VBox();
        root.getChildren().addAll(names, addDatabase, deleteDatabase);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Databases");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showTableNames(Stage primaryStage, String databaseName) {
        db = DatabaseManager.getDatabase(databaseName);

        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                db.getTableNames()
        );
        names.setItems(items);


        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showTable(primaryStage, db, names.getSelectionModel().getSelectedItem());
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(names);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showTable(Stage primaryStage, Database db, String tableName) {
        TableView tableView = new TableView();
        tableView.setEditable(true);
        Table table = db.getTable(tableName);
        List<List<String>> stringView = table.getStringView();
        for (int i = 0; i < stringView.get(0).size(); ++i) {
            String columnName = stringView.get(0).get(i);
            TableColumn<Row, DbType> column = new TableColumn(columnName);
            final int index = i;
            column.setEditable(true);
            column.setCellValueFactory((val)->{
                Row row = val.getValue();
                List<DbType> data = row.getData();
                return new SimpleObjectProperty<DbType>(data != null && data.size() > index ? data.get(index) : null) {
                };
            });
            column.setCellFactory(TextFieldTableCell.forTableColumn(DbTypeStringConverterFactory.stringConverterFromType("Int")));
            column.setOnEditCommit((val)->{
                Row row = val.getRowValue();
                List<DbType> data = row.getData();
                data.get(val.getTablePosition().getColumn()).fromString(val.getNewValue().toString());
            });
            tableView.getColumns().add(column);
        }
        ObservableList<Row> rowsView = FXCollections.observableArrayList(table.getRows());

        tableView.setItems(rowsView);
        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.showDatabaseNames(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (db != null) {
            db.save();
        }
    }
}
