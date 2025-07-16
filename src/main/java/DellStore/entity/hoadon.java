/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

/**
 *
 * @author docon
 */
public class hoadon {
     private int id;
    private String ma;
    private int nhan_vien_id;
    private int khach_hang_id;
    private java.util.Date ngay_tao;
    private double tong_tien;
    private int trang_thai;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }
    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getNhan_vien_id() {
        return nhan_vien_id;
    }
    public void setNhan_vien_id(int nhan_vien_id) {
        this.nhan_vien_id = nhan_vien_id;
    }

    public int getKhach_hang_id() {
        return khach_hang_id;
    }
    public void setKhach_hang_id(int khach_hang_id) {
        this.khach_hang_id = khach_hang_id;
    }

    public java.util.Date getNgay_tao() {
        return ngay_tao;
    }
    public void setNgay_tao(java.util.Date ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public double getTong_tien() {
        return tong_tien;
    }
    public void setTong_tien(double tong_tien) {
        this.tong_tien = tong_tien;
    }

    public int getTrang_thai() {
        return trang_thai;
    }
    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }
}
