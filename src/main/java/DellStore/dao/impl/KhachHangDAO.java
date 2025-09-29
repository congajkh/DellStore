/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;
import DellStore.entity.KhachHang;
import DellStore.entity.SanPhamDaMua;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangDAO {
   String createSql = "INSERT INTO khach_hang (ten, gioi_tinh, dia_chi, sdt, email) VALUES ( ?, ?, ?, ?, ?)";
   String updateSql = "UPDATE khach_hang SET ten = ?, gioi_tinh = ?, dia_chi = ?, sdt = ?, email = ? WHERE id = ?";
   String deleteSql = "DELETE FROM khach_hang WHERE ten = ?";
   String findAllSql = "SELECT * FROM khach_hang";
   String findByTenSql = "SELECT * FROM khach_hang WHERE ten LIKE ?";
   

   public KhachHang create(KhachHang entity) {
       Object[] values = {
           entity.getTen(),
           entity.isGioi_tinh(),
           entity.getDia_chi(),
           entity.getSdt(),
           entity.getEmail()
       };
       XJdbc.executeUpdate(createSql, values);
       return entity;
   }

   public void update(KhachHang entity) {
       Object[] values = {
           entity.getTen(),
           entity.isGioi_tinh(),
           entity.getDia_chi(),
           entity.getSdt(),
           entity.getEmail(),
           entity.getId()
       };
       XJdbc.executeUpdate(updateSql, values);
   }

   public void deleteById(String ten) {
       XJdbc.executeUpdate(deleteSql, ten);
   }

   public List<KhachHang> findAll() {
       return XQuery.getBeanList(KhachHang.class, findAllSql);
   }
  public List<KhachHang> findByTen(String ten) {
    return XQuery.getBeanList(KhachHang.class, findByTenSql, "%" + ten + "%");
}

   private KhachHang readFromResultSet(ResultSet rs) throws SQLException {
        KhachHang kh = new KhachHang();
        kh.setId(rs.getInt("id"));
        kh.setTen(rs.getString("ten"));
        kh.setGioi_tinh(rs.getBoolean("gioi_tinh"));
        kh.setDia_chi(rs.getString("dia_chi"));
        kh.setSdt(rs.getString("sdt"));
        kh.setEmail(rs.getString("email"));
        return kh;
    }
    private final String sql_spDaMua = """
        SELECT 
            sp.ma AS ma_sp,
            sp.ten AS ten_sp,
            hd.ngay_tao AS ngay_mua,
            cthd.so_luong,
            cthd.don_gia
        FROM hoa_don hd
        JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
        JOIN chi_tiet_san_pham ctsp ON ctsp.id = cthd.chi_tiet_san_pham_id
        JOIN serial s ON s.id = ctsp.serial_id
        JOIN san_pham sp ON sp.id = s.san_pham_id
        WHERE hd.khach_hang_id = ?
    """;
 public List<SanPhamDaMua> getSanPhamDaMua(int khachHangId) {
        List<SanPhamDaMua> list = new ArrayList<>();

        String sql = """
           SELECT 
                    sp.ma_sp, 
                    sp.ten as ten_sp, 
                    CAST(hd.ngay_tao AS date) AS ngay_mua,
                    SUM(cthd.so_luong) so_luong,
                    cthd.don_gia
                FROM hoa_don hd
                JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
                JOIN chi_tiet_san_pham ctsp ON ctsp.id = cthd.chi_tiet_san_pham_id
                JOIN serial s ON s.ctsp_id = ctsp.id
                JOIN san_pham sp ON sp.id = ctsp.san_pham_id
                WHERE hd.khach_hang_id = ? AND hd.trang_thai = 1
                GROUP BY 
                    sp.ma_sp, 
                    sp.ten, 
                    cthd.don_gia, 
                    CAST(hd.ngay_tao AS date)
                ORDER BY ngay_mua DESC
                     
        """;

        try (
            Connection con = XJdbc.openConnection(); // hoặc DriverManager.getConnection(...)
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, khachHangId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maSp = rs.getString("ma_sp");
                String tenSp = rs.getString("ten_sp");
                Date ngayMua = rs.getDate("ngay_mua");
                int soLuong = rs.getInt("so_luong");
                BigDecimal donGia = rs.getBigDecimal("don_gia");

                list.add(new SanPhamDaMua(maSp, tenSp, ngayMua, soLuong, donGia));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
 public boolean checkTrung(String ten, String sdt, String email) {
    String sql = "SELECT COUNT(*) FROM khach_hang WHERE ten = ? AND sdt = ? AND email = ?";
    Integer count = XJdbc.getValue(sql, ten, sdt, email);
    return count != null && count > 0;
}
 public KhachHang findById(int id) {
    String sql = "SELECT * FROM khach_hang WHERE id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
         
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            KhachHang kh = new KhachHang();
            kh.setId(rs.getInt("id"));
            kh.setTen(rs.getString("ten"));
            kh.setSdt(rs.getString("sdt"));
            // bổ sung thêm các cột khác nếu cần
            return kh;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

 

}

    

