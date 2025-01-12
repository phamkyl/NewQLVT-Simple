package qlvt.GuiView;


import qlvt.connect.PhieuXuatDAO;
import qlvt.model.ChiTietPhieuXuat;
import qlvt.model.PhieuXuat;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuXuatGUI extends JDialog {
    private JTextField txtMaPhieuXuat, txtMaNhanVien;
    private JFormattedTextField txtNgayXuat;
    private JButton btnSave, btnDelete, btnUpdate, btnClose;
    private JTable tablePhieuXuat, detailTable;
    private DefaultTableModel tableModel, detailTableModel;
    private PhieuXuatDAO phieuXuatDAO;

    public PhieuXuatGUI(ImportExportView parent) {
        phieuXuatDAO = new PhieuXuatDAO();

        initUI();
        loadPhieuXuats();
        setupTableSelectionListener();
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }

    private void setupTableSelectionListener() {
        tablePhieuXuat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tablePhieuXuat.getSelectedRow();
                if (selectedRow != -1) {
                    // Populate text fields with the selected row's data
                    txtMaPhieuXuat.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtNgayXuat.setValue(tableModel.getValueAt(selectedRow, 1));
                    txtMaNhanVien.setText(tableModel.getValueAt(selectedRow, 2).toString());


                    loadChiTietPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
                }
            }
        });
    }

    private void loadChiTietPhieuXuat(int maPhieuXuat) {
        detailTableModel.setRowCount(0);
        List<ChiTietPhieuXuat> details = phieuXuatDAO.getChiTietPhieuXuatByMaPhieuXuat(maPhieuXuat);
        for (ChiTietPhieuXuat detail : details) {
            detailTableModel.addRow(new Object[]{
                    detail.getMaVatTu(),
                    detail.getSoLuong(),
                    detail.getGia()
            });
        }
    }

    private void initUI() {
        txtMaPhieuXuat = new JTextField(15);
        txtMaNhanVien = new JTextField(15);

        txtNgayXuat = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        txtNgayXuat.setValue(new Date(System.currentTimeMillis()));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Mã Phiếu Xuất:"));
        inputPanel.add(txtMaPhieuXuat);
        inputPanel.add(new JLabel("Ngày Xuất:"));
        inputPanel.add(txtNgayXuat);
        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        inputPanel.add(txtMaNhanVien);

        btnSave = new JButton("Thêm");
        btnUpdate = new JButton("Cập Nhật");
        btnDelete = new JButton("Xóa");
        btnClose = new JButton("Đóng");


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);


        btnSave.addActionListener(e -> addPhieuXuat());
        btnUpdate.addActionListener(e -> updatePhieuXuat());
        btnDelete.addActionListener(e -> deletePhieuXuat());
        btnClose.addActionListener(e -> dispose());


        tableModel = new DefaultTableModel(new Object[]{"Mã Phiếu Xuất", "Ngày Xuất", "Mã Nhân Viên"}, 0);
        tablePhieuXuat = new JTable(tableModel);

        detailTableModel = new DefaultTableModel(new Object[]{"Mã Vật Tư", "Số Lượng", "Giá Xuất"}, 0);
        detailTable = new JTable(detailTableModel);

        // Panel for detail management
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);

        JButton btnAddDetail = new JButton("Thêm Chi Tiết");
        JButton btnUpdateDetail = new JButton("Cập Nhật Chi Tiết");
        JButton btnDeleteDetail = new JButton("Xóa Chi Tiết");

        for (JButton button : new JButton[]{btnSave, btnUpdate, btnDelete, btnClose, btnAddDetail, btnUpdateDetail, btnDeleteDetail}) {
            button.setBackground(new Color(3, 166, 120));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
        }

        JPanel detailButtonPanel = new JPanel();
        detailButtonPanel.add(btnAddDetail);
        detailButtonPanel.add(btnUpdateDetail);
        detailButtonPanel.add(btnDeleteDetail);

        detailPanel.add(detailButtonPanel, BorderLayout.SOUTH);

        btnAddDetail.addActionListener(e -> addDetail());
        btnUpdateDetail.addActionListener(e -> updateChiTietPhieuXuat());
        btnDeleteDetail.addActionListener(e -> deleteDetail());

        add(new JScrollPane(tablePhieuXuat), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(detailPanel, BorderLayout.EAST);
    }

    private void loadPhieuXuats() {
        tableModel.setRowCount(0);
        List<PhieuXuat> phieuXuats = phieuXuatDAO.getAllPhieuXuat();
        for (PhieuXuat phieuXuat : phieuXuats) {
            tableModel.addRow(new Object[]{
                    phieuXuat.getMaPhieuXuat(),
                    phieuXuat.getNgayXuat(),
                    phieuXuat.getMaNhanVien(),

            });
        }
    }

    private void addPhieuXuat() {
        PhieuXuat phieuXuat = new PhieuXuat();
        phieuXuat.setMaPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
        phieuXuat.setNgayXuat((Date) txtNgayXuat.getValue());
        phieuXuat.setMaNhanVien(Integer.parseInt(txtMaNhanVien.getText()));

        phieuXuatDAO.addPhieuXuat(phieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void updatePhieuXuat() {
        PhieuXuat phieuXuat = new PhieuXuat();
        phieuXuat.setMaPhieuXuat(Integer.parseInt(txtMaPhieuXuat.getText()));
        phieuXuat.setNgayXuat((Date) txtNgayXuat.getValue());
        phieuXuat.setMaNhanVien(Integer.parseInt(txtMaNhanVien.getText()));

        phieuXuatDAO.updatePhieuXuat(phieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void deletePhieuXuat() {
        int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
        phieuXuatDAO.deletePhieuXuat(maPhieuXuat);
        loadPhieuXuats(); // Refresh table
    }

    private void addDetail() {
        int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());
        int maVatTu = Integer.parseInt(JOptionPane.showInputDialog("Nhập Mã Vật Tư:"));
        int soLuong = Integer.parseInt(JOptionPane.showInputDialog("Nhập Số Lượng:"));
        BigDecimal gia = BigDecimal.valueOf(Double.parseDouble(JOptionPane.showInputDialog("Nhập Giá:")));
        ChiTietPhieuXuat chiTiet = new ChiTietPhieuXuat(maPhieuXuat, maVatTu, soLuong, gia);
        phieuXuatDAO.addChiTietPhieuXuat(chiTiet);
        loadChiTietPhieuXuat(maPhieuXuat); // Refresh details
    }

    private void updateChiTietPhieuXuat() {
        int selectedRow = detailTable.getSelectedRow();  // Assuming detailTable is a JTable

        // Kiểm tra xem có dòng nào được chọn hay không
        if (selectedRow != -1) {
            // Lấy các giá trị hiện tại của dòng đã chọn
            String currentMaterialCode = detailTable.getValueAt(selectedRow, 0).toString();  // Mã Vật Tư (cột đầu tiên)
            String currentQuantity = detailTable.getValueAt(selectedRow, 1).toString();  // Số Lượng (cột thứ hai)
            String currentPrice = detailTable.getValueAt(selectedRow, 2).toString();  // Giá Xuất (cột thứ ba)

            // Hiển thị hộp thoại yêu cầu người dùng nhập thông tin mới
            String updatedMaterialCode = JOptionPane.showInputDialog(this, "Cập nhật Mã Vật Tư", currentMaterialCode);
            String updatedQuantity = JOptionPane.showInputDialog(this, "Cập nhật Số Lượng", currentQuantity);
            String updatedPrice = JOptionPane.showInputDialog(this, "Cập nhật Giá Xuất", currentPrice);

            // Kiểm tra tính hợp lệ của các đầu vào
            if (updatedMaterialCode != null && !updatedMaterialCode.isEmpty() &&
                    updatedQuantity != null && !updatedQuantity.isEmpty() &&
                    updatedPrice != null && !updatedPrice.isEmpty()) {

                try {
                    // Cập nhật lại các giá trị trong bảng
                    detailTable.setValueAt(updatedMaterialCode, selectedRow, 0);  // Cập nhật Mã Vật Tư
                    detailTable.setValueAt(updatedQuantity, selectedRow, 1);      // Cập nhật Số Lượng
                    detailTable.setValueAt(updatedPrice, selectedRow, 2);         // Cập nhật Giá Xuất

                    // Tạo đối tượng ChiTietPhieuXuat với các giá trị cập nhật
                    int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText()); // Lấy mã phiếu xuất từ trường nhập liệu
                    int maVatTu = Integer.parseInt(updatedMaterialCode);  // Giả sử mã vật tư là một số nguyên
                    int soLuong = Integer.parseInt(updatedQuantity);
                    BigDecimal giaXuat = new BigDecimal(updatedPrice);

                    // Tạo đối tượng ChiTietPhieuXuat
                    ChiTietPhieuXuat chiTiet = new ChiTietPhieuXuat(maPhieuXuat, maVatTu, soLuong, giaXuat);

                    // Gọi phương thức DAO để cập nhật thông tin vào cơ sở dữ liệu
                    phieuXuatDAO.updateChiTietPhieuXuat(chiTiet);

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Cập nhật chi tiết thành công!");

                    // Tải lại chi tiết phiếu xuất sau khi cập nhật
                    loadChiTietPhieuXuat(maPhieuXuat);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng giá trị cho số lượng và giá xuất.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật chi tiết: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hợp lệ.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để cập nhật.");
        }
    }





    private void deleteDetail() {
        try {
            int selectedRow = detailTable.getSelectedRow();  // Lấy chỉ số dòng được chọn trong bảng

            if (selectedRow != -1) {
                // Lấy mã phiếu xuất và mã vật tư từ dòng đã chọn
                int maPhieuXuat = Integer.parseInt(txtMaPhieuXuat.getText());  // Giả sử có trường nhập liệu txtMaPhieuXuat
                int maVatTu = Integer.parseInt(detailTable.getValueAt(selectedRow, 0).toString());  // Lấy mã vật tư từ cột đầu tiên của dòng đã chọn

                // Xác nhận người dùng có muốn xóa chi tiết không
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this detail?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Gọi phương thức DAO để xóa chi tiết phiếu xuất khỏi cơ sở dữ liệu
                    phieuXuatDAO.deleteChiTietPhieuXuat(maPhieuXuat, maVatTu);

                    // Xóa dòng khỏi bảng (qlvt.model)
                    DefaultTableModel model = (DefaultTableModel) detailTable.getModel();
                    model.removeRow(selectedRow);  // Xóa dòng đã chọn khỏi bảng

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Detail deleted successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        } catch (Exception e) {
            // Hiển thị thông báo lỗi nếu có bất kỳ ngoại lệ nào xảy ra
            JOptionPane.showMessageDialog(this, "Error deleting detail: " + e.getMessage());
        }
    }








}
