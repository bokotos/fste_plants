package presentation;

import javax.swing.*;

import app.MainFrame;

import java.awt.*;

public class AccueilPanel extends JPanel {

    private MainFrame mainFrame;

    private Image background;
    private Image feuille;
    private Image plante;
    private Image logoIcon;

    public AccueilPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Chargement sécurisé des images depuis /t
        background = chargerImage("/rt/background.png");
        feuille    = chargerImage("/rt/feuille.png");
        plante     = chargerImage("/rt/plante.png");
        logoIcon   = chargerImage("/rt/logo_icon.png");

        setLayout(null); // placement libre
        initUI();
    }

    // ===== Chargement sécurisé des images =====
    private Image chargerImage(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Image introuvable : " + path);
            return null;
        }
    }

    // ===== Dessin des images (background + décor) =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // 1️⃣ Background
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        // 2️⃣ Feuille (gauche)
        if (feuille != null) {
            g.drawImage(feuille, 0, 180, 160, 240, this);
        }

        // 3️⃣ Plante (droite)
        if (plante != null) {
            int pWidth = 440;
            int pHeight = 520;
            int xPos = getWidth() - pWidth;
            int yPos = (getHeight() - pHeight) / 2 + 20;
            g.drawImage(plante, xPos, yPos, pWidth, pHeight, this);
        }
    }

    // ===== Interface (texte + bouton) =====
    private void initUI() {

        // ===== LOGO (image + texte PLANTS) =====
        JPanel logoPanel = new JPanel(null);
        logoPanel.setOpaque(false);
        logoPanel.setBounds(60, 35, 350, 60);

        if (logoIcon != null) {
            JLabel logoImg = new JLabel(new ImageIcon(
                    logoIcon.getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            ));
            logoImg.setBounds(0, 5, 32, 32);
            logoPanel.add(logoImg);
        }

        JLabel logoText = new JLabel(
                "<html><font color='#000000'>PLANT</font>"
              + "<font color='#0AB20A'>S</font></html>"
        );
        logoText.setFont(new Font("Arial", Font.BOLD, 28));
        logoText.setBounds(42, 0, 200, 40);
        logoPanel.add(logoText);

        // ===== SOUS-TITRE =====
        JLabel subtitle = new JLabel("Gestion intelligente des cultures");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitle.setForeground(new Color(80, 80, 80));
        subtitle.setBounds(42, 35, 260, 20);
        logoPanel.add(subtitle);

        add(logoPanel);

        // ===== TEXTE PRINCIPAL =====
        JLabel texteHero = new JLabel(
                "<html>Planter un jardin, c'est<br>"
              + "semblable à croire<br>"
              + "en demain</html>"
        );
        texteHero.setFont(new Font("Poppins", Font.BOLD, 50));
        texteHero.setForeground(new Color(40, 40, 40));
        texteHero.setBounds(250, 230, 650, 350);
        add(texteHero);

        // ===== BOUTON MODERNE =====
        JButton btnCommencer = new JButton("Commencer") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };

        btnCommencer.setBounds(250, 600, 170, 48);
        btnCommencer.setBackground(new Color(34, 139, 34));
        btnCommencer.setForeground(Color.WHITE);
        btnCommencer.setFont(new Font("Arial", Font.BOLD, 15));
        btnCommencer.setFocusPainted(false);
        btnCommencer.setBorder(BorderFactory.createEmptyBorder());
        btnCommencer.setContentAreaFilled(false);
        btnCommencer.setOpaque(false);

        btnCommencer.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.showPanel(MainFrame.AUTH);
            }
        });

        add(btnCommencer);
    }
}
