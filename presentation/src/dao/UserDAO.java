package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	public static String login(String email, String password) {
	    // Utilisation des backticks ` pour la table user
	    String sql = "SELECT nom FROM `user` WHERE email = ? AND password = ?";
	    try {
	        Connection conn = DBConnection.getConnection();
	        if (conn != null) {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, email);
	            ps.setString(2, password);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) return rs.getString("nom");
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return null;
	}
	
}
