package DellStore.dao.impl;

import DellStore.entity.Cpu;
import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cpuDAOImpl {
    public List<Cpu> findAll() {
        List<Cpu> list = new ArrayList<>();
        String sql = "SELECT * FROM cpu";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                Cpu entity = Cpu.builder()
                    .id(rs.getInt("id"))
                    .ten(rs.getString("ten"))
                    .toc_do(rs.getString("toc_do"))
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

    public void insert(Cpu entity) {
        String sql = "INSERT INTO cpu (ten, toc_do, loai, trang_thai) VALUES (?, ?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getToc_do(), entity.getLoai(), entity.getTrang_thai());
    }

    public void update(Cpu entity) {
        String sql = "UPDATE cpu SET ten=?, toc_do=?, loai=?, trang_thai=? WHERE id=?";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getToc_do(), entity.getLoai(), entity.getTrang_thai(), entity.getId());
    }
    public List<Cpu> getAllCPU() {
    List<Cpu> list = new ArrayList<>();
    String sql = "SELECT id, ten, toc_do,loai, trang_thai FROM cpu"; // hoặc tên bảng thật
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Cpu o = new Cpu(
                rs.getInt("id"),
                rs.getString("ten"),
                    rs.getString("toc_do"),
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
    public int getIdByTen(String ten) {
    String sql = "SELECT id FROM cpu WHERE ten = ?";
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
        String sql = "SELECT ten FROM cpu WHERE id = ?";
        try (Connection cn = XJdbc.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("ten");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
