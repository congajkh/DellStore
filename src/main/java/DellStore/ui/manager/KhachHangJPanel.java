/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.impl.KhachHangDAO;
import DellStore.entity.KhachHang;
import DellStore.entity.SanPhamDaMua;
import DellStore.utils.XDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author docon
 */
public class KhachHangJPanel extends javax.swing.JPanel {

    KhachHangDAO khachHangDAO = new KhachHangDAO();
    private KhachHang khachHangDuocChon;

    private DefaultTableModel defaultTableModel;
    int row = -1;

    /**
     * Creates new form KhachHangJPanel
     */
    public KhachHangJPanel() {
        initComponents();
        fillTable();
//        if (tbl_khachhang.getRowCount() > 0) {
//        Object val = tbl_khachhang.getValueAt(0, 0);
//        if (val != null) {
//            int khachHangId = Integer.parseInt(val.toString());
//            loadSanPhamDaMua(khachHangId);
//        }
        //}

    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_khachhang.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = khachHangDAO.findAll();
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getId(), kh.getTen(), kh.isGioi_tinh() ? "Nam" : "Nữ",
                    kh.getDia_chi(), kh.getSdt(), kh.getEmail()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }
    }

    private void fillTable(List<KhachHang> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_khachhang.getModel();
        model.setRowCount(0);
        try {
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getId(), kh.getTen(), kh.isGioi_tinh() ? "Nam" : "Nữ",
                    kh.getDia_chi(), kh.getSdt(), kh.getEmail()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đổ dữ liệu tìm kiếm!");
            e.printStackTrace();
        }
    }

    private boolean validateForm() {
        String ten = txtTen1.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSdt.getText().trim();
        String email = txtEmail.getText().trim();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!");
            txtTen1.requestFocus();
            return false;
        }

        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!");
            txtDiaChi.requestFocus();
            return false;
        }

        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtSdt.requestFocus();
            return false;
        }

        if (!sdt.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (phải là 10-11 chữ số)!");
            txtSdt.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email!");
            txtEmail.requestFocus();
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void insert() {
        if (!validateForm()) {
            return; // nếu không hợp lệ thì không thực hiện tiếp
        }
        String ten = txtTen1.getText().trim();
        String sdt = txtSdt.getText().trim();
        String email = txtEmail.getText().trim();
        KhachHang kh = getForm();
        if (kh == null) {
            return;
        }
        if (khachHangDAO.checkTrung(ten, sdt, email)) {
            JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại (trùng tên, SĐT và Email)!");
            return;
        }
        // 2️⃣ Hỏi người dùng trước khi thêm
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thêm khách hàng này không?",
                "Xác nhận khách hàng",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu người dùng chọn No, thoát khỏi hàm
        }
        try {
            khachHangDAO.create(kh);
            fillTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
            e.printStackTrace();
        }
    }

    private void update() {
        if (!validateForm()) {
            return;
        }

        KhachHang kh = getForm();
        if (kh == null) {
            return;
        }
        // 2️⃣ Hỏi người dùng trước khi thêm
int confirm = JOptionPane.showConfirmDialog(this,
        "Bạn có chắc muốn sửa khách hàng này không?",
        "Xác nhận sửa khách hàng",
        JOptionPane.YES_NO_OPTION);

if (confirm != JOptionPane.YES_OPTION) {
    return; // Nếu người dùng chọn No, thoát khỏi hàm
}
        try {
            khachHangDAO.update(kh);
            fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            e.printStackTrace();
        }
    }

    void delete() {
        String ten = txtTen1.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng cần xoá!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xoá khách hàng tên: " + ten + "?", "Xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                khachHangDAO.deleteById(ten);
                JOptionPane.showMessageDialog(this, "Đã xoá khách hàng!");
                fillTable(); // hoặc reload lại dữ liệu
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xoá khách hàng: " + e.getMessage());
            }
        }
    }

    void clearForm() {
        txtid.setText("");
        txtTen1.setText("");
        txtid.setText("");
        rdoNam.setSelected(true);
        txtDiaChi.setText("");
        txtSdt.setText("");
        txtEmail.setText("");
        tbl_khachhang.clearSelection();
    }

