/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.LoaiSanPham;
import DellStore.utils.XJdbc;
import java.sql.*;
import java.util.*;

public class LoaiSanPhamDAO {

    Connection conn = XJdbc.openConnection();

   public List<LoaiSanPham> findAll() {
        List<LoaiSanPham> list = new ArrayList<>();
        String sql = "SELECT id, ten FROM loai_san_pham";

        try (Connection conn = XJdbc.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LoaiSanPham loai = new LoaiSanPham();
                loai.setId(rs.getInt("id"));
                loai.setTen(rs.getString("ten"));
                list.add(loai);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int getIdByTen(String tenLoai) {
        String sql = "SELECT id FROM loai_san_pham WHERE ten = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy
    }

    public String getTenById(int id) {
        String sql = "SELECT ten FROM loai_san_pham WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

