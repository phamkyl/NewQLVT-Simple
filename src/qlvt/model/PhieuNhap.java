package qlvt.model;

import java.sql.Date;

public class PhieuNhap {
    private int maPhieuNhap;
    private Date ngayNhap;
    private int maNhanVien;

    private int maNhaCungCap;

    public PhieuNhap(int maPhieuNhap, Date ngayNhap, int maNhanVien, int maNhaCungCap) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.maNhanVien = maNhanVien;

        this.maNhaCungCap = maNhaCungCap;
    }

    // Getters and Setters
    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }





    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }




}
