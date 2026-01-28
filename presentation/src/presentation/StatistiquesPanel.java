package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import dao.DBConnection;

public class StatistiquesPanel extends JPanel {

    public StatistiquesPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250)); // Gris très clair

        // --- 1. HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        header.setOpaque(false);
        JLabel title = new JLabel("ANALYTIQUES DU JARDIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- 2. GRILLE PRINCIPALE ---
        JPanel mainGrid = new JPanel(new GridLayout(2, 1, 0, 20));
        mainGrid.setOpaque(false);
        mainGrid.setBorder(new EmptyBorder(0, 40, 40, 40));

        // Ligne du haut : Cartes KPI (Nombre total, etc.)
        JPanel topRow = new JPanel(new GridLayout(1, 3, 20, 0));
        topRow.setOpaque(false);
        
        int total = obtenirCompte("SELECT COUNT(*) FROM plant");
        int critiques = obtenirCompte("SELECT COUNT(*) FROM plant WHERE etat_sante = 'CRITIQUE'");
        
        topRow.add(createKpiCard("TOTAL PLANTES", String.valueOf(total), new Color(109, 209, 109)));
        topRow.add(createKpiCard("ALERTES", String.valueOf(critiques), new Color(255, 107, 107)));
        topRow.add(createKpiCard("ARROSAGES RÉCENTS", "12", new Color(100, 149, 237)));

        // Ligne du bas : Graphiques miniatures (Répartition et Santé)
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomRow.setOpaque(false);
        
        bottomRow.add(createHealthSection(total, critiques));
        bottomRow.add(createCategorySection());

        mainGrid.add(topRow);
        mainGrid.add(bottomRow);

        add(mainGrid, BorderLayout.CENTER);
    }

    // --- MINI GRAPHIQUE 1 : ÉTAT DE SANTÉ (%) ---
    private JPanel createHealthSection(int total, int critiques) {
        JPanel panel = createBaseCard("ÉTAT DE SANTÉ GLOBAL");
        
        double pourcentageSante = (total == 0) ? 0 : ((double)(total - critiques) / total) * 100;
        
        JProgressBar bar = new JProgressBar();
        bar.setValue((int)pourcentageSante);
        bar.setStringPainted(true);
        bar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bar.setForeground(new Color(109, 209, 109));
        bar.setBackground(new Color(230, 230, 230));
        bar.setPreferredSize(new Dimension(300, 30));
        bar.setBorder(null);

        JLabel info = new JLabel(String.format("%.1f%% de vos plantes sont en bonne santé", pourcentageSante));
        info.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        panel.add(Box.createVerticalGlue());
        panel.add(bar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(info);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    // --- MINI GRAPHIQUE 2 : RÉPARTITION PAR TYPE ---
    private JPanel createCategorySection() {
        JPanel panel = createBaseCard("RÉPARTITION PAR CATÉGORIE");
        panel.setLayout(new GridLayout(4, 1, 0, 5));

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT c.nom_categorie, COUNT(p.id_plante) as nb " +
                                           "FROM categorie c LEFT JOIN plant p ON c.id_categorie = p.id_categorie " +
                                           "GROUP BY c.nom_categorie LIMIT 3")) {
            while (rs.next()) {
                String cat = rs.getString("nom_categorie");
                int count = rs.getInt("nb");
                JLabel lbl = new JLabel("• " + cat + " : " + count + " plantes");
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                panel.add(lbl);
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return panel;
    }

    // Utilitaires de Design
    private JPanel createKpiCard(String title, String value, Color color) {
        JPanel card = createBaseCard(title);
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 40));
        val.setForeground(color);
        val.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(val);
        return card;
    }

    private JPanel createBaseCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(235, 235, 235), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(Color.LIGHT_GRAY);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(t);
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private int obtenirCompte(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}