package hse.dm_lab.util;

import hse.dm_lab.model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemConverter {

    static List<Item> entityFromResultSet(ResultSet resultSet) {
        List<Item> res = new ArrayList<>();
        try {
            int i = 1;
            while (resultSet.next()) {
                Item item = new Item();
                String[] object = resultSet.getString(i).substring(1, resultSet.getString(1).length() - 1).split(",");
                item.setId(Integer.parseInt(object[0]));
                item.setFio(object[1].substring(1, object[1].lastIndexOf("\"")));
                item.setSex(object[2].equals("t") ? "Мужской" : "Женский");
                item.setClaimCount(Integer.parseInt(object[3]));
                item.setRole(object[4]);
                res.add(item);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    //(1,"dkihg flwij",t,54,Пользователь)
}
