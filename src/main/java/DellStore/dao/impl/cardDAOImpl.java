package DellStore.dao.impl;

import DellStore.entity.Card;
import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cardDAOImpl {
    public List<Card> findAll() {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT * FROM gpu";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                Card entity = Card.builder()
                    .id(rs.getInt("id"))
                    .ten(rs.getString("ten"))
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
     public List<Card> getAllCard() {
    List<Card> list = new ArrayList<>();
    String sql = "SELECT id, ten, loai, trang_thai FROM gpu"; // hoặc tên bảng thật
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Card o = new Card(
                rs.getInt("id"),
                rs.getString("ten"),
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

    public void insert(Card entity) {
        String sql = "INSERT INTO card (ten, loai, trang_thai) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getLoai(), entity.getTrang_thai());
    }

    public void update(Card entity) {
        String sql = "UPDATE card SET ten=?, loai=?, trang_thai=? WHERE id=?";
        XJdbc.executeUpdate(sql, entity.getTen(), entity.getLoai(), entity.getTrang_thai(), entity.getId());
    }
    public int getIdByTen(String ten) {
    String sql = "SELECT id FROM gpu WHERE ten = ?";
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
        String sql = "SELECT ten FROM gpu WHERE id = ?";
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
