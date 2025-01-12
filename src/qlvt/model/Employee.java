package qlvt.model;

public class Employee {
    private int maNhanVien;
    private String hoTen;
    private String chucVu;


    private String matKhau;

    // Constructor
    public Employee(int maNhanVien, String hoTen,String matKhau) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }



    // Getters và setters
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }






    public String getMatKhau() {
        return matKhau;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
