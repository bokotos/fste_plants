package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/dbgestion?useUnicode=true&characterEncoding=UTF-8";
        String login = "root";
        String password = "Akram_0626613669";

        try {
            if (conn == null || conn.isClosed()) {
                // Utilisation de l'ancien driver correspondant à ton JAR 5.1.14
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                conn = DriverManager.getConnection(url, login, password);
                
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver 5.1.14 introuvable dans le Classpath !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de base de données : " + e.getMessage());
        }
        return conn;
    }
}