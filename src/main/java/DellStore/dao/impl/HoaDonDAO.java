/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.HoaDonDTO;
import DellStore.entity.HoaDon;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class HoaDonDAO {

    // SQL
    String createSql = "INSERT INTO hoa_don (ma, nhan_vien_id, khach_hang_id, ngay_tao, tong_tien, trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE hoadon SET ma = ?, nhan_vien_id = ?, khach_hang_id = ?, ngay_tao = ?, tong_tien = ?, trang_thai = ? WHERE id = ?";
    String deleteSql = "DELETE FROM hoa_don WHERE id = ?";
    String findAllSql = "SELECT * FROM hoa_don";
    String findByIdSql = "SELECT * FROM hoa_don WHERE id = ?";
    
    // CREATE
    public HoaDon create(HoaDon entity) {
        Object[] values = {
            entity.getMa(),
            entity.getNhan_vien_id(),
            entity.getKhach_hang_id(),
            entity.getNgay_tao(),
            entity.getTong_tien(),
            entity.getTrang_thai()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    // UPDATE
    public void update(HoaDon entity) {
        Object[] values = {
            entity.getMa(),
            entity.getNhan_vien_id(),
            entity.getKhach_hang_id(),
            entity.getNgay_tao(),
            entity.getTong_tien(),
            entity.getTrang_thai(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    // DELETE
    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    // FIND ALL
    public List<HoaDon> findAll() {
        return XQuery.getBeanList(HoaDon.class, findAllSql);
    }

    // FIND BY ID
    public HoaDon findById(int id) {
        return XQuery.getSingleBean(HoaDon.class, findByIdSql, id);
    }
   public List<HoaDonDTO> findAllDTO() {
    List<HoaDonDTO> list = new ArrayList<>();
    // Sửa tên bảng từ "hoa_don" thành "HoaDon" cho phù hợp với tên bảng trong SQL khác
    String sql = "SELECT hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai " +
                 "FROM hoa_don hd JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id";

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String ma = rs.getString("ma_hoa_don");
            java.util.Date ngayTao = rs.getDate("ngay_tao");
            String tenNV = rs.getString("ten_nhan_vien");
            int trangThai = rs.getInt("trang_thai");

            list.add(new HoaDonDTO(id, ma, ngayTao, tenNV, trangThai));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

 public Integer findIdByMa(String maHD) {
    String sql = "SELECT id FROM hoa_don WHERE ma = ?";
    try (
        Connection conn = XJdbc.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maHD);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        return null; // không tìm thấy
    } catch (Exception e) {
        throw new RuntimeException("Lỗi tìm id hóa đơn theo mã: " + maHD, e);
    }
}
 public int createAndReturnId(HoaDon hd) {
    String sql = "INSERT INTO hoa_don (ma, ngay_tao, nhan_vien_id, khach_hang_id, tong_tien, trang_thai) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    int generatedId = -1;

    try (Connection conn = XJdbc.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql) ){

        ps.setString(1, hd.getMa());
        ps.setTimestamp(2, new java.sql.Timestamp(hd.getNgay_tao().getTime()));
        ps.setInt(3, hd.getNhan_vien_id());
        ps.setInt(4, hd.getKhach_hang_id());
        ps.setDouble(5, hd.getTong_tien()); // nếu là BigDecimal
        ps.setInt(6, hd.getTrang_thai());

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                generatedId = rs.getInt(1); // Lấy ID được sinh tự động (IDENTITY)
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return generatedId;
}
public BigDecimal getTongTienByHoaDonId(int hoaDonId) {
    String sql = "SELECT SUM(don_gia * so_luong - giam_gia) AS tong_tien FROM chi_tiet_hoa_don WHERE hoa_don_id = ?";
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, hoaDonId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBigDecimal("tong_tien") != null ? rs.getBigDecimal("tong_tien") : BigDecimal.ZERO;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return BigDecimal.ZERO;
}



}
