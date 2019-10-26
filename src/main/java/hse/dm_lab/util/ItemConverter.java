package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import hse.dm_lab.model.ItemDTO;

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
}
