package Db.Components;

import Db.Types.DbType;

import java.util.Set;

enum ColumnFeature{Key, Required, Uniq, AutoIncrement};

public class ColumnHeader {
    String type, name;
    // Set<ColumnFeature> features;

    public ColumnHeader(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

//    public boolean findFeature(ColumnFeature val) {
//        if (features.contains(val))
//            return true;
//        return false;
//    }
}
