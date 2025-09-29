/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.NhanVien;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import DellStore.utils.EmailUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.mail.MessagingException;

/**
 *
 * @author docon
 */
public class nhanvienDAO {

    String createSql = "INSERT INTO nhan_vien (ma_nv, ten_nv, gioi_tinh, ngay_sinh, dia_chi, sdt, email, trang_thai, chuc_vu, tai_khoan, mat_khau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE nhan_vien SET ten_nv=?, gioi_tinh=?, ngay_sinh=?, dia_chi=?, sdt=?, email=?, trang_thai=?, chuc_vu=? WHERE ma_nv=?";
    String deleteSql = "DELETE FROM nhan_vien WHERE ma_nv=?";
    String findAllSql = "SELECT * FROM nhan_vien";
    String findByIdSql = "SELECT * FROM nhan_vien WHERE ma_nv=?";

    public void create(NhanVien entity) {
        Object[] values = {
            entity.getMa_nv(),
            entity.getTen_nv(),
            entity.isGioi_tinh(),
            entity.getNgay_sinh(),
            entity.getDia_chi(),
            entity.getSdt(),
            entity.getEmail(),
            entity.getTrang_thai(),
            entity.getChuc_vu(),
            entity.getTai_khoan(),
            entity.getMat_khau()
        };
        XJdbc.executeUpdate(createSql, values);

    }

    public void update(NhanVien entity) {
        Object[] values = {
            entity.getTen_nv(),
            entity.isGioi_tinh(),
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

    public List<NhanVien> findAll() {
        return XQuery.getBeanList(NhanVien.class, findAllSql);
    }

    public NhanVien findById(String ma_nv) {
        return XQuery.getSingleBean(NhanVien.class, findByIdSql, ma_nv);
    }

    public void deleteByNhanVienMaNV(String maNV) {
        // Since tai_khoan is a column in nhan_vien table, we update it to null instead of deleting
        String updateQuery = "UPDATE nhan_vien SET tai_khoan = NULL, mat_khau = NULL WHERE ma_nv = ?";
        XJdbc.executeUpdate(updateQuery, maNV);
    }

   public NhanVien findByTaiKhoan(String taiKhoan) {
    String sql = "SELECT * FROM nhan_vien WHERE tai_khoan = ?";
    try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, taiKhoan);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            NhanVien nv = new NhanVien();
            nv.setId(rs.getInt("id")); // ✅ Thêm dòng này
            nv.setMa_nv(rs.getString("ma_nv"));
            nv.setTen_nv(rs.getString("ten_nv"));
            nv.setTai_khoan(rs.getString("tai_khoan"));
            nv.setMat_khau(rs.getString("mat_khau"));
            nv.setChuc_vu(rs.getString("chuc_vu"));
            // bạn có thể thêm các trường khác nếu cần
            return nv;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}



    public NhanVien findByEmail(String email) {
    String sql = "SELECT * FROM nhan_vien WHERE email = ?";
    try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            NhanVien nv = new NhanVien();
            nv.setTen_nv(rs.getString("ten_nv"));
            nv.setEmail(rs.getString("email"));
            nv.setTai_khoan(rs.getString("tai_khoan"));
            nv.setMat_khau(rs.getString("mat_khau"));
            nv.setChuc_vu(rs.getString("chuc_vu")); // BỔ SUNG DÒNG NÀY
            return nv;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public boolean existsByMaNV(String maNV) {
    String sql = "SELECT COUNT(*) FROM nhan_vien WHERE ma_nv = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, maNV);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    public boolean existsByEmail(String email) {
    String sql = "SELECT COUNT(*) FROM nhan_vien WHERE email = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


public NhanVien findById(int id) {
    String sql = "SELECT * FROM nhan_vien WHERE id = ?";
    return XQuery.getSingleBean(NhanVien.class, sql, id);
}
public void updateMatKhauByEmail(String email, String newPassword) {
    String sql = "UPDATE nhan_vien SET mat_khau = ? WHERE email = ?";
    try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, newPassword);
        ps.setString(2, email);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
