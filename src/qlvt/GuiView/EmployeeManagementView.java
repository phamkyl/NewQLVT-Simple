package qlvt.GuiView;

import qlvt.connect.EmployeeDAO;
import qlvt.model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementView extends JPanel {
    private JTextField txtmaNhanVien;
    private JTextField txthoTen, txtmatKhau;
    private JTable employeeTable;
    private EmployeeDAO employeeDAO; // Data Access Object for employees


    public EmployeeManagementView(MainView_IM mainViewIm) throws SQLException {
        employeeDAO = new EmployeeDAO();
        setSize(800, 530);
        setVisible(true);
        initComponents();
    }

    private void initComponents() throws SQLException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout for the main JPanel

        // Initialize UI components
        txtmaNhanVien = new JTextField(20);
        txthoTen = new JTextField(20);

        txtmatKhau = new JTextField(20);

        // Create a JLabel for the table title
        JLabel titleLabel = new JLabel("DANH SÁCH NHÂN VIÊN ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(3, 166, 120));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        // Create a table qlvt.model for the employee list
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã nhân viên",  "Họ tên", "Mật khẩu"}, 0);
        employeeTable = new JTable(model);

        // Create a panel for the table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new LineBorder(Color.BLACK, 1));
        tablePanel.add(titleLabel, BorderLayout.NORTH);  // Add titleLabel to the top of tablePanel
        tablePanel.add(new JScrollPane(employeeTable), BorderLayout.CENTER); // Add table inside the scroll pane

        // Add MouseListener to table
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtmaNhanVien.setText(model.getValueAt(selectedRow, 0).toString());
                    txthoTen.setText(model.getValueAt(selectedRow, 1).toString());
                    txtmatKhau.setText(model.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        // Create buttons for actions
        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa ");
        JButton deleteButton = new JButton("Xóa");
        JButton removeButton = new JButton("Hoàn tác");

        // Change button colors
        for (JButton button : new JButton[]{addButton, editButton, deleteButton, removeButton}) {
            button.setBackground(new Color(3, 166, 120));
            button.setForeground(Color.WHITE);
            button.setSize(100, 60);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
        }

            // Enable buttons if maChiNhanh is not 0
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            removeButton.setEnabled(true);

            // Add action listeners to buttons
            addButton.addActionListener(e -> addEmployee());
            editButton.addActionListener(e -> editEmployee());
            deleteButton.addActionListener(e -> deleteEmployee());
            removeButton.addActionListener(e -> removeButton());




        // Create input panel for text fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 6, 6));
        inputPanel.add(new JLabel("  Mã nhân viên :"));
        inputPanel.add(txtmaNhanVien);
        inputPanel.add(new JLabel("  Họ tên :"));
        inputPanel.add(txthoTen);
        inputPanel.add(new JLabel("  Mật khẩu :"));
        inputPanel.add(txtmatKhau);
        JPanel buttonPanel = new JPanel();


        // Panel to hold the buttons

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(removeButton);



        // Create a panel to hold inputPanel, tablePanel, and buttonPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));  // Vertical layout for mainPanel
        mainPanel.add(inputPanel);  // Add input fields panel
        mainPanel.add(tablePanel);  // Add table panel (which already contains title and table)
        mainPanel.add(buttonPanel);  // Add buttons panel

        // Add the entire mainPanel to the parent panel
        add(mainPanel);

            loadEmployees(); // Initial load of employees



    }


    private void removeButton() {
        clearFields();
    }



    private void loadEmployees() {
        try {

            // Get the list of employees for the specified branch
            List<Employee> employees = employeeDAO.getAllEmployees();

            // Get the table qlvt.model from the JTable
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0);  // Clear existing rows in the table

            // Add rows to the table qlvt.model
            for (Employee employee : employees) {
                model.addRow(new Object[]{
                        employee.getMaNhanVien(),
                        employee.getHoTen(),
                        employee.getMatKhau()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        try {
            // Collect data from the input fields
            int maNhanVien = Integer.parseInt(txtmaNhanVien.getText());
            String hoTen = txthoTen.getText();
            String matKhau = txtmatKhau.getText();

            // Create an Employee object
            Employee employee = new Employee(maNhanVien, hoTen,matKhau);

            // Add the employee to the database
            employeeDAO.addEmployee(employee);

            // Reload the employee list
            loadEmployees();

            // Clear the input fields
            clearFields();
            JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editEmployee() {
        try {
            // Get the selected row from the table
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get employee ID from the selected row
            int maNhanVien = (int) employeeTable.getValueAt(selectedRow, 0);

            // Collect data from the input fields
            String hoTen = txthoTen.getText();



            String matKhau = txtmatKhau.getText();

            // Create an Employee object with updated data
            Employee employee = new Employee(maNhanVien, hoTen, matKhau);

            // Update the employee in the database
            employeeDAO.updateEmployee(employee);

            // Reload the employee list
            loadEmployees();

            // Clear the input fields
            clearFields();
            JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try {
            // Get the selected row from the table
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get employee ID from the selected row
            int maNhanVien = (int) employeeTable.getValueAt(selectedRow, 0);

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Delete the employee from the database
                employeeDAO.deleteEmployee(maNhanVien);

                // Reload the employee list
                loadEmployees();
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployeeTong() {
        try {
            // Lấy hàng được chọn từ bảng
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy mã nhân viên từ hàng được chọn
            int maNhanVien = (int) employeeTable.getValueAt(selectedRow, 0);

            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa nhân viên này không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                // Gọi phương thức DAO để xóa nhân viên trong cơ sở dữ liệu
                employeeDAO.deleteEmployeeTong(maNhanVien);

                // Tải lại danh sách nhân viên từ cơ sở dữ liệu
                loadEmployees();
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            // Xử lý lỗi khi xóa nhân viên
            JOptionPane.showMessageDialog(
                    this,
                    "Đã xảy ra lỗi khi xóa nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    private void clearFields() {
        txtmaNhanVien.setText("");
        txthoTen.setText("");
        txtmatKhau.setText("");
    }
}
