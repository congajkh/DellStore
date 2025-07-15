/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.chitietsanpham;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class chitietsanphamDAO {

    String insertSql = "INSERT INTO chi_tiet_san_pham (san_pham_id, ram_id, cpu_id, ocung_id, card_id, hang_id, serial, gia_ban, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE chi_tiet_san_pham SET san_pham_id=?, ram_id=?, cpu_id=?, ocung_id=?, card_id=?, hang_id=?, serial=?, gia_ban=?, trang_thai=? WHERE id=?";
    String deleteSql = "DELETE FROM chi_tiet_san_pham WHERE id=?";
    String findAllSql = "SELECT * FROM chi_tiet_san_pham";
    String findByIdSql = "SELECT * FROM chi_tiet_san_pham WHERE id=?";

    public chitietsanpham insert(chitietsanpham entity) {
        XJdbc.executeUpdate(insertSql,
            entity.getSan_pham_id(),
            entity.getRam_id(),
            entity.getCpu_id(),
            entity.getOcung_id(),
            entity.getCard_id(),
            entity.getHang_id(),
            entity.getSerial(),
            entity.getGia_ban(),
            entity.getTrang_thai()
        );
        return entity;
    }

    public void update(chitietsanpham entity) {
        XJdbc.executeUpdate(updateSql,
            entity.getSan_pham_id(),
            entity.getRam_id(),
            entity.getCpu_id(),
            entity.getOcung_id(),
            entity.getCard_id(),
            entity.getHang_id(),
            entity.getSerial(),
            entity.getGia_ban(),
            entity.getTrang_thai(),
            entity.getId()
        );
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<chitietsanpham> findAll() {
        return XQuery.getBeanList(chitietsanpham.class, findAllSql);
    }

    public chitietsanpham findById(int id) {
        return XQuery.getSingleBean(chitietsanpham.class, findByIdSql, id);
    }
public List<Object[]> getAllChiTietSanPhamView() {
    List<Object[]> list = new ArrayList<>();
    String sql = "SELECT * FROM vw_chi_tiet_san_pham_full";
    try (
        Connection conn = XJdbc.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("ten_san_pham"),
                rs.getString("ten_cpu"),
                rs.getString("ten_card"),
                rs.getString("ten_ram"),
                rs.getString("ten_ocung"),
                rs.getString("ten_hang"),
                rs.getString("serial"),
                rs.getDouble("gia_ban"),
                rs.getInt("trang_thai") == 1 ? "Còn hàng" : "Hết hàng"
            };
            list.add(row);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}

