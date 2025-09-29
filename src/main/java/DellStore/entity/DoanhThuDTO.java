/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;

public class DoanhThuDTO {
    private String maSanPham;
    private String tenSanPham;
    private BigDecimal gia;
    private int soLuong;
    private BigDecimal doanhThu;

    public DoanhThuDTO() {
    }

    // Constructor tính doanh thu tự động
    public DoanhThuDTO(String maSanPham, String tenSanPham, BigDecimal gia, int soLuong) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.doanhThu = gia.multiply(BigDecimal.valueOf(soLuong));
    }

    // Constructor đặt doanh thu trực tiếp (nếu cần)
    public DoanhThuDTO(String maSanPham, String tenSanPham, BigDecimal gia, int soLuong, BigDecimal doanhThu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
    }

    // Getter & Setter
    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }
}
