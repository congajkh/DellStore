/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

/**
 *
 * @author docon
 */
public class QRSanPham {
    private String ma;
    private String ten;
    private String moTa;
    private int trangThai;     // 0: Ngưng bán, 1: Đang bán

    // Constructors
    public QRSanPham() {}
    
    public QRSanPham(String ma, String ten, String moTa, int trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}

