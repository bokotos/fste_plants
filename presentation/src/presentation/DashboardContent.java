package presentation;

import javax.swing.*;
import java.awt.*;

public class DashboardContent extends JPanel {
    public DashboardContent(String userName) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // HEADER
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(new Color(225, 245, 225));
        header.setPreferredSize(new Dimension(0, 120));
        JLabel lblLogo = new JLabel("<html><font color='#333333'>PLANT</font><font color='#6dd16d'>S</font></html>");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 52));
        header.add(lblLogo);

        // CORPS
        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel welcome = new JLabel("Hello, " + userName + " !");
        welcome.setFont(new Font("Segoe UI Semilight", Font.ITALIC, 32));
        
        JButton btnAdd = new JButton("NOUVELLE PLANTE");
        btnAdd.setPreferredSize(new Dimension(400, 80));
        btnAdd.setBackground(new Color(109, 209, 109));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createEmptyBorder());

        // --- ACTION : OUVRIR LE FORMULAIRE ---
        btnAdd.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            new AddPlantDialog((Frame)parent).setVisible(true);
        });

        gbc.gridy = 0; body.add(welcome, gbc);
        gbc.gridy = 1; gbc.insets = new Insets(30,0,0,0); body.add(btnAdd, gbc);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }
}