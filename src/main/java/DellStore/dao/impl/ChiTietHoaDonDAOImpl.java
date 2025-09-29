
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.ChiTietHoaDon;
import DellStore.entity.ChiTietHoaDonDTO;
import DellStore.entity.GioHangDTO;
import DellStore.entity.Serial;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author docon
 */
//dangtuan 30/7
public class ChiTietHoaDonDAOImpl {

    String sql = """
                                        SELECT      
                                        hd.ma AS maHoaDon,
                                        sp.ten AS tenSanPham,
                                        cthd.don_gia AS donGia,
                                        SUM(cthd.so_luong) AS soLuong,
                                        -- Tính số tiền khuyến mại theo loại
                                        SUM(
                                            CASE 
                                                WHEN dgg.loai_giam = N'Tiền' THEN ISNULL(dgg.giam_gia, 0)
                                                WHEN dgg.loai_giam = N'%'   THEN (cthd.don_gia * cthd.so_luong * ISNULL(dgg.giam_gia, 0) / 100.0)
                                                ELSE 0
                                            END
                                        ) AS tienKhuyenMai,
                                        -- Tổng tiền sau giảm
                                        SUM(cthd.don_gia * cthd.so_luong) - 
                                        SUM(
                                            CASE 
                                                WHEN dgg.loai_giam = N'Tiền' THEN ISNULL(dgg.giam_gia, 0)
                                                WHEN dgg.loai_giam = N'%'   THEN (cthd.don_gia * cthd.so_luong * ISNULL(dgg.giam_gia, 0) / 100.0)
                                                ELSE 0
                                            END
                                        ) AS tongTien,
                                        ht.ten AS tenHinhThuc,
                                        ctsp.san_pham_id,
                                        ctsp.cpu_id,
                                        ctsp.ram_id,
                                        ctsp.gpu_id,
                                        ctsp.ssd_id
                                    FROM hoa_don hd
                                    JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
                                    JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
                                    LEFT JOIN chi_tiet_dot_giam_gia ctdgg ON ctdgg.ctsp_id = ctsp.id
                                    LEFT JOIN dot_giam_gia dgg ON ctdgg.dot_giam_gia_id = dgg.id
                                    JOIN san_pham sp ON sp.id = ctsp.san_pham_id
                                    LEFT JOIN hinh_thuc_thanh_toan ht ON cthd.hinh_thuc_id = ht.id
                                    WHERE hd.ma = ?
                                    GROUP BY 
                                        hd.ma,
                                        sp.ten,
                                        cthd.don_gia,
                                        ht.ten,
                                        ctsp.san_pham_id,
                                        ctsp.cpu_id,
                                        ctsp.ram_id,
                                        ctsp.gpu_id,
                                        ctsp.ssd_id
                                    ORDER BY sp.ten;
    """;

    public List<ChiTietHoaDonDTO> findByMaHoaDon(String maHoaDon) {
        return XQuery.getBeanList(ChiTietHoaDonDTO.class, sql, maHoaDon);
    }
//    public List<GioHangDTO> findByHoaDonId(int hoaDonId) {
//    List<GioHangDTO> list = new ArrayList<>();
//    String sql = """
//        SELECT sp.ma_sp, sp.ten, cthd.don_gia, cthd.so_luong, cthd.giam_gia,
//               (cthd.don_gia * cthd.so_luong) - ISNULL(cthd.giam_gia, 0) AS thanh_tien
//        FROM chi_tiet_hoa_don cthd
//        JOIN chi_tiet_san_pham ctsp ON ctsp.id = cthd.chi_tiet_san_pham_id
//        JOIN san_pham sp ON sp.id = ctsp.san_pham_id
//        WHERE cthd.hoa_don_id = ?
//    """;
//
//    try (Connection con = XJdbc.openConnection();
//         PreparedStatement ps = con.prepareStatement(sql)) {
//        ps.setInt(1, hoaDonId);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            GioHangDTO gh = new GioHangDTO();
//            gh.setMaSP(rs.getString("ma_sp"));
//            gh.setTenSP(rs.getString("ten"));
//            gh.setDonGia(rs.getBigDecimal("don_gia"));
//            gh.setSoLuong(rs.getInt("so_luong"));
//            gh.setGiamGia(rs.getBigDecimal("giam_gia"));
//            gh.setThanhTien(rs.getBigDecimal("thanh_tien"));
//            list.add(gh);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return list;
//}

