package qlvt.GuiView;

import qlvt.connect.PhieuXuatDAO;
import qlvt.connect.PurchaseOrderDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainView_IM extends JFrame {
    private String userName;
    private JPanel panel;

    // Constructor to initialize MainView with user information
    public MainView_IM(String userName) {
        this.userName = userName;
        initialize(); // Call the method to initialize the UI
    }

    public MainView_IM() {
        initialize();
    }

    private void initialize() {
        setTitle("Main Interface");
        setSize(1280, 720); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Left Panel: Contains personal information and function buttons
        JPanel leftPanel = createLeftPanel();

        // Right Panel: Shows detailed views for selected functions
        JPanel rightPanel = createRightPanel();

        // Split the screen using JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); // Set the divider position
        splitPane.setDividerSize(0); // Divider thickness
        splitPane.setOneTouchExpandable(false); // Disable the expand/collapse button

        // Add the splitPane to the main window
        add(splitPane);
    }

    // Create the left panel with personal information and functional buttons
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        // Options panel with buttons for different functionalities
        JPanel optionsPanel = createOptionsPanel();

        // Add personal info and options panels to the left panel
        leftPanel.add(optionsPanel, BorderLayout.CENTER);

        return leftPanel;
    }

    // Create the right panel to display detailed views for selected functions
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel defaultLabel = new JLabel("Select a function from the left to display details", JLabel.CENTER);
        defaultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rightPanel.add(defaultLabel, BorderLayout.CENTER);

        return rightPanel;
    }

    // Create a panel containing buttons for different options
    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Map icons with corresponding functions
        Map<String, String> iconPaths = new HashMap<>();
        iconPaths.put("Employee Management", "image/team.png");
        iconPaths.put("Material Management", "image/supplies.png");
        iconPaths.put("Supplier Management", "image/manufacture.png");
        iconPaths.put("Import/Export Management", "image/export.png");

        // Add buttons for each functionality
        for (String option : iconPaths.keySet()) {
            optionsPanel.add(createOptionButton(option, iconPaths));
        }

        return optionsPanel;
    }

    // Create a button for a given option with an associated icon
    private JButton createOptionButton(String option, Map<String, String> iconPaths) {
        JButton button = new JButton(option);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(3, 166, 120));
        button.setForeground(Color.WHITE);

        // Set icon if available
        if (iconPaths.containsKey(option)) {
            String iconPath = iconPaths.get(option);
            try {
                ImageIcon icon = new ImageIcon(iconPath);
                Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledIcon));
            } catch (Exception e) {
                System.err.println("Failed to load icon for function: " + option);
            }
        }

        // Add action listener to handle button clicks
        button.addActionListener(e -> {
            try {
                onOptionButtonClick(option);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        return button;
    }

    // Handle button click based on the selected option
    private void onOptionButtonClick(String option) throws SQLException {
        JPanel rightPanel = (JPanel) ((JSplitPane) getContentPane().getComponent(0)).getRightComponent();
        rightPanel.removeAll(); // Clear the current view in the right panel

        switch (option) {
            case "Employee Management":
                openEmployeeManagementView(rightPanel);
                break;
            case "Material Management":
                openMaterialManagementView(rightPanel);
                break;
            case "Supplier Management":
                openSupplierManagementView(rightPanel);
                break;
            case "Import/Export Management":
                OpenImportExportView(rightPanel);
                break;


            default:
                JLabel functionLabel = new JLabel("Function: " + option, JLabel.CENTER);
                functionLabel.setFont(new Font("Arial", Font.BOLD, 18));
                rightPanel.add(functionLabel, BorderLayout.CENTER);
                break;
        }

        rightPanel.revalidate(); // Refresh the right panel
        rightPanel.repaint();
    }







    // Open employee management view
    private void openEmployeeManagementView(JPanel rightPanel) throws SQLException {
        EmployeeManagementView employeeManagementView = new EmployeeManagementView(this);
        JScrollPane scrollPane = new JScrollPane(employeeManagementView);
        scrollPane.setPreferredSize(new Dimension(rightPanel.getWidth(), 600));
        updateRightPanelWithView(rightPanel, scrollPane);
    }

    // Open material management view
    private void openMaterialManagementView(JPanel rightPanel) {
        MaterialManagementView materialManagementView = new MaterialManagementView(this);
        JScrollPane scrollPane = new JScrollPane(materialManagementView);
        scrollPane.setPreferredSize(new Dimension(rightPanel.getWidth(), 700));
        updateRightPanelWithView(rightPanel, scrollPane);
    }

    private void OpenImportExportView(JPanel rightPanel) {
        // Tạo ImportExportView, truyền đúng đối tượng cần thiết
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();  // Khởi tạo DAO
        PhieuXuatDAO phieuXuatDAO = new PhieuXuatDAO();  // Khởi tạo DAO
        ImportExportView importExportView = new ImportExportView(this, purchaseOrderDAO, phieuXuatDAO);

        // Đặt ImportExportView vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(importExportView);

        // Đặt kích thước cho JScrollPane (có thể thay đổi theo nhu cầu)
        scrollPane.setPreferredSize(new Dimension(rightPanel.getWidth(), 600));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Hiển thị thanh cuộn dọc nếu cần

        // Đảm bảo rằng rightPanel sẽ được cập nhật với JScrollPane mới
        updateRightPanelWithView(rightPanel, scrollPane);
    }
    // Open supplier management view
    private void openSupplierManagementView(JPanel rightPanel) {
        SupplierManagementView supplierManagementView = new SupplierManagementView(this);
        JScrollPane scrollPane = new JScrollPane(supplierManagementView);
        scrollPane.setPreferredSize(new Dimension(rightPanel.getWidth(), 600));
        updateRightPanelWithView(rightPanel, scrollPane);
    }

    // Update the right panel with the selected view
    private void updateRightPanelWithView(JPanel rightPanel, JScrollPane scrollPane) {
        rightPanel.removeAll();
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    // Update the right panel with the selected view
    private void updateRightPanelWithView(JPanel rightPanel, JComponent component) {
        rightPanel.removeAll();
        rightPanel.add(component, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
