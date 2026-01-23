package app;

import javax.swing.*;
import java.awt.*;
import presentation.*;

public class MainFrame extends JFrame {

    public static final String ACCUEIL = "ACCUEIL";
    public static final String AUTH = "AUTH";
    public static final String DASHBOARD_VIEW = "DASHBOARD_VIEW";

    public static final String DASHBOARD = "DASHBOARD";
    public static final String PLANTES = "PLANTES";
    public static final String ARROSAGE = "ARROSAGE";
    public static final String STATS = "STATS";
    public static final String PARAMS = "PARAMS";

    private CardLayout cardLayout;
    private JPanel container;
    private CardLayout contentLayout;
    private JPanel contentContainer;

    public MainFrame() {
        setTitle("Gestion des Plantes");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
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
        
        // On ajoute les vraies classes ici
        contentContainer.add(new DashboardContent("Admin"), DASHBOARD);
        contentContainer.add(new JPanel(), PLANTES); // À remplacer par ton PlantePanel plus tard
        contentContainer.add(new JPanel(), ARROSAGE);
        contentContainer.add(new JPanel(), STATS);
        contentContainer.add(new JPanel(), PARAMS);

        dashboardPanel.add(nav, BorderLayout.WEST);
        dashboardPanel.add(contentContainer, BorderLayout.CENTER);

        container.add(dashboardPanel, DASHBOARD_VIEW);
    }

    public void showPanel(String panelName) {
        cardLayout.show(container, panelName);
    }

    public void showContent(String contentName) {
        contentLayout.show(contentContainer, contentName);
        cardLayout.show(container, DASHBOARD_VIEW);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}