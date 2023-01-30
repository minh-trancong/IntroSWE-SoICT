package hust.itep.quanlynhankhau.database;

import java.sql.Connection;
public class DatabaseConnectionTest {
    public static void main(String[] args) {
        Connection connect = DatabaseConnection.getConnection();
    }
}
