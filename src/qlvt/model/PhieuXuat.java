package qlvt.model;

import java.sql.Date;

public class PhieuXuat {
    private int maPhieuXuat;
    private Date ngayXuat;
    private int maNhanVien;



    public PhieuXuat(int maPhieuXuat, Date ngayXuat, int maNhanVien) {
        this.maPhieuXuat = maPhieuXuat;
        this.ngayXuat = ngayXuat;
        this.maNhanVien = maNhanVien;

    }

    public PhieuXuat() {

    }

    public int getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(int maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }


}
