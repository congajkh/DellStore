/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.GioHangDTO;
import DellStore.entity.HinhThucThanhToan;
import DellStore.entity.HoaDonDTO;
import DellStore.entity.HoaDon;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//dangtuan30/7
public class HoaDonDAOImpl {

    // SQL
    String createSql = "INSERT INTO hoa_don (ma, nhan_vien_id, khach_hang_id, ngay_tao, tong_tien, trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE hoa_don SET ma = ?, nhan_vien_id = ?, khach_hang_id = ?, ngay_tao = ?, tong_tien = ?, trang_thai = ? WHERE id = ?";
    String deleteSql = "UPDATE hoa_don SET trang_thai = 2 WHERE id = ?";
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
    String sql = "SELECT hd.id, hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai "
               + "FROM hoa_don hd "
               + "JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id "
               + "WHERE hd.trang_thai != 2"; // Không lấy hóa đơn đã hủy

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
   public List<HoaDonDTO> findHoaDonChoTT() {
    List<HoaDonDTO> list = new ArrayList<>();
   String sql = "SELECT hd.id, hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai "
           + "FROM hoa_don hd "
           + "JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id "
           + "WHERE hd.trang_thai = 0" // Chỉ lấy hóa đơn chờ thanh toán
           + "ORDER BY hd.id DESC";
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
                PreparedStatement ps = conn.prepareStatement(sql)) {
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


   public List<HoaDonDTO> findDTOByNgayVaTrangThai(Date ngay, int trangThai) {
    List<HoaDonDTO> list = new ArrayList<>();
    String sql = "SELECT hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai " +
                 "FROM hoa_don hd " +
                 "JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id " +
                 "WHERE CAST(hd.ngay_tao AS DATE) = ? ";

    if (trangThai == -1) {
        // Tất cả (nhưng loại bỏ hóa đơn huỷ)
        sql += "AND hd.trang_thai IN (0, 1)";
    } else {
        sql += "AND hd.trang_thai = ?";
    }

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDate(1, new java.sql.Date(ngay.getTime()));

        if (trangThai != -1) {
            ps.setInt(2, trangThai);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDonDTO dto = new HoaDonDTO();
            dto.setMa_hoa_don(rs.getString("ma_hoa_don"));
            dto.setNgay_tao(rs.getDate("ngay_tao"));
            dto.setTen_nhan_vien(rs.getString("ten_nhan_vien"));
            dto.setTrang_thai(rs.getInt("trang_thai"));
            list.add(dto);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public int createAndReturnId(HoaDon hd) {
        String sql = "INSERT INTO hoa_don (ma, ngay_tao, nhan_vien_id, khach_hang_id, tong_tien, trang_thai) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection con = XJdbc.openConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hd.getMa());
            ps.setTimestamp(2, new java.sql.Timestamp(hd.getNgay_tao().getTime()));
            ps.setInt(3, hd.getNhan_vien_id());
            ps.setInt(4, hd.getKhach_hang_id());
            ps.setDouble(5, hd.getTong_tien());
            ps.setInt(6, hd.getTrang_thai());

            // PHẢI GỌI trước
            int affectedRows = ps.executeUpdate();

            // Sau đó mới getGeneratedKeys
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public List<GioHangDTO> getGioHangByMaHoaDon(String maHD) {
        List<GioHangDTO> list = new ArrayList<>();
        String sql = """
        SELECT sp.ma_sp, sp.ten, cthd.don_gia, cthd.so_luong, cthd.giam_gia, 
               (cthd.don_gia * cthd.so_luong - ISNULL(cthd.giam_gia, 0)) AS thanh_tien
        FROM chi_tiet_hoa_don cthd
        JOIN serial s ON cthd.serial_id = s.id
        JOIN san_pham sp ON s.san_pham_id = sp.id
        WHERE cthd.hoa_don_id = ?
    """;

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GioHangDTO gh = new GioHangDTO();
                    gh.setMaSP(rs.getString("ma_sp"));
                    gh.setTenSP(rs.getString("ten"));
                    gh.setDonGia(rs.getBigDecimal("don_gia"));
                    gh.setSoLuong(rs.getInt("so_luong"));
                    gh.setGiamGia(rs.getBigDecimal("giam_gia"));
                    gh.setThanhTien(rs.getBigDecimal("thanh_tien"));
                    list.add(gh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GioHangDTO> getGioHangByHoaDonID(int hoaDonId) {
        List<GioHangDTO> list = new ArrayList<>();
        String sql = """
        SELECT sp.ma_sp, sp.ten, cthd.don_gia, cthd.so_luong, 0 AS giam_gia,
               (cthd.don_gia * cthd.so_luong) AS thanh_tien
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
        JOIN serial s ON ctsp.serial_id = s.id
        JOIN san_pham sp ON s.san_pham_id = sp.id
        WHERE cthd.hoa_don_id = ?
    """;

        try (Connection conn = XJdbc.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hoaDonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GioHangDTO gh = new GioHangDTO();
                gh.setMaSP(rs.getString("ma_sp"));
                gh.setTenSP(rs.getString("ten"));
                gh.setDonGia(rs.getBigDecimal("don_gia"));
                gh.setSoLuong(rs.getInt("so_luong"));
                gh.setGiamGia(rs.getBigDecimal("giam_gia")); // có thể sửa sau nếu có khuyến mãi
                gh.setThanhTien(rs.getBigDecimal("thanh_tien"));
                list.add(gh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
    
    
    

    public List<HoaDonDTO> findDTOByTrangThai(Integer trangThai) {
    List<HoaDonDTO> list = new ArrayList<>();
    String sql = "SELECT hd.ma AS ma_hoa_don, hd.ngay_tao, nv.ten_nv AS ten_nhan_vien, hd.trang_thai " +
                 "FROM hoa_don hd " +
                 "JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id ";

    if (trangThai == null) {
        // Không lọc theo trạng thái cụ thể → bỏ qua các hóa đơn huỷ
        sql += "WHERE hd.trang_thai IN (0, 1)";
    } else {
        // Lọc đúng theo trạng thái được chọn
        sql += "WHERE hd.trang_thai = ?";
    }

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        if (trangThai != null) {
            ps.setInt(1, trangThai);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDonDTO dto = new HoaDonDTO();
            dto.setMa_hoa_don(rs.getString("ma_hoa_don"));
            dto.setNgay_tao(rs.getDate("ngay_tao"));
            dto.setTen_nhan_vien(rs.getString("ten_nhan_vien"));
            dto.setTrang_thai(rs.getInt("trang_thai"));
            list.add(dto);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public HoaDon findByMa(String maHD) {
        String sql = "SELECT * FROM hoa_don WHERE ma = ?";
        try (Connection con = XJdbc.openConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                        rs.getInt("id"),
                        rs.getString("ma"),
                        rs.getInt("nhan_vien_id"),
                        rs.getInt("khach_hang_id"),
                        rs.getDate("ngay_tao"),
                        rs.getDouble("tong_tien"),
                        rs.getInt("trang_thai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int countHoaDonChuaThanhToan() {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM hoa_don WHERE trang_thai = 0";
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}
   
}
