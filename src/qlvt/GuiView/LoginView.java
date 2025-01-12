package qlvt.GuiView;

import qlvt.Controller.LoginController;
import qlvt.model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Objects;

public class LoginView extends JFrame {
    private JTextField txtMaNhanVien;
    private JPasswordField txtMatKhau;

    private LoginController controller;

    public LoginView(LoginController controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        setTitle("Đăng Nhập");
        setSize(750, 484);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tạo các panel
        JPanel panel1 = createTitlePanel();
        JPanel panel2 = createInputPanel();
        JPanel panel3 = createImagePanel();
        JPanel panel4 = createButtonPanel();

        // Tạo layout chính
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(panel3, BorderLayout.WEST);  // Thêm ảnh vào bên trái
        centerPanel.add(panel2, BorderLayout.CENTER); // Thêm input vào bên phải

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel1, BorderLayout.NORTH); // Tiêu đề
        mainPanel.add(centerPanel, BorderLayout.CENTER); // Trung tâm
        mainPanel.add(panel4, BorderLayout.SOUTH); // Nút ở dưới

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(600, 60));

        JLabel titleLabel = new JLabel("QUẢN LÍ VẬT TƯ XÂY DỰNG TOÀN MINH");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(titleLabel);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Mã Nhân Viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mã nhân viên:"), gbc);

        txtMaNhanVien = new JTextField(20);
        gbc.gridx = 2;
        panel.add(txtMaNhanVien, gbc);



        // Mật Khẩu
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        txtMatKhau = new JPasswordField(20);
        gbc.gridx = 2;
        panel.add(txtMatKhau, gbc);

        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        ImageIcon originalImage = new ImageIcon("image/logo.jpg"); // Cập nhật đường dẫn ảnh
        Image image = originalImage.getImage().getScaledInstance(334, 267, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(image);

        JLabel imageLabel = new JLabel(scaledImage);
        panel.add(imageLabel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);

        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(34, 139, 34));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnCancel = new JButton("Thoát");
        btnCancel.setBackground(new Color(237, 28, 36));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));

        // Sự kiện đăng nhập
        btnLogin.addActionListener((ActionEvent e) -> {
            String maNhanVien = txtMaNhanVien.getText();
            String matKhau = new String(txtMatKhau.getPassword());
            //String maChiNhanh = Objects.requireNonNull(cbMaChiNhanh.getSelectedItem()).toString();

            // Gọi hàm login trong controller
            try {
                controller.login(maNhanVien, matKhau);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Sự kiện thoát
        btnCancel.addActionListener(e -> System.exit(0));

        panel.add(btnLogin);
        panel.add(btnCancel);

        return panel;
    }

    public void showLoginSuccess(Employee employee) {
        JOptionPane.showMessageDialog(this, "Đăng nhập thành công: " + employee.getHoTen());
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Set controller
    public void setController(LoginController controller) {
        this.controller = controller;
    }
}
