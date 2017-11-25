package soap.client;

import Db.Clients.javafx.AddRowCorbaDialog;
import Db.Clients.javafx.ColumnHeaderDialog;
import Db.Components.rmi.iiop.interfaces.ColumnHeader;
import Db.Components.rmi.iiop.interfaces.Database;
import Db.Components.rmi.iiop.interfaces.DbType;
import Db.Components.rmi.iiop.interfaces.Table;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soap.interfaces.DatabaseManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class javafxGUI extends Application {
    private DatabaseManager databaseManager;
    private String dbName;
    private String tableName;

    public static void main(String[] args) {
        launch(args);
    }

    private void initClient() throws MalformedURLException {
        URL url = new URL("http://localhost:8080/WebService_war/services/DatabaseManager?wsdl");
        QName qname = new QName("http://server.soap/", "DatabaseManager");
        Service service = Service.create(url, qname);
        databaseManager = service.getPort(DatabaseManager.class);
    }

    public void showDatabaseNames(Stage primaryStage) {
        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                databaseManager.getDatabaseNames()
        );

        names.setItems(items);

        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    dbName = names.getSelectionModel().getSelectedItem();
                    showTableNames(primaryStage);
                }
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
                        databaseManager.createDatabase(result.get());
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
                        databaseManager.deleteDatabase(item);
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
                        databaseManager.renameDatabase(item, result.get());
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
        buttons.getChildren().addAll(addDatabase, deleteDatabase, renameDatabase);
        root.getChildren().addAll(names, buttons);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Databases");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showTableNames(Stage primaryStage) {
        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                databaseManager.getTableNames(dbName)
        );
        names.setItems(items);


        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    tableName = names.getSelectionModel().getSelectedItem();
                    showTable(primaryStage);
                }

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
                    databaseManager.addTable(dbName, result.get());
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
                        databaseManager.deleteTable(dbName, item);
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
                        databaseManager.renameTable(dbName, item, result.get());
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

    private void addNewColumn(TableView tableView, ColumnHeader columnHeader) {
        this.addNewColumn(tableView, columnHeader, tableView.getColumns().size());
    }

    private void addNewColumn(TableView tableView, ColumnHeader columnHeader, final int index) {
        TableColumn<DbType[], String> column = new TableColumn(columnHeader.title);
        column.setEditable(true);
        column.setCellValueFactory((val) -> {
            DbType[] data = val.getValue();
            return new SimpleObjectProperty<String>(data != null && data.length > index ? data[index].value : null) {
            };
        });
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit((val) -> {
            TablePosition position = val.getTablePosition();
            val.getRowValue()[position.getColumn()].value = val.getNewValue();
            databaseManager.setRow(dbName, tableName
                    , val.getNewValue()
                    , columnHeader.name, position.getRow());
        });
        tableView.getColumns().add(column);
    }

    public void showTable(Stage primaryStage) {
        TableView tableView = new TableView();
        tableView.setEditable(true);
        List<ColumnHeader> tableColumns
                = Arrays.asList(
                        databaseManager.getColumns(dbName, tableName));
        for (int i = 0; i < tableColumns.size(); ++i) {
            this.addNewColumn(tableView, tableColumns.get(i), i);
        }
        ObservableList<DbType[]> rowsView
                = FXCollections.observableArrayList(
                        databaseManager.getRows(dbName, tableName));

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
                    databaseManager.addColumn(dbName, tableName
                            , columnName, columnType);
                    addNewColumn(tableView
                            , databaseManager.getColumn(dbName, tableName
                                    , columnName));
                }

            }
        });

        Button addRow = new Button("Add row");
        addRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AddRowCorbaDialog dialog = null;
                dialog = new AddRowCorbaDialog(Arrays.asList(
                        databaseManager.getColumns(dbName, tableName)));
                dialog.showAndWait();
                try {
                    DbType[] insertedRow
                            = databaseManager.insertRow(dbName, tableName
                                , dialog.dataResult.toArray(new String[0]));
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
    public void start(Stage primaryStage) throws MalformedURLException {
        this.initClient();
        this.showDatabaseNames(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println(databaseManager.save());
    }
}
