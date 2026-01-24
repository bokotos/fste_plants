package presentation;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import dao.PlantDAO;
import java.time.LocalDate;

/**
 * Formulaire moderne pour ajouter une plante.
 * Connecté à PlantDAO.ajouter()
 */
public class AddPlantDialog extends JDialog {
    private JTextField txtNom, txtEspece, txtEau;
    private JComboBox<String> comboEtat, comboCategorie;

    public AddPlantDialog(Frame parent) {
        super(parent, "Nouvelle Plante", true);
        setSize(450, 600);
        setLocationRelativeTo(parent);
        setUndecorated(true); // Look moderne sans barre de titre Windows

        // Panneau principal avec bordure subtile
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));

        // --- 1. HEADER ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        header.setBackground(new Color(245, 252, 245));
        JLabel title = new JLabel("NOUVELLE PLANTE");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(50, 50, 50));
        header.add(title);

        // --- 2. CORPS DU FORMULAIRE (GRILLE) ---
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Initialisation des champs
        txtNom = createStyledField();
        txtEspece = createStyledField();
        txtEau = createStyledField();
        txtEau.setText("50"); // Valeur par défaut
        
        String[] etats = {"BON", "MOYEN", "CRITIQUE"};
        comboEtat = new JComboBox<>(etats);
        
        // Simuler des catégories (ID 1: Fleurs, 2: Arbres, etc.)
        String[] cats = {"1 - Fleurs", "2 - Arbustes", "3 - Intérieur"};
        comboCategorie = new JComboBox<>(cats);

        // Ajout des composants au layout
        addFormField(body, "NOM DE LA PLANTE", txtNom, gbc, 0);
        addFormField(body, "ESPÈCE", txtEspece, gbc, 2);
        addFormField(body, "BESOIN EN EAU (%)", txtEau, gbc, 4);
        
        gbc.gridy = 6; body.add(new JLabel("ÉTAT DE SANTÉ"), gbc);
        gbc.gridy = 7; body.add(comboEtat, gbc);
        
        gbc.gridy = 8; gbc.insets = new Insets(10,0,5,0); body.add(new JLabel("CATÉGORIE"), gbc);
        gbc.gridy = 9; body.add(comboCategorie, gbc);

        // --- 3. BOUTONS D'ACTION ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        footer.setBackground(Color.WHITE);

        JButton btnCancel = new JButton("ANNULER");
        styleButton(btnCancel, new Color(220, 220, 220), Color.BLACK);
        btnCancel.addActionListener(e -> dispose());

        JButton btnSave = new JButton("ENREGISTRER");
        styleButton(btnSave, new Color(109, 209, 109), Color.WHITE);
        
        // LOGIQUE D'INSERTION
        btnSave.addActionListener(e -> {
            try {
                String nom = txtNom.getText().trim();
                String espece = txtEspece.getText().trim();
                String etat = (String) comboEtat.getSelectedItem();
                int eau = Integer.parseInt(txtEau.getText().trim());
                String date = LocalDate.now().toString();
                
                // Extraire l'ID de la catégorie (le premier caractère de "1 - Fleurs")
                int idCat = Character.getNumericValue(comboCategorie.getSelectedItem().toString().charAt(0));

                if (nom.isEmpty() || espece.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir les champs obligatoires.");
                    return;
                }

                // APPEL À VOTRE MÉTHODE PlantDAO.ajouter
                boolean succes = PlantDAO.ajouter(nom, espece, etat, eau, date, idCat);

                if (succes) {
                    JOptionPane.showMessageDialog(this, "La plante '" + nom + "' a été ajoutée avec succès ! 🌱");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion en base de données.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Le besoin en eau doit être un nombre.");
            }
        });

        footer.add(btnCancel);
        footer.add(btnSave);

        container.add(header, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);
        container.add(footer, BorderLayout.SOUTH);
        add(container);
    }

    private void addFormField(JPanel p, String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(Color.GRAY);
        p.add(lbl, gbc);
        gbc.gridy = row + 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        p.add(field, gbc);
    }

    private JTextField createStyledField() {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(0, 35));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(new MatteBorder(0, 0, 2, 0, new Color(235, 235, 235)));
        return f;
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setPreferredSize(new Dimension(130, 40));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}