package Db.Clients.javafx;

import Db.Components.rmi.iiop.interfaces.ColumnHeader;
import Db.Components.rmi.iiop.interfaces.Database;
import Db.Components.rmi.iiop.interfaces.Table;
import Db.Components.rmi.iiop.interfaces.DatabaseManager;
import Db.Components.rmi.iiop.interfaces.DbType;

//import Db.Components.corba.ColumnHeader;
//import Db.Components.corba.Database;
//import Db.Components.corba.Table;
//import Db.Components.corba.DatabaseManager;
//import Db.Components.corba.DbType;

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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class corbaGUI extends Application {
    private DatabaseManager databaseManager;
    private Database db;
    private Table table;
    private Context context;

    public static void main(String[] args) {
        launch(args);
    }

    private void initClient() throws RemoteException, NotBoundException, MalformedURLException, NamingException {
        context = new InitialContext();
        Object objref = context.lookup("databaseManager_CORBA");
        databaseManager = (DatabaseManager) PortableRemoteObject.narrow(objref, DatabaseManager.class);
    }

    public void showDatabaseNames(Stage primaryStage) throws RemoteException {
        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                databaseManager.getDatabaseNames()
        );

        names.setItems(items);

        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2)
                    try {
                        String name = names.getSelectionModel().getSelectedItem();
                        String corbaName = databaseManager.getDatabase(name);
                        db = (Database) PortableRemoteObject
                                .narrow(context.lookup(corbaName), Database.class);
                        showTableNames(primaryStage);
                    } catch (RemoteException | NamingException e) {
                        e.printStackTrace();
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
                        try {
                            databaseManager.deleteDatabase(item);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
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
                            databaseManager.renameDatabase(item, result.get());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
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

    public void showTableNames(Stage primaryStage) throws RemoteException {
        ListView<String> names = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                db.getTableNames()
        );
        names.setItems(items);


        names.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String name = names.getSelectionModel().getSelectedItem();
                    String corbaName = null;
                    try {
                        corbaName = db.getTable(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        table = (Table) PortableRemoteObject
                                .narrow(context.lookup(corbaName), Table.class);
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }
                    try {
                        showTable(primaryStage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        db.addTable(result.get());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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
                        try {
                            db.deleteTable(item);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
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
                        try {
                            db.renameTable(item, result.get());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
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
//        column.setCellFactory(TextFieldTableCell
//                .forTableColumn(
//                        DbTypeStringConverterFactory.stringConverterFromType(columnHeader.type)));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit((val) -> {
            // data.get(val.getTablePosition().getColumn()).fromString(val.getNewValue()._toString());
            TablePosition position = val.getTablePosition();
            val.getRowValue()[position.getColumn()].value = val.getNewValue();
            try {
                table.setData(val.getNewValue(), columnHeader.name, position.getRow());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        tableView.getColumns().add(column);
    }

    public void showTable(Stage primaryStage) throws RemoteException {
        TableView tableView = new TableView();
        tableView.setEditable(true);
        List<ColumnHeader> tableColumns = Arrays.asList(table.getColumns());
        for (int i = 0; i < tableColumns.size(); ++i) {
            this.addNewColumn(tableView, tableColumns.get(i), i);
        }
        ObservableList<DbType[]> rowsView = FXCollections.observableArrayList(table.getRows());

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
                    try {
                        table.addColumn(columnName, columnType);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        addNewColumn(tableView, table.getColumn(columnName));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button addRow = new Button("Add row");
        addRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AddRowCorbaDialog dialog = null;
                try {
                    dialog = new AddRowCorbaDialog(Arrays.asList(table.getColumns()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                dialog.showAndWait();
                try {
                    DbType[] insertedRow = table.insertRow(dialog.dataResult.toArray(new String[0]));
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
    public void start(Stage primaryStage) throws Exception {
        this.initClient();
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
