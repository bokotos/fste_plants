package presentation;

import javax.swing.*;
import java.awt.*;
import app.MainFrame;

public class NavigationPanel extends JPanel {
    private MainFrame mainFrame;
    private JButton activeButton;

    private final Color DEFAULT_BG = new Color(34, 45, 50);
    private final Color ACTIVE_BG = new Color(27, 160, 133); // green/teal highlight
    private final Color DEFAULT_TEXT = new Color(200, 200, 200);
    private final Color ACTIVE_TEXT = Color.WHITE;


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

        // Style du bouton (normal)
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setPreferredSize(new Dimension(250, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(DEFAULT_TEXT);
        btn.setBackground(DEFAULT_BG);

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 25, 0, 0));

        // ACTION
        btn.addActionListener(e -> {

            // 🔄 Reset previous active button
            if (activeButton != null) {
                activeButton.setBackground(DEFAULT_BG);
                activeButton.setForeground(DEFAULT_TEXT);
            }

            // ⭐ Set current as active
            btn.setBackground(ACTIVE_BG);
            btn.setForeground(ACTIVE_TEXT);
            activeButton = btn;

            // 🔁 Switch content
            mainFrame.showContent(contentName);
        });

        container.add(btn);
        container.add(Box.createVerticalStrut(2));

        // 👉 Optional: set DASHBOARD active by default
        if (activeButton == null && contentName.equals(MainFrame.DASHBOARD)) {
            btn.setBackground(ACTIVE_BG);
            btn.setForeground(ACTIVE_TEXT);
            activeButton = btn;
        }
    }

}