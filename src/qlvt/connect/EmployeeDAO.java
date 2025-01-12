package qlvt.connect;

import qlvt.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private DistributedDatabaseConnection dbConnection;

    public EmployeeDAO() {
        dbConnection = new DistributedDatabaseConnection();
    }

    // Phương thức để tìm nhân viên theo mã nhân viên và mật khẩu
    public Employee getEmployeeByCredentials(String maNhanVien, String matKhau) throws SQLException {
        String query = "SELECT * FROM NhanVien WHERE MaNhanVien = ? AND MatKhau = ?  ";
        Employee employee = null;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, maNhanVien);
            stmt.setString(2, matKhau);


            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("MatKhau")
                );
            }
        }
        return employee; // Trả về đối tượng Employee hoặc null nếu không tìm thấy
    }



    // Lấy danh sách tất cả nhân viên dựa trên mã chi nhánh
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM NhanVien "; // Lọc theo mã chi nhánh


        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("MatKhau")
                ));
            }
        }
        return employees; // Trả về danh sách nhân viên
    }




    // Thêm một nhân viên mới
    public void addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau) VALUES (?, ?, ?)";


        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employee.getMaNhanVien());
            preparedStatement.setString(2, employee.getHoTen());
            preparedStatement.setString(3, employee.getMatKhau());
            preparedStatement.executeUpdate(); // Thực hiện chèn dữ liệu
        }
    }

    // Cập nhật một nhân viên đã tồn tại
    public void updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE NhanVien SET HoTen = ?, MatKhau = ? WHERE MaNhanVien = ? ";


        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getHoTen());

            preparedStatement.setString(2, employee.getMatKhau());
            preparedStatement.setInt(3, employee.getMaNhanVien());

            preparedStatement.executeUpdate(); // Thực hiện cập nhật
        }
    }

    // Xóa một nhân viên
    public void deleteEmployee(int maNhanVien) throws SQLException {
        String query = "DELETE FROM NhanVien WHERE MaNhanVien = ? ";


        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maNhanVien);

            preparedStatement.executeUpdate(); // Thực hiện xóa
        }
    }

    // Xóa một nhân viên
    public void deleteEmployeeTong(int maNhanVien) throws SQLException {
        String query = "DELETE FROM NhanVien WHERE MaNhanVien = ? ";


        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, maNhanVien);

            preparedStatement.executeUpdate(); // Thực hiện xóa
        }
    }


}
