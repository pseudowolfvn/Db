package Db.Components;

import Db.Types.DbType;

import java.util.Set;

enum ColumnFeature{Key, Required, Uniq, AutoIncrement};

public class ColumnHeader<T> {
    //I am not sure of it
    DbType<?> type;
    Set<ColumnFeature> features;
    public ColumnHeader()
    public boolean findFeature(ColumnFeature val) {
        if (features.contains(val))
            return true;
        return false;
    }
    public boolean validate() {

    }

}
