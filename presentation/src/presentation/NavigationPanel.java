package presentation;

import javax.swing.*;
import java.awt.*;
import app.MainFrame;

public class NavigationPanel extends JPanel {
    private MainFrame mainFrame;

    public NavigationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(new Color(34, 45, 50));
        setPreferredSize(new Dimension(220, 0));

        JLabel menuTitle = new JLabel("MENU");
        menuTitle.setForeground(new Color(109, 209, 109));
        menuTitle.setFont(new Font("Arial", Font.BOLD, 14));
        menuTitle.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 0));
        add(menuTitle, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(8, 1));
        buttonsPanel.setOpaque(false);

        addMenuButton(buttonsPanel, "DASHBOARD", MainFrame.DASHBOARD);
        addMenuButton(buttonsPanel, "PLANTES", MainFrame.PLANTES);
        addMenuButton(buttonsPanel, "ARROSAGE", MainFrame.ARROSAGE);
        addMenuButton(buttonsPanel, "STATISTIQUES", MainFrame.STATS);
        addMenuButton(buttonsPanel, "PARAMETRES", MainFrame.PARAMS);

        add(buttonsPanel, BorderLayout.CENTER);
    }

    private void addMenuButton(JPanel container, String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(34, 45, 50));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(10, 25, 10, 0));
        
        btn.addActionListener(e -> mainFrame.showContent(panelName));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(44, 59, 65)); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(new Color(34, 45, 50)); }
        });

        container.add(btn);
    }
}