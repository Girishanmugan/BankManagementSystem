import java.sql.Connection;
import java.sql.DriverManager;

public class Bank {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connection to SQLite has been established.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}