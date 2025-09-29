/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.Serial;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.math.BigDecimal;
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
public class SerialDAOImpl {

    public boolean existsBySerial(String maSerial) {
        String sql = "SELECT COUNT(*) FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSerial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateTrangThai(int serialId, int trangThaiMoi) {
        String sql = "UPDATE serial SET trang_thai = ? WHERE id = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, trangThaiMoi);
            ps.setInt(2, serialId);
            System.out.println("UPDATE serial SET trang_thai = " + trangThaiMoi + " WHERE id = " + serialId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Serial findById(int id) {
        String sql = "SELECT * FROM serial WHERE ctsp_id = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Serial s = new Serial();
                    s.setId(rs.getInt("id"));
                    s.setMa_serial(rs.getString("ma_serial"));
                    s.setCtsp_id(rs.getInt("ctsp_id"));
                    s.setTrang_thai(rs.getInt("trang_thai"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Serial> getSerialChuaBanTheoMaSP(String maSP) {
        List<Serial> list = new ArrayList<>();
        try {
            String sql = """
            SELECT s.ma_serial 
            FROM serial s
            JOIN chi_tiet_san_pham ct ON s.ctsp_id = ct.id
            JOIN san_pham sp ON ct.san_pham_id = sp.id
            WHERE sp.ma_sp = ? AND s.trang_thai = 0
        """;

            ResultSet rs = XJdbc.executeQuery(sql, maSP);
            while (rs.next()) {
                Serial s = new Serial();
                s.setMa_serial(rs.getString("ma_serial"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkExists(String maSerial) {
        String sql = "SELECT COUNT(*) FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSerial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSerialByMaSerial(String maSerial) {
        String sql = "DELETE FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSerial);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSerialById(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM serial WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    Connection con = XJdbc.openConnection();

    public Serial findByMaSerial(String maSerial) {
        String sql = "SELECT * FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSerial.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Serial s = new Serial();
                s.setId(rs.getInt("id"));
                s.setMa_serial(rs.getString("ma_serial"));
                s.setCtsp_id(rs.getInt("ctsp_id"));
                s.setTrang_thai(rs.getInt("trang_thai"));
                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Serial> getBySanPhamId(int sanPhamId) {
        List<Serial> list = new ArrayList<>();
        String sql = "SELECT * FROM serial WHERE ctsp_id = ?";
        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, sanPhamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serial s = new Serial();
                s.setId(rs.getInt("id"));
                s.setMa_serial(rs.getString("ma_serial"));
                s.setCtsp_id(rs.getInt("ctsp_id"));
                s.setTrang_thai(rs.getInt("trang_thai"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getIdByMa(String maSerial) {
        String sql = "SELECT id FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSerial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insert(Serial s) {
        try {
            Connection con = XJdbc.openConnection();
            String sql = "INSERT INTO serial (ma_serial, ctsp_id, trang_thai) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getMa_serial());
            ps.setInt(2, s.getCtsp_id());
            ps.setInt(3, s.getTrang_thai());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIdByMaSerial(String maSerial) {
        int id = -1;
        String sql = "SELECT id FROM serial WHERE ma_serial = ?";
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSerial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getTenById(int id) {
        String sql = "SELECT ma_serial FROM serial WHERE id = ?";
        try (Connection cn = XJdbc.openConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ma_serial");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByTen(String tenSerial) {
        try (Connection conn = XJdbc.openConnection()) {
            String sql = "SELECT id FROM serial WHERE ma_serial = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenSerial);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Serial> getByBienThe(
            String maSP, String cpu, String ramDungLuong,
            String gpu, String ssdDungLuong, BigDecimal giaBan
    ) {
        List<Serial> list = new ArrayList<>();
        String sql = """
        SELECT s.* 
        FROM serial s
        JOIN chi_tiet_san_pham ct ON s.ctsp_id = ct.id
        JOIN san_pham sp ON ct.san_pham_id = sp.id
        JOIN cpu c ON ct.cpu_id = c.id
        JOIN ram r ON ct.ram_id = r.id
        JOIN gpu g ON ct.gpu_id = g.id
        JOIN ssd sd ON ct.ssd_id = sd.id
        WHERE sp.ma_sp = ?
          AND c.ten = ?
          AND r.dung_luong = ?
          AND g.ten = ?
          AND sd.dung_luong = ?
          AND ct.gia_ban = ?
    """;

        try (
                Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, maSP);
            ps.setString(2, cpu);
            ps.setString(3, ramDungLuong);
            ps.setString(4, gpu);
            ps.setString(5, ssdDungLuong);
            ps.setBigDecimal(6, giaBan);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serial s = new Serial();
                s.setId(rs.getInt("id"));
                s.setMa_serial(rs.getString("ma_serial"));
                s.setCtsp_id(rs.getInt("ctsp_id"));
                s.setTrang_thai(rs.getInt("trang_thai"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Serial> getSerialByCTSPAndTrangThai(int ctspId, int trangThai) {
        String sql = "SELECT * FROM serial WHERE ctsp_id = ? AND trang_thai = ?";
        List<Serial> list = new ArrayList<>();
        try (Connection con = XJdbc.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ctspId);
            ps.setInt(2, trangThai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serial s = new Serial();
                s.setId(rs.getInt("id"));
                s.setCtsp_id(rs.getInt("ctsp_id"));
                s.setMa_serial(rs.getString("ma_serial"));
                s.setTrang_thai(rs.getInt("trang_thai"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
