package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DBConnection;

public class ExportUtil extends JPanel {
	private JPanel panel;
	private JButton btnExport;
	
	public ExportUtil () {
	    setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // --- HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        header.setOpaque(false);
        JLabel title = new JLabel("Exporter tous les Donnee de la Ferme ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title);
        add(header, BorderLayout.NORTH);
        
        JButton btnExport = new JButton("Export Data");
        btnExport.setFont(new Font("Segoe UI", Font.BOLD, 30));
        btnExport.setBackground(new Color(109,209,109));
        btnExport.setForeground(Color.WHITE);
        btnExport.setFocusPainted(false);
        btnExport.setBorder(BorderFactory.createEmptyBorder(90, 90, 90, 90));

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                if (ExportUtil.exportPlantsToTxt(chooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this, "Export réussi !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur d’export");
                }
            }
        });

        add(btnExport);
		
	}
	

    public static boolean exportPlantsToTxt(File file) {
        String sql = "SELECT * FROM plant";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("===== LISTE DES PLANTES =====");
            writer.newLine();
            writer.newLine();

            while (rs.next()) {
                writer.write("ID: " + rs.getInt("id_plante"));
                writer.newLine();
                writer.write("Nom: " + rs.getString("nom"));
                writer.newLine();
                writer.write("Espèce: " + rs.getString("espece"));
                writer.newLine();
                writer.write("Santé: " + rs.getString("etat_sante"));
                writer.newLine();
                writer.write("Besoin eau: " + rs.getInt("besoin_eau"));
                writer.newLine();
                writer.write("Date de plantation: " + rs.getDate("date_plantation"));
                writer.newLine();
                writer.write("-----------------------------");
                writer.newLine();
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
