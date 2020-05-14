package hse.dm_lab.util;

import hse.dm_lab.model.Item;

public class ItemConverter {

    static Item entityFromResultSet(String resultSet) {
        Item item = new Item();
        String object[] = resultSet.split(",");
        item.setId(Integer.parseInt(object[0]));
        item.setFio(object[1].substring(1, object[1].lastIndexOf("\"")));
        item.setSex(object[2].equals("t") ? "Мужской" : "Женский");
        item.setClaimCount(Integer.parseInt(object[3]));
        item.setRole(object[4]);
        return item;
    }
    //(1,"dkihg flwij",t,54,Пользователь)
}
