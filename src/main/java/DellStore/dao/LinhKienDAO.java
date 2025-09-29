/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao;

import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nguyendangtuan
 */
public class LinhKienDAO {
    public int getIdCPUByName(String name) {
        String sql = "SELECT id FROM cpu WHERE ten = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Không tìm thấy
    }

    public int getIdGPUByName(String name) {
        String sql = "SELECT id FROM gpu WHERE ten = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIdRAMByDungLuong(String dungLuong) {
        String sql = "SELECT id FROM ram WHERE dung_luong = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dungLuong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIdSSDByDungLuong(String dungLuong) {
        String sql = "SELECT id FROM ssd WHERE dung_luong = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dungLuong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String getTenCPUById(int id) {
    String sql = "SELECT ten FROM cpu WHERE id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("ten");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // Không tìm thấy
}

public String getTenGPUById(int id) {
    String sql = "SELECT ten FROM gpu WHERE id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("ten");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public String getDungLuongRAMById(int id) {
    String sql = "SELECT dung_luong FROM ram WHERE id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("dung_luong");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public String getDungLuongSSDById(int id) {
    String sql = "SELECT dung_luong FROM ssd WHERE id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("dung_luong");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}
