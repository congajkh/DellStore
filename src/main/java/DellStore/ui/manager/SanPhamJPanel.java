/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.impl.LoaiSanPhamDAO;
import DellStore.ui.JDialog.CardJDialog;
import DellStore.ui.JDialog.CpuJDialog;
import DellStore.ui.JDialog.RamJDialog;
import DellStore.ui.JDialog.OcungJDialog;
import DellStore.dao.impl.cardDAOImpl;
import DellStore.dao.impl.chitietsanphamDAO;
import DellStore.dao.impl.cpuDAOImpl;
import DellStore.dao.impl.hangDAOImpl;
import DellStore.dao.impl.ocungDAOImpl;
import DellStore.dao.impl.ramDAOImpl;
import DellStore.dao.impl.sanphamDAO;
import DellStore.entity.SanPhamChiTietViewModel;
import DellStore.entity.Card;
import DellStore.entity.ChiTietSanPham;
import DellStore.entity.Cpu;
import DellStore.entity.Hang;
import DellStore.entity.LoaiSanPham;
import DellStore.entity.NhanVien;
import DellStore.entity.Ocung;
import DellStore.entity.Ram;
import DellStore.entity.SanPham;
import DellStore.utils.XJdbc;
import DellStore.dao.impl.SerialDAOImpl;
import DellStore.entity.BienTheSanPhamViewModel;
import DellStore.entity.QRSanPham;
import DellStore.entity.Serial;
import DellStore.ui.JDialog.ChiTietSanPhamJDialog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DellStore.entity.LoaiSanPham;
import DellStore.ui.JDialog.ChonSerialJDialog;
import DellStore.ui.JDialog.DialogChonChiTietSP;
import DellStore.ui.JDialog.SerialNhapJDialog;
import com.github.sarxos.webcam.Webcam;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SanPhamJPanel extends javax.swing.JPanel {

    private sanphamDAO daosp = new sanphamDAO();
    private chitietsanphamDAO ctspDAO = new chitietsanphamDAO();
    private ocungDAOImpl oImpl = new ocungDAOImpl();
    private cardDAOImpl cImpl = new cardDAOImpl();
    private cpuDAOImpl cpImpl = new cpuDAOImpl();
    private ramDAOImpl rImpl = new ramDAOImpl();
    private SerialDAOImpl serialdao = new SerialDAOImpl();
    private List<LoaiSanPham> listLoaiSP = new ArrayList<>();
    private LoaiSanPhamDAO loaiSPDAO = new LoaiSanPhamDAO();
    private LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO();
    private hangDAOImpl hangDAO = new hangDAOImpl();
    private List<ChiTietSanPham> chiTietList = new ArrayList<>();
    private List<Cpu> listCPU;
    private List<Ram> listRAM;
    private List<Ocung> listSSD;
    private List<Card> listGPU;
    private int soLuong;
    private List<SanPham> listSanPham;
    private SanPhamChiTietViewModel selectedChiTietSP;
    private List<ChiTietSanPham> listChiTietSP = new ArrayList<>();
    private Map<Integer, List<String>> serialsPerRow = new HashMap<>();
    private Map<String, String> selectedSerialByProductId = new HashMap<>();

    public SanPhamJPanel() {
        initComponents();

        txt_timkiemsanpham.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                timKiemTuDong();
            }

            public void removeUpdate(DocumentEvent e) {
                timKiemTuDong();
            }

            public void changedUpdate(DocumentEvent e) {
                timKiemTuDong();
            }

            private void timKiemTuDong() {
                String keyword = txt_timkiemsanpham.getText().trim().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) tbl_bangsanpham.getModel();
                model.setRowCount(0);
                List<SanPham> list = daosp.findAll();
                int stt = 1;
                for (SanPham entity : list) {
                    if (keyword.isEmpty() || entity.getTen().toLowerCase().contains(keyword)) {
                        model.addRow(new Object[]{
                            stt++,
                            entity.getTen(),
                            entity.getMasp(),
                            entity.getSo_luong()

                        });
                    }
                }
            }
        });
        // Cấu hình lại model cho bảng biến thể
        DefaultTableModel modelBienThe = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa bất kỳ ô nào
            }
        };

// Thêm cả các cột ẩn ở đầu
        modelBienThe.setColumnIdentifiers(new Object[]{
            "san_pham_id", "cpu_id", "gpu_id", "ssd_id", "ram_id", // cột ẩn
            "Tên SP", "CPU", "Card", "Hãng", "Ổ Cứng", "Ram", "Đơn giá", "Số lượng tồn"
        });

        tbl_bangDSSanPham.setModel(modelBienThe);

