
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.KhuyenMai;
import DellStore.entity.SanPhamKhuyenMai;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author docon
 */
public class KhuyenMaiDAOImpl {
    
    
    String insertsql="""
                     INSERT INTO dot_giam_gia(ngay_bat_dau,ngay_ket_thuc,ten_km,ma_km,loai_giam,giam_gia,trang_thai)
                     VALUES (?,?,?,?,?,?,?);
                     """;
    String updatesql="""
                     UPDATE dot_giam_gia
                     SET ngay_bat_dau=?,ngay_ket_thuc=?,ten_km=?,loai_giam=?,giam_gia=?,trang_thai=?
                     WHERE id=?
                     """;
    
    String findByidsql="""
                       SELECT * FROM dot_giam_gia
                       WHERE id=?
                       """;
    String findByTensql="""
                     SELECT * FROM dot_giam_gia
                                            WHERE ten_km=?
                     
                     """;
    
    
   public KhuyenMai insertsqlAll(KhuyenMai kh) {
    String sql = "INSERT INTO dot_giam_gia (ngay_bat_dau, ngay_ket_thuc, ten_km, ma_km, loai_giam, giam_gia, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setDate(1, new java.sql.Date(kh.getNgay_bat_dau().getTime()));
        ps.setDate(2, new java.sql.Date(kh.getNgay_ket_thuc().getTime()));
        ps.setString(3, kh.getTen_km());
        ps.setString(4, kh.getMa_km());
        ps.setString(5, kh.getLoai_giam());
        ps.setBigDecimal(6, kh.getGiam_gia());
        ps.setInt(7, kh.getTrang_thai());

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int id = rs.getInt(1);
                kh.setId(id); // ⬅️ Gán ID vừa sinh
            }
        }

        return kh; // trả về đúng object đã được gán ID
    } catch (SQLException ex) {
        throw new RuntimeException("Lỗi khi thêm khuyến mãi: " + ex.getMessage(), ex);
    }
}
    
    public void updatesql(KhuyenMai kh){
        Object[] values = {
                kh.getNgay_bat_dau(),
                kh.getNgay_ket_thuc(),
                kh.getTen_km(),
                kh.getLoai_giam(),
                kh.getGiam_gia(),
                kh.getTrang_thai(),
                kh.getId()
               
            };
            XJdbc.executeUpdate(updatesql, values);
    }
    
    public KhuyenMai findById(int idKm) {
        return XQuery.getSingleBean(KhuyenMai.class, findByidsql, idKm);
    }
    public List<KhuyenMai> findByTrangThai(Integer trangThai) {
    List<KhuyenMai> list = new ArrayList<>();
    String sql = "SELECT * FROM dot_giam_gia ";

    if (trangThai != null) {
        sql += "WHERE trang_thai = ?";
    }

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        if (trangThai != null) {
            ps.setInt(1, trangThai);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            KhuyenMai km = new KhuyenMai();
            km.setId(rs.getInt("id"));
            km.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
            km.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
            km.setTen_km(rs.getString("ten_km"));
            km.setMa_km(rs.getString("ma_km"));
            km.setLoai_giam(rs.getString("loai_giam"));
            km.setGiam_gia(rs.getBigDecimal("giam_gia"));
            km.setTrang_thai(rs.getInt("trang_thai"));
            list.add(km);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
public List<SanPhamKhuyenMai> findAllSanPhamKhuyenMaiView() {
    List<SanPhamKhuyenMai> list = new ArrayList<>();

    String sql = """
        SELECT 
            sp.id AS sanPhamId,
            sp.ma_sp AS maSanPham,
            sp.ten AS tenSanPham,
            
            cpu.id AS cpuId,
            cpu.ten AS cpu,
            
            gpu.id AS gpuId,
            gpu.ten AS card,
            
            h.ten AS hang,
            
            ssd.id AS ssdId,
            ssd.dung_luong AS ocung,
            
            ram.id AS ramId,
            ram.dung_luong AS ram,
            
            ctsp.gia_ban AS giaBan,
            COUNT(*) AS soLuongBienThe
        FROM chi_tiet_san_pham ctsp
        JOIN san_pham sp ON sp.id = ctsp.san_pham_id
        JOIN ram ON ram.id = ctsp.ram_id
        JOIN cpu ON cpu.id = ctsp.cpu_id
        JOIN ssd ON ssd.id = ctsp.ssd_id
        JOIN gpu ON gpu.id = ctsp.gpu_id
        JOIN hang h ON h.id = sp.hang_id
        join serial sr on sr.ctsp_id=ctsp.id
        where sr.trang_thai=0
        GROUP BY 
            sp.id,
            sp.ma_sp,
            sp.ten,
            cpu.id,
            cpu.ten,
            gpu.id,
            gpu.ten,
            h.ten,
            ssd.id,
            ssd.dung_luong,
            ram.id,
            ram.dung_luong,
            ctsp.gia_ban
        ORDER BY sp.ma_sp;
    """;

    try (
        Connection con = XJdbc.openConnection(); 
        PreparedStatement ps = con.prepareStatement(sql); 
        ResultSet rs = ps.executeQuery()
    ) {
        while (rs.next()) {
            SanPhamKhuyenMai sp = SanPhamKhuyenMai.builder()
                .sanPhamId(rs.getInt("sanPhamId"))
                .maSanPham(rs.getString("maSanPham"))
                .tenSanPham(rs.getString("tenSanPham"))

                .cpuId(rs.getInt("cpuId"))
                .cpu(rs.getString("cpu"))

                .gpuId(rs.getInt("gpuId"))
                .card(rs.getString("card"))
                 
                .hang(rs.getString("hang"))

                .ssdId(rs.getInt("ssdId"))
                .ocung(rs.getString("ocung"))

                .ramId(rs.getInt("ramId"))
                .ram(rs.getString("ram"))

                .giaBan(rs.getBigDecimal("giaBan"))
                .soLuongBienThe(rs.getInt("soLuongBienThe"))
                .build();

            list.add(sp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    public boolean isSanPhamDaDuocGiamGia(int chiTietSanPhamId) throws SQLException {//hàm kiểm tra sản phẩm check sản phẩm đã được áp dụng chưa?
    String sql = "SELECT COUNT(*) FROM chi_tiet_dot_giam_gia WHERE chi_tiet_san_pham_id = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, chiTietSanPhamId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}
    
    public KhuyenMai findByTen(String ten_km) {
        return XQuery.getSingleBean(KhuyenMai.class, findByTensql, ten_km);
    }
    
    
    
 public List<KhuyenMai> findAll() {
    List<KhuyenMai> list = new ArrayList<>();

    String updateSQL = """
         UPDATE dot_giam_gia
                             SET trang_thai = CASE 
                                                 WHEN ngay_bat_dau > CAST(GETDATE() AS DATE) THEN 2
                                                 WHEN ngay_ket_thuc < CAST(GETDATE() AS DATE) THEN 0
                                                 ELSE 1
                                              END
    """;

    String selectSQL = "SELECT * FROM dot_giam_gia";

    try (Connection con = XJdbc.openConnection()) {

        // 1. Cập nhật trạng thái tự động
        try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
            ps.executeUpdate();
        }

        // 2. Xoá các chi tiết khuyến mãi hết hiệu lực
        KhuyenMaiDAOImpl chiTietDAO = new KhuyenMaiDAOImpl();
        chiTietDAO.xoaChiTietCuaDotHetHieuLuc();

        // 3. Truy vấn toàn bộ khuyến mãi
        try (PreparedStatement ps = con.prepareStatement(selectSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuyenMai dgg = new KhuyenMai();
                dgg.setId(rs.getInt("id"));
                dgg.setMa_km(rs.getString("ma_km"));
                dgg.setTen_km(rs.getString("ten_km"));
                dgg.setLoai_giam(rs.getString("loai_giam"));
                dgg.setGiam_gia(rs.getBigDecimal("giam_gia"));
                dgg.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
                dgg.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
                dgg.setTrang_thai(rs.getInt("trang_thai"));

                list.add(dgg);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    public List<SanPhamKhuyenMai> getSanPhamTheoDotGiamGia(int dotGiamGiaId) {
    String sql = """
      SELECT 
                        sp.id AS sanPhamId,
                        sp.ma_sp AS maSanPham,
                        sp.ten AS tenSanPham,
                    
                        cpu.id AS cpuId,
                        cpu.ten AS cpu,
                    
                        gpu.id AS gpuId,
                        gpu.ten AS card,
                        
                        hang.ten AS hang,
                    
                        ssd.id AS ssdId,
                        ssd.dung_luong AS ocung,
                    
                        ram.id AS ramId,
                        ram.dung_luong AS ram,
                    
                        ctsp.gia_ban AS giaBan,
                        COUNT(ctsp.id) AS soLuongBienThe
                    FROM chi_tiet_dot_giam_gia ctgg
                    JOIN chi_tiet_san_pham ctsp ON ctgg.ctsp_id = ctsp.id
                    JOIN san_pham sp ON ctsp.san_pham_id = sp.id
                    JOIN cpu ON ctsp.cpu_id = cpu.id
                    JOIN ram ON ctsp.ram_id = ram.id
                    JOIN gpu ON ctsp.gpu_id = gpu.id
                    JOIN ssd ON ctsp.ssd_id = ssd.id
                    JOIN hang ON sp.hang_id = hang.id
                    join serial sr on sr.ctsp_id=ctsp.id
                    WHERE ctgg.dot_giam_gia_id = ? and sr.trang_thai=0
                    GROUP BY 
                        sp.id, sp.ma_sp, sp.ten,
                        cpu.id, cpu.ten,
                        gpu.id, gpu.ten,
                        hang.ten,
                        ssd.id, ssd.dung_luong,
                        ram.id, ram.dung_luong,ctsp.gia_ban
    """;

    return XQuery.getBeanList(SanPhamKhuyenMai.class, sql, dotGiamGiaId);
}
   
     public void xoaChiTietCuaDotHetHieuLuc() {
        String sql = """
            DELETE FROM chi_tiet_dot_giam_gia 
            WHERE dot_giam_gia_id IN (
                SELECT id FROM dot_giam_gia WHERE trang_thai = 0
            )
        """;

        try {
            XJdbc.executeUpdate(sql);
            System.out.println("Đã xoá chi tiết các đợt giảm giá hết hiệu lực.");
        } catch (Exception e) {
            System.err.println("Lỗi khi xoá chi tiết đợt giảm giá: " + e.getMessage());
        }
    }
    public KhuyenMai findActiveByChiTietSanPhamId(int ctspId, Connection conn) throws SQLException {
    String sql = """
        SELECT dgg.*
        FROM chi_tiet_dot_giam_gia ctdgg
        JOIN dot_giam_gia dgg ON ctdgg.dot_giam_gia_id = dgg.id
        WHERE ctdgg.ctsp_id = ? AND dgg.trang_thai = 1
    """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ctspId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                KhuyenMai dgg = new KhuyenMai();
                dgg.setId(rs.getInt("id"));
                dgg.setMa_km(rs.getString("ma_km"));
                dgg.setTen_km(rs.getString("ten_km"));
                dgg.setLoai_giam(rs.getString("loai_giam"));
                dgg.setGiam_gia(rs.getBigDecimal("giam_gia"));
                dgg.setTrang_thai(rs.getInt("trang_thai"));
                return dgg;
            }
        }
    }
    return null;
}
public KhuyenMai findActiveByChiTietSanPhamId1(int ctspId) {
    String sql = """
        SELECT dgg.*
        FROM chi_tiet_dot_giam_gia ctdgg
        JOIN dot_giam_gia dgg ON ctdgg.dot_giam_gia_id = dgg.id
        WHERE ctdgg.ctsp_id = ? AND dgg.trang_thai = 1
    """;

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, ctspId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                KhuyenMai dgg = new KhuyenMai();
                dgg.setId(rs.getInt("id"));
                dgg.setMa_km(rs.getString("ma_km"));
                dgg.setTen_km(rs.getString("ten_km"));
                dgg.setLoai_giam(rs.getString("loai_giam"));
                dgg.setGiam_gia(rs.getBigDecimal("giam_gia"));
                dgg.setTrang_thai(rs.getInt("trang_thai"));
                return dgg;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    public BigDecimal getTongGiamGiaTheoHoaDon(int hoaDonId) {
    BigDecimal tongGiam = BigDecimal.ZERO;
    String sql = """
        SELECT ISNULL(SUM(
            CASE 
                WHEN dgg.loai_giam = '%' THEN cthd.don_gia * dgg.giam_gia / 100
                ELSE dgg.giam_gia
            END
        ), 0) AS tong_giam
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_dot_giam_gia ctdgg ON cthd.chi_tiet_san_pham_id = ctdgg.ctsp_id
        JOIN dot_giam_gia dgg ON ctdgg.dot_giam_gia_id = dgg.id
        WHERE cthd.hoa_don_id = ? AND dgg.trang_thai = 1
    """;

    try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, hoaDonId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            tongGiam = rs.getBigDecimal(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return tongGiam;
}

    // Xóa chi tiết đợt giảm giá theo ctsp_id
  public void deleteChiTietDotGiamGiaByCtspId(int ctspId, Connection conn) throws SQLException {
    String sql = "DELETE FROM chi_tiet_dot_giam_gia WHERE ctsp_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ctspId);
        ps.executeUpdate();
    }
}

   
    
    
}

