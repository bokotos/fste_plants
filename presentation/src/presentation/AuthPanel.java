package presentation;

import javax.swing.*;
import java.awt.*;
import app.MainFrame;
import dao.UserDAO;

public class AuthPanel extends JPanel {

    private MainFrame mainFrame;
    private Image logoIcon;

    public AuthPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        // 1. Charger les ressources en premier
        this.logoIcon = chargerImage("/rt/logo_icon.png");
        
        // 2. Initialiser l'UI
        setLayout(new GridLayout(1, 2));
        initUI();
    }

    private Image chargerImage(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Image introuvable : " + path);
            return null;
        }
    }

    private void initUI() {
        /* ---------- PANNEAU GAUCHE (FORMULAIRE) ---------- */
        JPanel leftPanel = new JPanel(null); // Layout null pour positionnement libre
        leftPanel.setBackground(Color.WHITE);

        // Groupe Logo
        if (logoIcon != null) {
            JLabel logoImg = new JLabel(new ImageIcon(
                    logoIcon.getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            ));
            logoImg.setBounds(60, 40, 32, 32);
            leftPanel.add(logoImg);
        }

        JLabel logoText = new JLabel("<html><font color='#000000'>PLANT</font><font color='#0AB20A'>S</font></html>");
        logoText.setFont(new Font("Arial", Font.BOLD, 28));
        logoText.setBounds(100, 35, 200, 40);
        leftPanel.add(logoText);

        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(new Color(34, 139, 34));
        title.setBounds(100, 150, 300, 45);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 230, 300, 45);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 300, 300, 45);
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));

        JButton loginBtn = new JButton("Se connecter");
        loginBtn.setBounds(170, 380, 160, 45);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setBackground(new Color(109, 209, 109));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(true);


        /* ---------- ACTION DU BOUTON LOGIN ---------- */
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Appel au DAO
            String userName = UserDAO.login(email, pass);

            if (userName != null) {
                // Effacer les champs pour la prochaine fois
                emailField.setText("");
                passwordField.setText("");
                
                // Redirection vers la vue complète du Dashboard
                // On utilise showContent car cela active le Dashboard_View (Menu + Centre)
                mainFrame.showContent(MainFrame.DASHBOARD); 
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        leftPanel.add(title);
        leftPanel.add(emailField);
        leftPanel.add(passwordField);
        leftPanel.add(loginBtn);

        /* ---------- PANNEAU DROIT (VISUEL) ---------- */
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(new Color(109, 209, 109));

        JLabel hello = new JLabel("Bonjour !");
        hello.setFont(new Font("Arial", Font.BOLD, 70));
        hello.setForeground(Color.WHITE);
        hello.setBounds(100, 250, 500, 80);

        JLabel infoText = new JLabel("<html>Veuillez saisir vos informations<br>d'administrateur pour continuer.</html>");
        infoText.setForeground(Color.WHITE);
        infoText.setFont(new Font("Arial", Font.PLAIN, 18));
        infoText.setBounds(100, 340, 500, 80);

        rightPanel.add(hello);
        rightPanel.add(infoText);

        add(leftPanel);
        add(rightPanel);
    }
}