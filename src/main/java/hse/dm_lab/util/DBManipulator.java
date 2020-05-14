package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManipulator {
    private final String URl = "jdbc:postgresql://localhost:5432/postgres";
    private final String id = "postgres";
    private final String password = "alfresco";
    private Connection connection;

    public DBManipulator() {
        try {
            String JDBC_DRIVER = "org.postgresql.Driver";
            Class.forName(JDBC_DRIVER);
            Properties properties = new Properties();
            properties.setProperty("user", id);
            properties.setProperty("password", password);
            connection = DriverManager.getConnection(URl,properties);
            createTableProcedure();
            initProcedures();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception while connecting with MySQL");
            e.printStackTrace();
        }
    }

    public void createDB() {
        try {
            deleteDB();
            createTableProcedure();
            CallableStatement proc = connection.prepareCall("{ ? = call createItemTable() }");
            proc.execute();
            initProcedures();
            proc.close();

            connection = DriverManager.getConnection(URl,id,password);
            System.out.println("Table created");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("Однотабличная база данных успешно создана");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not create database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось создать базу данных");
            alert.showAndWait();
        }
    }

    //удаление базы данных
    public void deleteDB() {
        try {
            CallableStatement proc = connection.prepareCall("{ ? = call dropItemTable() }");
            proc.execute();
            proc.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("База данных успешно удалена");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not delete database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось удалить базу данных");
            alert.showAndWait();
        }
    }

    //очистка базы данных
    public void clearDB() {
        try {
            CallableStatement proc = connection.prepareCall("{ ? = call cleanItemTable() }");
            proc.execute();
            proc.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("База данных успешно очищена");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not clear database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось очистить базу данных");
            alert.showAndWait();
        }
    }

    public List<Item> showAll() {
        try {
            CallableStatement proc = connection.prepareCall("{ ? = call selectAllItems() }");
            proc.execute();
            ResultSet results = proc.getResultSet();
            return ItemConverter.entityFromResultSet(results);
        } catch (SQLException e) {
            System.out.println("Ошибка во время выполнения select'a");
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDB(Item object) {
        try {
            CallableStatement statement = connection.prepareCall("{ call insertNewItem(?, ?, ?, ?) }");
            statement.setString(1, object.getFio());
            statement.setBoolean(2, object.getSex().equals("Мужской"));
            statement.setInt(3, object.getClaimCount());
            statement.setString(4, object.getRole());
            statement.execute();
            statement.close();
        } catch (Exception e) {
            writingException(e);
        }
    }

    public void deleteItem(Integer itemId) {
        try {
            CallableStatement statement = connection.prepareCall("{ call deleteItemById(?) }");
            statement.setInt(1, itemId);
            statement.execute();
            statement.close();
            System.out.println("Запись была успешно удалена");
        } catch (SQLException e) {
            System.out.println("Could not clear database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось удалить элемент из базы данных");
            alert.showAndWait();
        }
    }

    public List<Item> selectItems(String fio) {
        try {
            CallableStatement proc = connection.prepareCall("{ call findItemByFio(?) }");
            proc.setString(1, fio);
            proc.execute();
            ResultSet results = proc.getResultSet();
            return ItemConverter.entityFromResultSet(results);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writingException(Exception e) {
        System.out.println("Произошла ошибка при записи в базу данных");
        System.out.println("Cause: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Произошла ошибка при записи в базу данных");
        alert.showAndWait();
    }

    private void initProcedures() {
        try {
            Statement statement = connection.createStatement();

            String dropTableProcedure = "create or replace function dropItemTable() " +
                    "    returns int as " +
                    "$$ " +
                    "begin " +
                    "    drop table if exists dm_lab.claims cascade; " +
                    "    return 1; " +
                    "end; " +
                    "$$language plpgsql";

            String cleanTableProcedure = "create or replace function cleanItemTable() " +
                    "    returns int as " +
                    "$$ " +
                    "begin " +
                    "    truncate table dm_lab.claims; " +
                    "    return 1; " +
                    "end; " +
                    "$$language plpgsql";

            String showAllProcedure = "CREATE OR REPLACE FUNCTION selectAllItems() " +
                    "    RETURNS SETOF dm_lab.claims AS " +
                    "$$ " +
                    "BEGIN " +
                    "    return query " +
                    "        SELECT * " +
                    "        FROM dm_lab.claims; " +
                    "END; " +
                    "$$ language plpgsql";

            String insertNewItemProcedure = "create or replace function insertNewItem(fio_in varchar(128), sex_in bool, claim_count_in int, role_in varchar(64)) " +
                    "    returns int as " +
                    "$$ " +
                    "declare " +
                    "    f alias for $1; " +
                    "    s alias for $2; " +
                    "    cc alias for $3; " +
                    "    r alias for $4; " +
                    "    max_id dm_lab.claims.id%type; " +
                    "begin " +
                    "    select max(id) into max_id from dm_lab.claims; " +
                    "    if(max_id is null) then " +
                    "        max_id = 0; " +
                    "    end if; " +
                    " " +
                    "    if (r not in ('Пользователь', 'Администратор', 'Разработчик', 'Аналитик')) then " +
                    "        raise exception 'ROLE NOT FOUND'; " +
                    "    end if; " +
                    " " +
                    "    insert into dm_lab.claims (id, fio, sex, claim_count, role) values (max_id + 1, f, s, cc, r); " +
                    "    return 1; " +
                    "end; " +
                    "$$language plpgsql";

            String deleteItemProcedure = "create or replace function deleteItemById(user_id int) " +
                    "    returns int as " +
                    "$$ " +
                    "declare " +
                    "    n alias for $1; " +
                    "begin " +
                    "    delete from dm_lab.claims where dm_lab.claims.id = user_id; " +
                    " " +
                    "    if not found then " +
                    "        raise exception 'Записи нет в базе'; " +
                    "    end if; " +
                    "    return 1; " +
                    "end; " +
                    "$$language plpgsql";
            
            String findItemProcedure = "create or replace function findItemByFio(varchar) " +
                    "    returns SETOF dm_lab.claims AS " +
                    "$$ " +
                    "begin " +
                    "    return query " +
                    "        SELECT * FROM dm_lab.claims where fio like '%' || $1 || '%'; " +
                    "end; " +
                    "$$language plpgsql";

            statement.executeUpdate(dropTableProcedure);
            statement.executeUpdate(showAllProcedure);
            statement.executeUpdate(cleanTableProcedure);
            statement.executeUpdate(insertNewItemProcedure);
            statement.executeUpdate(deleteItemProcedure);
            statement.executeUpdate(findItemProcedure);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableProcedure() {
        try {
            Statement statement = connection.createStatement();
            String createTableProcedure = "create or replace function createItemTable() " +
                    "    returns int as " +
                    "$$ " +
                    "begin " +
                    "    create table  dm_lab.claims ( " +
                    "                        id serial NOT NULL PRIMARY KEY, " +
                    "                        fio VARCHAR(128), " +
                    "                        sex BOOL DEFAULT FALSE, " +
                    "                        claim_count INT, " +
                    "                        role VARCHAR(32)); " +
                    "    return 1; " +
                    "end; " +
                    "$$language plpgsql";
            statement.executeUpdate(createTableProcedure);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
