package presentation;

import dao.PlantDAO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableActionCell extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor {

    private JPanel panel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JTable table;

    public TableActionCell() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        panel.setOpaque(true);

        btnEdit = createButton("Modifier", new Color(52, 152, 219));
        btnDelete = createButton("Supprimer", new Color(231, 76, 60));
        
        btnEdit.setOpaque(true);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(true);
        
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(true);

        panel.add(btnEdit);
        panel.add(btnDelete);

        btnDelete.addActionListener(e -> deletePlant());
        btnEdit.addActionListener(e -> editPlant());
    }

    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return b;
    }

    private void deletePlant() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) table.getValueAt(row, 0);
        stopCellEditing();

        if (JOptionPane.showConfirmDialog(
                table, "Supprimer cette plante ?",
                "Confirmation", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {

            if (PlantDAO.supprimerParId(id)) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
            }
        }
    }

    private void editPlant() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) table.getValueAt(row, 0);
        stopCellEditing();

        new UpdatePlantDialog(
            (Frame) SwingUtilities.getWindowAncestor(table), id
        ).setVisible(true);

        ((PlantePanel) SwingUtilities
            .getAncestorOfClass(PlantePanel.class, table))
            .refreshTable();
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int col) {
        panel.setBackground(isSelected
                ? table.getSelectionBackground()
                : Color.WHITE);
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected,
            int row, int col) {
        this.table = table;
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() { return null; }
}
