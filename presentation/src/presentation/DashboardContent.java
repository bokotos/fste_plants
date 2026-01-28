package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import dao.DBConnection;

public class DashboardContent extends JPanel {

    public DashboardContent() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // --- HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        header.setOpaque(false);
        JLabel title = new JLabel("ANALYTIQUES DU JARDIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // --- GRILLE DES STATISTIQUES ---
        JPanel mainGrid = new JPanel(new GridLayout(2, 1, 0, 20));
        mainGrid.setOpaque(false);
        mainGrid.setBorder(new EmptyBorder(0, 40, 40, 40));

        // Données SQL
        int total = obtenirCompte("SELECT COUNT(*) FROM plant");
        int critiques = obtenirCompte("SELECT COUNT(*) FROM plant WHERE etat_sante = 'CRITIQUE'");

        // Ligne 1 : Les 3 Cartes KPI (Total, Alertes, Arrosages)
        JPanel topRow = new JPanel(new GridLayout(1, 3, 20, 0));
        topRow.setOpaque(false);
        topRow.add(createKpiCard("TOTAL PLANTES", String.valueOf(total), new Color(109, 209, 109)));
        topRow.add(createKpiCard("ALERTES", String.valueOf(critiques), new Color(255, 107, 107)));
        topRow.add(createKpiCard("ARROSAGES RÉCENTS", "12", new Color(100, 149, 237)));

        // Ligne 2 : Santé (%) et Répartition par Catégorie
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomRow.setOpaque(false);
        bottomRow.add(createHealthCard(total, critiques));
        bottomRow.add(createCategoryCard());

        mainGrid.add(topRow);
        mainGrid.add(bottomRow);
        add(mainGrid, BorderLayout.CENTER);
    }

    private JPanel createHealthCard(int total, int critiques) {
        JPanel p = createBaseCard("ÉTAT DE SANTÉ GLOBAL");
        double score = (total == 0) ? 0 : ((double)(total - critiques) / total) * 100;
        
        JProgressBar bar = new JProgressBar();
        bar.setValue((int)score);
        bar.setStringPainted(true);
        bar.setForeground(new Color(109, 209, 109));
        bar.setPreferredSize(new Dimension(300, 35));

        JLabel info = new JLabel(String.format("%.1f%% de vos plantes sont en bonne santé", score));
        info.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.add(Box.createVerticalGlue());
        p.add(bar);
        p.add(Box.createVerticalStrut(10));
        p.add(info);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JPanel createCategoryCard() {
        JPanel p = createBaseCard("RÉPARTITION PAR CATÉGORIE");
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT c.nom_categorie, COUNT(p.id_plante) FROM categorie c LEFT JOIN plant p ON c.id_categorie = p.id_categorie GROUP BY c.nom_categorie LIMIT 4")) {
            p.add(Box.createVerticalGlue());
            while(rs.next()) {
                JLabel l = new JLabel(" ● " + rs.getString(1) + " : " + rs.getInt(2));
                l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                l.setAlignmentX(Component.LEFT_ALIGNMENT);
                p.add(l);
                p.add(Box.createVerticalStrut(8));
            }
            p.add(Box.createVerticalGlue());
        } catch(Exception e) {}
        return p;
    }

    private JPanel createKpiCard(String title, String value, Color color) {
        JPanel p = createBaseCard(title);
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 50));
        v.setForeground(color);
        v.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(v);
        return p;
    }

    private JPanel createBaseCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(230,230,230)), BorderFactory.createEmptyBorder(15,20,15,20)));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(Color.LIGHT_GRAY);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(t);
        return p;
    }

    private int obtenirCompte(String sql) {
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {}
        return 0;
    }
}