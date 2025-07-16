/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.khuyenmai;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author docon
 */
public class khuyenmaiDAOImpl {

    String insertSql = "INSERT INTO khuyen_mai (ma_km, ten_km, loai_km, gia_tri, ngay_bat_dau, ngay_ket_thuc, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE khuyen_mai SET ten_km = ?, loai_km = ?, gia_tri = ?, ngay_bat_dau = ?, ngay_ket_thuc = ?, trang_thai = ? WHERE ma_km = ?";
    String deleteSql = "DELETE FROM khuyen_mai WHERE ma_km = ?";
    String findAllSql = "SELECT * FROM khuyen_mai";
    String findByMaSql = "SELECT * FROM khuyen_mai WHERE ma_km = ?";

    public void insert(khuyenmai km) {
        XJdbc.executeUpdate(insertSql,
            km.getMa_km(),
            km.getTen_km(),
            km.getLoai_km(),
            km.getGia_tri(),
            km.getNgay_bat_dau(),
            km.getNgay_ket_thuc(),
            km.getTrang_thai()
        );
    }

    public void update(khuyenmai km) {
        XJdbc.executeUpdate(updateSql,
            km.getTen_km(),
            km.getLoai_km(),
            km.getGia_tri(),
            km.getNgay_bat_dau(),
            km.getNgay_ket_thuc(),
            km.getTrang_thai(),
           km.getMa_km()
        );
    }

    public void deleteByMa(String maKm) {
        XJdbc.executeUpdate(deleteSql, maKm);
    }

  public List<khuyenmai> findAll() {
        List<khuyenmai> list = new ArrayList<>();
        try (Connection conn = XJdbc.openConnection();
             PreparedStatement ps = conn.prepareStatement(findAllSql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                khuyenmai km = new khuyenmai();
                km.setId(rs.getInt("id"));
                km.setMa_km(rs.getString("ma_km"));
                km.setTen_km(rs.getString("ten_km"));
                km.setLoai_km(rs.getString("loai_km"));
                km.setGia_tri(rs.getDouble("gia_tri")); // Ánh xạ cột gia_tri
                km.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
                km.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
                km.setTrang_thai(rs.getInt("trang_thai"));
                System.out.println("DAO: Mã KM=" + km.getMa_km()+ ", Giá trị=" + km.getGia_tri()); // Debug
                list.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn danh sách khuyến mãi: " + e.getMessage());
        }
        return list;
    }

    public khuyenmai findByMa(String maKm) {
        return XQuery.getSingleBean(khuyenmai.class, findByMaSql, maKm);
    }
}

