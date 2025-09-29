
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.ChiTietSanPham;
import DellStore.entity.Ocung;
import DellStore.entity.SanPhamChiTietViewModel;
import DellStore.entity.Serial;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author docon
 */
public class chitietsanphamDAO {

   public void deleteById(int ctspId, Connection conn) throws SQLException {
    String sql = "DELETE FROM chi_tiet_san_pham WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ctspId);
        ps.executeUpdate();
    }
}


    public void updateGiaKhuyenMaiTheoID(int idCTSP, BigDecimal giaSauKm) {
        String sql = """
        UPDATE chi_tiet_san_pham
        SET gia_ban = ?
        WHERE id = ?
    """;
        XJdbc.executeUpdate(sql, giaSauKm, idCTSP);
    }

    public void insertChiTietDotGiamGia(int dotGiamGiaId, int ctspId) {
        if (!daTonTaiTrongDotGiamGiaConHieuLuc(ctspId)) {
            String sql = "INSERT INTO chi_tiet_dot_giam_gia (dot_giam_gia_id, ctsp_id) VALUES (?, ?)";
            XJdbc.executeUpdate(sql, dotGiamGiaId, ctspId);
        } else {
            // Hiển thị cảnh báo bằng JOptionPane hoặc log nếu cần
            JOptionPane.showMessageDialog(null,
                    "Sản phẩm này đã được áp dụng trong một đợt giảm giá khác đang còn hiệu lực!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean daTonTaiTrongDotGiamGiaConHieuLuc(int ctspId) {
        String sql = """
        SELECT COUNT(*) 
        FROM chi_tiet_dot_giam_gia ct
        JOIN dot_giam_gia dg ON ct.dot_giam_gia_id = dg.id
        WHERE ct.ctsp_id = ? 
          AND dg.trang_thai IN (1, 2)
    """;
        ResultSet rs = XJdbc.executeQuery(sql, ctspId);
        try {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insert(ChiTietSanPham ctsp) {
        String sql = """
            INSERT INTO chi_tiet_san_pham (san_pham_id, cpu_id, ram_id, ssd_id, gpu_id, gia_ban, trang_thai)
            VALUES ( ?, ?, ?, ?, ?, ?,?)
        """;
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(2, ctsp.getSan_pham_id());
            ps.setInt(3, ctsp.getCpu_id());
            ps.setInt(4, ctsp.getRam_id());
            ps.setInt(5, ctsp.getSsd_id());
            ps.setInt(6, ctsp.getGpu_id());
            ps.setBigDecimal(7, ctsp.getGia_ban());
            ps.setInt(8, ctsp.getTrang_thai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertAndReturnId(ChiTietSanPham ctsp) {
        String sql = """
        INSERT INTO chi_tiet_san_pham (san_pham_id, cpu_id, ram_id, ssd_id, gpu_id, gia_ban, trang_thai)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) { // Sửa ở đây

            ps.setInt(1, ctsp.getSan_pham_id());
            ps.setInt(2, ctsp.getCpu_id());
            ps.setInt(3, ctsp.getRam_id());
            ps.setInt(4, ctsp.getSsd_id());
            ps.setInt(5, ctsp.getGpu_id());
            ps.setBigDecimal(6, ctsp.getGia_ban());
            ps.setInt(7, ctsp.getTrang_thai());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Chèn chi tiết sản phẩm thất bại, không có dòng nào được ảnh hưởng.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID vừa sinh
                } else {
                    throw new SQLException("Chèn chi tiết sản phẩm thất bại, không lấy được ID.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public SanPhamChiTietViewModel findByChiTietSanPhamId(int ctspId) {
        String sql = """
        SELECT c.id,
               sp.ten AS ten_san_pham,
               s.ma_serial,
               cpu.ten AS ten_cpu,
               gpu.ten AS ten_card,
               h.ten AS ten_hang,
               ssd.dung_luong AS ocung,
               ram.dung_luong AS ram,
               c.gia_ban,
               c.trang_thai
        FROM chi_tiet_san_pham c
        JOIN san_pham sp ON c.san_pham_id = sp.id
        JOIN cpu ON c.cpu_id = cpu.id
        JOIN gpu ON c.gpu_id = gpu.id
        JOIN hang h ON sp.hang_id = h.id
        JOIN ssd ON c.ssd_id = ssd.id
        JOIN ram ON c.ram_id = ram.id
        LEFT JOIN serial s ON s.ctsp_id = c.id
        WHERE c.id = ?
    """;

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ctspId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SanPhamChiTietViewModel vm = new SanPhamChiTietViewModel();
                vm.setId(rs.getInt("id"));
                vm.setTenSanPham(rs.getString("ten_san_pham"));
                vm.setSerial(rs.getString("ma_serial"));
                vm.setCpu(rs.getString("ten_cpu"));
                vm.setCard(rs.getString("ten_card"));
                vm.setHang(rs.getString("ten_hang"));
                vm.setOcung(rs.getString("ocung"));
                vm.setRam(rs.getString("ram"));
                vm.setGiaBan(rs.getBigDecimal("gia_ban"));
                vm.setTrangThai(rs.getInt("trang_thai"));
                return vm;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
public SanPhamChiTietViewModel findBySerial(String maSerial) {
    // 1. Xử lý serial: loại bỏ ký tự không phải chữ/số và chuyển thành chữ hoa
    String serialClean = maSerial.replaceAll("[^\\p{Alnum}]", "").toUpperCase();

    String sql = """
        SELECT c.id,
               sp.ten AS ten_san_pham,
               cpu.ten AS cpu,
               gpu.ten AS card,
               h.ten AS hang,
               ssd.dung_luong AS ocung,
               ram.dung_luong AS ram,
               s.ma_serial AS serial,
               c.gia_ban,
               c.trang_thai
        FROM chi_tiet_san_pham c
        LEFT JOIN san_pham sp ON c.san_pham_id = sp.id
        LEFT JOIN cpu ON c.cpu_id = cpu.id
        LEFT JOIN gpu ON c.gpu_id = gpu.id
        LEFT JOIN hang h ON sp.hang_id = h.id
        LEFT JOIN ssd ON c.ssd_id = ssd.id
        LEFT JOIN ram ON c.ram_id = ram.id
        JOIN serial s ON s.ctsp_id = c.id
        WHERE UPPER(LTRIM(RTRIM(s.ma_serial))) = ?
    """;

    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, serialClean);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            SanPhamChiTietViewModel vm = new SanPhamChiTietViewModel();
            vm.setId(rs.getInt("id"));
            vm.setTenSanPham(rs.getString("ten_san_pham"));
            vm.setCpu(rs.getString("cpu"));
            vm.setCard(rs.getString("card"));
            vm.setHang(rs.getString("hang"));
            vm.setOcung(rs.getString("ocung"));
            vm.setRam(rs.getString("ram"));
            vm.setSerial(rs.getString("serial"));
            vm.setGiaBan(rs.getBigDecimal("gia_ban"));
            vm.setTrangThai(rs.getInt("trang_thai"));

            System.out.println("DEBUG: Tìm thấy serial: " + vm.getSerial());
            return vm;
        } else {
            System.out.println("DEBUG: Không tìm thấy serial: " + serialClean);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}



    public List<SanPhamChiTietViewModel> findByChiTietSanPhamId1(int chiTietSanPhamId) {
        List<SanPhamChiTietViewModel> list = new ArrayList<>();
        String sql = """
        SELECT c.id,
               sp.ten AS ten_san_pham,
               cpu.ten AS cpu,
               gpu.ten AS card,
               h.ten AS hang,
               ssd.dung_luong AS ocung,
               ram.dung_luong AS ram,
               s.ma_serial AS serial,
               c.gia_ban,
               c.trang_thai
        FROM chi_tiet_san_pham c
        JOIN san_pham sp ON c.san_pham_id = sp.id
        JOIN cpu ON c.cpu_id = cpu.id
        JOIN gpu ON c.gpu_id = gpu.id
        JOIN hang h ON sp.hang_id = h.id
        JOIN ssd ON c.ssd_id = ssd.id
        JOIN ram ON c.ram_id = ram.id
        LEFT JOIN serial s ON s.ctsp_id = c.id
        WHERE c.id = ?
    """;

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, chiTietSanPhamId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTietViewModel vm = new SanPhamChiTietViewModel();
                vm.setId(rs.getInt("id"));
                vm.setTenSanPham(rs.getString("ten_san_pham"));
                vm.setCpu(rs.getString("cpu"));
                vm.setCard(rs.getString("card"));
                vm.setHang(rs.getString("hang"));
                vm.setOcung(rs.getString("ocung"));
                vm.setRam(rs.getString("ram"));
                vm.setSerial(rs.getString("serial"));
                vm.setGiaBan(rs.getBigDecimal("gia_ban"));
                vm.setTrangThai(rs.getInt("trang_thai"));
                list.add(vm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ChiTietSanPham> findBySanPhamId(int sanPhamId) {
        List<ChiTietSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_san_pham WHERE san_pham_id = ?";

        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sanPhamId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setId(rs.getInt("id"));
                ctsp.setSan_pham_id(rs.getInt("san_pham_id"));
                ctsp.setCpu_id(rs.getInt("cpu_id"));
                ctsp.setRam_id(rs.getInt("ram_id"));
                ctsp.setSsd_id(rs.getInt("ssd_id"));
                ctsp.setGpu_id(rs.getObject("gpu_id") != null ? rs.getInt("gpu_id") : null);
                ctsp.setGia_ban(rs.getBigDecimal("gia_ban"));
                ctsp.setTrang_thai(rs.getInt("trang_thai"));

                list.add(ctsp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietSanPham> findAll() {
        List<ChiTietSanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_san_pham";
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietSanPham entity = ChiTietSanPham.builder()
                        .id(rs.getInt("id"))
                        .cpu_id(rs.getInt("cpu_id"))
                        .ram_id(rs.getInt("ram_id"))
                        .ssd_id(rs.getInt("ssd_id"))
                        .gpu_id(rs.getInt("gpu_id"))
                        .gia_ban(rs.getBigDecimal("gia_ban"))
                        .trang_thai(rs.getInt("trang_thai"))
                        .build();
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ChiTietSanPham findById(int id) {
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

//    public boolean update(ChiTietSanPham ctsp) {
//    String sql = """
//        UPDATE chi_tiet_san_pham SET serial_id=?, san_pham_id=?, cpu_id=?, ram_id=?, ssd_id=?, gpu_id=?, gia_ban=?
//        WHERE id=?
//    """;
//
//    try (Connection con = XJdbc.openConnection(); 
//            PreparedStatement ps = con.prepareStatement(sql)) {
//        ps.setInt(1, ctsp.getSerial_id());
//        ps.setInt(2, ctsp.getSan_pham_id());
//        ps.setInt(3, ctsp.getCpu_id());
//        ps.setInt(4, ctsp.getRam_id());
//        ps.setInt(5, ctsp.getSsd_id());
//        ps.setInt(6, ctsp.getGpu_id());
//        ps.setBigDecimal(7, ctsp.getGia_ban());
//        ps.setInt(0, ctsp.getId());
//
//        return ps.executeUpdate() > 0;
//    } catch (Exception e) {
//        e.printStackTrace();
//        return false;
//    }
//}
    public String getTenById(String tableName, int id) {
        String sql = "SELECT ten FROM " + tableName + " WHERE id = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
 public boolean update(ChiTietSanPham ctsp) {
    String sql = """
        UPDATE chi_tiet_san_pham 
        SET san_pham_id=?, cpu_id=?, ram_id=?, ssd_id=?, gpu_id=?, gia_ban=?, trang_thai=? 
        WHERE id=?
    """;
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, ctsp.getSan_pham_id());
        ps.setInt(2, ctsp.getCpu_id());
        ps.setInt(3, ctsp.getRam_id());
        ps.setInt(4, ctsp.getSsd_id());
        ps.setInt(5, ctsp.getGpu_id());
        ps.setBigDecimal(6, ctsp.getGia_ban());
        ps.setInt(7, ctsp.getTrang_thai());
        ps.setInt(8, ctsp.getId());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public int getIdByName(String tableName, String ten) {

    // Cập nhật chi tiết sản phẩm
       
        int id = -1;
        String sql = "SELECT id FROM " + tableName + " WHERE ten = ?";
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ten);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getCpuId(String ten) {
        return getIdByName("cpu", ten);
    }

    public int getRamId(String ten) {
        return getIdByName("ram", ten);
    }

    public int getSsdId(String ten) {
        return getIdByName("ssd", ten);
    }

    public int getGpuId(String ten) {
        return getIdByName("gpu", ten);
    }

    public List<String> getAllNamesFromTable(String tableName) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT ten FROM " + tableName;
        try (Connection conn = XJdbc.openConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("ten"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChiTietSanPham findBySerialId(int serialId) {
        String sql = """
        SELECT c.* FROM chi_tiet_san_pham c
        JOIN serial s ON c.id = s.ctsp_id
        WHERE s.id = ?
    """;
        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serialId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setId(rs.getInt("id"));
                ctsp.setSan_pham_id(rs.getInt("san_pham_id"));
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

    public List<Integer> getDanhSachIdCTSPTheoBienThe(int sanPhamId, int cpuId, int gpuId, int ramId, int ssdId) {
        List<Integer> list = new ArrayList<>();
        String sql = """
        SELECT ctsp.id
        FROM chi_tiet_san_pham ctsp
        join serial sr on sr.ctsp_id=ctsp.id
        WHERE san_pham_id = ?
          AND cpu_id = ?
          AND gpu_id = ?
          AND ram_id = ?
          AND ssd_id = ?
          AND sr.trang_thai = 0
    """;

        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sanPhamId);
            ps.setInt(2, cpuId);
            ps.setInt(3, gpuId);
            ps.setInt(4, ramId);
            ps.setInt(5, ssdId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
      // Trả về san_pham_id từ ctsp.id
public int getSanPhamIdByChiTietId(Connection conn, int ctspId) throws SQLException {
    String sql = "SELECT san_pham_id FROM chi_tiet_san_pham WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, ctspId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("san_pham_id");
        }
    }
    return -1;
}

public int getSanPhamIdByChiTietId(int ctspId) throws SQLException {
    try (Connection conn = XJdbc.openConnection()) {
        return getSanPhamIdByChiTietId(conn, ctspId);
    }
}

public boolean isThongSoChanged(Connection conn, SanPhamChiTietViewModel sp) throws SQLException {
    String sql = """
        SELECT c.ten AS cpu, g.ten AS gpu, s.dung_luong AS ssd, r.dung_luong AS ram
        FROM chi_tiet_san_pham ct
        JOIN cpu c ON c.id = ct.cpu_id
        JOIN gpu g ON g.id = ct.gpu_id
        JOIN ssd s ON s.id = ct.ssd_id
        JOIN ram r ON r.id = ct.ram_id
        WHERE ct.id = ?
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, sp.getId());
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return false;
            return !(rs.getString("cpu").equalsIgnoreCase(sp.getCpu().trim())
                  && rs.getString("gpu").equalsIgnoreCase(sp.getCard().trim())
                  && rs.getString("ssd").equalsIgnoreCase(sp.getOcung().trim())
                  && rs.getString("ram").equalsIgnoreCase(sp.getRam().trim()));
        }
    }
}

public BigDecimal findGiaBanByCombo(Connection conn,
                                    int sanPhamId,
                                    String cpuName,
                                    String gpuName,
                                    String ssdDungLuong,
                                    String ramDungLuong,
                                    int excludeCtspId) throws SQLException {
    String sql = """
        SELECT TOP 1 ctsp.gia_ban
        FROM chi_tiet_san_pham ctsp
        JOIN cpu c  ON c.id  = ctsp.cpu_id  AND c.ten = ?
        JOIN gpu g  ON g.id  = ctsp.gpu_id  AND g.ten = ?
        JOIN ssd s  ON s.id  = ctsp.ssd_id  AND s.dung_luong = ?
        JOIN ram r  ON r.id  = ctsp.ram_id  AND r.dung_luong = ?
        WHERE ctsp.san_pham_id = ?
          AND ctsp.id <> ?
    """;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, cpuName);
        ps.setString(2, gpuName);
        ps.setString(3, ssdDungLuong);
        ps.setString(4, ramDungLuong);
        ps.setInt(5, sanPhamId);
        ps.setInt(6, excludeCtspId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getBigDecimal("gia_ban");
        }
    }
    return null;
}

public boolean updateSanPhamChiTiet(SanPhamChiTietViewModel sp) {
    try (Connection conn = XJdbc.openConnection()) {
        conn.setAutoCommit(false);

        int sanPhamId = getSanPhamIdByChiTietId(conn, sp.getId());
        if (sanPhamId <= 0) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm cha cho CTSP ID = " + sp.getId());
            return false;
        }

        boolean thongSoChanged = isThongSoChanged(conn, sp);

        if (thongSoChanged) {
            // Tìm giá từ biến thể tương tự (nếu có)
            BigDecimal existPrice = findGiaBanByCombo(conn, sanPhamId,
                    sp.getCpu(), sp.getCard(), sp.getOcung(), sp.getRam(), sp.getId());

            if (existPrice != null) {
                sp.setGiaBan(existPrice);
            } else if (sp.getGiaBan() == null || sp.getGiaBan().compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy biến thể tương tự. Vui lòng nhập giá mới!");
                conn.rollback();
                return false;
            }

            String sqlCtsp = """
                UPDATE chi_tiet_san_pham
                SET cpu_id = (SELECT id FROM cpu WHERE ten = ?),
                    gpu_id = (SELECT id FROM gpu WHERE ten = ?),
                    ssd_id = (SELECT id FROM ssd WHERE dung_luong = ?),
                    ram_id = (SELECT id FROM ram WHERE dung_luong = ?),
                    gia_ban = ?
                WHERE id = ?
            """;
            try (PreparedStatement ps = conn.prepareStatement(sqlCtsp)) {
                ps.setString(1, sp.getCpu());
                ps.setString(2, sp.getCard());
                ps.setString(3, sp.getOcung());
                ps.setString(4, sp.getRam());
                ps.setBigDecimal(5, sp.getGiaBan());
                ps.setInt(6, sp.getId());
                ps.executeUpdate();
            }

        } else {
            // Chỉ đổi giá → áp dụng cho tất cả biến thể cùng combo
            String sqlUpdateGiaChung = """
                UPDATE t
                SET t.gia_ban = ?
                FROM chi_tiet_san_pham t
                INNER JOIN chi_tiet_san_pham src ON src.id = ?
                WHERE t.san_pham_id = src.san_pham_id
                  AND t.cpu_id = src.cpu_id
                  AND t.gpu_id = src.gpu_id
                  AND t.ssd_id = src.ssd_id
                  AND t.ram_id = src.ram_id
            """;
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateGiaChung)) {
                ps.setBigDecimal(1, sp.getGiaBan());
                ps.setInt(2, sp.getId());
                if (ps.executeUpdate() == 0) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhóm biến thể để cập nhật giá chung.");
                    conn.rollback();
                    return false;
                }
            }
        }

        // Cập nhật serial
        String sqlSerial = "UPDATE serial SET ma_serial = ?, trang_thai = ? WHERE ctsp_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlSerial)) {
            ps.setString(1, sp.getSerial());
            ps.setInt(2, sp.getTrangThai());
            ps.setInt(3, sp.getId());
            ps.executeUpdate();
        }

        conn.commit();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + e.getMessage());
        return false;
    }
}

}
