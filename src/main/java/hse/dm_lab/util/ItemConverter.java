package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import hse.dm_lab.model.ItemDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemConverter {

    static Item modelToEntity(ItemDTO model) {
        String sex;
        if (model.getSex()) {
            sex = "Мужской";
        } else {
            sex = "Женский";
        }
        return new Item(model.getId(), model.getFio(), sex, model.getClaimCount(), model.getRole());
    }

    static Item entityFromResultSet(ResultSet resultSet) {
        try {
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setFio(resultSet.getString("fio"));
            item.setSex(resultSet.getBoolean("sex") ? "Мужской" : "Женский");
            item.setClaimCount(resultSet.getInt("claim_count"));
            item.setRole(resultSet.getString("role"));
            return item;
        } catch (SQLException e) {
            System.out.println("Ошибка во время извлечения сущности из бд");
            e.printStackTrace();
        }
        return null;
    }
}
