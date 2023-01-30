package hust.itep.quanlynhankhau.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                FileInputStream input = new FileInputStream(
                        new File(DatabaseConnection.class.getResource("/database/config.properties").toURI())
                );
                properties.load(input);

                String database = properties.getProperty("database");
                String host = properties.getProperty("host");
                String port = properties.getProperty("port");
                String databaseName = properties.getProperty("databaseName");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                String url = String.format("jdbc:%s://%s:%s/%s", database, host, port, databaseName);

                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connect database success");
            } catch (URISyntaxException| IOException | SQLException e) {
                System.out.println("Connect database failed");
                e.printStackTrace();
            }
        }

        return connection;
    }
}
