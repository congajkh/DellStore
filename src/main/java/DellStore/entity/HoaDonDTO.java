/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.util.Date;

/**
 *
 * @author docon
 */
public class HoaDonDTO {
 private String ma_hoa_don;
    private Date ngay_tao;
    private String ten_nhan_vien;
    private int trang_thai;

    public HoaDonDTO(String ma_hoa_don, Date ngay_tao, String ten_nhan_vien, int trang_thai) {
        this.ma_hoa_don = ma_hoa_don;
        this.ngay_tao = ngay_tao;
        this.ten_nhan_vien = ten_nhan_vien;
        this.trang_thai = trang_thai;
    }

    public String getMa_hoa_don() { return ma_hoa_don; }
    public Date getNgay_tao() { return ngay_tao; }
    public String getTen_nhan_vien() { return ten_nhan_vien; }
    public int getTrang_thai() { return trang_thai; }
}