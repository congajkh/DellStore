/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.ChiTietHoaDonDTO;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class ChiTietHoaDonDAO {
    String sql = """
        SELECT 
            cthd.id,
            hd.ma AS maHoaDon,
            sp.ten AS tenSanPham,
            cthd.don_gia,
            cthd.so_luong,
            cthd.tien_khuyen_mai,
            cthd.tong_tien,
            s.ma_serial
        FROM ct_hoa_don cthd
        JOIN hoa_don hd ON cthd.hoa_don_id = hd.id
        JOIN serial s ON cthd.serial_id = s.id
        JOIN chi_tiet_san_pham ctsp ON s.chi_tiet_sp_id = ctsp.id
        JOIN san_pham sp ON ctsp.san_pham_id = sp.id
        WHERE hd.ma = ?
    """;

    public List<ChiTietHoaDonDTO> findByMaHoaDon(String maHoaDon) {
        return XQuery.getBeanList(ChiTietHoaDonDTO.class, sql, maHoaDon);
    }
}


