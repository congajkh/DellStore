/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.LinhKienDAO;
import DellStore.dao.impl.BanHangDAOImpl;
import DellStore.dao.impl.ChiTietHoaDonDAOImpl;
import DellStore.dao.impl.HinhThucThanhToanDAOImpl;
import DellStore.dao.impl.HoaDonDAOImpl;
import DellStore.dao.impl.KhachHangDAO;
import DellStore.dao.impl.KhuyenMaiDAOImpl;
import DellStore.dao.impl.SerialDAOImpl;
import DellStore.dao.impl.chitietsanphamDAO;
import DellStore.dao.impl.nhanvienDAO;
import DellStore.dao.impl.sanphamDAO;
import DellStore.entity.ChiTietHoaDon;
import DellStore.entity.ChiTietHoaDonDTO;
import DellStore.entity.GioHangDTO;
import DellStore.entity.HinhThucThanhToan;
import DellStore.entity.HoaDon;
import DellStore.entity.HoaDonDTO;
import DellStore.entity.KhachHang;
import DellStore.entity.SanPhamBanHang;
import DellStore.entity.ChiTietSanPham;
import DellStore.entity.KhuyenMai;
import DellStore.entity.NhanVien;
import DellStore.entity.SanPham;
import DellStore.entity.SanPhamChiTietViewModel;
import DellStore.entity.Serial;
import DellStore.ui.JDialog.ChonSerialJDialog;
import DellStore.ui.JDialog.KhachHangChonJDialog;
import DellStore.utils.QRCodeScannerUtil;
import DellStore.utils.XAuth;
import DellStore.utils.XJdbc;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author docon
 */
public class BanHangJPanel extends javax.swing.JPanel {
    // List để lưu các Serial đã chọn (liên kết 1-1 với JTable giỏ hàng)
    // field của class
// Mã hóa đơn hiện tại

    private String currentHoaDonId;

// Map lưu danh sách serial của từng hóa đơn
    private Map<String, List<Serial>> gioHangTheoHoaDon = new HashMap<>();

// Map lưu TableModel cho từng hóa đơn
    private Map<String, DefaultTableModel> hoaDonModels = new HashMap<>();

// Map lưu liên kết serial -> mã hóa đơn (để biết serial thuộc hóa đơn nào)
    private Map<String, String> serialToHoaDon = new HashMap<>();

    private HoaDonDAOImpl hoadonDAO = new HoaDonDAOImpl();
    private SerialDAOImpl serialDAO = new SerialDAOImpl();
    private sanphamDAO spDAO = new sanphamDAO();
    private chitietsanphamDAO ctspDAO = new chitietsanphamDAO();
    private ChiTietSanPham ctsp = new ChiTietSanPham();
    private Integer hoaDonDangTaoId = null;
    private ChiTietHoaDonDAOImpl cthdDAO = new ChiTietHoaDonDAOImpl();
    private nhanvienDAO nvDAO = new nhanvienDAO();
    private KhachHangDAO khachhangDAO = new KhachHangDAO();
    private nhanvienDAO nhanvienDAO = new nhanvienDAO();
    private HinhThucThanhToanDAOImpl htttDAO = new HinhThucThanhToanDAOImpl();
    private KhachHang getKhachHangIdDuocChon;
    private KhuyenMaiDAOImpl khuyenMaiDAOImpl = new KhuyenMaiDAOImpl();
    private LinhKienDAO linhKienDAO = new LinhKienDAO();
    private boolean gioHangDaLoadLai = false;

    /**
     * Creates new form banhangJPanel
     */
    public BanHangJPanel() {
        initComponents();
        loadHoaDonTable();
        loadSanPhamBanHang();
        String[] columnNames = {
            "STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng",
            "Giá giảm/1SP", "Thành tiền",
            "san_pham_id", // cột ẩn
            "cpu_id", // cột ẩn
            "ram_id", // cột ẩn
            "ssd_id", // cột ẩn
            "gpu_id" // cột ẩn
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tbl_giohang.setModel(model);

// Ẩn các cột thông số
        int[] hiddenCols = {7, 8, 9, 10, 11};
        for (int col : hiddenCols) {
            if (tbl_giohang.getColumnCount() > col) {
                tbl_giohang.getColumnModel().getColumn(col).setMinWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setMaxWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setPreferredWidth(0);
            }
        }
        setupAutoTinhTienTraLai();
//        timKiemTuDong();

        fillToCboHinhThucThanhToan();
    }

    private void setupAutoTinhTienTraLai() {
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                tinhTienTraLai();
            }

            public void removeUpdate(DocumentEvent e) {
                tinhTienTraLai();
            }

            public void changedUpdate(DocumentEvent e) {
                tinhTienTraLai();
            }
        };

        txt_tienkhachdua.getDocument().addDocumentListener(docListener);
        txt_tienkhachck.getDocument().addDocumentListener(docListener);
        txt_giamgia.getDocument().addDocumentListener(docListener);
    }

    private void timKiemTuDong() {
        String keyword = txt_timkiem.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        BanHangDAOImpl dao = new BanHangDAOImpl();
        List<SanPhamBanHang> list = dao.findAllSanPham();
        int stt = 1;

        for (SanPhamBanHang sp : list) {
            // Tìm theo tên sản phẩm hoặc mã sản phẩm (bạn có thể mở rộng thêm nếu muốn)
            if (keyword.isEmpty()
                    || sp.getTenSanPham().toLowerCase().contains(keyword)
                    || sp.getMaSanPham().toLowerCase().contains(keyword)) {

                model.addRow(new Object[]{
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getCpu(),
                    sp.getRam(),
                    sp.getCard(),
                    sp.getOcung(),
                    sp.getHang(),
                    sp.getGiaBan(),
                    sp.getSoLuong()
                });
            }
        }
    }

    private void loadHoaDonTable(List<HoaDonDTO> list) {//Hàm có tham số hỗ trợ lọc tình trạng 
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0); // Xoá dữ liệu cũ
        int stt = 1;
        for (HoaDonDTO hd : list) {
            String tinhTrangStr;
            switch (hd.getTrang_thai()) {
                case 0 ->
                    tinhTrangStr = "Chờ thanh toán";
                case 1 ->
                    tinhTrangStr = "Đã thanh toán";
                case 2 ->
                    tinhTrangStr = "Hủy";
                default ->
                    tinhTrangStr = "Không xác định";
            }
            model.addRow(new Object[]{
                hd.getId(),
                hd.getMa_hoa_don(),
                hd.getNgay_tao(),
                hd.getTen_nhan_vien(),
                tinhTrangStr
            });
        }
    }

    private void loadHoaDonTable() {//Hàm không tham số load bảng
        HoaDonDAOImpl dao = new HoaDonDAOImpl();
        List<HoaDonDTO> list = dao.findHoaDonChoTT();
        loadHoaDonTable(list); // gọi hàm có tham số
    }
