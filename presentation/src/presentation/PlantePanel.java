package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.sql.*;
import dao.DBConnection;

public class PlantePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public PlantePanel() {
    	setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // light background

        // ===== CARD CONTAINER =====
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Plantes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Gestion et suivi des plantes");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);

        JButton btnAdd = new JButton("+ Ajouter");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        btnAdd.setContentAreaFilled(true);

        btnAdd.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnAdd.addActionListener(e -> {
            new AddPlantDialog(
                (Frame) SwingUtilities.getWindowAncestor(this)
            ).setVisible(true);
            refreshTable();
        });

        header.add(titleBox, BorderLayout.WEST);
        header.add(btnAdd, BorderLayout.EAST);

        // ===== TABLE =====
        String[] cols = {"ID", "Nom", "Espèce", "Santé", "Eau (%)", "Actions"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        table = new JTable(model);
        table.setRowHeight(44);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        // Action buttons column
        TableActionCell actionCell = new TableActionCell();
        table.getColumnModel().getColumn(5).setCellRenderer(actionCell);
        table.getColumnModel().getColumn(5).setCellEditor(actionCell);
        table.getColumnModel().getColumn(5).setPreferredWidth(160);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        card.add(header, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement()
                 .executeQuery("SELECT * FROM plant")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_plante"),
                    rs.getString("nom"),
                    rs.getString("espece"),
                    rs.getString("etat_sante"),
                    rs.getInt("besoin_eau"),
                    ""
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}