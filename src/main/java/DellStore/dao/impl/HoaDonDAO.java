/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.HoaDonDTO;
import DellStore.entity.hoadon;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
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
    public hoadon create(hoadon entity) {
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
    public void update(hoadon entity) {
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
    public List<hoadon> findAll() {
        return XQuery.getBeanList(hoadon.class, findAllSql);
    }

    // FIND BY ID
    public hoadon findById(int id) {
        return XQuery.getSingleBean(hoadon.class, findByIdSql, id);
    }
   public List<HoaDonDTO> findAllDTO() {
    List<HoaDonDTO> list = new ArrayList<>();
    // Sửa tên bảng từ "hoa_don" thành "hoadon" cho phù hợp với tên bảng trong SQL khác
    String sql = "SELECT hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai " +
                 "FROM hoa_don hd JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id";

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String ma = rs.getString("ma_hoa_don");
            java.util.Date ngayTao = rs.getDate("ngay_tao");
            String tenNV = rs.getString("ten_nhan_vien");
            int trangThai = rs.getInt("trang_thai");

            list.add(new HoaDonDTO(ma, ngayTao, tenNV, trangThai));
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


}
