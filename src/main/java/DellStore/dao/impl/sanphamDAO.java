package DellStore.dao.impl;

import DellStore.entity.BienTheSanPhamViewModel;
import DellStore.entity.SanPhamChiTietViewModel;
import DellStore.entity.ChiTietSanPham;
import DellStore.entity.SanPham;
import DellStore.utils.XJdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class sanphamDAO {
public List<SanPhamChiTietViewModel> findByThuocTinh(
        int sanPhamId, int cpuId, int ramId, int gpuId, int ssdId, String maHoaDon) {

    List<SanPhamChiTietViewModel> list = new ArrayList<>();
    String sql = """
       SELECT 
              ctsp.id,
              sp.ten AS tenSanPham,
              cpu.ten AS tenCpu,
              ram.dung_luong AS tenRam,
              gpu.ten AS tenGpu,
              h.ten AS tenHang,
              ssd.dung_luong AS tenSsd,
              s.ma_serial AS maSerial,
              ctsp.gia_ban,
              s.trang_thai
          FROM chi_tiet_hoa_don cthd
          JOIN hoa_don hd ON hd.id = cthd.hoa_don_id
          JOIN chi_tiet_san_pham ctsp ON cthd.chi_tiet_san_pham_id = ctsp.id
          JOIN san_pham sp ON sp.id = ctsp.san_pham_id
          LEFT JOIN cpu cpu ON cpu.id = ctsp.cpu_id
          LEFT JOIN ram ram ON ram.id = ctsp.ram_id
          LEFT JOIN gpu gpu ON gpu.id = ctsp.gpu_id
          LEFT JOIN ssd ssd ON ssd.id = ctsp.ssd_id
          LEFT JOIN serial s ON s.ctsp_id = ctsp.id
          LEFT JOIN hang h ON h.id = sp.hang_id
          WHERE sp.id = ? 
            AND ctsp.cpu_id = ?
            AND ctsp.ram_id = ?
            AND ctsp.gpu_id = ?
            AND ctsp.ssd_id = ?
            AND hd.ma = ?;
        """;

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, sanPhamId);
        ps.setInt(2, cpuId);
        ps.setInt(3, ramId);
        ps.setInt(4, gpuId);
        ps.setInt(5, ssdId);
        ps.setString(6, maHoaDon);

        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
    SanPhamChiTietViewModel spct = new SanPhamChiTietViewModel();
    spct.setId(rs.getInt("id"));
    spct.setTenSanPham(rs.getString("tenSanPham"));
    spct.setCpu(rs.getString("tenCpu"));
    spct.setCard(rs.getString("tenGpu"));      // Card đồ họa (GPU)
    spct.setHang(rs.getString("tenHang"));     // Hãng sản xuất
    spct.setOcung(rs.getString("tenSsd"));     // Ổ cứng (SSD)
    spct.setRam(rs.getString("tenRam"));       // RAM
    spct.setSerial(rs.getString("maSerial"));  // Mã serial
    spct.setGiaBan(rs.getBigDecimal("gia_ban")); // Giá bán
    spct.setTrangThai(rs.getInt("trang_thai"));  // Trạng thái
    list.add(spct);
}
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    public SanPham findByMa(String maSP) {
        String sql = "SELECT * FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id"));
                sp.setTen(rs.getString("ten"));
                sp.setMasp(rs.getString("ma_sp"));
                sp.setMo_ta(rs.getString("mo_ta"));
                sp.setLoai_san_pham_id(rs.getInt("loai_san_pham_id"));
                sp.setHang_id(rs.getInt("hang_id"));
                sp.setTrang_thai(rs.getInt("trang_thai"));
                sp.setSo_luong(rs.getInt("so_luong"));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String findTenById(int id) {
        String sql = "SELECT ten FROM san_pham WHERE id = ?";
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // không tìm thấy
    }

    public boolean checkMaSPExists(String maSP) {
        String sql = "SELECT COUNT(*) FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void giamSoLuongSanPhamById(int sanPhamId) {
        try {
            String sql = "UPDATE san_pham SET so_luong = so_luong - 1 WHERE id = ?";
            Connection con = XJdbc.openConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sanPhamId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public List<SanPham> findAll() {
    List<SanPham> list = new ArrayList<>();
    try {
        Connection con = XJdbc.openConnection();
        String sql = "SELECT id, ten, ma_sp, mo_ta, loai_san_pham_id, hang_id, trang_thai, so_luong FROM san_pham";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SanPham sp = new SanPham();

            sp.setId(rs.getInt("id")); // ✅ thêm dòng này
            sp.setTen(rs.getString("ten"));
            sp.setMasp(rs.getString("ma_sp"));
            sp.setMo_ta(rs.getString("mo_ta"));
            sp.setLoai_san_pham_id(rs.getInt("loai_san_pham_id"));
            sp.setHang_id(rs.getInt("hang_id"));
            sp.setTrang_thai(rs.getInt("trang_thai"));
            sp.setSo_luong(rs.getInt("so_luong"));

            list.add(sp);
        }
        rs.close();
        ps.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    public boolean insert(SanPham sp) {
        try {
            Connection con = XJdbc.openConnection();
            String sql = "INSERT INTO san_pham (ten, ma_sp, mo_ta, loai_san_pham_id, hang_id, trang_thai, so_luong) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sp.getTen());
            ps.setString(2, sp.getMasp());
            ps.setString(3, sp.getMo_ta());
            ps.setInt(4, sp.getLoai_san_pham_id());
            ps.setInt(5, sp.getHang_id());
            ps.setInt(6, sp.getTrang_thai());
            ps.setInt(7, sp.getSo_luong());
            boolean success = ps.executeUpdate() > 0;
            ps.close();
            con.close();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertAndReturnId(SanPham sp) {
        String sql = "INSERT INTO san_pham (ten, ma_sp, mo_ta, loai_san_pham_id, hang_id, trang_thai, so_luong) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getTen());
            ps.setString(2, sp.getMasp());
            ps.setString(3, sp.getMo_ta());
            ps.setInt(4, sp.getLoai_san_pham_id());
            ps.setInt(5, sp.getHang_id());
            ps.setInt(6, sp.getTrang_thai());
            ps.setInt(7, sp.getSo_luong());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy ID tự tăng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void update(SanPham sp) {
        try {
            Connection con = XJdbc.openConnection();
            String sql = "UPDATE san_pham SET ten=?, mo_ta=?, loai_san_pham_id=?, hang_id=?, trang_thai=? WHERE ma_sp=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sp.getTen());
            ps.setString(2, sp.getMo_ta());
            ps.setInt(3, sp.getLoai_san_pham_id());
            ps.setInt(4, sp.getHang_id());
            ps.setInt(5, sp.getTrang_thai());
            ps.setString(6, sp.getMasp()); // dùng mã sản phẩm để WHERE
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String masp) {
        try {
            Connection con = XJdbc.openConnection();
            String sql = "DELETE FROM san_pham WHERE ma_sp=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, masp);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//dangtuan11/8
public List<SanPhamChiTietViewModel> getChiTietBienThe(
        int sanPhamId, int cpuId, int gpuId, int ssdId, int ramId) {

    List<SanPhamChiTietViewModel> list = new ArrayList<>();
    String sql = """
        SELECT ctsp.id, sp.ten AS ten_sp, cpu.ten AS cpu, gpu.ten AS card, h.ten AS hang,
               ssd.dung_luong AS ocung, ram.dung_luong AS ram,
               sr.ma_serial AS serial, ctsp.gia_ban, sr.trang_thai
        FROM chi_tiet_san_pham ctsp
        JOIN san_pham sp ON sp.id = ctsp.san_pham_id
        JOIN ram ON ram.id = ctsp.ram_id
        JOIN cpu ON cpu.id = ctsp.cpu_id
        JOIN ssd ON ssd.id = ctsp.ssd_id
        JOIN gpu ON gpu.id = ctsp.gpu_id
        JOIN hang h ON h.id = sp.hang_id
        LEFT JOIN serial sr ON sr.ctsp_id = ctsp.id
        WHERE ctsp.san_pham_id = ?
          AND ctsp.cpu_id = ?
          AND ctsp.gpu_id = ?
          AND ctsp.ssd_id = ?
          AND ctsp.ram_id = ?
    """;

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, sanPhamId);
        ps.setInt(2, cpuId);
        ps.setInt(3, gpuId);
        ps.setInt(4, ssdId);
        ps.setInt(5, ramId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPhamChiTietViewModel sp = new SanPhamChiTietViewModel(
                        rs.getInt("id"),
                        rs.getString("ten_sp"),
                        rs.getString("cpu"),
                        rs.getString("card"),
                        rs.getString("hang"),
                        rs.getString("ocung"),
                        rs.getString("ram"),
                        rs.getString("serial"),
                        rs.getBigDecimal("gia_ban"),
                        rs.getInt("trang_thai")
                );
                list.add(sp);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    public List<SanPhamChiTietViewModel> findAllView() {
        List<SanPhamChiTietViewModel> list = new ArrayList<>();
        String sql = """
                SELECT ctsp.id, sp.ten AS ten_sp, cpu.ten AS cpu, gpu.ten AS card, h.ten AS hang,
                  ssd.dung_luong AS ocung, ram.dung_luong AS ram,
                  sr.ma_serial AS serial, ctsp.gia_ban, ctsp.trang_thai
           FROM chi_tiet_san_pham ctsp
           JOIN san_pham sp ON sp.id = ctsp.san_pham_id
           JOIN ram ON ram.id = ctsp.ram_id
           JOIN cpu ON cpu.id = ctsp.cpu_id
           JOIN ssd ON ssd.id = ctsp.ssd_id
           JOIN gpu ON gpu.id = ctsp.gpu_id
           JOIN hang h ON h.id = sp.hang_id
           LEFT JOIN serial sr ON sr.ctsp_id = ctsp.id
    """;

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPhamChiTietViewModel sp = new SanPhamChiTietViewModel(
                        rs.getInt("id"),
                        rs.getString("ten_sp"),
                        rs.getString("cpu"),
                        rs.getString("card"),
                        rs.getString("hang"),
                        rs.getString("ocung"),
                        rs.getString("ram"),
                        rs.getString("serial"),
                        rs.getBigDecimal("gia_ban"),
                        rs.getInt("trang_thai")
                );
                list.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // Sửa method này trong SanPhamDAO.java
    //dangtuan11/8
    public List<BienTheSanPhamViewModel> findBienTheByMaSP(String maSP) {
    List<BienTheSanPhamViewModel> list = new ArrayList<>();
    String sql = """
        SELECT 
            sp.id AS san_pham_id,
            cpu.id AS cpu_id,
            gpu.id AS gpu_id,
            ssd.id AS ssd_id,
            ram.id AS ram_id,

            sp.ten AS ten_sp,
            cpu.ten AS cpu,
            gpu.ten AS card,
            h.ten AS hang,
            ssd.dung_luong AS ocung,
            ram.dung_luong AS ram,
            ctsp.gia_ban AS giaBan,
            COUNT(sr.id) AS soLuong
        FROM chi_tiet_san_pham ctsp
        JOIN san_pham sp ON sp.id = ctsp.san_pham_id
        JOIN ram ON ram.id = ctsp.ram_id
        JOIN cpu ON cpu.id = ctsp.cpu_id
        JOIN ssd ON ssd.id = ctsp.ssd_id
        JOIN gpu ON gpu.id = ctsp.gpu_id
        JOIN hang h ON h.id = sp.hang_id
        LEFT JOIN serial sr 
            ON sr.ctsp_id = ctsp.id 
           AND sr.trang_thai = 0 -- 0: Đang bán
        WHERE sp.ma_sp = ?
        GROUP BY sp.id, cpu.id, gpu.id, ssd.id, ram.id,
                 sp.ten, cpu.ten, gpu.ten, h.ten, ssd.dung_luong, ram.dung_luong, ctsp.gia_ban
        ORDER BY ram.dung_luong, ssd.dung_luong, cpu.ten
    """;

    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maSP);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BienTheSanPhamViewModel bt = BienTheSanPhamViewModel.builder()
                    .san_pham_id(rs.getInt("san_pham_id"))
                    .cpu_id(rs.getInt("cpu_id"))
                    .gpu_id(rs.getInt("gpu_id"))
                    .ssd_id(rs.getInt("ssd_id"))
                    .ram_id(rs.getInt("ram_id"))

                    .ten_sp(rs.getString("ten_sp"))
                    .cpu(rs.getString("cpu"))
                    .card(rs.getString("card"))
                    .hang(rs.getString("hang"))
                    .ocung(rs.getString("ocung"))
                    .ram(rs.getString("ram"))
                    .giaBan(rs.getBigDecimal("giaBan"))
                    .soLuong(rs.getInt("soLuong"))
                    .build();
                list.add(bt);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    public String findIdByName2(String ten) {
        String sql = "SELECT ma_sp FROM san_pham WHERE ten = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ten);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ma_sp"); // trả về chuỗi
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int findIdByName3(String tenSP) {
    String sql = "SELECT id FROM san_pham WHERE ten_sp = ?";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tenSP);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1;
}


    public int findIdByName(String tenSP) {
        String sql = "SELECT id FROM san_pham WHERE ten = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIdByMaSP(String maSP) {
        int id = -1;
        String sql = "SELECT id FROM san_pham WHERE ma_sp = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public SanPham findById(int id) {
        String sql = "SELECT * FROM san_pham WHERE id = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id"));
                sp.setTen(rs.getString("ten"));
                sp.setMasp(rs.getString("ma_sp"));
                sp.setMo_ta(rs.getString("mo_ta"));
                sp.setLoai_san_pham_id(rs.getInt("loai_san_pham_id"));
                sp.setHang_id(rs.getInt("hang_id"));
                sp.setSo_luong(rs.getInt("so_luong"));
                sp.setTrang_thai(rs.getInt("trang_thai"));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChiTietSanPham findById1(int id) {
        String sql = "SELECT * FROM chi_tiet_san_pham WHERE id = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setId(rs.getInt("id"));
                ctsp.setSan_pham_id(rs.getInt("san_pham_id")); // ✅ NHỚ DÒNG NÀY!
                ctsp.setCpu_id(rs.getInt("cpu_id"));
                ctsp.setRam_id(rs.getInt("ram_id"));
                ctsp.setSsd_id(rs.getInt("ssd_id"));
                ctsp.setGpu_id(rs.getInt("gpu_id"));
                ctsp.setGia_ban(rs.getBigDecimal("gia_ban"));
                ctsp.setTrang_thai(rs.getInt("trang_thai"));
                return ctsp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int demSoLuongTonKho(int sanPhamId) {
    String sql = "SELECT COUNT(*) FROM serial WHERE san_pham_id = ? AND trang_thai = 0";
    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, sanPhamId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
    public boolean deleteSanPhamFull(int sanPhamId) {
    Connection con = null;
    try {
        con = XJdbc.openConnection();
        con.setAutoCommit(false);

        // 1. Xoá serial
        String sqlSerial = """
            DELETE s FROM serial s
            INNER JOIN chi_tiet_san_pham ct ON s.ctsp_id = ct.id
            WHERE ct.san_pham_id = ?
        """;
        try (PreparedStatement ps = con.prepareStatement(sqlSerial)) {
            ps.setInt(1, sanPhamId);
            ps.executeUpdate();
        }

        // 2. Xoá chi tiết sản phẩm
        String sqlCT = "DELETE FROM chi_tiet_san_pham WHERE san_pham_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlCT)) {
            ps.setInt(1, sanPhamId);
            ps.executeUpdate();
        }

        // 3. Xoá sản phẩm
        String sqlSP = "DELETE FROM san_pham WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlSP)) {
            ps.setInt(1, sanPhamId);
            ps.executeUpdate();
        }

        con.commit();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        if (con != null) try { con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        return false;
    } finally {
        if (con != null) try { con.setAutoCommit(true); con.close(); } catch (Exception ex) { ex.printStackTrace(); }
    }
}
    public List<BienTheSanPhamViewModel> findBienTheBySanPhamId(int sanPhamId) {
    List<BienTheSanPhamViewModel> list = new ArrayList<>();
    String sql = """
        SELECT 
            sp.id AS san_pham_id,
            cpu.id AS cpu_id,
            gpu.id AS gpu_id,
            ssd.id AS ssd_id,
            ram.id AS ram_id,

            sp.ten AS ten_sp,
            cpu.ten AS cpu,
            gpu.ten AS card,
            h.ten AS hang,
            ssd.dung_luong AS ocung,
            ram.dung_luong AS ram,
            ctsp.gia_ban AS giaBan,
            COUNT(sr.id) AS soLuong
        FROM chi_tiet_san_pham ctsp
        JOIN san_pham sp ON sp.id = ctsp.san_pham_id
        JOIN ram ON ram.id = ctsp.ram_id
        JOIN cpu ON cpu.id = ctsp.cpu_id
        JOIN ssd ON ssd.id = ctsp.ssd_id
        JOIN gpu ON gpu.id = ctsp.gpu_id
        JOIN hang h ON h.id = sp.hang_id
        LEFT JOIN serial sr 
            ON sr.ctsp_id = ctsp.id 
           AND sr.trang_thai = 0 -- 0: Đang bán
        WHERE sp.id = ?
        GROUP BY sp.id, cpu.id, gpu.id, ssd.id, ram.id,
                 sp.ten, cpu.ten, gpu.ten, h.ten, ssd.dung_luong, ram.dung_luong, ctsp.gia_ban
        ORDER BY ram.dung_luong, ssd.dung_luong, cpu.ten
    """;

    try (Connection conn = XJdbc.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, sanPhamId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BienTheSanPhamViewModel bt = BienTheSanPhamViewModel.builder()
                    .san_pham_id(rs.getInt("san_pham_id"))
                    .cpu_id(rs.getInt("cpu_id"))
                    .gpu_id(rs.getInt("gpu_id"))
                    .ssd_id(rs.getInt("ssd_id"))
                    .ram_id(rs.getInt("ram_id"))

                    .ten_sp(rs.getString("ten_sp"))
                    .cpu(rs.getString("cpu"))
                    .card(rs.getString("card"))
                    .hang(rs.getString("hang"))
                    .ocung(rs.getString("ocung"))
                    .ram(rs.getString("ram"))
                    .giaBan(rs.getBigDecimal("giaBan"))
                    .soLuong(rs.getInt("soLuong"))
                    .build();
                list.add(bt);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
     
    


}
