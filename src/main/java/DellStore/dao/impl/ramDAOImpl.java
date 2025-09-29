package DellStore.dao.impl;

import DellStore.entity.Ram;
import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ramDAOImpl {
    public List<Ram> findAll() {
        List<Ram> list = new ArrayList<>();
        String sql = "SELECT * FROM ram";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                Ram entity = Ram.builder()
                    .id(rs.getInt("id"))
                    .ten(rs.getString("ten"))
                    .dung_luong(rs.getString("dung_luong"))
                    .loai(rs.getString("loai"))
                    .trang_thai(rs.getInt("trang_thai"))
                    .build();
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Ram> getAllRAM() {
    List<Ram> list = new ArrayList<>();
    String sql = "SELECT id, ten, dung_luong,loai, trang_thai FROM ram"; // hoặc tên bảng thật
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Ram o = new Ram(
                rs.getInt("id"),
                rs.getString("ten"),
                    rs.getString("dung_luong"),
                rs.getString("loai"),
                rs.getInt("trang_thai")
            );
            list.add(o);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public void create(Ram entity) {
        String sql = "INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (?, ?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getDung_luong(), entity.getLoai(), entity.getTrang_thai());
    }

    public void update(Ram entity) {
        String sql = "UPDATE ram SET ten=?, dung_luong=?, loai=?, trang_thai=? WHERE id=?";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getDung_luong(), entity.getLoai(), entity.getTrang_thai(), entity.getId());
    }
    public int getIdByTen(String ten) {
    String sql = "SELECT id FROM ram WHERE ten = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, ten);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}
    public static String getTenById(int id) {
        String sql = "SELECT dung_luong FROM ram WHERE id = ?";
        try (Connection cn = XJdbc.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("dung_luong");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