    public List<ChiTietHoaDon> getCTHDByHoaDonId(int hoaDonId) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM chi_tiet_hoa_don WHERE hoa_don_id = ?")) {
            ps.setInt(1, hoaDonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setId(rs.getInt("id"));
                cthd.setHoa_don_id(rs.getInt("hoa_don_id"));
                cthd.setChi_tiet_san_pham_id(rs.getInt("chi_tiet_san_pham_id"));
                cthd.setSo_luong(rs.getInt("so_luong"));
                cthd.setDon_gia(rs.getBigDecimal("don_gia"));
                cthd.setGiam_gia(rs.getBigDecimal("giam_gia"));
                cthd.setTrang_thai(rs.getInt("trang_thai"));
                list.add(cthd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa tất cả chi tiết hóa đơn theo chi_tiet_san_pham_id
    public void deleteByChiTietSanPhamId(int ctspId, Connection conn) throws SQLException {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE chi_tiet_san_pham_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ctspId);
            ps.executeUpdate();
        }
    }

    public void insert(ChiTietHoaDon cthd) {
        String sql = "INSERT INTO chi_tiet_hoa_don (hoa_don_id, chi_tiet_san_pham_id, so_luong, don_gia, trang_thai) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cthd.getHoa_don_id());
            ps.setInt(2, cthd.getChi_tiet_san_pham_id());
            ps.setInt(3, cthd.getSo_luong());
            ps.setBigDecimal(4, cthd.getDon_gia());
            ps.setInt(5, cthd.getTrang_thai());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChiTietHoaDon findByHoaDonAndCTSP(int hoaDonId, int ctspId) {
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE hoa_don_id = ? AND chi_tiet_san_pham_id = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hoaDonId);
            ps.setInt(2, ctspId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setId(rs.getInt("id"));
                cthd.setHoa_don_id(rs.getInt("hoa_don_id"));
                cthd.setChi_tiet_san_pham_id(rs.getInt("chi_tiet_san_pham_id"));
                cthd.setSo_luong(rs.getInt("so_luong"));
                cthd.setDon_gia(rs.getBigDecimal("don_gia"));
                cthd.setGiam_gia(rs.getBigDecimal("giam_gia"));
                cthd.setTrang_thai(rs.getInt("trang_thai"));
                return cthd;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(ChiTietHoaDon cthd) {
        String sql = "UPDATE chi_tiet_hoa_don SET "
                + "so_luong = ?, "
                + "don_gia = ?, "
                + "giam_gia = ?, "
                + "chi_tiet_dot_giam_gia_id = ?, "
                + "trang_thai = ?, "
                + "hinh_thuc_id = ? "
                + "WHERE id = ?";

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cthd.getSo_luong());
            ps.setBigDecimal(2, cthd.getDon_gia());
            ps.setBigDecimal(3, cthd.getGiam_gia());

            // Nếu có dot giảm giá → set bình thường, nếu không thì setNull
            if (cthd.getChi_tiet_dot_giam_gia_id() != null) {
                ps.setInt(4, cthd.getChi_tiet_dot_giam_gia_id());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, cthd.getTrang_thai());
            ps.setInt(6, cthd.getHinh_thuc_id());
            ps.setInt(7, cthd.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ChiTietHoaDon> getByHoaDonId(int hoaDonId) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE hoa_don_id = ?";

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hoaDonId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon();
                cthd.setId(rs.getInt("id"));
                cthd.setHoa_don_id(rs.getInt("hoa_don_id"));
                cthd.setChi_tiet_san_pham_id(rs.getInt("chi_tiet_san_pham_id"));
                cthd.setSo_luong(rs.getInt("so_luong"));
                cthd.setDon_gia(rs.getBigDecimal("don_gia"));
                cthd.setGiam_gia(rs.getBigDecimal("giam_gia"));
                cthd.setTrang_thai(rs.getInt("trang_thai"));
                list.add(cthd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateHinhThucThanhToan(int cthdId, int hinhThucId) {
        String sql = "UPDATE chi_tiet_hoa_don SET hinh_thuc_id = ? WHERE id = ?";
        XJdbc.executeUpdate(sql, hinhThucId, cthdId);
    }

    public void delete(int id) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE id = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Serial> getSerialByHoaDonId(int hoaDonId) {
        List<Serial> list = new ArrayList<>();
        String sql = "SELECT s.* "
                + "FROM serial s "
                + "INNER JOIN chi_tiet_san_pham ctsp ON s.ctsp_id = ctsp.id "
                + "INNER JOIN chi_tiet_hoa_don cthd ON cthd.chi_tiet_san_pham_id = ctsp.id "
                + "WHERE cthd.hoa_don_id = ?";

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hoaDonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serial s = new Serial();
                s.setId(rs.getInt("id"));
                s.setMa_serial(rs.getString("ma_serial"));
                s.setCtsp_id(rs.getInt("ctsp_id"));
                s.setTrang_thai(rs.getInt("trang_thai")); // trạng thái: 0=chưa bán, 1=đã bán, 2=đang giữ...
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi load Serial cho hóa đơn ID = " + hoaDonId + ": " + e.getMessage());
        }
        return list;
    }

}
