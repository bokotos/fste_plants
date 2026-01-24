package presentation;

import dao.PlantDAO;
import dao.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdatePlantDialog extends JDialog {
    private JTextField txtNom, txtEspece, txtEau;
    private JComboBox<String> cbSante;
    private int plantId;

    public UpdatePlantDialog(Frame parent, int id) {
        super(parent, "Modifier la Plante", true);
        this.plantId = id;
        
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panel de formulaire avec espacement
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 20));
        form.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        txtNom = new JTextField();
        txtEspece = new JTextField();
        txtEau = new JTextField();
        cbSante = new JComboBox<>(new String[]{"BON", "MOYEN", "CRITIQUE"});

        // Chargement des données actuelles
        chargerDonneesPlante();

        form.add(new JLabel("Nom :")); form.add(txtNom);
        form.add(new JLabel("Espèce :")); form.add(txtEspece);
        form.add(new JLabel("Santé :")); form.add(cbSante);
        form.add(new JLabel("Besoin Eau (%) :")); form.add(txtEau);

        // Bouton de validation
        JButton btnSave = new JButton("ENREGISTRER LES MODIFICATIONS");
        btnSave.setBackground(new Color(100, 149, 237)); // Bleu
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        btnSave.addActionListener(e -> {
            if(validerEtSauvegarder()) {
                dispose();
            }
        });

        add(form, BorderLayout.CENTER);
        add(btnSave, BorderLayout.SOUTH);
    }

    private void chargerDonneesPlante() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM plant WHERE id_plant = ?")) {
            ps.setInt(1, plantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtNom.setText(rs.getString("nom"));
                txtEspece.setText(rs.getString("espece"));
                txtEau.setText(String.valueOf(rs.getInt("besoin_eau")));
                cbSante.setSelectedItem(rs.getString("etat_sante"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private boolean validerEtSauvegarder() {
        try {
            String nom = txtNom.getText();
            String espece = txtEspece.getText();
            String sante = cbSante.getSelectedItem().toString();
            int eau = Integer.parseInt(txtEau.getText());

            if (PlantDAO.updatePlant(plantId, nom, espece, sante, eau)) {
                JOptionPane.showMessageDialog(this, "Plante mise à jour avec succès !");
                return true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide pour l'eau.");
        }
        return false;
    }
}