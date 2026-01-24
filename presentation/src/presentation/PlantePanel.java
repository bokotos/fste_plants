package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import dao.DBConnection;

public class PlantePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public PlantePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("LISTE DES PLANTES");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JButton btnAdd = new JButton("+ AJOUTER");
        btnAdd.setBackground(new Color(109, 209, 109));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> {
            // Ouvrir ton dialogue d'ajout ici
            refreshTable();
        });

        header.add(title, BorderLayout.WEST);
        header.add(btnAdd, BorderLayout.EAST);

        // Tableau
        String[] cols = {"ID", "Nom", "Espèce", "Santé", "Eau (%)", "Actions"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return col == 5; }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        
        // Appliquer les boutons à la colonne "Actions"
        TableActionCell actionCell = new TableActionCell();
        table.getColumnModel().getColumn(5).setCellRenderer(actionCell);
        table.getColumnModel().getColumn(5).setCellEditor(actionCell);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);

        refreshTable();
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refreshTable() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM plant")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_plante"), rs.getString("nom"),
                    rs.getString("espece"), rs.getString("etat_sante"),
                    rs.getInt("besoin_eau"), ""
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}