//    void edit() {
//        int id = Integer.parseInt(tbl_khachhang.getValueAt(row, 0).toString());
//        KhachHang kh = khachHangDAO.findById(String.valueOf(id));
//        setForm(kh);
//    }
//    void setForm(KhachHang kh) {
//        txtMaKH.setText(String.valueOf(kh.getId()));
//        txtid.setText(kh.getTen());
//        (kh.isGioiTinh() ? rdoNam : rdoNu).setSelected(true);
//        txtDiaChi.setText(kh.getDiaChi());
//        txtSDT.setText(kh.getSdt());
//        txtEmail.setText(kh.getEmail());
//    }
    KhachHang getForm() {
        KhachHang kh = new KhachHang();

        // Ép kiểu String → int (nên kiểm tra để tránh lỗi)
        try {
            if (!txtid.getText().trim().isEmpty()) {
                kh.setId(Integer.parseInt(txtid.getText().trim()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng phải là số nguyên!");
            return null; // hoặc throw exception tùy cách bạn xử lý
        }

        kh.setTen(txtTen1.getText().trim());
        kh.setGioi_tinh(rdoNam.isSelected());
        kh.setDia_chi(txtDiaChi.getText().trim());
        kh.setSdt(txtSdt.getText().trim());
        kh.setEmail(txtEmail.getText().trim());

        return kh;
    }

    public void loadSanPhamDaMua(int khachHangId) {
        DefaultTableModel model = (DefaultTableModel) tbl_spdamua.getModel();
        model.setRowCount(0);

        List<SanPhamDaMua> ds = khachHangDAO.getSanPhamDaMua(khachHangId);
        DefaultTableModel modelSP = (DefaultTableModel) tbl_spdamua.getModel();
        int stt = 1;
        modelSP.setRowCount(0);
        for (SanPhamDaMua sp : ds) {
            modelSP.addRow(new Object[]{
                stt++,
                sp.getMaSp(),
                sp.getTenSp(),
                XDate.format(sp.getNgayMua(), "dd-MM-yyyy"),
                sp.getSoLuong(),
                sp.getDonGia()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnclear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        txtTen1 = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khachhang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_spdamua = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("Tên khách hàng");

        jLabel6.setText("Địa chỉ");

        jLabel7.setText("Số điện thoại");

        btnThem.setBackground(new java.awt.Color(255, 153, 51));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 153, 51));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel8.setText("Giới tính");

        jLabel9.setText("Email");

        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        btnclear.setBackground(new java.awt.Color(255, 153, 51));
        btnclear.setText("Làm mới");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel5.setText("ID");

        btnxoa.setBackground(new java.awt.Color(255, 153, 51));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtid))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoNu))
                                    .addComponent(txtTen1)
                                    .addComponent(txtDiaChi)
                                    .addComponent(txtSdt)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnclear)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtTen1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtDiaChi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtSdt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Thông Tin Khách Hàng");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Khách Hàng"));

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 153, 51));
        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 153, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timkiem.png"))); // NOI18N
        jLabel3.setText("Tìm Kiếm Tên");

        tbl_khachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ Tên", "Giới tính", "Địa chỉ", "Số ĐT", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_khachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_khachhangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_khachhang);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_spdamua.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Ngày Mua", "Số Lượng", "Đơn Giá"
            }
        ));
        jScrollPane2.setViewportView(tbl_spdamua);
        if (tbl_spdamua.getColumnModel().getColumnCount() > 0) {
            tbl_spdamua.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Sản Phẩm Đã Mua");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(350, 350, 350))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_khachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_khachhangMouseClicked
        // TODO add your handling code here:
        int row = tbl_khachhang.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel model = (DefaultTableModel) tbl_khachhang.getModel();

            // Lấy dữ liệu từ các cột tương ứng
            int id = (int) model.getValueAt(row, 0); // Cột 0: ID
            String ten = model.getValueAt(row, 1).toString(); // Cột 1: Tên
            String gioiTinh = model.getValueAt(row, 2).toString(); // Cột 2: Giới tính
            String diaChi = model.getValueAt(row, 3).toString(); // Cột 3: Địa chỉ
            String sdt = model.getValueAt(row, 4).toString(); // Cột 4: SĐT
            String email = model.getValueAt(row, 5).toString(); // Cột 5: Email

            // Set giá trị vào các trường nhập liệu
            txtid.setText(String.valueOf(id));
            txtTen1.setText(ten);
            txtDiaChi.setText(diaChi);
            txtSdt.setText(sdt);
            txtEmail.setText(email);
            if (gioiTinh.equalsIgnoreCase("Nam")) {
                rdoNam.setSelected(true);
                rdoNu.setSelected(false);
            } else {
                rdoNam.setSelected(false);
                rdoNu.setSelected(true);
            }

            // Load sản phẩm đã mua
            loadSanPhamDaMua(id);

//       
        }
    }//GEN-LAST:event_tbl_khachhangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        clearForm();

    }//GEN-LAST:event_btnclearActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        KhachHangDAO khdao = new KhachHangDAO();
        String tenKhachHang = txtTimKiem.getText().trim();

        List<KhachHang> list = khdao.findByTen(tenKhachHang);
        if (!list.isEmpty()) {
            fillTable(list); // truyền danh sách tìm được vào
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng tên: " + tenKhachHang);
        }
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btnxoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tbl_khachhang;
    private javax.swing.JTable tbl_spdamua;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen1;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
public KhachHang getKhachHangDuocChon() {
        return khachHangDuocChon;
    }

}
