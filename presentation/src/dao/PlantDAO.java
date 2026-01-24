package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantDAO {

    // 1. AJOUTER UNE PLANTE
	public static boolean ajouter(String nom, String espece, String etat, int eau, String date, int idCat) {
        String sql = "INSERT INTO plant (nom, espece, etat_sante, besoin_eau, date_plantation, id_categorie) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, espece);
            ps.setString(3, etat);
            ps.setInt(4, eau);
            ps.setString(5, date);
            ps.setInt(6, idCat);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. MODIFIER UNE PLANTE
    public static boolean modifier(int id, String nom, String espece, String etat, int eau, String date, int idCat) {
        String sql = "UPDATE plant SET nom=?, espece=?, etat_sante=?, besoin_eau=?, date_plantation=?, id_categorie=? WHERE id_plante=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, espece);
            ps.setString(3, etat);
            ps.setInt(4, eau);
            ps.setString(5, date);
            ps.setInt(6, idCat);
            ps.setInt(7, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. SUPPRIMER UNE PLANTE
 // Dans PlantDAO.java
    public static boolean supprimerParId(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM plant WHERE id_plante = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePlant(int id, String nom, String espece, String sante, int eau) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE plant SET nom=?, espece=?, etat_sante=?, besoin_eau=? WHERE id_plante=?")) {
            ps.setString(1, nom);
            ps.setString(2, espece);
            ps.setString(3, sante);
            ps.setInt(4, eau);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // 4. RECHERCHER PAR NOM
    public static ResultSet rechercherParNom(String nom) {
        String sql = "SELECT p.*, c.nom_categorie FROM plant p LEFT JOIN categorie c ON p.id_categorie = c.id_categorie WHERE p.nom LIKE ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nom + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 5. FILTRER (Type/Catégorie, État, Date)
    public static ResultSet filtrerPlantes(Integer idCat, String etat, String date) {
        StringBuilder sql = new StringBuilder("SELECT p.*, c.nom_categorie FROM plant p LEFT JOIN categorie c ON p.id_categorie = c.id_categorie WHERE 1=1");
        
        if (idCat != null) sql.append(" AND p.id_categorie = ").append(idCat);
        if (etat != null && !etat.equals("TOUS")) sql.append(" AND p.etat_sante = '").append(etat).append("'");
        if (date != null) sql.append(" AND p.date_plantation = '").append(date).append("'");

        try {
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();
            return st.executeQuery(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 6. STATUT SANTÉ AUTOMATIQUE (Exemple basé sur le dernier arrosage)
    // Si pas d'arrosage depuis 7 jours -> CRITIQUE
    public static String calculerStatutAuto(int idPlante) {
        String sql = "SELECT DATEDIFF(CURDATE(), MAX(date_arrosage)) as jours FROM arrosage WHERE id_plante = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPlante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int jours = rs.getInt("jours");
                if (jours > 7) return "CRITIQUE";
                if (jours > 3) return "MOYEN";
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "BON";
    }

    // 7. HISTORIQUE D'ARROSAGE
    public static ResultSet getHistoriqueArrosage(int idPlante) {
        String sql = "SELECT * FROM arrosage WHERE id_plante = ? ORDER BY date_arrosage DESC";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPlante);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}