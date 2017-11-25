package Db.Clients.javafx;

import Db.Components.*;
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
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;

public class DesktopGUI extends Application {

    private Database db = null;

    private final String DB_PATH = "/home/charmer/dev/projects/KNU/IT/Db/out/db";
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

        Button addDatabase = new Button("Add");
        addDatabase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("database_name");
                dialog.setTitle("Enter name of the new database");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    try {
                        db = new Database(DB_PATH, result.get());
                        db.save();
                        names.getItems().add(result.get());
                    }
                    catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        Button deleteDatabase = new Button("Delete");
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

        Button renameDatabase = new Button("Rename");
        renameDatabase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<String> selectedItems = names.getSelectionModel().getSelectedItems();
                Map<String, String> renameNames = new HashMap<>();
                for (String item: selectedItems) {
                    TextInputDialog dialog = new TextInputDialog(item);
                    dialog.setTitle("Enter new name for the database");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        try {
                            DatabaseManager.renameDatabase(item, result.get());
                            renameNames.put(item, result.get());
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                List<String> items = names.getItems();
                for (int i = 0; i < items.size(); ++i)
                    if (renameNames.containsKey(items.get(i)))
                        items.set(i, renameNames.get(items.get(i)));
            }
        });

        VBox root = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(addDatabase, deleteDatabase, renameDatabase);
        root.getChildren().addAll(names, buttons);

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
                if (event.getClickCount() == 2)
                    showTable(primaryStage, db, names.getSelectionModel().getSelectedItem());
            }
        });

        Button addTable = new Button("Add");
        addTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog dialog = new TextInputDialog("table_name");
                dialog.setTitle("Enter name of the new table");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    db.addTable(result.get());
                    names.getItems().add(result.get());
                }
            }
        });

        Button deleteTable = new Button("Delete");
        deleteTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<String> items = names.getSelectionModel().getSelectedItems();
                List<String> removeNames = new ArrayList<>();
                for (String item: items) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to delete " + item + "?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        db.deleteTable(item);
                        removeNames.add(item);
                    }
                }
                names.getItems().removeAll(removeNames);
            }
        });

        Button renameTable = new Button("Rename");
        renameTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                List<String> selectedItems = names.getSelectionModel().getSelectedItems();
                Map<String, String> renameNames = new HashMap<>();
                for (String item: selectedItems) {
                    TextInputDialog dialog = new TextInputDialog(item);
                    dialog.setTitle("Enter new name for the table");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        db.renameTable(item, result.get());
                        renameNames.put(item, result.get());
                    }
                }
                List<String> items = names.getItems();
                for (int i = 0; i < items.size(); ++i)
                    if (renameNames.containsKey(items.get(i)))
                        items.set(i, renameNames.get(items.get(i)));
            }
        });

        VBox root = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(addTable, deleteTable, renameTable);
        root.getChildren().addAll(names, buttons);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addNewColumn(TableView tableView, String columnName, String typeName) {
        this.addNewColumn(tableView, columnName, typeName, tableView.getColumns().size());
    }

    private void addNewColumn(TableView tableView, String columnName, String typeName, final int index) {
        TableColumn<Row, DbType> column = new TableColumn(columnName);
        column.setEditable(true);
        column.setCellValueFactory((val)->{
            Row row = val.getValue();
            List<DbType> data = row.getData();
            return new SimpleObjectProperty<DbType>(data != null && data.size() > index ? data.get(index) : null) {
            };
        });
        column.setCellFactory(TextFieldTableCell
                .forTableColumn(
                        DbTypeStringConverterFactory.stringConverterFromType(typeName)));
        column.setOnEditCommit((val)->{
            Row row = val.getRowValue();
            List<DbType> data = row.getData();
            data.get(val.getTablePosition().getColumn()).fromString(val.getNewValue().toString());
        });
        tableView.getColumns().add(column);
    }

    public void showTable(Stage primaryStage, Database db, String tableName) {
        TableView tableView = new TableView();
        tableView.setEditable(true);
        Table table = db.getTable(tableName);
        List<ColumnHeader> tableColumns = table.getColumns();
        for (int i = 0; i < tableColumns.size(); ++i) {
            String columnName = tableColumns.get(i).toString();
            String typeName = tableColumns.get(i).getType();
            this.addNewColumn(tableView, columnName, typeName, i);

        }
        ObservableList<Row> rowsView = FXCollections.observableArrayList(table.getRows());

        Button addColumn = new Button("Add column");
        addColumn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ColumnHeaderDialog dialog = new ColumnHeaderDialog();
                dialog.showAndWait();
                if (dialog.nameResult.isPresent()
                        && dialog.typeResult.isPresent()) {
                    String columnName = dialog.nameResult.get();
                    String columnType = dialog.typeResult.get();
                    table.addColumn(columnName, columnType);
                    addNewColumn(tableView, table.getColumn(columnName).toString(), columnType);
                }

            }
        });

        Button addRow = new Button("Add row");
        addRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AddRowDialog dialog = new AddRowDialog(table.getColumns());
                dialog.showAndWait();
                try {
                    Row insertedRow = table.insertRow(dialog.dataResult.toArray(new String[0]));
                    tableView.getItems().add(insertedRow);
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });

        tableView.setItems(rowsView);

        VBox root = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(addColumn, addRow);
        root.getChildren().addAll(tableView, buttons);

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
