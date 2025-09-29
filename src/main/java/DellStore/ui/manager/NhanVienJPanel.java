package DellStore.ui.manager;

import DellStore.dao.impl.nhanvienDAO;
import DellStore.entity.NhanVien;
import DellStore.utils.EmailUtil;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author orin
 */
public class NhanVienJPanel extends javax.swing.JPanel {

    private nhanvienDAO nvDAO;
    private DefaultTableModel defaultTableModel;
    

    
    /**
     * Creates new form NhanVienJPanel
     */
    public NhanVienJPanel() {
        initComponents();
        //tim kiem ten nv
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
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
                String keyword = txtTimKiem.getText().trim().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) tblDSnhanvien.getModel();
                model.setRowCount(0);
                List<NhanVien> list = nvDAO.findAll();
                for (NhanVien nv : list) {
                    if (nv.getTen_nv().toLowerCase().contains(keyword)) {
                        model.addRow(new Object[]{
                            nv.getMa_nv(),
                            nv.getTen_nv(),
                            nv.isGioi_tinh() ? "Nam" : "Nữ",
                            nv.getNgay_sinh(),
                            nv.getDia_chi(),
                            nv.getSdt(),
                            nv.getEmail(),
                            nv.getTrang_thai() == 1 ? "Hoạt động" : "Nghỉ việc",
                            nv.getChuc_vu()
                        });
                    }
                }
                if (tblDSnhanvien.getRowCount() > 0) {
                    tblDSnhanvien.setRowSelectionInterval(0, 0);
                    loadDataToForm(0);
                } else {
                    txtmanv.setText("");
                    txthoten.setText("");
                    txtngaysinh.setText("");
                    txtdiachi.setText("");
                    txtsdt.setText("");
                    txtemail.setText("");
                    rdoNam.setSelected(true);
                    rdoNhanVien.setSelected(true);
                    rdohoatdong.setSelected(true);
                }
            }
        });
        nvDAO = new nhanvienDAO();
        defaultTableModel = (DefaultTableModel) tblDSnhanvien.getModel();
        fillTable();
        if (tblDSnhanvien.getRowCount() > 0) {
            loadDataToForm(0); // Fill dòng đầu tiên lên form
            tblDSnhanvien.setRowSelectionInterval(0, 0); // Chọn dòng đầu tiên trên bảng
        }
    }

    private void fillTable() {
        nhanvienDAO dao = new nhanvienDAO();
        List<NhanVien> list = dao.findAll();
        DefaultTableModel model = (DefaultTableModel) tblDSnhanvien.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMa_nv(),
                nv.getTen_nv(),
                nv.isGioi_tinh() ? "Nam" : "Nữ",
                nv.getNgay_sinh(),
                nv.getDia_chi(),
                nv.getSdt(),
                nv.getEmail(),
                nv.getTrang_thai() == 1 ? "Hoạt động" : "Nghỉ việc",
                nv.getChuc_vu()
            });
        }
    }

    private void clearFrom() {
        txtmanv.setText("");
        txthoten.setText("");
        txtngaysinh.setText("");
        txtdiachi.setText("");
        txtsdt.setText("");
        txtemail.setText("");
        rdoNam.setSelected(true);
        rdoNhanVien.setSelected(true);
        rdohoatdong.setSelected(true);
    }

    private void loadDataToForm(int row) {
        DefaultTableModel model = (DefaultTableModel) tblDSnhanvien.getModel();
        String maNV = model.getValueAt(row, 0).toString();
        String tenNV = model.getValueAt(row, 1).toString();
        String gioiTinh = model.getValueAt(row, 2).toString();
        String ngaySinh = model.getValueAt(row, 3).toString();
        String diaChi = model.getValueAt(row, 4).toString();
        String sdt = model.getValueAt(row, 5).toString();
        String email = model.getValueAt(row, 6).toString();
        String trangThai = model.getValueAt(row, 7).toString();
        String chucVu = model.getValueAt(row, 8).toString();

        // Đổ dữ liệu lên các trường nhập liệu
        txtmanv.setText(maNV);
        txthoten.setText(tenNV);
        txtngaysinh.setText(ngaySinh);
        txtdiachi.setText(diaChi);
        txtsdt.setText(sdt);
        txtemail.setText(email);

        // Giới tính
        if ("Nam".equalsIgnoreCase(gioiTinh)) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        // Chức vụ
        if ("Quản lý".equalsIgnoreCase(chucVu) || "quan_ly".equalsIgnoreCase(chucVu)) {
            rdoQuanly.setSelected(true);
        } else {
            rdoNhanVien.setSelected(true);
        }

        // Trạng thái
        if ("Hoạt động".equalsIgnoreCase(trangThai)) {
            rdohoatdong.setSelected(true);
        } else {
            rdonghiviec.setSelected(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoQuanly = new javax.swing.JRadioButton();
        btnxoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        txthoten = new javax.swing.JTextField();
        txtngaysinh = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        btnlammoi = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        rdohoatdong = new javax.swing.JRadioButton();
        rdonghiviec = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnImport = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDSnhanvien = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Quản lý nhân viên");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setName(""); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Họ tên");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Giới tính");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Ngày sinh");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Số điện thoại");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Địa chỉ");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Email");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Chức vụ:");

        buttonGroup2.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhanVienActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoQuanly);
        rdoQuanly.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdoQuanly.setText("Quản lý");
        rdoQuanly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoQuanlyActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtngaysinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngaysinhActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnlammoi.setText("làm Mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Trạng Thái:");

        buttonGroup3.add(rdohoatdong);
        rdohoatdong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdohoatdong.setText("Hoạt Động");
        rdohoatdong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdohoatdongActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdonghiviec);
        rdonghiviec.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rdonghiviec.setText("Nghỉ Việc");
        rdonghiviec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdonghiviecActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Mã NV");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoQuanly)
                                    .addComponent(rdohoatdong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoNhanVien)
                                    .addComponent(rdonghiviec)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnxoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlammoi)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(rdoQuanly)
                    .addComponent(rdoNhanVien)
                    .addComponent(jLabel7)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(rdohoatdong)
                    .addComponent(rdonghiviec)
                    .addComponent(jLabel8)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnxoa)
                            .addComponent(btnThem)
                            .addComponent(btnSua)
                            .addComponent(btnlammoi))))
                .addContainerGap())
        );

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnImport.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnImport.setText("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnExport.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrevious.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnPrevious.setText("Previous");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnFirst.setText("First");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLast.setText("Last");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Tìm Kiếm NV");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(234, 234, 234)
                .addComponent(btnImport)
                .addGap(33, 33, 33)
                .addComponent(btnExport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFirst)
                .addGap(18, 18, 18)
                .addComponent(btnPrevious)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(18, 18, 18)
                .addComponent(btnLast)
                .addGap(482, 482, 482))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport)
                    .addComponent(btnExport)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnPrevious)
                    .addComponent(btnLast)
                    .addComponent(btnFirst))
                .addContainerGap())
        );

        tblDSnhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "Email", "Trạng thái", "Chức vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSnhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSnhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDSnhanvien);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNhanVienActionPerformed

    private void rdoQuanlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoQuanlyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoQuanlyActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        int row = tblDSnhanvien.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa nhân viên này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String maNV = txtmanv.getText();
                try {
                    // Thực hiện xóa
                    nvDAO.deleteByNhanVienMaNV(maNV);
                    nvDAO.deleteById(maNV);

                    // Làm mới bảng
                    fillTable();

                    // Hiển thị thông báo
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");

                    // Chọn dòng đầu nếu còn dữ liệu, ngược lại xóa trắng form
                    if (tblDSnhanvien.getRowCount() > 0) {
                        tblDSnhanvien.setRowSelectionInterval(0, 0);
                        loadDataToForm(0);
                    } else {
                        clearFrom();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Xóa thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int row = tblDSnhanvien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên để sửa",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 2️⃣ Hỏi người dùng trước khi thêm


        try {
            // Lấy dữ liệu từ form
            String maNV = txtmanv.getText().trim();
            String hoTen = txthoten.getText().trim();
            String ngaySinhStr = txtngaysinh.getText().trim();
            String diaChi = txtdiachi.getText().trim();
            String sdt = txtsdt.getText().trim();
            String email = txtemail.getText().trim();

            // 1️⃣ Kiểm tra bắt buộc
            if (maNV.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()
                    || diaChi.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2️⃣ Kiểm tra ngày sinh hợp lệ
            java.sql.Date ngaySinh = null;
            try {
                ngaySinh = java.sql.Date.valueOf(ngaySinhStr);
                if (ngaySinh.after(new java.sql.Date(System.currentTimeMillis()))) {
                    JOptionPane.showMessageDialog(this,
                            "Ngày sinh không được lớn hơn ngày hiện tại",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ngày sinh không đúng định dạng (yyyy-MM-dd)",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3️⃣ Kiểm tra giới tính
            if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn giới tính",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4️⃣ Kiểm tra số điện thoại Việt Nam
            if (!sdt.matches("^(03|05|07|08|09|02)\\d{8,9}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ. Phải là số Việt Nam (10-11 chữ số, đầu 03/05/07/08/09/02)",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5️⃣ Kiểm tra email cơ bản
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không hợp lệ",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
        "Bạn có chắc muốn sửa nhân viên này không?",
        "Xác nhận sửa nhân viên",
        JOptionPane.YES_NO_OPTION);

if (confirm != JOptionPane.YES_OPTION) {
    return; // Nếu người dùng chọn No, thoát khỏi hàm
}

            // 6️⃣ Cập nhật nhân viên
            NhanVien nv = nvDAO.findById(maNV);
            nv.setTen_nv(hoTen);
            nv.setGioi_tinh(rdoNam.isSelected()); // true=Nam, false=Nữ
            nv.setNgay_sinh(ngaySinh);
            nv.setDia_chi(diaChi);
            nv.setSdt(sdt);
            nv.setEmail(email);
            nv.setTrang_thai(rdohoatdong.isSelected() ? 1 : 0);
            nv.setChuc_vu(rdoQuanly.isSelected() ? "Quản lý" : "Nhân viên");

            nvDAO.update(nv);

            // Cập nhật bảng và form
            fillTable();
            tblDSnhanvien.setRowSelectionInterval(row, row);
            loadDataToForm(row);

            JOptionPane.showMessageDialog(this,
                    "Cập nhật nhân viên thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi cập nhật nhân viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            if (!validateForm()) {
                return;
            }
            // 2️⃣ Hỏi người dùng trước khi thêm
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thêm nhân viên này không?",
                    "Xác nhận thêm nhân viên",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nếu người dùng chọn No, thoát khỏi hàm
            }

            NhanVien nv = new NhanVien();
            String maNV = txtmanv.getText().trim();
            String tenNV = txthoten.getText().trim();

            nv.setMa_nv(maNV);
            nv.setTen_nv(tenNV);
            nv.setGioi_tinh(rdoNam.isSelected());

            java.sql.Date ngaySinh = java.sql.Date.valueOf(txtngaysinh.getText().trim());
            nv.setNgay_sinh(ngaySinh);

            nv.setDia_chi(txtdiachi.getText().trim());
            nv.setSdt(txtsdt.getText().trim());
            nv.setEmail(txtemail.getText().trim());
            nv.setTrang_thai(rdohoatdong.isSelected() ? 1 : 0);
            nv.setChuc_vu(rdoQuanly.isSelected() ? "Quản lý" : "Nhân viên");

            String taiKhoan = EmailUtil.generateTaiKhoan(tenNV, maNV);
            String matKhau = EmailUtil.generateRandomPassword();

            nv.setTai_khoan(taiKhoan);
            nv.setMat_khau(matKhau);

            nvDAO.create(nv);

            fillTable();
            tblDSnhanvien.setRowSelectionInterval(tblDSnhanvien.getRowCount() - 1, tblDSnhanvien.getRowCount() - 1);
            loadDataToForm(tblDSnhanvien.getRowCount() - 1);

            EmailUtil.sendAccountInfo(nv.getEmail(), taiKhoan, matKhau);

            JOptionPane.showMessageDialog(this, "Tạo nhân viên và gửi tài khoản thành công đến " + nv.getEmail());
            System.out.println("Email gửi đến: " + nv.getEmail());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã tạo nhân viên nhưng gửi email thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemActionPerformed
    private boolean validateForm() {
        String maNV = txtmanv.getText().trim();
        String tenNV = txthoten.getText().trim();
        String ngaysinh = txtngaysinh.getText().trim();
        String diaChi = txtdiachi.getText().trim();
        String sdt = txtsdt.getText().trim();
        String email = txtemail.getText().trim();

        // Kiểm tra rỗng
        if (maNV.isEmpty() || tenNV.isEmpty() || ngaysinh.isEmpty()
                || diaChi.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        // Kiểm tra định dạng ngày
        try {
            java.sql.Date.valueOf(ngaysinh);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh sai định dạng (yyyy-MM-dd)!");
            return false;
        }

        // Kiểm tra số điện thoại (bắt đầu bằng 03, 05, 07, 08, 09 và đủ 10 số)
        if (!sdt.matches("^(03|05|07|08|09)\\d{8}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
            return false;
        }

        // Kiểm tra định dạng email
        if (!email.matches("^[\\w.-]+@gmail\\.com$")) {
            JOptionPane.showMessageDialog(this, "Email phải có định dạng hợp lệ @gmail.com!");
            return false;
        }
        if (nvDAO.existsByMaNV(maNV)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!");
            return false;
        }

        if (nvDAO.existsByEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại trong hệ thống!");
            return false;
        }

        return true;
    }

    private void txtngaysinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngaysinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngaysinhActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        // TODO add your handling code here:
        clearFrom();
        tblDSnhanvien.clearSelection();
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void rdohoatdongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdohoatdongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdohoatdongActionPerformed

    private void rdonghiviecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdonghiviecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdonghiviecActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn file Excel để import");
            int userSelection = chooser.showOpenDialog(this);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = chooser.getSelectedFile();
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            nhanvienDAO dao = new nhanvienDAO();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String maNV = getCellStringValue(row.getCell(0), 0);
                String tenNV = getCellStringValue(row.getCell(1), 1);
                String gioiTinhStr = getCellStringValue(row.getCell(2), 2).trim().toLowerCase();
                boolean gioiTinh = gioiTinhStr.equals("nam");
                String ngaySinhStr = getCellStringValue(row.getCell(3), 3);
                java.sql.Date ngaySinh = java.sql.Date.valueOf(ngaySinhStr);
                String diaChi = getCellStringValue(row.getCell(4), 4);
                String sdt = getCellStringValue(row.getCell(5), 5);
                String email = getCellStringValue(row.getCell(6), 6);
                String trangThaiStr = getCellStringValue(row.getCell(7), 7).trim().toLowerCase();
                int trangThai = trangThaiStr.equals("đang làm") || trangThaiStr.equals("hoạt động") ? 1 : 0;
                String chucVu = getCellStringValue(row.getCell(8), 8);
                String taiKhoan = getCellStringValue(row.getCell(9), 9);
                String matKhau = getCellStringValue(row.getCell(10), 10);

                NhanVien nv = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, diaChi, sdt, email, trangThai, chucVu, taiKhoan, matKhau);

                dao.create(nv);
            }

            workbook.close();
            fis.close();

            JOptionPane.showMessageDialog(this, "Import Excel thành công!");
            fillTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Import Excel thất bại!");
        }
    }//GEN-LAST:event_btnImportActionPerformed

    // Sửa lại hàm này để xử lý ngày sinh kiểu số
    private String getCellStringValue(Cell cell, int colIndex) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (colIndex == 3) { // Ngày sinh
                    java.util.Date date = cell.getDateCellValue();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(date);
                } else {
                    // Nếu là số điện thoại, mã NV, ... thì lấy nguyên giá trị (không ép về long)
                    double d = cell.getNumericCellValue();
                    long l = (long) d;
                    if (d == l) {
                        return String.valueOf(l);
                    } else {
                        return String.valueOf(d);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                return cell.getStringCellValue();
            } catch (IllegalStateException e) {
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception ex) {
                    return "";
                }
            }
            default:
                return "";
        }
    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn nơi lưu file Excel");
            int userSelection = chooser.showSaveDialog(this);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileToSave = chooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            // Lấy danh sách nhân viên từ database
            List<NhanVien> list = new nhanvienDAO().findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("NhanVien");

            // Tạo hàng header
            Row header = sheet.createRow(0);
            String[] headers = {
                "Mã NV", "Tên NV", "Giới tính", "Ngày sinh", "Địa chỉ",
                "SĐT", "Email", "Trạng thái", "Chức vụ", "Tài khoản", "Mật khẩu"
            };
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Đổ dữ liệu từng dòng
            int rowNum = 1;
            for (NhanVien nv : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nv.getMa_nv());
                row.createCell(1).setCellValue(nv.getTen_nv());
                row.createCell(2).setCellValue(nv.isGioi_tinh() ? "Nam" : "Nữ");
                row.createCell(3).setCellValue(nv.getNgay_sinh().toString());
                row.createCell(4).setCellValue(nv.getDia_chi());
                row.createCell(5).setCellValue(nv.getSdt());
                row.createCell(6).setCellValue(nv.getEmail());
                row.createCell(7).setCellValue(nv.getTrang_thai() == 1 ? "Đang làm" : "Nghỉ");
                row.createCell(8).setCellValue(nv.getChuc_vu()); // nếu là enum hoặc int thì format lại nếu cần
                row.createCell(9).setCellValue(nv.getTai_khoan());
                row.createCell(10).setCellValue(nv.getMat_khau());
            }

            // Ghi file Excel
            FileOutputStream fos = new FileOutputStream(fileToSave);
            workbook.write(fos);
            fos.close();
            workbook.close();

            JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại!");
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void tblDSnhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSnhanvienMouseClicked
        // TODO add your handling code here:
        int row = tblDSnhanvien.getSelectedRow();
        if (row >= 0) {
            loadDataToForm(row);
        }
    }//GEN-LAST:event_tblDSnhanvienMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int row = tblDSnhanvien.getSelectedRow();
        if (row < tblDSnhanvien.getRowCount() - 1 && row >= 0) {
            tblDSnhanvien.setRowSelectionInterval(row + 1, row + 1);
            loadDataToForm(row + 1);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        int row = tblDSnhanvien.getSelectedRow();
        if (row > 0) {
            tblDSnhanvien.setRowSelectionInterval(row - 1, row - 1);
            loadDataToForm(row - 1);
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        if (tblDSnhanvien.getRowCount() > 0) {
            tblDSnhanvien.setRowSelectionInterval(0, 0);
            loadDataToForm(0);
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        int lastRow = tblDSnhanvien.getRowCount() - 1;
        if (lastRow >= 0) {
            tblDSnhanvien.setRowSelectionInterval(lastRow, lastRow);
            loadDataToForm(lastRow);
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnxoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQuanly;
    private javax.swing.JRadioButton rdohoatdong;
    private javax.swing.JRadioButton rdonghiviec;
    private javax.swing.JTable tblDSnhanvien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txthoten;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables

}
