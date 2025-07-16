/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.sanpham;
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
public class sanphamDAO {
    String insertSql = "INSERT INTO san_pham (ten, masp, mo_ta, loai_san_pham_id, hang_id, trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE san_pham SET ten = ?, masp = ?, mo_ta = ?, loai_san_pham_id = ?, hang_id = ?, trang_thai = ? WHERE id = ?";
    String deleteSql = "DELETE FROM san_pham WHERE id = ?";
//    String findAllSql = "SELECT * FROM san_pham";
    String findByIdSql = "SELECT * FROM san_pham WHERE id = ?";
    String findShortSql = "SELECT id, ten, masp FROM san_pham WHERE trang_thai = 1";

    public sanpham create(sanpham entity) {
        Object[] values = {
            entity.getTen(),
            entity.getMasp(),
            entity.getMo_ta(),
            entity.getLoai_san_pham_id(),
            entity.getHang_id(),
            entity.getTrang_thai()
        };
        XJdbc.executeUpdate(insertSql, values);
        return entity;
    }

    public void update(sanpham entity) {
        Object[] values = {
            entity.getTen(),
            entity.getMasp(),
            entity.getMo_ta(),
            entity.getLoai_san_pham_id(),
            entity.getHang_id(),
            entity.getTrang_thai(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

  public List<sanpham> findAll() {
    List<sanpham> list = new ArrayList<>();
    String sql = "SELECT * FROM san_pham";

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            sanpham sp = new sanpham();
            sp.setId(rs.getInt("id"));
            sp.setTen(rs.getString("ten"));
            sp.setMasp(rs.getString("masp"));
            sp.setMo_ta(rs.getString("mo_ta"));
            sp.setLoai_san_pham_id(rs.getInt("loai_san_pham_id"));
            sp.setHang_id(rs.getInt("hang_id"));
            sp.setTrang_thai(rs.getInt("trang_thai"));
            list.add(sp);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public sanpham findById(int id) {
        return XQuery.getSingleBean(sanpham.class, findByIdSql, id);
    }

    public List<sanpham> findShort() {
        return XQuery.getBeanList(sanpham.class, findShortSql);
    }
}
