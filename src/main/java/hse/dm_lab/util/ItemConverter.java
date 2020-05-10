package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import hse.dm_lab.model.ItemDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemConverter {

    public static Item modelToEntity(ItemDTO model) {
        String sex;
        if (model.getSex()) {
            sex = "Мужской";
        } else {
            sex = "Женский";
        }
        String role;
        switch (model.getRole()) {
            case 'A':
                role = "Администратор";
                break;
            case 'D':
                role = "Разработчик";
                break;
            case 'N':
                role = "Аналитик";
                break;
            default:
                role = "Пользователь";
                break;
        }
        return new Item(model.getId(), model.getFio(), sex, model.getClaimCount(), role);
    }

    public static ItemDTO entityToModel(Item entity) {
        boolean sex = entity.getSex().equals("Мужской");
        char role;
        switch (entity.getRole()) {
            case "Администратор":
                role = 'A';
                break;
            case "Разработчик":
                role = 'D';
                break;
            case "Аналитик":
                role = 'N';
                break;
            default:
                role = 'U';
                break;
        }
        return new ItemDTO(entity.getId(), entity.getFio(), sex, entity.getClaimCount(), role);
    }

    public static Item entityFromResultSet(ResultSet resultSet) {
        try {
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setFio(resultSet.getString("fio"));
            item.setSex(resultSet.getBoolean("sex") ? "Мужской" : "Женский");
            item.setClaimCount(resultSet.getInt("claim_count"));
            item.setRole(getRole(resultSet.getString("role").charAt(0)));
            return item;
        } catch (SQLException e) {
            System.out.println("Ошибка во время извлечения сущности из бд");
            e.printStackTrace();
        }
        return null;
    }

    private static String getRole(Character character) {
        String role;
        switch (character) {
            case 'A':
                role = "Администратор";
                break;
            case 'D':
                role = "Разработчик";
                break;
            case 'N':
                role = "Аналитик";
                break;
            default:
                role = "Пользователь";
                break;
        }
        return role;
    }
}
