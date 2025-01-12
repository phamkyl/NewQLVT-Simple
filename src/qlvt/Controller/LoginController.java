package qlvt.Controller;

import qlvt.GuiView.LoginView;
import qlvt.GuiView.MainView_IM;
import qlvt.connect.EmployeeDAO;
import qlvt.model.Employee;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private LoginView view;
    private EmployeeDAO employeeDAO;

    public LoginController(LoginView view) {
        this.view = view;
        employeeDAO = new EmployeeDAO();  // Kết nối với cơ sở dữ liệu
    }

    public void login(String maNhanVien, String matKhau) throws SQLException {
        // Kiểm tra nếu mã nhân viên hoặc mật khẩu bị bỏ trống
        if (maNhanVien.isEmpty() || matKhau.isEmpty()) {
            view.showError("Vui lòng nhập mã nhân viên và mật khẩu.");
            return;
        }

        // Kiểm tra thông tin đăng nhập
        Employee employee = employeeDAO.getEmployeeByCredentials(maNhanVien, matKhau);

        if (employee != null) {
            // Nếu đăng nhập thành công, ẩn LoginView và hiển thị MainView_IM
            view.setVisible(false); // Ẩn LoginView
            MainView_IM mainView = new MainView_IM(); // Khởi tạo MainView_IM
            mainView.setVisible(true); // Hiển thị MainView_IM
        } else {
            // Nếu đăng nhập thất bại
            view.showError("Mã nhân viên hoặc mật khẩu không chính xác.");
        }
    }
}
