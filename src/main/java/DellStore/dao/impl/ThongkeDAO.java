package DellStore.dao.impl;

import DellStore.entity.DoanhThuDTO;
import DellStore.entity.Thongketheongay;
import DellStore.entity.ThongketheosanphamDTO;
import DellStore.utils.XJdbc;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThongkeDAO {

    // Thống kê tất cả sản phẩm
    public List<ThongketheosanphamDTO> thongkeSanPham() {
        String sql = """
                SELECT 
                    sp.ma_sp AS [MaSP], 
                    sp.ten AS [TenSP], 
                    ctsp.gia_ban AS [Gia], 
                    SUM(cthd.so_luong) AS [SLBan], 
                    SUM(cthd.so_luong * cthd.don_gia) AS [DoanhThu] 
                FROM chi_tiet_hoa_don cthd 
                JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id 
                JOIN san_pham sp ON ctsp.san_pham_id = sp.id 
                GROUP BY sp.ma_sp, sp.ten, ctsp.gia_ban
                """;

        List<ThongketheosanphamDTO> list = new ArrayList<>();
        try (Connection conn = XJdbc.openConnection(); var ps = conn.prepareStatement(sql); var rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(ThongketheosanphamDTO.builder()
                        .MaSP(rs.getString("MaSP"))
                        .TenSP(rs.getString("TenSP"))
                        .Gia(rs.getDouble("Gia"))
                        .SLBan(rs.getInt("SLBan"))
                        .DoanhThu(rs.getDouble("DoanhThu"))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thống kê sản phẩm: " + e.getMessage(), e);
        }
    }

    public List<ThongketheosanphamDTO> thongkeSanPhamTheoNgay(LocalDate ngay) {
    String sql = """
        SELECT 
            sp.ma_sp AS MaSP,
            sp.ten AS TenSP,
            ctsp.gia_ban AS Gia,
            cthd.so_luong AS SLBan,
            (cthd.so_luong * cthd.don_gia) AS DoanhThu
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
        JOIN san_pham sp ON ctsp.san_pham_id = sp.id
        JOIN hoa_don hd ON cthd.hoa_don_id = hd.id
        WHERE CAST(hd.ngay_tao AS DATE) = ?
        ORDER BY sp.ma_sp
    """;

    List<ThongketheosanphamDTO> list = new ArrayList<>();
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDate(1, java.sql.Date.valueOf(ngay));

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(ThongketheosanphamDTO.builder()
                        .MaSP(rs.getString("MaSP"))
                        .TenSP(rs.getString("TenSP"))
                        .Gia(rs.getDouble("Gia"))
                        .SLBan(rs.getInt("SLBan"))
                        .DoanhThu(rs.getDouble("DoanhThu"))
                        .build());
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi khi thống kê sản phẩm theo ngày: " + e.getMessage(), e);
    }
    return list;
}

    public List<ThongketheosanphamDTO> thongkeSanPhamTheoThangHienTai() {
        LocalDate now = LocalDate.now();
        int nam = now.getYear();
        int thang = now.getMonthValue();

        String sql = """
        SELECT 
            sp.ma_sp AS MaSP, 
            sp.ten AS TenSP, 
            ctsp.gia_ban AS Gia,
            SUM(cthd.so_luong) AS SLBan, 
            SUM(cthd.so_luong * cthd.don_gia) AS DoanhThu
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
        JOIN san_pham sp ON ctsp.san_pham_id = sp.id
        JOIN hoa_don hd ON cthd.hoa_don_id = hd.id
        WHERE YEAR(hd.ngay_tao) = ? AND MONTH(hd.ngay_tao) = ?
        GROUP BY sp.ma_sp, sp.ten, ctsp.gia_ban
    """;

        List<ThongketheosanphamDTO> list = new ArrayList<>();
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nam);
            ps.setInt(2, thang);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongketheosanphamDTO(
                            rs.getString("MaSP"),
                            rs.getString("TenSP"),
                            rs.getInt("SLBan"),
                            rs.getDouble("DoanhThu"),
                            rs.getDouble("Gia")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thống kê hóa đơn theo ngày
    public List<Thongketheongay> thongKeHoaDonTheoNgay(LocalDate ngay) {
        String sql = """
                SELECT 
                    hd.ma AS [MaHD], 
                    nv.ma_nv AS [MaNV], 
                    nv.ten_nv AS [TenNV], 
                    httt.ten AS [HinhThuc], 
                    cttt.so_tien AS [TienMat], 
                    hd.tong_tien AS [TongTien]
                FROM hoa_don hd
                JOIN nhan_vien nv ON hd.nhan_vien_id = nv.id
                JOIN chi_tiet_thanh_toan cttt ON hd.id = cttt.hoa_don_id
                JOIN hinh_thuc_thanh_toan httt ON cttt.hinh_thuc_thanh_toan_id = httt.id
                WHERE CAST(hd.ngay_tao AS date) = ?
                """;

        List<Thongketheongay> list = new ArrayList<>();
        try (Connection conn = XJdbc.openConnection(); var ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(ngay));

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(Thongketheongay.builder()
                            .maHD(rs.getString("MaHD"))
                            .maNV(rs.getString("MaNV"))
                            .tenNV(rs.getString("TenNV"))
                            .hinhThuc(rs.getString("HinhThuc"))
                            .tienMat(rs.getDouble("TienMat"))
                            .tongTien(rs.getDouble("TongTien"))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thống kê hóa đơn theo ngày: " + e.getMessage(), e);
        }
        return list;
    }

    // Tính tổng doanh thu theo ngày
    public double tongDoanhThuTheoNgay(LocalDate ngay) {
        String sql = """
                SELECT SUM(hd.tong_tien) AS [TongDoanhThu]
                FROM hoa_don hd 
                WHERE CAST(hd.ngay_tao AS date) = ?
                """;

        try (Connection conn = XJdbc.openConnection(); var ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(ngay));

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("TongDoanhThu");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tính tổng doanh thu theo ngày: " + e.getMessage(), e);
        }
        return 0;
    }
    public Map<Integer, Double> getDoanhThuTheoTungThangTrongNam(int nam) {
    Map<Integer, Double> result = new LinkedHashMap<>();
    String sql = """
        SELECT MONTH(hd.ngay_tao) AS thang,
               SUM(cthd.so_luong * cthd.don_gia - ISNULL(cthd.giam_gia, 0)) AS doanhthu
        FROM hoa_don hd
        JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
        WHERE YEAR(hd.ngay_tao) = ?
        GROUP BY MONTH(hd.ngay_tao)
        ORDER BY MONTH(hd.ngay_tao)
    """;
    try {
        ResultSet rs = XJdbc.executeQuery(sql, nam);
        while (rs.next()) {
            result.put(rs.getInt("thang"), rs.getDouble("doanhthu"));
        }
        rs.getStatement().getConnection().close(); // đóng kết nối sau khi đọc xong
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return result;
}
    public Map<Integer, Double> getDoanhThuTheoTungNgayTrongThang(int thang, int nam) {
    Map<Integer, Double> result = new LinkedHashMap<>();
    String sql = """
        SELECT DAY(hd.ngay_tao) AS ngay,
               SUM(cthd.so_luong * cthd.don_gia - ISNULL(cthd.giam_gia, 0)) AS doanhthu
        FROM hoa_don hd
        JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
        WHERE MONTH(hd.ngay_tao) = ? AND YEAR(hd.ngay_tao) = ?
        GROUP BY DAY(hd.ngay_tao)
        ORDER BY DAY(hd.ngay_tao)
    """;
    try {
        ResultSet rs = XJdbc.executeQuery(sql, thang, nam);
        while (rs.next()) {
            result.put(rs.getInt("ngay"), rs.getDouble("doanhthu"));
        }
        rs.getStatement().getConnection().close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return result;
}



    public List<ThongketheosanphamDTO> getDoanhThuTheoNgay(java.sql.Date ngay) {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
        SELECT sp.ma_sp, sp.ten_sp, sp.gia, SUM(cthd.so_luong) AS so_luong_ban,
               SUM(cthd.so_luong * sp.gia) AS doanh_thu
        FROM hoa_don hd
        JOIN chi_tiet_hoa_don cthd ON hd.id = cthd.hoa_don_id
        JOIN san_pham sp ON cthd.san_pham_id = sp.id
        WHERE hd.ngay_tao >= ? AND hd.ngay_tao < DATEADD(DAY, 1, ?)
        GROUP BY sp.ma_sp, sp.ten_sp, sp.gia
    """;
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, ngay);
            ps.setDate(2, ngay);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ThongketheosanphamDTO(
                        rs.getString("ma_sp"),
                        rs.getString("ten_sp"),
                        rs.getInt("so_luong_ban"),
                        rs.getDouble("doanh_thu"),
                        rs.getDouble("gia")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThongketheosanphamDTO> getDoanhThuTheoNgay(Date date) throws SQLException {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
        SELECT masp, tensp, gia,
               SUM(soluong) AS slBan,
               SUM(soluong * gia) AS doanhThu
        FROM hoadon
        WHERE CAST(ngayban AS DATE) = CAST(? AS DATE)
        GROUP BY masp, tensp, gia
        """;

        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(date.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongketheosanphamDTO(
                            rs.getString("masp"),
                            rs.getString("tensp"),
                            rs.getInt("slBan"),
                            rs.getDouble("doanhThu"),
                            rs.getDouble("gia")
                    ));
                }
            }
        }
        return list;
    }

    public List<ThongketheosanphamDTO> getDoanhThuTheoThang(Date date) throws SQLException {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
        SELECT masp, tensp, gia,
               SUM(soluong) AS slBan,
               SUM(soluong * gia) AS doanhThu
        FROM hoadon
        WHERE MONTH(ngayban) = MONTH(?)
          AND YEAR(ngayban) = YEAR(?)
        GROUP BY masp, tensp, gia
        """;

        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongketheosanphamDTO(
                            rs.getString("masp"),
                            rs.getString("tensp"),
                            rs.getInt("slBan"),
                            rs.getDouble("doanhThu"),
                            rs.getDouble("gia")
                    ));
                }
            }
        }
        return list;
    }

    public List<ThongketheosanphamDTO> getDoanhThuTheoNam(Date date) throws SQLException {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
        SELECT masp, tensp, gia,
               SUM(soluong) AS slBan,
               SUM(soluong * gia) AS doanhThu
        FROM hoadon
        WHERE YEAR(ngayban) = YEAR(?)
        GROUP BY masp, tensp, gia
        """;

        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(date.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongketheosanphamDTO(
                            rs.getString("masp"),
                            rs.getString("tensp"),
                            rs.getInt("slBan"),
                            rs.getDouble("doanhThu"),
                            rs.getDouble("gia")
                    ));
                }
            }
        }
        return list;
    }
 // Doanh thu theo ngày
    public List<ThongketheosanphamDTO> getDoanhThuTheoNgay1(Date ngay) {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
            SELECT sp.ten, SUM(ct.so_luong) AS SoLuong, 
                   SUM(ct.so_luong * ct.don_gia) AS DoanhThu
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id = ct.hoa_don_id
            JOIN san_pham sp ON ct.chi_tiet_san_pham_id = sp.id
            WHERE hd.ngay_tao = ?
            GROUP BY sp.ten
            ORDER BY DoanhThu DESC
        """;
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(ngay.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongketheosanphamDTO dto = new ThongketheosanphamDTO();
                dto.setTenSP(rs.getString("ten"));
                dto.setSLBan(rs.getInt("SoLuong"));
                dto.setDoanhThu(rs.getDouble("DoanhThu"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doanh thu theo tháng
    public List<ThongketheosanphamDTO> getDoanhThuTheoThang1(Date ngay) {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
            SELECT sp.ten, SUM(ct.so_luong) AS SoLuong, 
                   SUM(ct.so_luong * ct.don_gia) AS DoanhThu
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id = ct.hoa_don_id
            JOIN san_pham sp ON ct.chi_tiet_san_pham_id = sp.id
            WHERE MONTH(hd.ngay_tao) = ? AND YEAR(hd.ngay_tao) = ?
            GROUP BY sp.ten
            ORDER BY DoanhThu DESC
        """;
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngay);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongketheosanphamDTO dto = new ThongketheosanphamDTO();
                dto.setTenSP(rs.getString("ten"));
                dto.setSLBan(rs.getInt("SoLuong"));
                dto.setDoanhThu(rs.getDouble("DoanhThu"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doanh thu theo năm
    public List<ThongketheosanphamDTO> getDoanhThuTheoNam1(Date ngay) {
        List<ThongketheosanphamDTO> list = new ArrayList<>();
        String sql = """
            SELECT sp.ten, SUM(ct.so_luong) AS SoLuong, 
                   SUM(ct.so_luong * ct.don_gia) AS DoanhThu
            FROM hoa_don hd
            JOIN chi_tiet_hoa_don ct ON hd.id = ct.hoa_don_id
            JOIN san_pham sp ON ct.chi_tiet_san_pham_id = sp.id
            WHERE YEAR(hd.ngay_tao) = ?
            GROUP BY sp.ten
            ORDER BY DoanhThu DESC
        """;
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngay);
        int year = cal.get(Calendar.YEAR);

        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongketheosanphamDTO dto = new ThongketheosanphamDTO();
                dto.setTenSP(rs.getString("ten"));
                dto.setSLBan(rs.getInt("SoLuong"));
                dto.setDoanhThu(rs.getDouble("DoanhThu"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ThongketheosanphamDTO> thongkeSanPhamTheoThang(int thang, int nam) {
    String sql = """
        SELECT 
            sp.ma_sp AS MaSP,
            sp.ten AS TenSP,
            ctsp.gia_ban AS Gia,
            SUM(cthd.so_luong) AS SLBan,
            SUM(cthd.so_luong * cthd.don_gia) AS DoanhThu
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
        JOIN san_pham sp ON ctsp.san_pham_id = sp.id
        JOIN hoa_don hd ON cthd.hoa_don_id = hd.id
        WHERE MONTH(hd.ngay_tao) = ? AND YEAR(hd.ngay_tao) = ?
        GROUP BY sp.ma_sp, sp.ten, ctsp.gia_ban
        ORDER BY sp.ma_sp
    """;

    List<ThongketheosanphamDTO> list = new ArrayList<>();
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, thang);
        ps.setInt(2, nam);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(ThongketheosanphamDTO.builder()
                        .MaSP(rs.getString("MaSP"))
                        .TenSP(rs.getString("TenSP"))
                        .Gia(rs.getDouble("Gia"))
                        .SLBan(rs.getInt("SLBan"))
                        .DoanhThu(rs.getDouble("DoanhThu"))
                        .build());
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi khi thống kê sản phẩm theo tháng: " + e.getMessage(), e);
    }
    return list;
}
public List<ThongketheosanphamDTO> thongkeSanPhamTheoNam(int nam) {
    String sql = """
        SELECT 
            sp.ma_sp AS MaSP,
            sp.ten AS TenSP,
            ctsp.gia_ban AS Gia,
            SUM(cthd.so_luong) AS SLBan,
            SUM(cthd.so_luong * cthd.don_gia) AS DoanhThu
        FROM chi_tiet_hoa_don cthd
        JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
        JOIN san_pham sp ON ctsp.san_pham_id = sp.id
        JOIN hoa_don hd ON cthd.hoa_don_id = hd.id
        WHERE YEAR(hd.ngay_tao) = ?
        GROUP BY sp.ma_sp, sp.ten, ctsp.gia_ban
        ORDER BY sp.ma_sp
    """;

    List<ThongketheosanphamDTO> list = new ArrayList<>();
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, nam);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(ThongketheosanphamDTO.builder()
                        .MaSP(rs.getString("MaSP"))
                        .TenSP(rs.getString("TenSP"))
                        .Gia(rs.getDouble("Gia"))
                        .SLBan(rs.getInt("SLBan"))
                        .DoanhThu(rs.getDouble("DoanhThu"))
                        .build());
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi khi thống kê sản phẩm theo năm: " + e.getMessage(), e);
    }
    return list;
}

}
