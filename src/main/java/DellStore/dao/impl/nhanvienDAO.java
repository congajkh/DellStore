/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;
import DellStore.entity.nhanvien;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;
/**
 *
 * @author docon
 */
public class nhanvienDAO {

    String createSql = "INSERT INTO nhan_vien(ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE nhan_vien SET ten_nv=?, gioi_tinh=?, ngay_sinh=?, dia_chi=?, sdt=?, email=?, trang_thai=?, chuc_vu=? WHERE ma_nv=?";
    String deleteSql = "DELETE FROM nhan_vien WHERE ma_nv=?";
    String findAllSql = "SELECT * FROM nhan_vien";
    String findByIdSql = "SELECT * FROM nhan_vien WHERE ma_nv=?";

    public nhanvien create(nhanvien entity) {
        Object[] values = {
            entity.getMa_nv(),
            entity.getTen_nv(),
            entity.getGioi_tinh(),
            entity.getNgay_sinh(),
            entity.getDia_chi(),
            entity.getSdt(),
            entity.getEmail(),
            entity.getTrang_thai(),
            entity.getChuc_vu()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    public void update(nhanvien entity) {
        Object[] values = {
            entity.getTen_nv(),
            entity.getGioi_tinh(),
            entity.getNgay_sinh(),
            entity.getDia_chi(),
            entity.getSdt(),
            entity.getEmail(),
            entity.getTrang_thai(),
            entity.getChuc_vu(),
            entity.getMa_nv()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    public void deleteById(String ma_nv) {
        XJdbc.executeUpdate(deleteSql, ma_nv);
    }

    public List<nhanvien> findAll() {
        return XQuery.getBeanList(nhanvien.class, findAllSql);
    }

    public nhanvien findById(String ma_nv) {
        return XQuery.getSingleBean(nhanvien.class, findByIdSql, ma_nv);
    }
   public void deleteByNhanVienMaNV(String maNV) {
    String deleteByMaNV = "DELETE FROM tai_khoan WHERE manv = ?";
    XJdbc.executeUpdate(deleteByMaNV, maNV);
}

}
