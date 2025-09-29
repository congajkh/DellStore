/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.HinhThucThanhToan;
import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author docon
 */
public class HinhThucThanhToanDAOImpl {
    public List<HinhThucThanhToan> getAll() {
        List<HinhThucThanhToan> list = new ArrayList<>();
        String sql = "SELECT id, ten FROM hinh_thuc_thanh_toan";
        try (Connection con = XJdbc.openConnection(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                list.add(new HinhThucThanhToan(id, ten));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}


