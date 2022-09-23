package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class Postgres2JSONDriver {
    private Connection connection;

    public Postgres2JSONDriver(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Used for INSERT, UPDATE, and DELETE operations
    public void executeUpdate(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

            System.out.println("Update successful!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Used for SELECT operations
    public String executeQuery(String sql) {
        JSONObject json = new JSONObject();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            json.put("status_code", "200");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();

            JSONArray array = new JSONArray();

            while (resultSet.next()) {
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columns; i++) {
                    obj.put(metaData.getColumnName(i), resultSet.getString(i));
                }
                array.put(obj);
            }

            json.put("data", array);
            statement.close();

            return json.toString();

        } catch (SQLException e) {
            json.put("status_code", "400");
            json.put("data", "");

            return json.toString();
        }
    }
}
