package presentation;

import dao.PlantDAO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableActionCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel panel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JTable table;

    public TableActionCell() {
        // Panel qui contient les boutons
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(true); // Indispensable pour voir la couleur de fond

        btnEdit = new JButton("Modifier");
        btnDelete = new JButton("Supprimer");

        // --- STYLE BOUTON MODIFIER (Bleu) ---
        btnEdit.setBackground(new Color(100, 149, 237));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // --- STYLE BOUTON SUPPRIMER (Rouge) ---
        btnDelete.setBackground(new Color(255, 107, 107));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(btnEdit);
        panel.add(btnDelete);

        // --- LOGIQUE SUPPRESSION ---
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                stopCellEditing(); // Important pour libérer le tableau

                int confirm = JOptionPane.showConfirmDialog(table, "Supprimer cette plante ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (PlantDAO.supprimerParId(id)) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                }
            }
        });

        // --- LOGIQUE MODIFICATION ---
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                stopCellEditing();
                new UpdatePlantDialog((Frame) SwingUtilities.getWindowAncestor(table), id).setVisible(true);
                // Rafraîchir après modification
                refreshParentTable();
            }
        });
    }

    private void refreshParentTable() {
        Container parent = table.getParent();
        while (parent != null && !(parent instanceof PlantePanel)) parent = parent.getParent();
        if (parent instanceof PlantePanel) ((PlantePanel) parent).refreshTable();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Assigne la couleur de fond de la ligne au panel des boutons
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() { return null; }
}