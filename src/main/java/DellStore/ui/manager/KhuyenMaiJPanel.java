/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.LinhKienDAO;
import DellStore.dao.impl.KhuyenMaiDAOImpl;
import DellStore.dao.impl.chitietsanphamDAO;
import DellStore.entity.KhuyenMai;
import DellStore.entity.SanPhamKhuyenMai;
import DellStore.utils.XJdbc;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author docon
 */
public class KhuyenMaiJPanel extends javax.swing.JPanel {

    private KhuyenMaiDAOImpl khuyenMaiDAO = new KhuyenMaiDAOImpl();
    private chitietsanphamDAO ctspDAO = new chitietsanphamDAO();
    private KhuyenMai maDuocChon;
    private LinhKienDAO linhKienDAO = new LinhKienDAO();

    /**
     * Creates new form KhuyenMaiJPanel
     */
    public KhuyenMaiJPanel() {
        initComponents();
        txt_timkm.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                timKiemKhuyenMai();
            }

            public void removeUpdate(DocumentEvent e) {
                timKiemKhuyenMai();
            }

            public void changedUpdate(DocumentEvent e) {
                timKiemKhuyenMai();
            }

            private void timKiemKhuyenMai() {
                String keyword = txt_timkm.getText().trim().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) tbl_khuyenmai.getModel();
                model.setRowCount(0); // Clear bảng

                List<KhuyenMai> list = khuyenMaiDAO.findAll(); // Lấy toàn bộ danh sách
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                for (KhuyenMai km : list) {
                    if (km.getMa_km().toLowerCase().contains(keyword)
                            || km.getTen_km().toLowerCase().contains(keyword)) {
                        String giaTri;
                        if (km.getLoai_giam() == null) {
                            giaTri = "N/A";
                        } else if (km.getLoai_giam().equals("%")) {
                            giaTri = String.format("%.0f%%", km.getGiam_gia());
                        } else if (km.getLoai_giam().equals("Tiền")) {
                            giaTri = String.format("%,.0f VNĐ", km.getGiam_gia());
                        } else {
                            giaTri = String.format("%,.2f", km.getGiam_gia());
                        }
                        Object[] row = {
                            km.getId(), // Cột ID ẩn
                            km.getMa_km() != null ? km.getMa_km() : "N/A",
                            km.getTen_km() != null ? km.getTen_km() : "N/A",
                            km.getLoai_giam() != null ? km.getLoai_giam() : "N/A",
                            giaTri,
                            km.getNgay_bat_dau() != null ? dateFormat.format(km.getNgay_bat_dau()) : "N/A",
                            km.getNgay_ket_thuc() != null ? dateFormat.format(km.getNgay_ket_thuc()) : "N/A",
                            km.getTrang_thai() == 1 ? "Đang diễn ra"
                            : km.getTrang_thai() == 2 ? "Sắp diễn ra"
                            : "Đã kết thúc"
                        };
                        model.addRow(row);
                    }
                }
                // Ẩn cột ID
                tbl_khuyenmai.getColumnModel().getColumn(0).setMinWidth(0);
                tbl_khuyenmai.getColumnModel().getColumn(0).setMaxWidth(0);
                tbl_khuyenmai.getColumnModel().getColumn(0).setWidth(0);

