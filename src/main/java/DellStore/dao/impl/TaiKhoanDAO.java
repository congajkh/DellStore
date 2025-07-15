package DellStore.dao.impl;

import DellStore.utils.XJdbc;
import java.sql.ResultSet;

public class TaiKhoanDAO {

    // Hàm kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        try {
            String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap = ? AND mat_khau = ?";
            ResultSet rs = XJdbc.executeQuery(sql, username, password);
            if (rs.next()) {
                return true; // Tìm thấy tài khoản hợp lệ
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi lỗi ra console
        }
        return false; // Sai tài khoản hoặc mật khẩu
    }
}
