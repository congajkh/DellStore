/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.khachhang;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class KhachHangDAOImpl {
     String insertSql = "INSERT INTO khach_hang (ten, ngay_sinh, gioi_tinh, sdt, email, trang_thai, dia_chi) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE khach_hang SET ten = ?, ngay_sinh = ?, gioi_tinh = ?, sdt = ?, email = ?, trang_thai = ? WHERE id = ?";
    String deleteSql = "DELETE FROM khach_hang WHERE id = ?";
    String findAllSql = "SELECT id, ten, ngay_sinh, gioi_tinh, sdt, email, trang_thaiFROM khach_hang";
    String findByIdSql = "SELECT id, ten, ngay_sinh, gioi_tinh, sdt, email, trang_thai FROM khach_hang WHERE id = ?";

    public khachhang create(khachhang kh) {
        Object[] values = {
            kh.getTen(),
            kh.getNgay_sinh(),
            kh.getGioi_tinh(),
            kh.getSdt(),
            kh.getEmail(),
            kh.getTrang_thai()
        };
        XJdbc.executeUpdate(insertSql, values);
        return kh;
    }

    public void update(khachhang kh) {
        Object[] values = {
            kh.getTen(),
            kh.getNgay_sinh(),
            kh.getGioi_tinh(),
            kh.getSdt(),
            kh.getEmail(),
            kh.getTrang_thai(),
            kh.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<khachhang> findAll() {
    String sql = "SELECT id, ten, ngay_sinh, gioi_tinh, sdt, email, trang_thai FROM khach_hang";
    return XQuery.getBeanList(khachhang.class, sql);
}


    public khachhang findById(int id) {
        return XQuery.getSingleBean(khachhang.class, findByIdSql, id);
    }
}
