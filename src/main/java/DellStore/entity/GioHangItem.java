/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;

/**
 *
 * @author docon
 */
public class GioHangItem {
    private String maSanPham;
    private String tenSanPham;
    private BigDecimal donGia;
    private int soLuong;
    private BigDecimal giamGia;     // đơn vị là số tiền giảm hoặc phần trăm tuỳ hệ thống bạn định nghĩa
    private BigDecimal thanhTien;

    public GioHangItem(String maSanPham, String tenSanPham, BigDecimal donGia, int soLuong, BigDecimal giamGia) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.giamGia = giamGia;
        this.tinhThanhTien();
    }

    public void tinhThanhTien() {
        BigDecimal tong = donGia.multiply(new BigDecimal(soLuong));
        this.thanhTien = tong.subtract(giamGia != null ? giamGia : BigDecimal.ZERO);
    }

    // Getters & Setters
    public String getMaSanPham() { return maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public BigDecimal getDonGia() { return donGia; }
    public int getSoLuong() { return soLuong; }
    public BigDecimal getGiamGia() { return giamGia; }
    public BigDecimal getThanhTien() { return thanhTien; }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        tinhThanhTien();
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
        tinhThanhTien();
    }
}