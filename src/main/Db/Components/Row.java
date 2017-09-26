package Db.Components;

import Db.Types.DbType;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<DbType> data;
    public Row() {
        data = new ArrayList<>();
    }

    public void insertData(DbType value) {
        data.add(value);
    }

    public void deleteData(int dataInd) {
        data.remove(dataInd);
    }

    public List<String> getStringView() {
        List<String> view = new ArrayList<>();
        for (int i = 0; i < data.size(); ++i)
            view.add(data.get(i).toString());
        return view;
    }
}