// Ẩn các cột ID
        for (int i = 0; i < 5; i++) {
            tbl_bangDSSanPham.getColumnModel().getColumn(i).setMinWidth(0);
            tbl_bangDSSanPham.getColumnModel().getColumn(i).setMaxWidth(0);
            tbl_bangDSSanPham.getColumnModel().getColumn(i).setPreferredWidth(0);
        }

        fillComboBoxRam();
        fillComboBoxCPU();
        fillComboBoxHang();
        fillComboBoxhang1();
        fillComboBoxOCung();
        fillComboBoxGPU();
        fillLoaiSanPham();
        loadTableSanPham();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txt_timkiemsanpham = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btn_themsanpham = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangsanpham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbo_hang = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cbo_loaisanpham = new javax.swing.JComboBox<>();
        txt_tensanpham = new javax.swing.JTextField();
        txt_masanpham = new javax.swing.JTextField();
        txt_mota = new javax.swing.JTextField();
        txt_soLuong = new javax.swing.JTextField();
        btn_quetqr = new javax.swing.JButton();
        btn_lammoi = new javax.swing.JButton();
        rdo_conban = new javax.swing.JRadioButton();
        rdo_ngungkinhdoanh = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_bangDSSanPham = new javax.swing.JTable();
        txt_timkiemdssanpham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btn_Export = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_tensp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_gia = new javax.swing.JTextField();
        btn_lammoidssanpham = new javax.swing.JButton();
        btn_suasanpham1 = new javax.swing.JButton();
        txt_serial = new javax.swing.JTextField();
        rdo_dangban = new javax.swing.JRadioButton();
        rdo_daban = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_idChiTiet = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbo_CPU = new javax.swing.JComboBox<>();
        cbo_Card = new javax.swing.JComboBox<>();
        btn_themRam = new javax.swing.JButton();
        cbo_Ram = new javax.swing.JComboBox<>();
        btn_ThemCard = new javax.swing.JButton();
        cbo_Hang = new javax.swing.JComboBox<>();
        btn_themCPU = new javax.swing.JButton();
        cbo_OCung = new javax.swing.JComboBox<>();
        btn_themOCung = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1120, 660));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("THÔNG TIN SẢN PHẨM"));

        txt_timkiemsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timkiemsanphamActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Tìm kiếm:");

        btn_themsanpham.setBackground(new java.awt.Color(255, 153, 102));
        btn_themsanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_themsanpham.setText("Thêm");
        btn_themsanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themsanphamActionPerformed(evt);
            }
        });

        tbl_bangsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Tên Sản Phẩm", "Mã Sản Phẩm", "Số Lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_bangsanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangsanphamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bangsanpham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mô Tả:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên Sản Phẩm:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Mã Sản Phẩm:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Hãng:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Trạng Thái:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Số Lượng");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Loại Sản Phẩm:");

        btn_quetqr.setBackground(new java.awt.Color(255, 153, 102));
        btn_quetqr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/qr-code.png"))); // NOI18N
        btn_quetqr.setText("Quét QR");
        btn_quetqr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quetqrActionPerformed(evt);
            }
        });

        btn_lammoi.setBackground(new java.awt.Color(255, 153, 102));
        btn_lammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reset.png"))); // NOI18N
        btn_lammoi.setText("Làm Mới");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        rdo_conban.setText("Còn Bán");

        rdo_ngungkinhdoanh.setText("Ngừng Kinh Doanh");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbo_loaisanpham, 0, 197, Short.MAX_VALUE)
                                    .addComponent(txt_tensanpham)
                                    .addComponent(txt_masanpham)
                                    .addComponent(txt_mota))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbo_hang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_soLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdo_conban)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdo_ngungkinhdoanh)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(93, 93, 93)
                                        .addComponent(btn_themsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(btn_quetqr, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(179, 179, 179)
                                        .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(101, 101, 101))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_timkiemsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_hang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tensanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_masanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_conban)
                            .addComponent(rdo_ngungkinhdoanh))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_mota, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_loaisanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_timkiemsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_themsanpham)
                            .addComponent(btn_quetqr))
                        .addGap(18, 18, 18)
                        .addComponent(btn_lammoi)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DANH SÁCH SẢN PHẨM"));

        tbl_bangDSSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên SP", "CPU", "Card", "Hãng", "Ổ Cứng", "Ram", "Don gia", "SL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_bangDSSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangDSSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_bangDSSanPham);

        txt_timkiemdssanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timkiemdssanphamActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Tìm kiếm:");

        btn_Export.setBackground(new java.awt.Color(255, 153, 102));
        btn_Export.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/excel.png"))); // NOI18N
        btn_Export.setText("Export");
        btn_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExportActionPerformed(evt);
            }
        });

        btn_import.setBackground(new java.awt.Color(255, 153, 102));
        btn_import.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/excel.png"))); // NOI18N
        btn_import.setText("Import");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txt_timkiemdssanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218)
                .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Export)
                .addGap(0, 16, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_timkiemdssanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_import, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chức Năng"));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Tên SP:");

        txt_tensp.setEditable(false);
        txt_tensp.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_tensp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tensp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenspActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Serial:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Giá:");

        txt_gia.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_gia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_gia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_giaActionPerformed(evt);
            }
        });

        btn_lammoidssanpham.setBackground(new java.awt.Color(255, 153, 102));
        btn_lammoidssanpham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/reset.png"))); // NOI18N
        btn_lammoidssanpham.setText("Làm Mới");
        btn_lammoidssanpham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoidssanphamActionPerformed(evt);
            }
        });

        btn_suasanpham1.setBackground(new java.awt.Color(255, 153, 102));
        btn_suasanpham1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_suasanpham1.setText("Sửa");
        btn_suasanpham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suasanpham1ActionPerformed(evt);
            }
        });

        txt_serial.setEditable(false);
        txt_serial.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_serial.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_serial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_serialActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_dangban);
        rdo_dangban.setText("Đang Bán");

        buttonGroup1.add(rdo_daban);
        rdo_daban.setText("Đã Bán");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Trạng Thái:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setText("ID");

        txt_idChiTiet.setEditable(false);
        txt_idChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_idChiTiet.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_idChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(rdo_dangban)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdo_daban))
                            .addComponent(txt_tensp, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txt_gia)
                            .addComponent(txt_serial, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txt_idChiTiet)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btn_lammoidssanpham)
                        .addGap(63, 63, 63)
                        .addComponent(btn_suasanpham1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_idChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_tensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_serial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_gia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdo_dangban)
                        .addComponent(jLabel13))
                    .addComponent(rdo_daban))
                .addGap(28, 28, 28)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_suasanpham1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_lammoidssanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Thuộc Tính"));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Hãng: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel9.setText("Ram: ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel10.setText("Card: ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("CPU: ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setText("Ổ Cứng: ");

        cbo_CPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_CPUActionPerformed(evt);
            }
        });

        btn_themRam.setBackground(new java.awt.Color(255, 153, 102));
        btn_themRam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_themRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themRamActionPerformed(evt);
            }
        });

        btn_ThemCard.setBackground(new java.awt.Color(255, 153, 102));
        btn_ThemCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_ThemCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemCardActionPerformed(evt);
            }
        });

        btn_themCPU.setBackground(new java.awt.Color(255, 153, 102));
        btn_themCPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_themCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themCPUActionPerformed(evt);
            }
        });

        cbo_OCung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_OCungActionPerformed(evt);
            }
        });

        btn_themOCung.setBackground(new java.awt.Color(255, 153, 102));
        btn_themOCung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/them.png"))); // NOI18N
        btn_themOCung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themOCungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(28, 28, 28)
                                .addComponent(cbo_CPU, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(btn_themCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(254, 254, 254)
                                .addComponent(btn_themOCung, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbo_Hang, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(112, 112, 112)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbo_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbo_OCung, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(111, 111, 111)
                                .addComponent(jLabel10)
                                .addGap(28, 28, 28)
                                .addComponent(cbo_Card, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(177, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(464, 464, 464)
                        .addComponent(btn_themRam, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_ThemCard, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(237, 237, 237))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(cbo_Ram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbo_Card, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_Hang, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_themRam, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemCard, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_CPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(cbo_OCung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_themCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_themOCung, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(74, 74, 74))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chi Tiết Sản Phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fillComboBoxRam() {
        cbo_Ram.removeAllItems();
        List<Ram> list = rImpl.getAllRAM();
        for (Ram o : list) {
            cbo_Ram.addItem(o);
        }
    }

    private void fillComboBoxCPU() {
        cbo_CPU.removeAllItems();
        List<Cpu> list = cpImpl.getAllCPU();
        for (Cpu o : list) {
            cbo_CPU.addItem(o);
        }

    }

    public void fillComboBoxHang() {
        hangDAOImpl dao = new hangDAOImpl();
        List<Hang> list = dao.findAll();
        cbo_Hang.removeAllItems();
        for (Hang item : list) {
            cbo_Hang.addItem(item.getTen());
        }
    }

    public void fillComboBoxhang1() {
        DefaultComboBoxModel<Hang> model = new DefaultComboBoxModel<>();
        for (Hang h : hangDAO.findAll()) {
            model.addElement(h);
        }
        cbo_hang.setModel(model);

    }

    public void fillComboBoxOCung() {
        cbo_OCung.removeAllItems();
        List<Ocung> list = oImpl.getAllOCung();
        for (Ocung o : list) {
            cbo_OCung.addItem(o);
        }

    }

    public void fillComboBoxGPU() {
        cbo_Card.removeAllItems();
        List<Card> list = cImpl.getAllCard();
        for (Card o : list) {
            cbo_Card.addItem(o);
        }

    }

    private void loadTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tbl_bangsanpham.getModel();
        model.setRowCount(0);
        List<SanPham> list = daosp.findAll();
        int stt = 1;
        for (SanPham sp : list) {
            model.addRow(new Object[]{stt++, sp.getTen(), sp.getMasp(), sp.getSo_luong()});
        }
    }

    private void fillLoaiSanPham() {
        cbo_loaisanpham.removeAllItems();
        List<LoaiSanPham> list = loaiSanPhamDAO.findAll();

        for (LoaiSanPham loai : list) {
            cbo_loaisanpham.addItem(loai);
        }
    }


    private void txt_tenspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenspActionPerformed

    private void txt_giaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_giaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_giaActionPerformed

    private void btn_lammoidssanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoidssanphamActionPerformed

        txt_tensp.setText("");
        cbo_CPU.setSelectedIndex(-1);
        cbo_Card.setSelectedIndex(-1);
        cbo_Hang.setSelectedIndex(-1);
        cbo_OCung.setSelectedIndex(-1);
        cbo_Ram.setSelectedIndex(-1);
        txt_serial.setText("");
        txt_gia.setText("");
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_btn_lammoidssanphamActionPerformed
    private void fillFormFromTable() {
        int row = tbl_bangDSSanPham.getSelectedRow();
        if (row >= 0) {
            cbo_CPU.setSelectedItem(tbl_bangDSSanPham.getValueAt(row, 1).toString());
            cbo_Card.setSelectedItem(tbl_bangDSSanPham.getValueAt(row, 2).toString());
            cbo_OCung.setSelectedItem(tbl_bangDSSanPham.getValueAt(row, 4).toString());
            cbo_Ram.setSelectedItem(tbl_bangDSSanPham.getValueAt(row, 5).toString());
            txt_serial.setText(tbl_bangDSSanPham.getValueAt(row, 6).toString());
            txt_gia.setText(tbl_bangDSSanPham.getValueAt(row, 7).toString());
        }
    }
    private void btn_suasanpham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suasanpham1ActionPerformed
        SanPhamChiTietViewModel sp;
        try {
            sp = getThongTinTuForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu trên form không hợp lệ: " + ex.getMessage());
            return;
        }

// ==== VALIDATE CƠ BẢN ====
        if (sp.getGiaBan() == null || sp.getGiaBan().compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0!");
            return;
        }
        if (sp.getCpu() == null || sp.getCpu().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn CPU!");
            return;
        }
        if (sp.getCard() == null || sp.getCard().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn GPU!");
            return;
        }
        if (sp.getOcung() == null || sp.getOcung().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Ổ cứng!");
            return;
        }
        if (sp.getRam() == null || sp.getRam().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn RAM!");
            return;
        }
        if (sp.getSerial() == null || sp.getSerial().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Serial!");
            return;
        }

// ==== GỌI DAO ====
        boolean updateSuccess = ctspDAO.updateSanPhamChiTiet(sp);

// ==== KẾT QUẢ ====
        if (updateSuccess) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            try {
                int sanPhamId = ctspDAO.getSanPhamIdByChiTietId(sp.getId());
                List<BienTheSanPhamViewModel> dsBienThe = daosp.findBienTheBySanPhamId(sanPhamId);
                fillTableBienThe(dsBienThe);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật xong nhưng không load lại bảng được: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
}//GEN-LAST:event_btn_suasanpham1ActionPerformed
    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_bangDSSanPham.getModel();
        model.setRowCount(0);

        List<ChiTietSanPham> list = ctspDAO.findAll();
        for (ChiTietSanPham ct : list) {
            Object[] row = new Object[]{
                ct.getId(),
                ctspDAO.getTenById("cpu", ct.getCpu_id()),
                ctspDAO.getTenById("gpu", ct.getGpu_id()),
                "Dell", // Hoặc: ctspDAO.getTenById("Hang", ct.getHang_id()) nếu có
                ctspDAO.getTenById("ssd", ct.getSsd_id()),
                ctspDAO.getTenById("ram", ct.getRam_id()),
                ct.getGia_ban(),
                ct.getTrang_thai()
            };
            model.addRow(row);
        }
    }


    private void cbo_CPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_CPUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_CPUActionPerformed

    private void cbo_OCungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_OCungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_OCungActionPerformed

    private void txt_timkiemdssanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timkiemdssanphamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timkiemdssanphamActionPerformed

    private void btn_themCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themCPUActionPerformed
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        CpuJDialog dialog = new CpuJDialog(parent, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

    }//GEN-LAST:event_btn_themCPUActionPerformed

    private void btn_themRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themRamActionPerformed
        // TODO add your handling code here:
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        RamJDialog dialog = new RamJDialog(parent, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_themRamActionPerformed

    private void btn_themOCungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themOCungActionPerformed
        // TODO add your handling code here:
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        OcungJDialog dialog = new OcungJDialog(parent, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_themOCungActionPerformed

    private void btn_ThemCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemCardActionPerformed
        // TODO add your handling code here:
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        CardJDialog dialog = new CardJDialog(parent, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_ThemCardActionPerformed

//    private void tbl_bangDSSanPhamMouseClicked(java.awt.event.MouseEvent evt) {                                               
//  
//}
    public int getIdLoaiSP(String ten) {
        for (LoaiSanPham lsp : listLoaiSP) {
            if (lsp.getTen().equalsIgnoreCase(ten)) {
                return lsp.getId();
            }
        }
        return -1;
    }

    private void txt_serialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_serialActionPerformed

    private void txt_timkiemsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timkiemsanphamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_timkiemsanphamActionPerformed

    private void btn_themsanphamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themsanphamActionPerformed
        try {
            // ===== 1. Lấy và kiểm tra dữ liệu từ form =====
            String ten = txt_tensanpham.getText().trim();
            String ma = txt_masanpham.getText().trim();
            String moTa = txt_mota.getText().trim();
            String soLuongStr = txt_soLuong.getText().trim();
            LoaiSanPham loai = (LoaiSanPham) cbo_loaisanpham.getSelectedItem();
            Hang hang = (Hang) cbo_hang.getSelectedItem();

            if (ten.isEmpty() || ma.isEmpty() || soLuongStr.isEmpty() || loai == null || hang == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên.");
                return;
            }

            // Kiểm tra mã trùng
            if (daosp.findByMa(ma) != null) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại.");
                return;
            }
            // 2️⃣ Hỏi người dùng trước khi thêm
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thêm sản phẩm này không?",
                    "Xác nhận thêm sản phẩm",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nếu người dùng chọn No, thoát khỏi hàm
            }

            // ===== 2. Lấy cấu hình từ DB =====
            List<Cpu> listCPU = cpImpl.getAllCPU();
            List<Ram> listRAM = rImpl.getAllRAM();
            List<Ocung> listSSD = oImpl.getAllOCung();
            List<Card> listGPU = cImpl.getAllCard();

            // Tạo sản phẩm
            SanPham sp = new SanPham();
            sp.setTen(ten);
            sp.setMasp(ma);
            sp.setMo_ta(moTa);
            sp.setLoai_san_pham_id(loai.getId());
            sp.setHang_id(hang.getId());
            sp.setTrang_thai(0);
            sp.setSo_luong(soLuong);

            // ===== 3. Nhập cấu hình chi tiết =====
            ChiTietSanPhamJDialog ctspDialog = new ChiTietSanPhamJDialog(
                    null, true, sp, listSanPham, listCPU, listRAM, listSSD, listGPU, soLuong);
            ctspDialog.setVisible(true);

            List<ChiTietSanPham> ctspList = ctspDialog.getChiTietList();
            if (ctspList == null || ctspList.size() != soLuong) {
                JOptionPane.showMessageDialog(this, "Chưa nhập đủ cấu hình hoặc đã huỷ.");
                return;
            }

            // ===== 4. Insert sản phẩm và chi tiết sản phẩm =====
            int idSanPhamMoi = daosp.insertAndReturnId(sp);
            sp.setId(idSanPhamMoi);

            for (ChiTietSanPham ct : ctspList) {
                ct.setSan_pham_id(idSanPhamMoi);
                int idCTSP = ctspDAO.insertAndReturnId(ct);
                ct.setId(idCTSP);
            }

            // ===== 5. Reload listSanPham và cấu hình sau khi insert =====
            listSanPham = daosp.findAll();
            listCPU = cpImpl.getAllCPU();
            listRAM = rImpl.getAllRAM();
            listSSD = oImpl.getAllOCung();
            listGPU = cImpl.getAllCard();

            // ===== 6. Nhập Serial =====
            SerialNhapJDialog serialDialog = new SerialNhapJDialog(
                    null, true, ctspList, listSanPham, listCPU, listRAM, listSSD, listGPU);
            serialDialog.setVisible(true);

            List<Serial> serialList = serialDialog.getSerialList();
            if (serialList == null || serialList.size() != soLuong) {
                JOptionPane.showMessageDialog(this, "Chưa nhập đủ serial hoặc đã huỷ.");
                return;
            }

            // Insert serial
            for (Serial s : serialList) {
                s.setTrang_thai(0);
                serialdao.insert(s);
            }

            // ===== 7. Reload bảng =====
            listChiTietSP = ctspDAO.findAll(); // hoặc getAllBySanPhamId nếu bạn chỉ muốn sp vừa thêm
            loadTableSanPham();

            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_themsanphamActionPerformed

    private void tbl_bangsanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangsanphamMouseClicked
        int row = tbl_bangsanpham.getSelectedRow();
        if (row < 0) {
            return;
        }

        // Lấy mã sản phẩm từ cột 0 ("Mã Sản Phẩm")
        String maSP = String.valueOf(tbl_bangsanpham.getValueAt(row, 2));

        // Gọi DAO để lấy danh sách biến thể
        List<BienTheSanPhamViewModel> list = daosp.findBienTheByMaSP(maSP);

        // Fill bảng biến thể
        fillTableBienThe(list);

        // Chuyển sang tab Biến Thể
        jTabbedPane1.setSelectedIndex(1);
}//GEN-LAST:event_tbl_bangsanphamMouseClicked
    public void reloadBangGop(String maSP) {
        List<BienTheSanPhamViewModel> list = daosp.findBienTheByMaSP(maSP);
        fillTableBienThe(list);
    }

    private void txt_idChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idChiTietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idChiTietActionPerformed

    private void btn_quetqrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quetqrActionPerformed
        Thread qrThread = new Thread(() -> {
            try {
                Webcam webcam = Webcam.getDefault();
                webcam.setViewSize(new Dimension(640, 480));
                WebcamPanel panel = new WebcamPanel(webcam);
                panel.setMirrored(true);

                JFrame window = new JFrame("Quét mã QR sản phẩm");
                window.add(panel);
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);

                Result result = null;
                BufferedImage image;

                while (true) {
                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }

                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        // QR chưa thấy
                    }

                    if (result != null) {
                        webcam.close();
                        window.dispose();  // đóng khung webcam
                        String qrData = result.getText(); // Dữ liệu JSON
                        SwingUtilities.invokeLater(() -> fillThongTinSanPhamFromJson(qrData));
                        break;
                    }

                    Thread.sleep(100); // giảm tần suất quét
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi quét QR: " + e.getMessage());
                e.printStackTrace();
            }
        });
        qrThread.start();
    }//GEN-LAST:event_btn_quetqrActionPerformed

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        // TODO add your handling code here:
        txt_tensanpham.setText("");
        txt_masanpham.setText("");
        txt_mota.setText("");
        cbo_loaisanpham.setSelectedItem(0);
        rdo_conban.setSelected(true);
        txt_gia.setText("");
        txt_soLuong.setText("");
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void tbl_bangDSSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangDSSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tbl_bangDSSanPham.getSelectedRow();
            if (row < 0) {
                return;
            }

            // Lấy các ID từ cột ẩn
            int sanPhamId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(row, 0).toString());
            int cpuId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(row, 1).toString());
            int gpuId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(row, 2).toString());
            int ssdId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(row, 3).toString());
            int ramId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(row, 4).toString());

            // Lấy danh sách serial chi tiết từ DAO theo bộ tham số
            List<SanPhamChiTietViewModel> filteredList = daosp.getChiTietBienThe(
                    sanPhamId, cpuId, gpuId, ssdId, ramId
            );

            // Mở dialog chọn chi tiết sản phẩm
            DialogChonChiTietSP dialog = new DialogChonChiTietSP(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    true,
                    filteredList
            );
            dialog.setVisible(true);

            // Lấy kết quả từ dialog
            SanPhamChiTietViewModel result = dialog.getSelectedItem();
            if (result != null) {
                fillThongTinChiTietSP(result);
            }
        }
    }//GEN-LAST:event_tbl_bangDSSanPhamMouseClicked
    private void fillThongTinChiTietSP(SanPhamChiTietViewModel sp) {
        // Cho phép chỉnh sửa các trường
        txt_tensp.setEditable(true);
//        cbo_CPU.setEnabled(true);
//        cbo_Card.setEnabled(true);
//        cbo_Hang.setEnabled(true);
//        cbo_OCung.setEnabled(true);
//        cbo_Ram.setEnabled(true);
        txt_serial.setEditable(true);
        txt_gia.setEditable(true);
//        rdo_dangban.setEnabled(true);
//        rdo_daban.setEnabled(true);

        // ID & tên sản phẩm
        txt_idChiTiet.setText(String.valueOf(sp.getId()));
        txt_tensp.setText(sp.getTenSanPham() != null ? sp.getTenSanPham() : "");

        // Fill combo box cấu hình
        selectComboBoxItem(cbo_CPU, sp.getCpu());
        selectComboBoxItem(cbo_Card, sp.getCard());
        selectComboBoxItem(cbo_Hang, sp.getHang());
        selectComboBoxItem(cbo_OCung, sp.getOcung());
        selectComboBoxItem(cbo_Ram, sp.getRam());

        // Serial & giá
        txt_serial.setText(sp.getSerial() != null ? sp.getSerial() : "");
        txt_gia.setText(sp.getGiaBan() != null
                ? sp.getGiaBan().toPlainString()
                : "");

        // Trạng thái
        boolean dangBan = sp.getTrangThai() == 0; // 0 = đang bán, 1 = đã bán
        rdo_dangban.setSelected(dangBan);
        rdo_daban.setSelected(!dangBan);
    }

    private SanPhamChiTietViewModel getThongTinTuForm() {
        SanPhamChiTietViewModel sp = new SanPhamChiTietViewModel();

        try {
            sp.setId(Integer.parseInt(txt_idChiTiet.getText().trim()));
        } catch (NumberFormatException e) {
            sp.setId(0); // hoặc xử lý lỗi
        }

        sp.setTenSanPham(txt_tensp.getText().trim());

        sp.setCpu(cbo_CPU.getSelectedItem() != null ? cbo_CPU.getSelectedItem().toString() : "");
        sp.setCard(cbo_Card.getSelectedItem() != null ? cbo_Card.getSelectedItem().toString() : "");
        sp.setHang(cbo_Hang.getSelectedItem() != null ? cbo_Hang.getSelectedItem().toString() : "");
        sp.setOcung(cbo_OCung.getSelectedItem() != null ? cbo_OCung.getSelectedItem().toString() : "");
        sp.setRam(cbo_Ram.getSelectedItem() != null ? cbo_Ram.getSelectedItem().toString() : "");

        sp.setSerial(txt_serial.getText().trim());

        try {
            sp.setGiaBan(new BigDecimal(txt_gia.getText().trim()));
        } catch (NumberFormatException e) {
            sp.setGiaBan(BigDecimal.ZERO); // hoặc xử lý lỗi nhập
        }

        sp.setTrangThai(rdo_dangban.isSelected() ? 0 : 1); // 0=Đang bán, 1=Đã bán
        return sp;
    }

    private void selectComboBoxItem(JComboBox comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item.toString().equalsIgnoreCase(value.trim())) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
        // Nếu không tìm thấy thì có thể chọn index mặc định hoặc không chọn
        comboBox.setSelectedIndex(-1);
    }


    private void btn_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExportActionPerformed
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("ChiTietSanPham");

            // Tạo header
            String[] headers = {"ID", "Tên SP", "CPU", "GPU", "Hãng", "Ổ cứng", "RAM", "Serial", "Giá bán", "Trạng thái"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Lấy dữ liệu từ bảng JTable
            DefaultTableModel model = (DefaultTableModel) tbl_bangDSSanPham.getModel();
            int rowCount = model.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < headers.length; j++) {
                    Object value = model.getValueAt(i, j);
                    row.createCell(j).setCellValue(value == null ? "" : value.toString());
                }
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Lưu file
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn nơi lưu file");
            int option = chooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile() + ".xlsx");
                workbook.write(fos);
                fos.close();
                JOptionPane.showMessageDialog(null, "Xuất file thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file!");
        }
    }//GEN-LAST:event_btn_ExportActionPerformed

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn file Excel để import");
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                Workbook workbook = new XSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(0);

                DefaultTableModel model = (DefaultTableModel) tbl_bangDSSanPham.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    Object[] rowData = new Object[row.getLastCellNum()];
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        rowData[j] = (cell == null) ? "" : getCellValue(cell);
                    }
                    model.addRow(rowData);

                    try {
                        String tenSP = rowData[1].toString();
                        String cpu = rowData[2].toString();
                        String gpu = rowData[3].toString();
                        String hang = rowData[4].toString();
                        String ocung = rowData[5].toString();
                        String ram = rowData[6].toString();
                        String serialStr = rowData[7].toString();
                        String giaBanStr = rowData[8].toString();
                        String trangThaiStr = rowData[9].toString();

                        int cpuId = cpImpl.getIdByTen(cpu);
                        int gpuId = cImpl.getIdByTen(gpu);
                        int ocungId = oImpl.getIdByTen(ocung);
                        int ramId = rImpl.getIdByTen(ram);
                        int hangId = hangDAO.getIdByTen(hang);

                        // Tìm hoặc tạo Serial
                        Serial s = serialdao.findByMaSerial(serialStr);
                        if (s == null) {
                            s = new Serial();
                            s.setMa_serial(serialStr);
                            s.setCtsp_id(0); // hoặc tìm theo tên sản phẩm nếu có
                            s.setTrang_thai(0);
                            serialdao.insert(s);
                            s = serialdao.findByMaSerial(serialStr);
                        }

                        // Tạo chi tiết sản phẩm
                        ChiTietSanPham ctsp = new ChiTietSanPham();
                        // ctsp.setSerial_id(s.getId()); // Nếu có cột serial_id trong DB
                        ctsp.setSan_pham_id(s.getCtsp_id());
                        ctsp.setCpu_id(cpuId);
                        ctsp.setRam_id(ramId);
                        ctsp.setSsd_id(ocungId);
                        ctsp.setGpu_id(gpuId);
                        ctsp.setGia_ban(new java.math.BigDecimal(giaBanStr));
                        ctsp.setTrang_thai("Đang Bán".equalsIgnoreCase(trangThaiStr) ? 1 : 0);

                        ctspDAO.insert(ctsp);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // Có thể log lỗi từng dòng nếu cần
                    }
                }
                workbook.close();
                fis.close();
                JOptionPane.showMessageDialog(null, "Import file và lưu DB thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi import file!");
        }
    }//GEN-LAST:event_btn_importActionPerformed
    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double val = cell.getNumericCellValue();
                    return (val == (int) val) ? (int) val : val;
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                return getCellValue(evaluator.evaluateInCell(cell));
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private void fillThongTinSanPhamFromJson(String json) {
        Gson gson = new Gson();
        QRSanPham sp = gson.fromJson(json, QRSanPham.class);

        txt_masanpham.setText(sp.getMa());
        txt_tensanpham.setText(sp.getTen());
        txt_mota.setText(sp.getMoTa());
        rdo_conban.setSelected(sp.getTrangThai() == 1);
        rdo_ngungkinhdoanh.setSelected(sp.getTrangThai() == 0);
    }

    //dang sua
    private void fillTableBienThe(List<BienTheSanPhamViewModel> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_bangDSSanPham.getModel();
        model.setRowCount(0);

        for (BienTheSanPhamViewModel bt : list) {
            model.addRow(new Object[]{
                bt.getSan_pham_id(),
                bt.getCpu_id(),
                bt.getGpu_id(),
                bt.getSsd_id(),
                bt.getRam_id(),
                bt.getTen_sp(),
                bt.getCpu(),
                bt.getCard(),
                bt.getHang(),
                bt.getOcung(),
                bt.getRam(),
                bt.getGiaBan(),
                bt.getSoLuong()
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Export;
    private javax.swing.JButton btn_ThemCard;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JButton btn_lammoidssanpham;
    private javax.swing.JButton btn_quetqr;
    private javax.swing.JButton btn_suasanpham1;
    private javax.swing.JButton btn_themCPU;
    private javax.swing.JButton btn_themOCung;
    private javax.swing.JButton btn_themRam;
    private javax.swing.JButton btn_themsanpham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Cpu> cbo_CPU;
    private javax.swing.JComboBox<Card> cbo_Card;
    private javax.swing.JComboBox<String> cbo_Hang;
    private javax.swing.JComboBox<Ocung> cbo_OCung;
    private javax.swing.JComboBox<Ram> cbo_Ram;
    private javax.swing.JComboBox<Hang> cbo_hang;
    private javax.swing.JComboBox<LoaiSanPham> cbo_loaisanpham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdo_conban;
    private javax.swing.JRadioButton rdo_daban;
    private javax.swing.JRadioButton rdo_dangban;
    private javax.swing.JRadioButton rdo_ngungkinhdoanh;
    private javax.swing.JTable tbl_bangDSSanPham;
    private javax.swing.JTable tbl_bangsanpham;
    private javax.swing.JTextField txt_gia;
    private javax.swing.JTextField txt_idChiTiet;
    private javax.swing.JTextField txt_masanpham;
    private javax.swing.JTextField txt_mota;
    private javax.swing.JTextField txt_serial;
    private javax.swing.JTextField txt_soLuong;
    private javax.swing.JTextField txt_tensanpham;
    private javax.swing.JTextField txt_tensp;
    private javax.swing.JTextField txt_timkiemdssanpham;
    private javax.swing.JTextField txt_timkiemsanpham;
    // End of variables declaration//GEN-END:variables

    public JTable getTblBangDSSanPham() {
        return tbl_bangDSSanPham;
    }

    public void giamSoLuongTheoCtsp(int ctspId) {
        for (int i = 0; i < tbl_bangDSSanPham.getRowCount(); i++) {
            // Lấy ctspId từ cột 0 của bảng gộp
            int panelCtspId = Integer.parseInt(tbl_bangDSSanPham.getValueAt(i, 0).toString());

            if (panelCtspId == ctspId) {
                int soLuong = Integer.parseInt(tbl_bangDSSanPham.getValueAt(i, 7).toString()); // cột 5 = số lượng
                soLuong = Math.max(0, soLuong - 1); // tránh số lượng âm
                tbl_bangDSSanPham.setValueAt(soLuong, i, 7);
                break; // chỉ giảm 1 dòng phù hợp
            }
        }
    }

}
