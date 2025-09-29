/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.SanPhamBanHang;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author nguyendangtuan
 */
public class BanHangDAOImpl {
    String sql = """
SELECT 
    sp.ma_sp AS maSanPham, 
    sp.ten AS tenSanPham, 
    cpu.ten AS cpu, 
    ram.dung_luong AS ram,
    gpu.ten AS card, 
    ssd.dung_luong AS ocung, 
    h.ten AS hang,
    ctsp.gia_ban AS giaBan,
    COUNT(s.id) AS soLuong
FROM serial s
JOIN chi_tiet_san_pham ctsp ON s.ctsp_id = ctsp.id
JOIN san_pham sp ON ctsp.san_pham_id = sp.id
JOIN hang h ON sp.hang_id = h.id
JOIN cpu ON ctsp.cpu_id = cpu.id
JOIN ram ON ctsp.ram_id = ram.id
JOIN gpu ON ctsp.gpu_id = gpu.id
JOIN ssd ON ctsp.ssd_id = ssd.id
WHERE s.trang_thai = 0
GROUP BY 
    sp.ma_sp, sp.ten, cpu.ten, ram.dung_luong, gpu.ten, ssd.dung_luong, h.ten, ctsp.gia_ban
""";

    public List<SanPhamBanHang> findAllSanPham() {
        return XQuery.getBeanList(SanPhamBanHang.class, sql);
    }
    
    public int demSoSerialTonKho(int sanPhamId) {
    String sql = "SELECT COUNT(*) FROM serial WHERE san_pham_id = ? AND trang_thai = 0";
    try (Connection con = XJdbc.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
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

}
