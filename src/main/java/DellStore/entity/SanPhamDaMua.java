/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nguyendangtuan
 */
public class SanPhamDaMua {
    private String maSp;
    private String tenSp;
    private Date ngayMua;
    private int soLuong;
    private BigDecimal donGia;

    public SanPhamDaMua(String maSp, String tenSp, Date ngayMua, int soLuong, BigDecimal donGia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.ngayMua = ngayMua;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public SanPhamDaMua() {
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }
    
}