// note: Dang tuan 30/7

    public String generateMaHoaDon() {
        String prefix = "HD";
        int nextId = 1;
        try {
            String sql = "SELECT ISNULL(MAX(id), 0) + 1 FROM hoa_don";
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                nextId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + String.format("%04d", nextId);
    }

    private void loadSanPhamBanHang() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int stt = 1;
        BanHangDAOImpl dao = new BanHangDAOImpl();
        List<SanPhamBanHang> list = dao.findAllSanPham();

        for (SanPhamBanHang spbh : list) {
            model.addRow(new Object[]{
                stt++,
                spbh.getMaSanPham(),
                spbh.getTenSanPham(),
                spbh.getCpu(),
                spbh.getRam(),
                spbh.getCard(),
                spbh.getOcung(),
                spbh.getHang(),
                spbh.getGiaBan(),
                spbh.getSoLuong()
            });
        }
    }

    public void loadGioHangTheoHoaDon(int hoaDonId) {
        System.out.println("🛒 Load giỏ hàng cho hóa đơn ID = " + hoaDonId);
        currentHoaDonId = String.valueOf(hoaDonId);

        DefaultTableModel model = cloneTableModel(tbl_giohang.getModel());
        model.setRowCount(0);

        List<Serial> serialList = gioHangTheoHoaDon.getOrDefault(currentHoaDonId, new ArrayList<>());

        int stt = 1;
        for (Serial serial : serialList) {
            ChiTietSanPham ctsp = ctspDAO.findBySerialId(serial.getId());
            if (ctsp == null) {
                continue;
            }
            SanPham sp = spDAO.findById(ctsp.getSan_pham_id());
            if (sp == null) {
                continue;
            }

            BigDecimal donGia = ctsp.getGia_ban() != null ? ctsp.getGia_ban() : BigDecimal.ZERO;
            BigDecimal giamGia = BigDecimal.ZERO;
            KhuyenMai km = khuyenMaiDAOImpl.findActiveByChiTietSanPhamId1(ctsp.getId());
            if (km != null) {
                if ("%".equals(km.getLoai_giam())) {
                    giamGia = donGia.multiply(km.getGiam_gia()).divide(BigDecimal.valueOf(100));
                } else {
                    giamGia = km.getGiam_gia();
                }
                if (giamGia.compareTo(donGia) > 0) {
                    giamGia = donGia;
                }
            }
            BigDecimal thanhTien = donGia.subtract(giamGia);

            // Gộp sản phẩm nếu cùng biến thể (5 thông số ID)
            boolean found = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                int spIdRow = (int) model.getValueAt(i, 7);
                int cpuIdRow = (int) model.getValueAt(i, 8);
                int ramIdRow = (int) model.getValueAt(i, 9);
                int ssdIdRow = (int) model.getValueAt(i, 10);
                int gpuIdRow = (int) model.getValueAt(i, 11);

                if (spIdRow == ctsp.getSan_pham_id()
                        && cpuIdRow == ctsp.getCpu_id()
                        && ramIdRow == ctsp.getRam_id()
                        && ssdIdRow == ctsp.getSsd_id()
                        && gpuIdRow == ctsp.getGpu_id()) {

                    int soLuong = (int) model.getValueAt(i, 4) + 1;
                    model.setValueAt(soLuong, i, 4);
                    model.setValueAt(thanhTien.multiply(BigDecimal.valueOf(soLuong)), i, 6);
                    found = true;
                    break;
                }
            }

            // Nếu chưa có biến thể này thì thêm mới
            if (!found) {
                model.addRow(new Object[]{
                    stt++, // STT
                    sp.getMasp(), // Mã SP
                    sp.getTen(), // Tên SP
                    donGia, // Giá
                    1, // SL
                    giamGia, // Giảm giá
                    thanhTien, // Thành tiền
                    ctsp.getSan_pham_id(), // san_pham_id (ẩn)
                    ctsp.getCpu_id(), // cpu_id (ẩn)
                    ctsp.getRam_id(), // ram_id (ẩn)
                    ctsp.getSsd_id(), // ssd_id (ẩn)
                    ctsp.getGpu_id() // gpu_id (ẩn)
                });
            }

            // Gán serial → hóa đơn
            serialToHoaDon.put(serial.getMa_serial(), currentHoaDonId);
        }

        // Lưu map & model
        gioHangTheoHoaDon.put(currentHoaDonId, serialList);
        hoaDonModels.put(currentHoaDonId, model);

        // Hiển thị
        tbl_giohang.setModel(model);

        // Ẩn các cột ID
        int[] hiddenCols = {7, 8, 9, 10, 11};
        for (int col : hiddenCols) {
            if (tbl_giohang.getColumnCount() > col) {
                tbl_giohang.getColumnModel().getColumn(col).setMinWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setMaxWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setPreferredWidth(0);
            }
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

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_giohang = new javax.swing.JTable();
        btn_xoagiohang = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txt_timkiem = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        txt_tenkh = new javax.swing.JTextField();
        btn_chonkh = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbo_hinhthuctt = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txt_tienkhachdua = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_tienkhachck = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_tientralai = new javax.swing.JTextField();
        txt_mahoadon = new javax.swing.JTextField();
        txt_tennhanvien = new javax.swing.JTextField();
        txt_giamgia = new javax.swing.JTextField();
        txt_ngaytao = new javax.swing.JTextField();
        txt_tongtien = new javax.swing.JTextField();
        btn_lammoi = new javax.swing.JButton();
        btn_thanhtoan = new javax.swing.JButton();
        btn_quetqrhoadon = new javax.swing.JButton();
        btn_themhoadon = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("HÓA ĐƠN"));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày Tạo", "Tên NV", "Trạng thái"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("GIỎ HÀNG"));

        tbl_giohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn Giá", "Số Lượng", "Giảm Giá", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_giohang);

        btn_xoagiohang.setBackground(new java.awt.Color(255, 153, 102));
        btn_xoagiohang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/xoa.png"))); // NOI18N
        btn_xoagiohang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoagiohangActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(255, 153, 102));
        btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reset.png"))); // NOI18N
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_reset)
                .addGap(32, 32, 32)
                .addComponent(btn_xoagiohang)
                .addGap(66, 66, 66))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_xoagiohang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "CPU", "Ram", "Card", "Ổ Cứng", "Hãng", "Giá Bán", "Số Lượng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        txt_timkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timkiemKeyReleased(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 153, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/timkiem.png"))); // NOI18N
        jButton5.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("ĐƠN HÀNG"));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        jLabel1.setText("SDT: ");

        jLabel2.setText("Tên KH:");

        btn_chonkh.setText("Chọn KH");
        btn_chonkh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chonkhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(txt_tenkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_chonkh)
                .addGap(5, 5, 5))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_tenkh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_chonkh)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin đơn hàng"));

        jLabel3.setText("Tên NV: ");

        jLabel4.setText("Mã HD:");

        jLabel5.setText("Giảm Giá");

        jLabel13.setText("Ngày Tạo: ");

        jLabel14.setText("Tổng Tiền: ");

        jLabel15.setText("HT thanh toán:");

        jLabel17.setText("Tiền Khách Đưa:");

        jLabel18.setText("Tiền khách CK:");

        txt_tienkhachck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tienkhachckActionPerformed(evt);
            }
        });

        jLabel19.setText("Tiền Trả Lại: ");

        txt_tientralai.setEnabled(false);
        txt_tientralai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tientralaiActionPerformed(evt);
            }
        });

        txt_mahoadon.setBorder(null);
        txt_mahoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mahoadonActionPerformed(evt);
            }
        });

        txt_tennhanvien.setBorder(null);
        txt_tennhanvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tennhanvienActionPerformed(evt);
            }
        });

        txt_giamgia.setBorder(null);
        txt_giamgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_giamgiaActionPerformed(evt);
            }
        });

        txt_ngaytao.setBorder(null);
        txt_ngaytao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngaytaoActionPerformed(evt);
            }
        });

        txt_tongtien.setBorder(null);
        txt_tongtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tongtienActionPerformed(evt);
            }
        });

        btn_lammoi.setBackground(new java.awt.Color(255, 153, 102));
        btn_lammoi.setForeground(new java.awt.Color(255, 0, 51));
        btn_lammoi.setText("Làm mới");

        btn_thanhtoan.setBackground(new java.awt.Color(255, 153, 102));
        btn_thanhtoan.setForeground(new java.awt.Color(255, 0, 0));
        btn_thanhtoan.setText("Thanh toán");
        btn_thanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhtoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tienkhachdua))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel13))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ngaytao)
                            .addComponent(txt_mahoadon, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(txt_tennhanvien)
                            .addComponent(txt_giamgia)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_hinhthuctt, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tientralai)
                            .addComponent(txt_tienkhachck))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_mahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_tennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_giamgia, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_ngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_tongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(cbo_hinhthuctt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txt_tienkhachdua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_tienkhachck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tientralai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_quetqrhoadon.setBackground(new java.awt.Color(255, 153, 102));
        btn_quetqrhoadon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/qr-code.png"))); // NOI18N
        btn_quetqrhoadon.setText("Quét mã");
        btn_quetqrhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quetqrhoadonActionPerformed(evt);
            }
        });

        btn_themhoadon.setBackground(new java.awt.Color(255, 153, 102));
        btn_themhoadon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_themhoadon.setText("Thêm hóa đơn");
        btn_themhoadon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themhoadonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_quetqrhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_themhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(btn_quetqrhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_themhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tienkhachckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tienkhachckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tienkhachckActionPerformed

    private void txt_tientralaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tientralaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tientralaiActionPerformed

    private void txt_mahoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mahoadonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mahoadonActionPerformed

    private void txt_tennhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tennhanvienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tennhanvienActionPerformed

    private void txt_giamgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_giamgiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_giamgiaActionPerformed

    private void txt_ngaytaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngaytaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ngaytaoActionPerformed

    private void txt_tongtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tongtienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tongtienActionPerformed
    // 1. Sửa hàm btn_themhoadonActionPerformed
    private void btn_themhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themhoadonActionPerformed
        HoaDon hd = new HoaDon();
        if (getKhachHangIdDuocChon != null) {
            System.out.println("👉 Tạo hóa đơn với KH ID: " + getKhachHangIdDuocChon.getId());
        }

        System.out.println("👉 Tạo hóa đơn KH mặc định (null)");
        try {
            // Kiểm tra đã đăng nhập hay chưa
            if (!XAuth.isLogin()) {
                JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập trước khi tạo hóa đơn!");
                return;
            }
            if (XAuth.user == null) {
                JOptionPane.showMessageDialog(this, "Chưa đăng nhập nhân viên!");
                return;
            }
            int nhanVienId = XAuth.user.getId();
            System.out.println("ID nhân viên đăng nhập: " + nhanVienId);
            if (nhanVienId <= 0) {
                JOptionPane.showMessageDialog(this, "ID nhân viên đăng nhập không hợp lệ! Vui lòng đăng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Kiểm tra nhân viên có tồn tại trong DB không
            NhanVien nvCheck = nvDAO.findById(nhanVienId);
            if (nvCheck == null) {
                JOptionPane.showMessageDialog(this, "Nhân viên với ID " + nhanVienId + " không tồn tại trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;

            }
            int soHoaDonChuaTT = hoadonDAO.countHoaDonChuaThanhToan();
            if (soHoaDonChuaTT >= 10) {
                JOptionPane.showMessageDialog(this, "❌ Chỉ được tạo tối đa 10 hóa đơn chờ thanh toán!");
                return;
            }
            // Kiểm tra khách hàng đã chọn chưa (nếu có chọn thủ công từ UI)
            int khachHangId;
            if (getKhachHangIdDuocChon != null) {
                System.out.println("👉 Tạo hóa đơn với KH ID: " + getKhachHangIdDuocChon.getId());
                khachHangId = getKhachHangIdDuocChon.getId();
                hd.setKhach_hang_id(khachHangId);
                System.out.println(">> KH được chọn: ID = " + khachHangId);
            } else {
                khachHangId = 14;// khách hàng mặc định khi tạo hoá đơn mà không chọn khách hàng trước 
                hd.setKhach_hang_id(khachHangId);
                System.out.println(">> KH mặc định: ID = 14");
            }

            hd.setMa(generateMaHoaDon());
            hd.setNhan_vien_id(nhanVienId);
            hd.setNgay_tao(new java.util.Date());
            hd.setTong_tien(0.0);
            hd.setTrang_thai(0);

            // Lưu và lấy ID
            int newHoaDonId = hoadonDAO.createAndReturnId(hd);
            this.hoaDonDangTaoId = newHoaDonId;
            this.currentHoaDonId = String.valueOf(newHoaDonId); // ✅ CẬP NHẬT currentHoaDonId

            JOptionPane.showMessageDialog(this, "Tạo hóa đơn mới thành công: " + hd.getMa());

            // ✅ Load lại bảng hóa đơn
            loadHoaDonTable();

            // ✅ Tìm và chọn đúng hóa đơn vừa tạo
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int hoaDonIdInTable = (int) model.getValueAt(i, 0); // Cột đầu tiên là ID
                if (hoaDonIdInTable == newHoaDonId) {
                    tblHoaDon.setRowSelectionInterval(i, i);
                    break;
                }
            }

            // ✅ Reset giỏ hàng cho hóa đơn mới
            resetGioHangForNewHoaDon(newHoaDonId);

            // ✅ Cập nhật thông tin hóa đơn trên UI
            updateHoaDonInfoUI(newHoaDonId);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_themhoadonActionPerformed
    // 2. Thêm hàm reset giỏ hàng cho hóa đơn mới
    private void resetGioHangForNewHoaDon(int newHoaDonId) {
        String newHoaDonIdStr = String.valueOf(newHoaDonId);

        // ✅ Tạo giỏ hàng trống cho hóa đơn mới
        gioHangTheoHoaDon.put(newHoaDonIdStr, new ArrayList<>());

        // ✅ Tạo model trống cho hóa đơn mới
        String[] columnNames = {
            "STT", "Mã SP", "Tên SP", "Đơn giá", "Số lượng",
            "Giá giảm/1SP", "Thành tiền",
            "san_pham_id", "cpu_id", "ram_id", "ssd_id", "gpu_id"
        };
        DefaultTableModel emptyModel = new DefaultTableModel(columnNames, 0);
        hoaDonModels.put(newHoaDonIdStr, emptyModel);

        // ✅ Hiển thị model trống
        tbl_giohang.setModel(emptyModel);

        // ✅ Ẩn các cột thông số
        int[] hiddenCols = {7, 8, 9, 10, 11};
        for (int col : hiddenCols) {
            if (tbl_giohang.getColumnCount() > col) {
                tbl_giohang.getColumnModel().getColumn(col).setMinWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setMaxWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setPreferredWidth(0);
            }
        }
    }
    
    // 3. Thêm hàm cập nhật thông tin hóa đơn trên UI
    private void updateHoaDonInfoUI(int hoaDonId) {
        try {
            HoaDon hd = hoadonDAO.findById(hoaDonId);
            if (hd != null) {
                txt_mahoadon.setText(hd.getMa());

                NhanVien nv = nvDAO.findById(hd.getNhan_vien_id());
                txt_tennhanvien.setText(nv != null ? nv.getTen_nv() : "");

                txt_ngaytao.setText(hd.getNgay_tao() != null ? hd.getNgay_tao().toString() : "");

                // ✅ Reset các giá trị về 0 cho hóa đơn mới
                txt_giamgia.setText("0 VNĐ");
                txt_tongtien.setText("0 VNĐ");
                txt_tienkhachdua.setText("");
                txt_tienkhachck.setText("");
                txt_tientralai.setText("0 VNĐ");

                // ✅ Thông tin khách hàng
                String tenKH = "Khách lẻ", sdtKH = "";
                try {
                    KhachHang kh = khachhangDAO.findById(hd.getKhach_hang_id());
                    if (kh != null) {
                        if (kh.getTen() != null && !kh.getTen().isEmpty()) {
                            tenKH = kh.getTen();
                        }
                        if (kh.getSdt() != null && !kh.getSdt().isEmpty()) {
                            sdtKH = kh.getSdt();
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("Lỗi khi lấy thông tin khách hàng: " + ex.getMessage());
                }
                txt_tenkh.setText(tenKH);
                txt_sdt.setText(sdtKH);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

// TODO add your handling code here:
        // Kiểm tra đã có hóa đơn chưa
        if (hoaDonDangTaoId == null || hoaDonDangTaoId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Bạn chưa chọn hóa đơn!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            int selectedRow = tblSanPham.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            // Lấy dữ liệu từ bảng theo đúng vị trí cột
            String maSP = tblSanPham.getValueAt(selectedRow, 1).toString(); // Mã SP
            String tenSP = tblSanPham.getValueAt(selectedRow, 2).toString(); // Tên SP
            String cpu = tblSanPham.getValueAt(selectedRow, 3).toString();
            String ramDungLuong = tblSanPham.getValueAt(selectedRow, 4).toString();
            String gpu = tblSanPham.getValueAt(selectedRow, 5).toString();
            String ssdDungLuong = tblSanPham.getValueAt(selectedRow, 6).toString();
            BigDecimal giaBan = new BigDecimal(tblSanPham.getValueAt(selectedRow, 8).toString());

            // Tạo đối tượng sản phẩm
            SanPham sp = new SanPham();
            sp.setMasp(maSP);
            sp.setTen(tenSP);

            // Lấy danh sách Serial chưa bán của đúng biến thể
            List<Serial> serialList = serialDAO.getByBienThe(
                    maSP, cpu, ramDungLuong, gpu, ssdDungLuong, giaBan
            ).stream()
                    .filter(s -> s.getTrang_thai() == 0) // 0 = chưa bán
                    .toList();

            // Mở JDialog chọn serial
            ChonSerialJDialog dialog = new ChonSerialJDialog(null, true, hoaDonDangTaoId);
            dialog.loadTable(sp, serialList);
            dialog.setVisible(true);

            // Lấy serial được chọn và thêm vào giỏ hàng
            List<Serial> selectedSerials = dialog.getSelectedSerials();
            for (Serial s : selectedSerials) {
                addToGioHang(s);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi chọn sản phẩm hoặc serial: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked
    // 4. Sửa hàm tblHoaDonMouseClicked để đảm bảo đồng bộ
    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        try {
            int row = tblHoaDon.getSelectedRow();
            if (row >= 0) {
                int hoaDonId = (int) tblHoaDon.getValueAt(row, 0);

                // ✅ Cập nhật cả hai biến quan trọng
                this.hoaDonDangTaoId = hoaDonId;
                this.currentHoaDonId = String.valueOf(hoaDonId);

                // ✅ Load giỏ hàng từ database cho hóa đơn được chọn
                List<Serial> serialList = cthdDAO.getSerialByHoaDonId(hoaDonId);
                gioHangTheoHoaDon.put(String.valueOf(hoaDonId), serialList);

                // ✅ Load lại giỏ hàng
                loadGioHangTheoHoaDon(hoaDonId);

                // ✅ Cập nhật thông tin hóa đơn
                updateHoaDonInfoFromSelected(hoaDonId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi tải dữ liệu hóa đơn: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    // 5. Tách riêng logic cập nhật thông tin hóa đơn khi click
    private void updateHoaDonInfoFromSelected(int hoaDonId) {
        try {
            HoaDon hd = hoadonDAO.findById(hoaDonId);
            if (hd != null) {
                txt_mahoadon.setText(hd.getMa());

                NhanVien nv = nvDAO.findById(hd.getNhan_vien_id());
                txt_tennhanvien.setText(nv != null ? nv.getTen_nv() : "Không rõ");

                // Giảm giá tổng
                DecimalFormat df = new DecimalFormat("#,##0");
                BigDecimal tongGiamGia = BigDecimal.ZERO;
                List<ChiTietHoaDon> ds = cthdDAO.getCTHDByHoaDonId(hoaDonId);
                for (ChiTietHoaDon cthd : ds) {
                    if (cthd.getGiam_gia() != null) {
                        tongGiamGia = tongGiamGia.add(cthd.getGiam_gia());
                    }
                }
                txt_giamgia.setText(df.format(tongGiamGia) + " VNĐ");

                txt_ngaytao.setText(hd.getNgay_tao() != null ? hd.getNgay_tao().toString() : "");

                BigDecimal tongTien = hoadonDAO.getTongTienByHoaDonId(hd.getId());
                BigDecimal tongSauGiam = tongTien.subtract(tongGiamGia);
                txt_tongtien.setText(df.format(tongSauGiam) + " VNĐ");

                // Tính tiền trả lại
                tinhTienTraLai();

                // Khách hàng
                String tenKH = "Khách lẻ", sdtKH = "";
                try {
                    KhachHang kh = khachhangDAO.findById(hd.getKhach_hang_id());
                    if (kh != null) {
                        if (kh.getTen() != null && !kh.getTen().isEmpty()) {
                            tenKH = kh.getTen();
                        }
                        if (kh.getSdt() != null && !kh.getSdt().isEmpty()) {
                            sdtKH = kh.getSdt();
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("Lỗi khi lấy thông tin khách hàng: " + ex.getMessage());
                }
                txt_tenkh.setText(tenKH);
                txt_sdt.setText(sdtKH);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void btn_thanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhtoanActionPerformed
        // Nếu giỏ hàng đang trống → thông báo và dừng
        // Nếu giỏ hàng chưa load lại thì chặn
// Nếu giỏ hàng chưa load lại thì chặn
        if (!gioHangDaLoadLai) {
            JOptionPane.showMessageDialog(this, "❌ Bạn cần load lại giỏ hàng trước khi thanh toán.");
            gioHangDaLoadLai = false; // reset luôn
            return;
        }

// Lấy hóa đơn đang chọn
        int row = tblHoaDon.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán.");
            gioHangDaLoadLai = false;
            return;
        }

        int hoaDonId = (int) tblHoaDon.getValueAt(row, 0);
        HoaDon hd = hoadonDAO.findById(hoaDonId);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn.");
            gioHangDaLoadLai = false;
            return;
        }

// 🔍 Kiểm tra giỏ hàng của hóa đơn này có sản phẩm không
        List<ChiTietHoaDon> cthdList = cthdDAO.getCTHDByHoaDonId(hoaDonId);
        if (cthdList == null || cthdList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Giỏ hàng của hóa đơn này đang trống. Không thể thanh toán!");
            gioHangDaLoadLai = false;
            return;
        }

// Tính tổng tiền thực tế (sau giảm)
        BigDecimal tongTienGoc = hoadonDAO.getTongTienByHoaDonId(hoaDonId);

// Lấy tổng giảm giá nếu có
        BigDecimal giamGia = BigDecimal.ZERO;
        try {
            giamGia = khuyenMaiDAOImpl.getTongGiamGiaTheoHoaDon(hoaDonId);
        } catch (Exception e) {
        }

// Tổng tiền sau giảm
        BigDecimal tongTien = tongTienGoc.subtract(giamGia);
        hd.setTong_tien(tongTien.doubleValue());

// Lấy tiền khách đưa
        BigDecimal tienKhachDua = BigDecimal.ZERO;
        BigDecimal tienKhachCK = BigDecimal.ZERO;
        try {
            tienKhachDua = new BigDecimal(txt_tienkhachdua.getText().replaceAll("[^0-9]", "").trim());
        } catch (Exception e) {
        }
        try {
            tienKhachCK = new BigDecimal(txt_tienkhachck.getText().replaceAll("[^0-9]", "").trim());
        } catch (Exception e) {
        }

        BigDecimal tongTienKhachTra = tienKhachDua.add(tienKhachCK);
        BigDecimal tienTraLai = tongTienKhachTra.subtract(tongTien);

// Kiểm tra đủ tiền chưa
        if (tienTraLai.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "❌ Số tiền khách trả không đủ để thanh toán.");
            gioHangDaLoadLai = false; // bắt load lại giỏ hàng nếu fail
            return;
        }

// ✅ Cập nhật hóa đơn đã thanh toán
        hd.setTrang_thai(1);
        hoadonDAO.update(hd);

// Cập nhật tồn kho + serial
        int hinhThucId = 0;
        Object selected = cbo_hinhthuctt.getSelectedItem();
        if (selected instanceof HinhThucThanhToan) {
            hinhThucId = ((HinhThucThanhToan) selected).getId();
        }
        for (ChiTietHoaDon cthd : cthdList) {
            cthdDAO.updateHinhThucThanhToan(cthd.getId(), hinhThucId);

            ChiTietSanPham ctsp = ctspDAO.findById(cthd.getChi_tiet_san_pham_id());
            if (ctsp != null) {
                SanPham sp = spDAO.findById(ctsp.getSan_pham_id());
                if (sp != null) {
                    int soLuongMoi = Math.max(0, sp.getSo_luong() - cthd.getSo_luong());
                    sp.setSo_luong(soLuongMoi);
                    spDAO.update(sp);
                }

                // Cập nhật serial
                List<Serial> listSerial = serialDAO.getSerialByCTSPAndTrangThai(ctsp.getId(), 0);
                for (Serial s : listSerial) {
                    s.setTrang_thai(1);
                    serialDAO.updateTrangThai(s.getId(), s.getTrang_thai());
                }
            }
        }

// Thông báo + in hóa đơn
        JOptionPane.showMessageDialog(this, "✅ Thanh toán thành công cho hóa đơn: " + hd.getMa());
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn không?", "In hóa đơn", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            inHoaDon(hd.getMa());
        }

// Làm mới dữ liệu
        loadHoaDonTable();
        loadSanPhamBanHang();
        DefaultTableModel modelGioHang = (DefaultTableModel) tbl_giohang.getModel();
        modelGioHang.setRowCount(0);

// Reset các trường
        txt_mahoadon.setText("");
        txt_tennhanvien.setText("");
        txt_ngaytao.setText("");
        txt_tenkh.setText("");
        txt_sdt.setText("");
        txt_giamgia.setText("0");
        txt_tongtien.setText("0");
        txt_tienkhachdua.setText("");
        txt_tienkhachck.setText("");
        txt_tientralai.setText("");

// ✅ Chỉ reset hoaDonDangTaoId một lần duy nhất sau khi thanh toán
        hoaDonDangTaoId = -1;
        gioHangDaLoadLai = false;
    }//GEN-LAST:event_btn_thanhtoanActionPerformed

    private void btn_xoagiohangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoagiohangActionPerformed
        int selectedRow = tbl_giohang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn sản phẩm để xóa.");
            return;
        }

        // Lấy thông tin biến thể từ bảng (các cột ẩn)
        int spId = (int) tbl_giohang.getValueAt(selectedRow, 7);
        int cpuId = (int) tbl_giohang.getValueAt(selectedRow, 8);
        int ramId = (int) tbl_giohang.getValueAt(selectedRow, 9);
        int ssdId = (int) tbl_giohang.getValueAt(selectedRow, 10);
        int gpuId = (int) tbl_giohang.getValueAt(selectedRow, 11);

        // Lấy danh sách serial trong giỏ của hóa đơn hiện tại
        List<Serial> gioHangSerials = gioHangTheoHoaDon.get(currentHoaDonId);
        if (gioHangSerials == null || gioHangSerials.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Giỏ hàng trống hoặc dữ liệu không đồng bộ.");
            return;
        }

        // Lọc serial thuộc đúng biến thể
        List<Serial> serialCuaBienThe = gioHangSerials.stream()
                .filter(s -> {
                    ChiTietSanPham ctsp = ctspDAO.findBySerialId(s.getId());
                    return ctsp != null
                            && ctsp.getSan_pham_id() == spId
                            && ctsp.getCpu_id() == cpuId
                            && ctsp.getRam_id() == ramId
                            && ctsp.getSsd_id() == ssdId
                            && ctsp.getGpu_id() == gpuId;
                })
                .collect(Collectors.toList());

        if (serialCuaBienThe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Không tìm thấy serial cho biến thể này.");
            return;
        }

        // Lấy thông tin sản phẩm
        SanPham sp = spDAO.findById(spId);

        if (serialCuaBienThe.size() == 1) {
            // Chỉ có 1 serial → xác nhận xóa
            Serial s = serialCuaBienThe.get(0);
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa sản phẩm với serial: " + s.getMa_serial() + " ?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                removeSerialFromHoaDon(currentHoaDonId, s.getMa_serial());
                JOptionPane.showMessageDialog(this, "✅ Đã xóa sản phẩm khỏi giỏ hàng.");
            }
        } else {
            // Nhiều serial → mở dialog chọn (giữ nguyên format bảng nhập)
            showMultiSelectDialog(currentHoaDonId, sp, serialCuaBienThe);
        }
        loadGioHangTheoHoaDon(Integer.parseInt(currentHoaDonId));
        loadSanPhamBanHang();

    }//GEN-LAST:event_btn_xoagiohangActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        if (hoaDonDangTaoId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi reset.");
            return;
        }

        // Load lại giỏ hàng
        loadGioHangTheoHoaDon(hoaDonDangTaoId);

        // Lấy tổng tiền trước giảm
        BigDecimal tongTien = hoadonDAO.getTongTienByHoaDonId(hoaDonDangTaoId);

        // Lấy giá trị giảm (nếu có)
        BigDecimal giamGia = BigDecimal.ZERO;
        try {
            giamGia = khuyenMaiDAOImpl.getTongGiamGiaTheoHoaDon(hoaDonDangTaoId);
        } catch (Exception e) {
            // Không có giảm giá thì giữ nguyên 0
        }

        // Tạo formatter: phân cách hàng nghìn, không hiển thị phần thập phân nếu số nguyên
        DecimalFormat df = new DecimalFormat("#,##0");

        // Hiển thị giảm giá
        txt_giamgia.setText(df.format(giamGia) + " VNĐ");

        // Cập nhật tổng tiền sau giảm
        BigDecimal tongSauGiam = tongTien.subtract(giamGia);
        txt_tongtien.setText(df.format(tongSauGiam) + " VNĐ");

        // Cập nhật tiền trả lại
        BigDecimal tienKhachDua = BigDecimal.ZERO;
        BigDecimal tienKhachCK = BigDecimal.ZERO;

        try {
            tienKhachDua = new BigDecimal(txt_tienkhachdua.getText().replaceAll("[^0-9]", "").trim());
        } catch (Exception e) {
            // giữ nguyên 0
        }

        try {
            tienKhachCK = new BigDecimal(txt_tienkhachck.getText().replaceAll("[^0-9]", "").trim());
        } catch (Exception e) {
            // giữ nguyên 0
        }

        BigDecimal tienTraLai = tienKhachDua.add(tienKhachCK).subtract(tongSauGiam);
        txt_tientralai.setText(df.format(tienTraLai) + " VNĐ");
        gioHangDaLoadLai = true;

    }//GEN-LAST:event_btn_resetActionPerformed

    private void txt_timkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timkiemKeyReleased
        // TODO add your handling code here:
        timKiemTuDong();
    }//GEN-LAST:event_txt_timkiemKeyReleased

    private void btn_quetqrhoadonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quetqrhoadonActionPerformed
        QRCodeScannerUtil.openScannerFrame(maSerial -> {
            xuLyQR(maSerial);
        });
    }//GEN-LAST:event_btn_quetqrhoadonActionPerformed
    private void xuLyQR(String maSerialQR) {
        // 1. Kiểm tra hóa đơn đã chọn chưa
        if (hoaDonDangTaoId == null || hoaDonDangTaoId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Bạn chưa chọn hóa đơn!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Chuẩn hóa QR
            String serialClean = maSerialQR.trim().toUpperCase();

            // 3. Lấy Serial từ DAO
            Serial s = serialDAO.findByMaSerial(serialClean);
            if (s == null) {
                JOptionPane.showMessageDialog(this,
                        "Mã serial không tồn tại: " + serialClean,
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // 4. Lấy chi tiết sản phẩm theo Serial
            ChiTietSanPham ctsp = ctspDAO.findById(s.getCtsp_id());
            if (ctsp == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy chi tiết sản phẩm cho serial: " + serialClean,
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // 5. Kiểm tra trạng thái Serial
            if (s.getTrang_thai() != 0) { // 0 = chưa bán
                JOptionPane.showMessageDialog(this,
                        "Serial này đã được bán hoặc đang giữ!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // 6. Kiểm tra trùng chi tiết hóa đơn
            ChiTietHoaDon cthdCu = cthdDAO.findByHoaDonAndCTSP(hoaDonDangTaoId, ctsp.getId());
            if (cthdCu != null) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Serial đã được chọn trước đó: " + serialClean,
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // 7. Thêm vào chi tiết hóa đơn
            ChiTietHoaDon cthd = new ChiTietHoaDon();
            cthd.setHoa_don_id(hoaDonDangTaoId);
            cthd.setChi_tiet_san_pham_id(ctsp.getId());
            cthd.setSo_luong(1); // mỗi Serial chỉ 1 sản phẩm
            cthd.setDon_gia(ctsp.getGia_ban());
            cthd.setGiam_gia(BigDecimal.ZERO);
            cthd.setTrang_thai(0);
            cthdDAO.insert(cthd);

            // 8. Update trạng thái Serial sang "đang giữ" (2)
            s.setTrang_thai(2);
            serialDAO.updateTrangThai(s.getId(), s.getTrang_thai());

            // 9. Thêm vào giỏ hàng UI
            addToGioHang(s);

            System.out.println("✅ Serial " + serialClean + " đã được thêm vào giỏ hàng và lưu SQL.");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi xử lý QR: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void btn_chonkhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chonkhActionPerformed
        KhachHangChonJDialog dialog = new KhachHangChonJDialog(null, true);
        dialog.setVisible(true);

        KhachHang khachHangChon = dialog.getKhachHangIdDuocChon();
        if (khachHangChon != null) {
            this.getKhachHangIdDuocChon = khachHangChon;
            System.out.println("Đã chọn khách hàng ID: " + khachHangChon.getId());

            // Hiển thị tên và SĐT lên giao diện
            txt_tenkh.setText(khachHangChon.getTen());
            txt_sdt.setText(khachHangChon.getSdt());
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btn_chonkhActionPerformed

    private void tinhTienTraLai() {
        try {
            // Tạo formatter: phân cách hàng nghìn, không hiện phần thập phân
            DecimalFormat df = new DecimalFormat("#,##0");

            // Lấy tổng tiền từ txt_tongtien (loại bỏ ký tự không phải số)
            BigDecimal tongTien = new BigDecimal(txt_tongtien.getText().replaceAll("[^0-9]", "").trim());

            BigDecimal tienKhachDua = BigDecimal.ZERO;
            BigDecimal tienKhachCK = BigDecimal.ZERO;

            try {
                tienKhachDua = new BigDecimal(txt_tienkhachdua.getText().replaceAll("[^0-9]", "").trim());
            } catch (NumberFormatException e) {
                tienKhachDua = BigDecimal.ZERO;
            }

            try {
                tienKhachCK = new BigDecimal(txt_tienkhachck.getText().replaceAll("[^0-9]", "").trim());
            } catch (NumberFormatException e) {
                tienKhachCK = BigDecimal.ZERO;
            }

            // Tính tiền trả lại: tiền khách đưa + CK - tổng tiền
            BigDecimal tienTraLai = tienKhachDua.add(tienKhachCK).subtract(tongTien);

            // Hiển thị với định dạng
            txt_tientralai.setText(df.format(tienTraLai) + " VNĐ");
        } catch (Exception e) {
            txt_tientralai.setText("0 VNĐ");
        }
    }

    // Giỏ hàng của từng hóa đơn: hóa đơn ID -> danh sách Serial
// ✅ Thêm sản phẩm (Serial) vào giỏ hàng của hóa đơn hiện tại
    private void addToGioHang(Serial serial) {
        // Nếu serial đang ở hóa đơn khác → xóa khỏi hóa đơn cũ
        String hoaDonCu = serialToHoaDon.get(serial.getMa_serial());
        if (hoaDonCu != null && !hoaDonCu.equals(currentHoaDonId)) {
            JOptionPane.showMessageDialog(this,
                    "🔄 Sản phẩm '" + serial.getMa_serial()
                    + "' đã được chuyển từ hóa đơn " + hoaDonCu
                    + " sang hóa đơn " + currentHoaDonId + ".");
            removeSerialFromHoaDon(hoaDonCu, serial.getMa_serial());
        }

        List<Serial> gioHangSerials = gioHangTheoHoaDon.computeIfAbsent(currentHoaDonId, k -> new ArrayList<>());
        DefaultTableModel model = hoaDonModels.computeIfAbsent(currentHoaDonId, k -> cloneTableModel(tbl_giohang.getModel()));

        // Lấy thông tin sản phẩm từ Serial
        ChiTietSanPham ctsp = ctspDAO.findBySerialId(serial.getId());
        if (ctsp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết sản phẩm cho serial: " + serial.getMa_serial());
            return;
        }
        SanPham sp = spDAO.findById(ctsp.getSan_pham_id());
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm cho serial: " + serial.getMa_serial());
            return;
        }

        // Giá bán & khuyến mãi
        BigDecimal donGia = ctsp.getGia_ban() != null ? ctsp.getGia_ban() : BigDecimal.ZERO;
        BigDecimal giamGia = BigDecimal.ZERO;
        KhuyenMai km = khuyenMaiDAOImpl.findActiveByChiTietSanPhamId1(ctsp.getId());
        if (km != null) {
            if ("%".equals(km.getLoai_giam())) {
                giamGia = donGia.multiply(km.getGiam_gia()).divide(BigDecimal.valueOf(100));
            } else {
                giamGia = km.getGiam_gia();
            }
            if (giamGia.compareTo(donGia) > 0) {
                giamGia = donGia;
            }
        }
        BigDecimal thanhTien = donGia.subtract(giamGia);

        // Kiểm tra xem biến thể đã tồn tại chưa (so sánh theo 5 thông số)
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            int spIdRow = (int) model.getValueAt(i, 7);
            int cpuIdRow = (int) model.getValueAt(i, 8);
            int ramIdRow = (int) model.getValueAt(i, 9);
            int ssdIdRow = (int) model.getValueAt(i, 10);
            int gpuIdRow = (int) model.getValueAt(i, 11);

            if (spIdRow == ctsp.getSan_pham_id()
                    && cpuIdRow == ctsp.getCpu_id()
                    && ramIdRow == ctsp.getRam_id()
                    && ssdIdRow == ctsp.getSsd_id()
                    && gpuIdRow == ctsp.getGpu_id()) {

                int soLuong = (int) model.getValueAt(i, 4) + 1;
                model.setValueAt(soLuong, i, 4);
                model.setValueAt(thanhTien.multiply(BigDecimal.valueOf(soLuong)), i, 6);
                found = true;
                break;
            }
        }

        // Nếu chưa có → thêm mới
        if (!found) {
            model.addRow(new Object[]{
                model.getRowCount() + 1, // STT
                sp.getMasp(), // Mã SP
                sp.getTen(), // Tên SP
                donGia, // Giá
                1, // SL
                giamGia, // Giảm giá
                thanhTien, // Thành tiền
                ctsp.getSan_pham_id(), // san_pham_id (ẩn)
                ctsp.getCpu_id(), // cpu_id (ẩn)
                ctsp.getRam_id(), // ram_id (ẩn)
                ctsp.getSsd_id(), // ssd_id (ẩn)
                ctsp.getGpu_id() // gpu_id (ẩn)
            });
        }

        // Thêm serial vào list
        gioHangSerials.add(serial);

        // Lưu lại map
        serialToHoaDon.put(serial.getMa_serial(), currentHoaDonId);
        gioHangTheoHoaDon.put(currentHoaDonId, gioHangSerials);
        hoaDonModels.put(currentHoaDonId, model);

        // Refresh bảng
        tbl_giohang.setModel(model);
        loadSanPhamBanHang();

        // Ẩn các cột ID
        int[] hiddenCols = {7, 8, 9, 10, 11};
        for (int col : hiddenCols) {
            if (tbl_giohang.getColumnCount() > col) {
                tbl_giohang.getColumnModel().getColumn(col).setMinWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setMaxWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setWidth(0);
                tbl_giohang.getColumnModel().getColumn(col).setPreferredWidth(0);
            }
        }
    }

// ✅ Xóa Serial khỏi giỏ hàng của một hóa đơn
    private void removeSerialFromHoaDon(String hoaDonId, String maSerial) {

        Serial s = serialDAO.findByMaSerial(maSerial);
        if (s != null) {
            serialDAO.updateTrangThai(s.getId(), 0);
        }

        List<Serial> gioHangSerials = gioHangTheoHoaDon.get(hoaDonId);
        DefaultTableModel model = hoaDonModels.get(hoaDonId);

        if (gioHangSerials == null || gioHangSerials.isEmpty() || model == null) {
            System.out.println("⚠️ Không có serial hoặc model để xóa cho hóa đơn " + hoaDonId);
            return;
        }

        // Tìm Serial cần xóa
        Serial serialDaXoa = null;
        for (int i = 0; i < gioHangSerials.size(); i++) {
            if (gioHangSerials.get(i).getMa_serial().equals(maSerial)) {
                serialDaXoa = gioHangSerials.remove(i);
                break;
            }
        }

        if (serialDaXoa == null) {
            System.out.println("⚠️ Không tìm thấy serial: " + maSerial);
            return;
        }

        // Lấy CTSP_ID của serial này
        ChiTietSanPham ctsp = ctspDAO.findBySerialId(serialDaXoa.getId());
        if (ctsp == null) {
            return;
        }
        String ctspIdStr = String.valueOf(ctsp.getId());

        // Cập nhật lại model: giảm SL hoặc xóa row nếu SL = 1
        for (int row = 0; row < model.getRowCount(); row++) {
            String ctspInRow = String.valueOf(model.getValueAt(row, 7)); // cột 7 lưu CTSP_ID
            if (ctspInRow.equals(ctspIdStr)) {
                int soLuong = (int) model.getValueAt(row, 4);
                BigDecimal donGia = (BigDecimal) model.getValueAt(row, 3);
                BigDecimal giamGia = (BigDecimal) model.getValueAt(row, 5);

                if (soLuong > 1) {
                    soLuong -= 1;
                    model.setValueAt(soLuong, row, 4);
                    model.setValueAt(donGia.subtract(giamGia).multiply(BigDecimal.valueOf(soLuong)), row, 6);
                } else {
                    model.removeRow(row);
                }
                break;
            }
        }

        // Xóa mapping Serial → Hóa đơn
        serialToHoaDon.remove(maSerial);

        // Xóa CTHD trong DB
        int hoaDonInt = Integer.parseInt(hoaDonId);
        ChiTietHoaDon cthd = cthdDAO.findByHoaDonAndCTSP(hoaDonInt, ctsp.getId());
        if (cthd != null) {
            cthdDAO.delete(cthd.getId());
            System.out.println("✅ Đã xóa chi tiết hóa đơn: " + cthd.getId());
        }

        // Nếu đang mở đúng hóa đơn này → refresh bảng
        if (hoaDonId.equals(currentHoaDonId)) {
            tbl_giohang.setModel(model);
        }
    }

    private void showMultiSelectDialog(
            String hoaDonId,
            SanPham sp,
            List<Serial> serialList
    ) {
        // Tạo model giống loadTable
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{
                    "Chọn", "Mã SP", "Tên SP", "Serial",
                    "SAN_PHAM_ID", "CPU_ID", "RAM_ID", "SSD_ID", "GPU_ID"
                }, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // chỉ cho tick checkbox
            }
        };

        // Đổ dữ liệu
        for (Serial s : serialList) {
            ChiTietSanPham ctsp = ctspDAO.findById(s.getCtsp_id());
            if (ctsp != null) {
                tableModel.addRow(new Object[]{
                    false,
                    sp.getMasp(),
                    sp.getTen(),
                    s.getMa_serial(),
                    ctsp.getSan_pham_id(),
                    ctsp.getCpu_id(),
                    ctsp.getRam_id(),
                    ctsp.getSsd_id(),
                    ctsp.getGpu_id()
                });
            }
        }

        JTable tbl = new JTable(tableModel);
        tbl.setAutoCreateRowSorter(true);

        // Ẩn các cột ID
        int[] hiddenCols = {4, 5, 6, 7, 8};
        for (int col : hiddenCols) {
            tbl.getColumnModel().getColumn(col).setMinWidth(0);
            tbl.getColumnModel().getColumn(col).setMaxWidth(0);
            tbl.getColumnModel().getColumn(col).setWidth(0);
            tbl.getColumnModel().getColumn(col).setPreferredWidth(0);
        }

        // Checkbox cho cột chọn
        tbl.getColumnModel().getColumn(0).setCellEditor(tbl.getDefaultEditor(Boolean.class));
        tbl.getColumnModel().getColumn(0).setCellRenderer(tbl.getDefaultRenderer(Boolean.class));

        JScrollPane scrollPane = new JScrollPane(tbl);

        // Nút "Chọn tất cả"
        JButton btnSelectAll = new JButton("Chọn tất cả");
        btnSelectAll.addActionListener(e -> {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(true, i, 0);
            }
        });

        JButton btnUnselectAll = new JButton("Bỏ chọn tất cả");
        btnUnselectAll.addActionListener(e -> {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt(false, i, 0);
            }
        });

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnSelectAll);
        topPanel.add(btnUnselectAll);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(
                this, panel, "Chọn Serial cần xóa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            List<String> serialsToDelete = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean isChecked = (Boolean) tableModel.getValueAt(i, 0);
                if (Boolean.TRUE.equals(isChecked)) {
                    serialsToDelete.add(tableModel.getValueAt(i, 3).toString());
                }
            }

            if (serialsToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Bạn chưa chọn serial nào để xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa " + serialsToDelete.size() + " serial đã chọn?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                for (String serialStr : serialsToDelete) {
                    removeSerialFromHoaDon(hoaDonId, serialStr);
                }
                JOptionPane.showMessageDialog(this, "✅ Đã xóa các serial đã chọn.");
            }
        }
    }

// ✅ Clone model để mỗi hóa đơn có model riêng
    private DefaultTableModel cloneTableModel(TableModel original) {
        DefaultTableModel newModel = new DefaultTableModel();
        for (int col = 0; col < original.getColumnCount(); col++) {
            newModel.addColumn(original.getColumnName(col));
        }
        return newModel;
    }

//    private void fillToGioHangTable(List<GioHangDTO> list) {
//        DefaultTableModel model = (DefaultTableModel) tbl_giohang.getModel();
//        model.setRowCount(0);
//        int stt = 1;
//        for (GioHangDTO gh : list) {
//            model.addRow(new Object[]{
//                stt++, // STT
//                gh.getMaSP(), // Mã sản phẩm
//                gh.getTenSP(), // Tên sản phẩm
//                gh.getDonGia(), // Đơn giá
//                gh.getSoLuong(), // Số lượng
//                gh.getGiamGia(), // Giảm giá
//                gh.getThanhTien() // Thành tiền
//            });
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chonkh;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_quetqrhoadon;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_thanhtoan;
    private javax.swing.JButton btn_themhoadon;
    private javax.swing.JButton btn_xoagiohang;
    private javax.swing.JComboBox<HinhThucThanhToan> cbo_hinhthuctt;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tbl_giohang;
    private javax.swing.JTextField txt_giamgia;
    private javax.swing.JTextField txt_mahoadon;
    private javax.swing.JTextField txt_ngaytao;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_tenkh;
    private javax.swing.JTextField txt_tennhanvien;
    private javax.swing.JTextField txt_tienkhachck;
    private javax.swing.JTextField txt_tienkhachdua;
    private javax.swing.JTextField txt_tientralai;
    private javax.swing.JTextField txt_timkiem;
    private javax.swing.JTextField txt_tongtien;
    // End of variables declaration//GEN-END:variables

    public void inHoaDon(String maHoaDon) {
        try {
            // Lấy thông tin hóa đơn
            HoaDon hd = hoadonDAO.findByMa(maHoaDon);
            List<ChiTietHoaDonDTO> dsCT = cthdDAO.findByMaHoaDon(maHoaDon);

            NhanVien nv = nhanvienDAO.findById(hd.getNhan_vien_id());
            KhachHang kh = khachhangDAO.findById(hd.getKhach_hang_id());

            // Gộp sản phẩm giống nhau (tên + cấu hình)
            class SPKey {

                String ten;
                String config;

                SPKey(String ten, String config) {
                    this.ten = ten;
                    this.config = config;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
                    SPKey spKey = (SPKey) o;
                    return ten.equals(spKey.ten) && config.equals(spKey.config);
                }

                @Override
                public int hashCode() {
                    return ten.hashCode() * 31 + config.hashCode();
                }
            }

            Map<SPKey, ChiTietHoaDonDTO> mapSP = new LinkedHashMap<>();
            Map<SPKey, Integer> mapSL = new LinkedHashMap<>();

            for (ChiTietHoaDonDTO ct : dsCT) {
                // Lấy tên sản phẩm từ san_pham_id
                String tenSP = "";
                SanPham sp = spDAO.findById(ct.getSan_pham_id());
                if (sp != null) {
                    tenSP = sp.getTen();
                }
                String cpu = new DellStore.dao.impl.cpuDAOImpl().findAll().stream()
                        .filter(c -> c.getId() == ct.getCpu_id()).findFirst().map(c -> c.getTen()).orElse("");
                String ram = new DellStore.dao.impl.ramDAOImpl().findAll().stream()
                        .filter(r -> r.getId() == ct.getRam_id()).findFirst().map(r -> r.getTen()).orElse("");
                String ssd = new DellStore.dao.impl.ocungDAOImpl().findAll().stream()
                        .filter(s -> s.getId() == ct.getSsd_id()).findFirst().map(s -> s.getTen()).orElse("");
                String gpu = "";
                try {
                    gpu = new DellStore.dao.impl.cardDAOImpl().findAll().stream()
                            .filter(s -> s.getId() == ct.getGpu_id()).findFirst().map(s -> s.getTen()).orElse("");
                } catch (Exception ex) {
                }
                String config = cpu + " / " + ram + " / " + ssd + " / " + gpu;
                SPKey key = new SPKey(tenSP, config);
                int soLuong = ct.getSoLuong();
                if (mapSP.containsKey(key)) {
                    mapSL.put(key, mapSL.get(key) + soLuong);
                } else {
                    mapSP.put(key, ct);
                    mapSL.put(key, soLuong);
                }
            }

            // Tạo file PDF
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            String fileName = "HoaDon_" + maHoaDon + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Xác định đường dẫn font theo hệ điều hành
            String fontPath;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                fontPath = "c:/windows/fonts/arial.ttf";
            } else {
                fontPath = "/System/Library/Fonts/Supplemental/Arial.ttf"; // Font Arial trên macOS
            }

            // Tạo BaseFont từ đường dẫn
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font fontTitle = new Font(bf, 16, Font.BOLD);
            Font fontNormal = new Font(bf, 12, Font.NORMAL);
            Font fontItalic = new Font(bf, 11, Font.ITALIC, BaseColor.DARK_GRAY);

            Paragraph shopName = new Paragraph("CỬA HÀNG LAPTOP DELLSTORE", fontTitle);
            shopName.setAlignment(Element.ALIGN_CENTER);
            document.add(shopName);

            Paragraph shopInfo = new Paragraph("Địa chỉ: 123 Đường Vô Tận, Xã Hư Cấu, Huyện Mộng Mơ, Tỉnh Giấc Mộng\nĐiện thoại: 0987654321", fontNormal);
            shopInfo.setAlignment(Element.ALIGN_CENTER);
            shopInfo.setSpacingAfter(15);
            document.add(shopInfo);

            PdfPTable tableInfo = new PdfPTable(2);
            tableInfo.setWidthPercentage(100);
            tableInfo.setWidths(new float[]{50, 50});
            tableInfo.addCell(noBorderCell("Mã HĐ: " + hd.getMa(), fontNormal));
            tableInfo.addCell(noBorderCell("Ngày: " + DellStore.utils.XDate.format(hd.getNgay_tao(), "dd/MM/yyyy"), fontNormal));
            tableInfo.addCell(noBorderCell("Nhân viên: " + (nv != null ? nv.getTen_nv() : ""), fontNormal));
            tableInfo.addCell(noBorderCell("Khách hàng: " + (kh != null ? kh.getTen() : ""), fontNormal));
            tableInfo.setSpacingAfter(15);
            document.add(tableInfo);

            PdfPTable table = new PdfPTable(7); // 7 cột
            table.setWidthPercentage(100);
            table.setWidths(new float[]{5, 30, 10, 15, 15, 15, 15}); // điều chỉnh tỉ lệ

// Header
            table.addCell(headerCell("STT", fontNormal));
            table.addCell(headerCell("Sản phẩm", fontNormal));
            table.addCell(headerCell("SL", fontNormal));
            table.addCell(headerCell("Đơn giá", fontNormal));
            table.addCell(headerCell("Tổng cộng", fontNormal));
            table.addCell(headerCell("Giảm giá", fontNormal));
            table.addCell(headerCell("Thành tiền", fontNormal));

            int stt = 1;
            BigDecimal tongCongBanDauAll = BigDecimal.ZERO;
            BigDecimal tongGiamGiaAll = BigDecimal.ZERO;
            BigDecimal tongThanhToanAll = BigDecimal.ZERO;

            for (Map.Entry<SPKey, ChiTietHoaDonDTO> entry : mapSP.entrySet()) {
                SPKey key = entry.getKey();
                ChiTietHoaDonDTO ct = entry.getValue();
                int soLuong = mapSL.get(key);

                BigDecimal donGia = ct.getDonGia();               // Đơn giá
                BigDecimal giamGia1SP = ct.getTienKhuyenMai();   // Giảm giá 1 sản phẩm

                // Tổng cộng ban đầu = Số lượng * Đơn giá
                BigDecimal tongCong = donGia.multiply(BigDecimal.valueOf(soLuong));

                // Tổng giảm giá = số lượng * giảm giá 1 SP
                BigDecimal tongGiamGia = giamGia1SP.multiply(BigDecimal.valueOf(soLuong));

                // Thành tiền = Tổng cộng ban đầu – Tổng giảm giá
                BigDecimal thanhTien = tongCong.subtract(tongGiamGia);

                // Cộng dồn tổng
                tongCongBanDauAll = tongCongBanDauAll.add(tongCong);
                tongGiamGiaAll = tongGiamGiaAll.add(tongGiamGia);
                tongThanhToanAll = tongThanhToanAll.add(thanhTien);

                // Thêm các cell vào bảng
                table.addCell(centerCell(String.valueOf(stt++), fontNormal));

                PdfPCell cellSP = new PdfPCell();
                cellSP.addElement(new Phrase(key.ten, fontNormal));
                cellSP.addElement(new Phrase("Cấu hình: " + key.config, fontItalic));
                table.addCell(cellSP);

                table.addCell(centerCell(String.valueOf(soLuong), fontNormal));
                table.addCell(rightCell(String.format("%,.0f", donGia), fontNormal));
                table.addCell(rightCell(String.format("%,.0f", tongCong), fontNormal));
                table.addCell(rightCell(String.format("%,.0f", tongGiamGia), fontNormal));
                table.addCell(rightCell(String.format("%,.0f", thanhTien), fontNormal));
            }

            document.add(table);

            // In ra tổng cộng, giảm giá, tổng thanh toán
            Paragraph tongCongPara = new Paragraph("Tổng cộng ban đầu: " + String.format("%,.0f", tongCongBanDauAll) + " VNĐ", fontNormal);
            tongCongPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(tongCongPara);

            Paragraph giamGiaPara = new Paragraph("Tổng giảm giá: " + String.format("%,.0f", tongGiamGiaAll) + " VNĐ", fontNormal);
            giamGiaPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(giamGiaPara);

            Paragraph tongThanhToanPara = new Paragraph("Tổng thanh toán: " + String.format("%,.0f", tongThanhToanAll) + " VNĐ", fontTitle);
            tongThanhToanPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(tongThanhToanPara);

            // Bảng ký tên
            PdfPTable tableSign = new PdfPTable(2);
            tableSign.setWidthPercentage(100);
            tableSign.setSpacingBefore(20);

            PdfPCell cellNV = new PdfPCell(new Phrase("Người lập hóa đơn: __________________\n(Ký, ghi rõ họ tên)", fontNormal));
            cellNV.setBorder(PdfPCell.NO_BORDER);
            cellNV.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cellKH = new PdfPCell(new Phrase("Khách hàng: __________________\n(Ký, ghi rõ họ tên)", fontNormal));
            cellKH.setBorder(PdfPCell.NO_BORDER);
            cellKH.setHorizontalAlignment(Element.ALIGN_RIGHT);

            tableSign.addCell(cellNV);
            tableSign.addCell(cellKH);
            document.add(tableSign);

            document.close();

            JOptionPane.showMessageDialog(null, "In hóa đơn thành công: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi in hóa đơn: " + e.getMessage());
        }
    }

    // ======= Hàm tiện ích =======
    private PdfPCell noBorderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell headerCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    private PdfPCell centerCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell rightCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }

    private void fillToCboHinhThucThanhToan() {
        List<HinhThucThanhToan> list = htttDAO.getAll();

        DefaultComboBoxModel<HinhThucThanhToan> model = new DefaultComboBoxModel<>();
        for (HinhThucThanhToan httt : list) {
            model.addElement(httt);
        }
        cbo_hinhthuctt.setModel(model);
    }

}
