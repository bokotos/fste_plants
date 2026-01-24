package presentation;

import javax.swing.*;
import java.awt.*;
import app.MainFrame;

public class NavigationPanel extends JPanel {
    private MainFrame mainFrame;

    public NavigationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // Configuration du panneau latéral
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(34, 45, 50)); // Couleur sombre d'origine
        setLayout(new BorderLayout());

        // --- TITRE DU MENU ---
        JLabel lblMenu = new JLabel("MENU", SwingConstants.LEFT);
        lblMenu.setForeground(new Color(109, 209, 109)); // Vert clair
        lblMenu.setFont(new Font("Arial", Font.BOLD, 14));
        lblMenu.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 0));
        add(lblMenu, BorderLayout.NORTH);

        // --- CONTENEUR DES BOUTONS ---
        JPanel buttonContainer = new JPanel();
        buttonContainer.setOpaque(false);
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));

        // Ajout des boutons (Sans Statistiques)
        addMenuButton(buttonContainer, "DASHBOARD", MainFrame.DASHBOARD);
        addMenuButton(buttonContainer, "PLANTES", MainFrame.PLANTES);
        addMenuButton(buttonContainer, "ARROSAGE", MainFrame.ARROSAGE);
        addMenuButton(buttonContainer, "PARAMETRES", MainFrame.PARAMS);

        add(buttonContainer, BorderLayout.CENTER);
    }

    /**
     * Crée un bouton stylisé sans effet de survol (Hover)
     */
    private void addMenuButton(JPanel container, String text, String contentName) {
        JButton btn = new JButton(text);
        
        // Style du bouton
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setPreferredSize(new Dimension(250, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(200, 200, 200)); // Gris clair/Blanc
        btn.setBackground(new Color(34, 45, 50)); // Fond fixe identique au panel
        
        // Retrait des bordures et effets système
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true); // Garde le fond opaque
        btn.setOpaque(true);
        
        // Alignement du texte
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 25, 0, 0));

        // Action : Switch de panel dans le MainFrame
        btn.addActionListener(e -> mainFrame.showContent(contentName));

        container.add(btn);
        container.add(Box.createVerticalStrut(2)); // Petit espace entre les boutons
    }
}