                // Tự động chọn dòng đầu tiên nếu có kết quả
                if (tbl_khuyenmai.getRowCount() > 0) {
                    tbl_khuyenmai.setRowSelectionInterval(0, 0);
                    loadKhuyenMaiToForm(0); // Bạn cần có hàm này để đổ dữ liệu lên form
                } else {
                    clearForm(); // Hàm để xóa trắng form nếu không có kết quả
                }
            }
        });
        fillTableKhuyenMai();
        loadSanPhamKhuyenMai();
    }

    private void fillTableKhuyenMai(List<KhuyenMai> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_khuyenmai.getModel();
        model.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (KhuyenMai km : list) {
            String giaTri;
            if (km.getLoai_giam() == null) {
                giaTri = "N/A";
            } else if (km.getLoai_giam().equals("%")) {
                giaTri = String.format("%.0f%%", km.getGiam_gia());
            } else if (km.getLoai_giam().equals("Tiền")) {
                giaTri = String.format("%,.0f VNĐ", km.getGiam_gia());
            } else {
                giaTri = String.format("%,.2f", km.getGiam_gia());
            }

            Object[] row = {
                km.getId(), // Cột ID ẩn
                km.getMa_km() != null ? km.getMa_km() : "N/A",
                km.getTen_km() != null ? km.getTen_km() : "N/A",
                km.getLoai_giam() != null ? km.getLoai_giam() : "N/A",
                giaTri,
                km.getNgay_bat_dau() != null ? dateFormat.format(km.getNgay_bat_dau()) : "N/A",
                km.getNgay_ket_thuc() != null ? dateFormat.format(km.getNgay_ket_thuc()) : "N/A",
                km.getTrang_thai() == 1 ? "Đang diễn ra"
                : km.getTrang_thai() == 2 ? "Sắp diễn ra"
                : "Đã kết thúc"
            };
            model.addRow(row);
        }

        // Ẩn cột ID
        tbl_khuyenmai.getColumnModel().getColumn(0).setMinWidth(0);
        tbl_khuyenmai.getColumnModel().getColumn(0).setMaxWidth(0);
        tbl_khuyenmai.getColumnModel().getColumn(0).setWidth(0);

        System.out.println("Loaded " + list.size() + " khuyến mãi (lọc sẵn)");
    }

    private void fillTableKhuyenMai() {
        List<KhuyenMai> list = khuyenMaiDAO.findAll();
        fillTableKhuyenMai(list);
    }

    public String generateMaKhuyenMai() {
        String prefix = "KM";
        int nextId = 1;
        try {
            String sql = "SELECT ISNULL(MAX(id), 0) + 1 FROM dot_giam_gia";
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                nextId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + String.format("%03d", nextId);
    }

    private KhuyenMai validateKhuyenMai(boolean isUpdate) {
        int id = isUpdate ? maDuocChon.getId() : -1;  // Sử dụng ID nếu update, còn thêm mới thì bỏ qua
        String maKm = isUpdate ? maDuocChon.getMa_km() : generateMaKhuyenMai();

        String tenKm = txtTen.getText().trim();
        String giaTriStr = txtGiaTri.getText().trim();
        String loaiKm = cboLoaiKm.getSelectedItem().toString();
        String trangThaiStr = cboTrangThai.getSelectedItem().toString();
        Date ngayBatDau = dateBatDau.getDate();
        Date ngayKetThuc = dateKetThuc.getDate();

        if (tenKm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên khuyến mãi.");
            return null;
        }

        KhuyenMai kmCheckTen = khuyenMaiDAO.findByTen(tenKm);
        if (kmCheckTen != null && (!isUpdate || kmCheckTen.getId() != id)) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi đã tồn tại.");
            return null;
        }

        if (giaTriStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập giá trị khuyến mãi.");
            return null;
        }

        BigDecimal giaTri;

        try {
            giaTri = new BigDecimal(giaTriStr);

            if (giaTri.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(null, "Giá trị phải lớn hơn 0.");
                return null;
            }

            if (loaiKm.equals("%") && giaTri.compareTo(new BigDecimal("100")) > 0) {
                JOptionPane.showMessageDialog(null, "Giảm giá phần trăm không được vượt quá 100%.");
                return null;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá trị không hợp lệ.");
            return null;
        }

        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và kết thúc.");
            return null;
        }

        if (ngayBatDau.after(ngayKetThuc)) {
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước ngày kết thúc.");
            return null;
        }

        Date today = new Date();
        if (ngayKetThuc.before(today)) {
            JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày hiện tại.");
            return null;
        }

        int trangThai;

        if (trangThaiStr.equalsIgnoreCase("Đang diễn ra")) {
            trangThai = 1;
        } else if (trangThaiStr.equalsIgnoreCase("Sắp diễn ra")) {
            trangThai = 2;
        } else {
            trangThai = 0;
        }

        KhuyenMai km = new KhuyenMai();
        if (isUpdate) {
            km.setId(id); // Gán ID nếu là update
        }
        km.setMa_km(maKm);
        km.setTen_km(tenKm);
        km.setLoai_giam(loaiKm);
        km.setGiam_gia(giaTri);
        km.setNgay_bat_dau(ngayBatDau);
        km.setNgay_ket_thuc(ngayKetThuc);
        km.setTrang_thai(trangThai);

        return km;
    }

    private void loadKhuyenMaiToForm(int row) {
        int idKm = Integer.parseInt(tbl_khuyenmai.getValueAt(row, 0).toString());
        KhuyenMai km = khuyenMaiDAO.findById(idKm);
        if (km == null) {
            return;
        }

        txtTen.setText(km.getTen_km());
        DecimalFormat df = new DecimalFormat("0.##");
        txtGiaTri.setText(df.format(km.getGiam_gia().doubleValue()));
        cboLoaiKm.setSelectedItem(km.getLoai_giam());
        cboTrangThai.setSelectedItem(km.getTrang_thai());
        dateBatDau.setDate(km.getNgay_bat_dau());
        dateKetThuc.setDate(km.getNgay_ket_thuc());

        maDuocChon = km;
    }

    private void clearForm() {
        txtTen.setText("");
        txtGiaTri.setText("");

        // Đặt combobox về item đầu tiên (nếu có dữ liệu)
        if (cboLoaiKm.getItemCount() > 0) {
            cboLoaiKm.setSelectedIndex(0);
        }
        if (cboTrangThai.getItemCount() > 0) {
            cboTrangThai.setSelectedIndex(0);
        }

        // Đặt ngày về null hoặc ngày hiện tại nếu muốn
        dateBatDau.setDate(null);
        dateKetThuc.setDate(null);

        maDuocChon = null;
        // Bỏ chọn trên bảng
        tbl_khuyenmai.clearSelection();
        loadSanPhamKhuyenMai();
    }

    private void filterByTinhTrang() {
        Integer trangThai = null; // null nghĩa là "Tất cả"

        if (rdo_daketthuc.isSelected()) {
            trangThai = 0;
        } else if (rdo_dangdienra.isSelected()) {
            trangThai = 1;
        } else if (rdo_sapdienra.isSelected()) {
            trangThai = 2;
        }

        List<KhuyenMai> list = khuyenMaiDAO.findByTrangThai(trangThai);
        fillTableKhuyenMai(list);
    }

    private void loadSanPhamKhuyenMai() {
        try {

            List<SanPhamKhuyenMai> ds = khuyenMaiDAO.findAllSanPhamKhuyenMaiView();

            DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
            model.setRowCount(0);

            for (SanPhamKhuyenMai dto : ds) {
                model.addRow(new Object[]{
                    dto.getMaSanPham(),
                    dto.getTenSanPham(),
                    dto.getCpu(),
                    dto.getHang(),
                    dto.getRam(),
                    dto.getCard(),
                    dto.getOcung(),
                    dto.getSoLuongBienThe(),
                    dto.getGiaBan(),
                    false, // checkbox chọn áp dụng khuyến mãi
                    dto.getSanPhamId(), // index 10 – ẩn
                    dto.getCpuId(), // index 11 – ẩn nếu cần
                    dto.getGpuId(), // index 12 – ẩn nếu cần
                    dto.getRamId(), // index 13 – ẩn nếu cần
                    dto.getSsdId() // index 14 – ẩn nếu cần
                });
            }
            int[] hiddenCols = {10, 11, 12, 13, 14}; // danh sách các cột ID cần ẩn
            for (int col : hiddenCols) {
                tbl_sanpham.getColumnModel().getColumn(col).setMinWidth(0);
                tbl_sanpham.getColumnModel().getColumn(col).setMaxWidth(0);
                tbl_sanpham.getColumnModel().getColumn(col).setWidth(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load sản phẩm ", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboLoaiKm = new javax.swing.JComboBox<>();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        dateBatDau = new com.toedter.calendar.JDateChooser();
        dateKetThuc = new com.toedter.calendar.JDateChooser();
        txtTen = new javax.swing.JTextField();
        txtGiaTri = new javax.swing.JTextField();
        btnclear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_khuyenmai = new javax.swing.JTable();
        rdo_dangdienra = new javax.swing.JRadioButton();
        rdo_daketthuc = new javax.swing.JRadioButton();
        txt_timkm = new javax.swing.JTextField();
        rdoTatca = new javax.swing.JRadioButton();
        rdo_sapdienra = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_sanpham = new javax.swing.JTable();
        btn_apdungkhuyenmai = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Khuyến Mại");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Loại khuyến mại");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Tên khuyến mại");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Giá trị");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Ngày bắt đầu");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Ngày kết thúc");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Trạng thái");

        cboLoaiKm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền", "%" }));

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang diễn ra", "Sắp diễn ra", "Đã kết thúc" }));

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setText("Cập nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnclear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnclear.setText("Làm mới");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboLoaiKm, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnThem)
                        .addGap(61, 61, 61)
                        .addComponent(btnSua))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(btnclear)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateBatDau, dateKetThuc});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtGiaTri, txtTen});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboLoaiKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateBatDau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnSua)))
                    .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnclear)
                .addGap(56, 56, 56))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboLoaiKm, cboTrangThai, dateBatDau, dateKetThuc, txtGiaTri, txtTen});

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Tìm khuyến mại");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Trạng thái");

        tbl_khuyenmai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Mã KM", "Tên KM", "Loại KM", "Giá trị", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_khuyenmai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_khuyenmaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_khuyenmai);

        buttonGroup1.add(rdo_dangdienra);
        rdo_dangdienra.setText("Đang diễn ra");
        rdo_dangdienra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_dangdienraActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_daketthuc);
        rdo_daketthuc.setText("Đã kết thúc");
        rdo_daketthuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_daketthucActionPerformed(evt);
            }
        });

        txt_timkm.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_timkm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_timkm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timkmActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTatca);
        rdoTatca.setSelected(true);
        rdoTatca.setText("Tất cả");
        rdoTatca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatcaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_sapdienra);
        rdo_sapdienra.setText("Sắp diễn ra");
        rdo_sapdienra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_sapdienraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(txt_timkm)
                .addGap(149, 149, 149)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoTatca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdo_dangdienra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdo_sapdienra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdo_daketthuc)
                .addGap(70, 70, 70))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_timkm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(27, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(rdo_dangdienra)
                            .addComponent(rdo_daketthuc)
                            .addComponent(rdoTatca)
                            .addComponent(rdo_sapdienra))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Tìn sản phẩm");

        tbl_sanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "CPU", "Hãng", "Ram", "Card", "Ổ cứng", "SL", "Giá", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_sanpham);

        btn_apdungkhuyenmai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_apdungkhuyenmai.setText("Áp dụng khuyến mại");
        btn_apdungkhuyenmai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apdungkhuyenmaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_apdungkhuyenmai, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_apdungkhuyenmai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_timkmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timkmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timkmActionPerformed

    private void tbl_khuyenmaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_khuyenmaiMouseClicked
        int row = tbl_khuyenmai.getSelectedRow();
        if (row < 0) {
            return;
        }

        int idKm = Integer.parseInt(tbl_khuyenmai.getValueAt(row, 0).toString());
        
        // Tìm khuyến mãi theo mã
        maDuocChon = khuyenMaiDAO.findById(idKm);
        if (maDuocChon == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi với mã: " + idKm);
            return;
        }

        // Gán dữ liệu lên form
        txtTen.setText(maDuocChon.getTen_km());
        DecimalFormat df = new DecimalFormat("0.##");
        txtGiaTri.setText(df.format(maDuocChon.getGiam_gia().doubleValue()));

        cboLoaiKm.setSelectedItem(maDuocChon.getLoai_giam());
        if (maDuocChon.getTrang_thai() == 1) {
            cboTrangThai.setSelectedItem("Đang diễn ra");
        } else if (maDuocChon.getTrang_thai() == 2) {
            cboTrangThai.setSelectedItem("Sắp diễn ra");
        } else if (maDuocChon.getTrang_thai() == 0) {
            cboTrangThai.setSelectedItem("Đã kết thúc");
        }
        try {
            dateBatDau.setDate(maDuocChon.getNgay_bat_dau());
            dateKetThuc.setDate(maDuocChon.getNgay_ket_thuc());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ======= Load danh sách sản phẩm đã áp dụng khuyến mãi =========
        List<SanPhamKhuyenMai> listSP = khuyenMaiDAO.getSanPhamTheoDotGiamGia(maDuocChon.getId());

        DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
        model.setRowCount(0); // Xóa bảng cũ

        if (listSP == null || listSP.isEmpty()) {
            // ✅ Nếu chưa áp dụng → hiển thị toàn bộ sản phẩm và không tick gì cả
            listSP = khuyenMaiDAO.findAllSanPhamKhuyenMaiView(); // DAO cần viết thêm
            for (SanPhamKhuyenMai dto : listSP) {
                model.addRow(new Object[]{
                    dto.getMaSanPham(),
                    dto.getTenSanPham(),
                    dto.getCpu(),
                    dto.getHang(),
                    dto.getRam(),
                    dto.getCard(),
                    dto.getOcung(),
                    dto.getSoLuongBienThe(),
                    dto.getGiaBan(),
                    false,
                    dto.getSanPhamId(),
                    dto.getCpuId(),
                    dto.getGpuId(),
                    dto.getRamId(),
                    dto.getSsdId()
                });
            }
        } else {
            // ✅ Nếu đã có sản phẩm dùng KM → chỉ hiển thị những sản phẩm đó và tick checkbox
            for (SanPhamKhuyenMai dto : listSP) {
                model.addRow(new Object[]{
                    dto.getMaSanPham(),
                    dto.getTenSanPham(),
                    dto.getCpu(),
                    dto.getHang(),
                    dto.getRam(),
                    dto.getCard(),
                    dto.getOcung(),
                    dto.getSoLuongBienThe(),
                    dto.getGiaBan(),
                    true, // ✅ đã áp dụng → tick checkbox
                    dto.getSanPhamId(),
                    dto.getCpuId(),
                    dto.getGpuId(),
                    dto.getRamId(),
                    dto.getSsdId()
                });
            }
        }
        int[] hiddenCols = {10, 11, 12, 13, 14}; // danh sách các cột ID cần ẩn
        for (int col : hiddenCols) {
            tbl_sanpham.getColumnModel().getColumn(col).setMinWidth(0);
            tbl_sanpham.getColumnModel().getColumn(col).setMaxWidth(0);
            tbl_sanpham.getColumnModel().getColumn(col).setWidth(0);
        }
    }//GEN-LAST:event_tbl_khuyenmaiMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        try {
            // Kiểm tra hợp lệ và lấy đối tượng KhuyenMai
            KhuyenMai km = validateKhuyenMai(false); // false = thêm mới
            if (km == null) {
                return;
            }
            // 2️⃣ Hỏi người dùng trước khi thêm
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thêm khuyến mãi này không?",
                    "Xác nhận thêm khuyến mãi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nếu người dùng chọn No, thoát khỏi hàm
            }

            // Gọi DAO để thêm vào CSDL
            KhuyenMai kmMoi = khuyenMaiDAO.insertsqlAll(km);
            this.maDuocChon = kmMoi; // ← dòng này là BẮT BUỘC để nhớ km vừa tạo

            // Làm mới bảng
            fillTableKhuyenMai();
            clearForm();

            // Chọn dòng vừa thêm (dòng cuối)
            int lastRow = tbl_khuyenmai.getRowCount() - 1;
            if (lastRow >= 0) {
                tbl_khuyenmai.setRowSelectionInterval(lastRow, lastRow);
            }

            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Thêm khuyến mãi thất bại!\nLý do: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
            KhuyenMai km = validateKhuyenMai(true);
            if (km == null) {
                return;
            }
            // 2️⃣ Hỏi người dùng trước khi thêm
