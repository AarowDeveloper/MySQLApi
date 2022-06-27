package dev.aarow.mysqlapi;

import dev.aarow.mysqlapi.data.mysql.MySQLConnection;
import dev.aarow.mysqlapi.data.mysql.other.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MySQLApi {
    public static boolean doesPlayerExist(UUID uuid, MySQLConnection mySQLConnection, String table){
        try {
            PreparedStatement statement = mySQLConnection.getMainConnection()
                    .prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(UUID uuid, MySQLConnection mySQLConnection, String table){
        try {
            PreparedStatement statement = mySQLConnection.getMainConnection()
                    .prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (doesPlayerExist(uuid, mySQLConnection, table) != true) {
                StringBuilder columns = new StringBuilder();
                StringBuilder questionMarks = new StringBuilder();

                List<String> questionMarkCount = new ArrayList<>();

                columns.append("INSERT INTO " + table + " (UUID");

                questionMarkCount.add("?");

                mySQLConnection.getData().get(table).forEach(data -> {
                    columns.append("," + data.getName());

                    questionMarkCount.add("?");
                });

                questionMarkCount.forEach(mark -> {
                    questionMarks.append(",?");
                });

                columns.append(") VALUES (?" + questionMarks + ")");

                System.out.println(columns.toString());

                PreparedStatement insert = mySQLConnection.getMainConnection()
                        .prepareStatement(columns.toString());

                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateField(UUID uuid, MySQLConnection mySQLConnection, Data data, String table, Object newValue){
        try {
            PreparedStatement statement = mySQLConnection.getMainConnection()
                    .prepareStatement("UPDATE " + table + " SET " + data.getName() + "=? WHERE UUID=?");

            // SOON

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
