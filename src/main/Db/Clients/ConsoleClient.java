package Db.Clients;

import Db.Components.Database;
import Db.Components.Table;
import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleClient {
    private enum Option { DB_OPEN, DB_CLOSE, DB_DELETE
        , TABLE_INSERT, TABLE_ADD_COL, TABLE_DELETE_ROW, TABLE_DELETE_COL
        , CREATE, SHOW, TABLELIST, UNKNOWN}

    public ConsoleClient() {}

    public String[] parseLine(String line) {
        return line.split(" ");
    }

    public Option parseCommands(String[] commands) {
        int argc = commands.length;
        if (argc > 1) {
            if (commands[1].equalsIgnoreCase("open"))
                return Option.DB_OPEN;
            else if (commands[1].equalsIgnoreCase("close"))
                return Option.DB_CLOSE;
            else if (commands[1].equalsIgnoreCase("insert"))
                return Option.TABLE_INSERT;
            else if (commands[1].equalsIgnoreCase("add"))
                return Option.TABLE_ADD_COL;
            else if (commands[1].equalsIgnoreCase("delete")) {
                if (commands[2].equalsIgnoreCase("col"))
                    return Option.TABLE_DELETE_COL;
                else if (commands[2].equalsIgnoreCase("row"))
                    return Option.TABLE_DELETE_ROW;

            }
            else if (commands[1].equalsIgnoreCase("create"))
                return Option.CREATE;
            else if (commands[1].equalsIgnoreCase("show"))
                return Option.SHOW;
        }
        else if (argc > 0) {
            if (commands[0].equalsIgnoreCase("tablelist"))
                return Option.TABLELIST;
        }
        return Option.UNKNOWN;
    }

    public void run(String path) throws IOException, ClassNotFoundException {
        Scanner s = new Scanner(System.in);
        Database db = null;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] commands = this.parseLine(line);
            Option option = this.parseCommands(commands);
            try {
                if (option == Option.TABLELIST) {
                    if (db != null) {
                        List<String> names = db.getTableNames();
                        for (String name: names)
                            System.out.println(name);
                    }

                }
                else if (option == Option.DB_OPEN) {
                    if (db == null) {
                        db = Database.load(path, commands[0]);
                    }
                    else {
                        db.save();
                        db = Database.load(path, commands[0]);
                    }
                }
                else if (option == Option.DB_CLOSE) {
                    if (db != null) {
                        db.save();
                        db = null;
                    }
                }
                else if (option == Option.CREATE) {
                    if (db == null) {
                        db = new Database(path, commands[0]);
                        System.out.println(db.getName() + " was created");
                    }
                    else {
                        db.addTable(commands[0]);
                        System.out.println(db.getTable(commands[0]).getName() + " was added");
                    }
                }
                else if (option == Option.TABLE_ADD_COL) {
                    db.getTable(commands[0]).addColumn(commands[3], commands[4]);
                }
                else if (option == Option.TABLE_DELETE_COL) {
                    db.getTable(commands[0]).deleteColumn(commands[3]);
                }
                else if (option == Option.TABLE_DELETE_ROW) {
                    db.getTable(commands[0]).deleteRow(Integer.parseInt(commands[3]));
                }
                else if (option == Option.TABLE_INSERT) {
                    try {
                        db.getTable(commands[0]).insertRow(Arrays.copyOfRange(commands, 2, commands.length));
                    } catch (IllegalConversionException | UnsupportedTypeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (option == Option.SHOW) {
                    db.getTable(commands[0]).printToStdout();
                }
                else if (option == Option.UNKNOWN) {
                    System.out.println("Unknown command");
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("Unknown command");
            }
        }
        if (db != null)
            db.save();
    }

}