int confirm = JOptionPane.showConfirmDialog(this,
        "Bạn có chắc muốn sửa khuyến mãi này không?",
        "Xác nhận sửa khuyến mãi",
        JOptionPane.YES_NO_OPTION);

if (confirm != JOptionPane.YES_OPTION) {
    return; // Nếu người dùng chọn No, thoát khỏi hàm
}

            // Cập nhật vào DB
            khuyenMaiDAO.updatesql(km);

            // Làm mới bảng
            int selectedRow = tbl_khuyenmai.getSelectedRow();
            fillTableKhuyenMai();

            // Giữ lại dòng đang chọn
            if (selectedRow >= 0 && selectedRow < tbl_khuyenmai.getRowCount()) {
                tbl_khuyenmai.setRowSelectionInterval(selectedRow, selectedRow);
            }

            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Cập nhật khuyến mãi thất bại!\nLý do: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void rdo_dangdienraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_dangdienraActionPerformed
        // TODO add your handling code here:
        filterByTinhTrang();
    }//GEN-LAST:event_rdo_dangdienraActionPerformed

    private void rdo_daketthucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_daketthucActionPerformed
        // TODO add your handling code here:
        filterByTinhTrang();
    }//GEN-LAST:event_rdo_daketthucActionPerformed

    private void rdoTatcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatcaActionPerformed
        // TODO add your handling code here:
        filterByTinhTrang();
    }//GEN-LAST:event_rdoTatcaActionPerformed

    private void btn_apdungkhuyenmaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apdungkhuyenmaiActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) tbl_sanpham.getModel();
            int rowCount = model.getRowCount();
            boolean daChon = false;

            KhuyenMai km = maDuocChon;
            if (km == null || km.getId() <= 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi để áp dụng (ID không hợp lệ)!");
                return;
            }

            // ===== Kiểm tra trạng thái khuyến mãi =====
            if (km.getTrang_thai() != 1 && km.getTrang_thai() != 2) {
                JOptionPane.showMessageDialog(this, "Khuyến mãi đã kết thúc hoặc bị vô hiệu hoá!", "Không thể áp dụng", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 2️⃣ Hỏi người dùng trước khi thêm
            int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn áp dụng khuyến mãi cho sản phẩm này không?",
            "Xác nhận áp dụng khuyến mãi cho sản phẩm này ?",
            JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu người dùng chọn No, thoát khỏi hàm
            }

            List<String> sanPhamBiBoQua = new ArrayList<>();

            for (int row = 0; row < rowCount; row++) {
                Boolean isSelected = (Boolean) model.getValueAt(row, 9);
                if (!Boolean.TRUE.equals(isSelected)) {
                    continue; // bỏ qua nếu không được chọn
                }

                daChon = true;

                // Lấy thông tin sản phẩm
                int sanPhamId = Integer.parseInt(model.getValueAt(row, 10).toString());
                String cpu = model.getValueAt(row, 2).toString();
                String hang = model.getValueAt(row, 3).toString();
                String ram = model.getValueAt(row, 4).toString();
                String gpu = model.getValueAt(row, 5).toString();
                String ssd = model.getValueAt(row, 6).toString();

                // Lấy ID linh kiện
                int cpuId = linhKienDAO.getIdCPUByName(cpu);
                int gpuId = linhKienDAO.getIdGPUByName(gpu);
                int ramId = linhKienDAO.getIdRAMByDungLuong(ram);
                int ssdId = linhKienDAO.getIdSSDByDungLuong(ssd);

                // Lấy danh sách ID chi tiết sản phẩm
                List<Integer> ids = ctspDAO.getDanhSachIdCTSPTheoBienThe(sanPhamId, cpuId, gpuId, ramId, ssdId);

                for (Integer idctsp : ids) {
                    boolean daApDung = ctspDAO.daTonTaiTrongDotGiamGiaConHieuLuc(idctsp);
                    if (daApDung) {
                        sanPhamBiBoQua.add("ID CTSP: " + idctsp);
                        continue;
                    }

                    // Chỉ lưu mối quan hệ, KHÔNG cập nhật giá
                    ctspDAO.insertChiTietDotGiamGia(km.getId(), idctsp);
                }
            }

            if (!daChon) {
                JOptionPane.showMessageDialog(this, "Vui lòng tick chọn sản phẩm cần áp dụng khuyến mãi!");
                return;
            }

            // Thông báo kết quả
            if (!sanPhamBiBoQua.isEmpty()) {
                StringBuilder msg = new StringBuilder("Một số cấu hình đã nằm trong chương trình khác và bị bỏ qua:\n");
                for (String sp : sanPhamBiBoQua) {
                    msg.append("- ").append(sp).append("\n");
                }
                JOptionPane.showMessageDialog(this, msg.toString(), "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Áp dụng khuyến mãi thành công cho tất cả cấu hình!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }

            loadSanPhamKhuyenMai();

            } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi áp dụng khuyến mãi: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_apdungkhuyenmaiActionPerformed

    private void rdo_sapdienraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_sapdienraActionPerformed
        // TODO add your handling code here:
        filterByTinhTrang();
    }//GEN-LAST:event_rdo_sapdienraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btn_apdungkhuyenmai;
    private javax.swing.JButton btnclear;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoaiKm;
    private javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dateBatDau;
    private com.toedter.calendar.JDateChooser dateKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JRadioButton rdoTatca;
    private javax.swing.JRadioButton rdo_daketthuc;
    private javax.swing.JRadioButton rdo_dangdienra;
    private javax.swing.JRadioButton rdo_sapdienra;
    private javax.swing.JTable tbl_khuyenmai;
    private javax.swing.JTable tbl_sanpham;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txt_timkm;
    // End of variables declaration//GEN-END:variables
}
