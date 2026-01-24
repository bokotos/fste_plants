package app;

import javax.swing.*;
import java.awt.*;
import presentation.*;

public class MainFrame extends JFrame {
    public static final String ACCUEIL = "ACCUEIL", AUTH = "AUTH", DASHBOARD_VIEW = "DASHBOARD_VIEW";
    public static final String DASHBOARD = "DASHBOARD", PLANTES = "PLANTES", ARROSAGE = "ARROSAGE", PARAMS = "PARAMS";

    private CardLayout cardLayout, contentLayout;
    private JPanel container, contentContainer;

    public MainFrame() {
        setTitle("EcoPlanter - Gestion de Jardin");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(new AccueilPanel(this), ACCUEIL);
        container.add(new AuthPanel(this), AUTH);
        initDashboardView();

        add(container);
        showPanel(ACCUEIL);
    }

    private void initDashboardView() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        NavigationPanel nav = new NavigationPanel(this);
        
        contentLayout = new CardLayout();
        contentContainer = new JPanel(contentLayout);
        
        contentContainer.add(new DashboardContent(), DASHBOARD);
        contentContainer.add(new PlantePanel(), PLANTES);
        contentContainer.add(new JPanel(), ARROSAGE);
        contentContainer.add(new JPanel(), PARAMS);

        dashboardPanel.add(nav, BorderLayout.WEST);
        dashboardPanel.add(contentContainer, BorderLayout.CENTER);
        container.add(dashboardPanel, DASHBOARD_VIEW);
    }

    public void showPanel(String name) { cardLayout.show(container, name); }
    
    public void showContent(String name) {
        if (name.equals(DASHBOARD)) contentContainer.add(new DashboardContent(), DASHBOARD);
        contentLayout.show(contentContainer, name);
        showPanel(DASHBOARD_VIEW);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}