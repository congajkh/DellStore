/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.impl.ThongkeDAO;
import DellStore.entity.DoanhThuDTO;
import DellStore.entity.ThongketheosanphamDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author docon
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    private ThongkeDAO dao = new ThongkeDAO();
    private JDateChooser dateChooser;
    private JComboBox<String> cboLoaiLoc;

    public ThongKeJPanel() {
        initComponents();
        initComboLoaiThoiGian();

        cbo_item.setModel(new DefaultComboBoxModel<>(new String[]{"Tháng", "Năm"}));
        cbo_chon.setModel(new DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new java.util.Date()); // mặc định hôm nay

        tbl_ngay.setModel(new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Giá", "Số lượng", "Doanh thu"}, 0));
        tbl_thang.setModel(new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Giá", "Số lượng", "Doanh thu"}, 0));
        loadDoanhThuNgay(LocalDate.now());
        loadDoanhThuThangHienTai();

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        Map<Integer, Double> data = dao.getDoanhThuTheoTungNgayTrongThang(month, year);

        veBieuDoTongDoanhThu(data,
                "Doanh thu tháng " + month + "/" + year,
                "Ngày",
                "Doanh thu (VND)"
        );
    }

    private void initComboLoaiThoiGian() {
        cbo_item.addActionListener(e -> {
            cbo_chon.removeAllItems();

            if (cbo_item.getSelectedItem().equals("Tháng")) {
                for (int i = 1; i <= 12; i++) {
                    cbo_chon.addItem(String.valueOf(i));
                }
                txt_chon.setText("Chọn tháng");
            } else {
                int yearNow = Calendar.getInstance().get(Calendar.YEAR);
                for (int y = yearNow; y >= yearNow - 10; y--) {
                    cbo_chon.addItem(String.valueOf(y));
                }
                txt_chon.setText("Chọn năm");
            }

        });

        // Khởi tạo mặc định là Tháng
        cbo_item.setSelectedItem("Tháng");
    }

    public void loadDoanhThuNgay(LocalDate ngay) {
        List<ThongketheosanphamDTO> listNgay = dao.thongkeSanPhamTheoNgay(ngay);

        System.out.println("Số lượng record: " + listNgay.size());
        for (ThongketheosanphamDTO dto : listNgay) {
            System.out.println(dto.getMaSP() + " - " + dto.getTenSP());
        }

        fillTableNgay(listNgay);

    }

    public void exportExcel(JTable tblNgay, JTable tblThang, String filePath) {
        Workbook workbook = new XSSFWorkbook();

        // Xuất bảng doanh thu ngày
        Sheet sheetNgay = workbook.createSheet("Doanh thu theo ngày");
        writeTableToSheet(tblNgay, sheetNgay);

        // Xuất bảng doanh thu tháng
        Sheet sheetThang = workbook.createSheet("Doanh thu theo tháng");
        writeTableToSheet(tblThang, sheetThang);

        // Ghi ra file
        try (FileOutputStream fos = new FileOutputStream(filePath + ".xlsx")) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTableToSheet(JTable table, Sheet sheet) {
        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();

        // Header
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < colCount; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(table.getColumnName(col));
        }

        // Dữ liệu
        for (int row = 0; row < rowCount; row++) {
            Row excelRow = sheet.createRow(row + 1);
            for (int col = 0; col < colCount; col++) {
                Object value = table.getValueAt(row, col);
                Cell cell = excelRow.createCell(col);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Auto resize cột
        for (int col = 0; col < colCount; col++) {
            sheet.autoSizeColumn(col);
        }
    }

    private void fillTableNgay(List<ThongketheosanphamDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_ngay.getModel();
        model.setRowCount(0);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Map để gộp theo MaSP
        Map<String, ThongketheosanphamDTO> map = new LinkedHashMap<>();
        for (ThongketheosanphamDTO dto : list) {
            if (map.containsKey(dto.getMaSP())) {
                ThongketheosanphamDTO exist = map.get(dto.getMaSP());
                exist.setSLBan(exist.getSLBan() + dto.getSLBan());
                exist.setDoanhThu(exist.getDoanhThu() + dto.getDoanhThu());
            } else {
                // Tạo bản sao tránh reference cùng object
                map.put(dto.getMaSP(), ThongketheosanphamDTO.builder()
                        .MaSP(dto.getMaSP())
                        .TenSP(dto.getTenSP())
                        .Gia(dto.getGia())
                        .SLBan(dto.getSLBan())
                        .DoanhThu(dto.getDoanhThu())
                        .build());
            }
        }

        // Fill table từ map
        double tongDoanhThu = 0;
        for (ThongketheosanphamDTO dto : map.values()) {
            String giaStr = formatter.format(dto.getGia());
            String doanhThuStr = formatter.format(dto.getDoanhThu());

            model.addRow(new Object[]{
                dto.getMaSP(),
                dto.getTenSP(),
                giaStr,
                dto.getSLBan(),
                doanhThuStr
            });
            tongDoanhThu += dto.getDoanhThu(); // cộng dồn doanh thu
        }
        txt_tongdoanhthu2.setText(formatter.format(tongDoanhThu));
    }

    public void loadDoanhThuThangHienTai() {

        List<ThongketheosanphamDTO> list = dao.thongkeSanPhamTheoThangHienTai();
        fillTableThang(list);
    }

    private void fillTableThang(List<ThongketheosanphamDTO> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_thang.getModel();
        model.setRowCount(0);
        double tongDoanhThu = 0; // biến cộng dồn
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (ThongketheosanphamDTO dto : list) {
            String giaStr = formatter.format(dto.getGia());
            String doanhThuStr = formatter.format(dto.getDoanhThu());

            model.addRow(new Object[]{
                dto.getMaSP(),
                dto.getTenSP(),
                giaStr,
                dto.getSLBan(),
                doanhThuStr
            });
            tongDoanhThu += dto.getDoanhThu(); // cộng dồn doanh thu
        }
        txt_tongdoanhthu.setText(formatter.format(tongDoanhThu));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_ngay = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txt_ngay3 = new javax.swing.JTextField();
        btn_chonngay3 = new javax.swing.JButton();
        btn_loc3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txt_tongdoanhthu2 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        txt_tongdoanhthu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_thang = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        tab2Panel = new javax.swing.JPanel();
        panelBieuDo = new javax.swing.JPanel();
        cbo_item = new javax.swing.JComboBox<>();
        btn_loc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_chon = new javax.swing.JLabel();
        cbo_chon = new javax.swing.JComboBox<>();

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Doanh Thu Theo Ngày"));

        tbl_ngay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Giá", "SL Bán", "Doanh Thu"
            }
        ));
        jScrollPane4.setViewportView(tbl_ngay);

        jLabel7.setText("Chọn Ngày");

        txt_ngay3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_ngay3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_ngay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ngay3ActionPerformed(evt);
            }
        });

        btn_chonngay3.setText("...");
        btn_chonngay3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chonngay3ActionPerformed(evt);
            }
        });

        btn_loc3.setBackground(new java.awt.Color(51, 204, 255));
        btn_loc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timkiem.png"))); // NOI18N
        btn_loc3.setText("Lọc");
        btn_loc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loc3ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 204, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xoa.png"))); // NOI18N
        jButton6.setText("Bỏ Lọc");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel11.setText("Tổng Doanh Thu:");

        txt_tongdoanhthu2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_tongdoanhthu2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tongdoanhthu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tongdoanhthu2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_ngay3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_chonngay3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_loc3)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(txt_tongdoanhthu2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ngay3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_chonngay3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_loc3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tongdoanhthu2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Doanh Thu Theo Tháng"));

        jLabel8.setText("Chọn Ngày");

        jButton7.setText("...");

        jButton8.setBackground(new java.awt.Color(51, 204, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/timkiem.png"))); // NOI18N
        jButton8.setText("Lọc");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 204, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xoa.png"))); // NOI18N
        jButton9.setText("Bỏ Lọc");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        txt_tongdoanhthu.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_tongdoanhthu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tongdoanhthu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tongdoanhthuActionPerformed(evt);
            }
        });

        jLabel9.setText("Tổng Doanh Thu:");

        tbl_thang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Giá", "SL Bán", "Doanh Thu"
            }
        ));
        jScrollPane5.setViewportView(tbl_thang);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(txt_tongdoanhthu, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tongdoanhthu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jButton10.setBackground(new java.awt.Color(255, 153, 102));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/excel.png"))); // NOI18N
        jButton10.setText("Export Excel");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(896, 896, 896)
                        .addComponent(jButton10)
                        .addGap(128, 128, 128)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        panelBieuDo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelBieuDoLayout = new javax.swing.GroupLayout(panelBieuDo);
        panelBieuDo.setLayout(panelBieuDoLayout);
        panelBieuDoLayout.setHorizontalGroup(
            panelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBieuDoLayout.setVerticalGroup(
            panelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        cbo_item.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_loc.setText("Lọc");
        btn_loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_locActionPerformed(evt);
            }
        });

        jLabel1.setText("Loại thời gian");

        txt_chon.setText("Chọn tháng");

        cbo_chon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_chon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_chonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tab2PanelLayout = new javax.swing.GroupLayout(tab2Panel);
        tab2Panel.setLayout(tab2PanelLayout);
        tab2PanelLayout.setHorizontalGroup(
            tab2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2PanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbo_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(txt_chon)
                .addGap(18, 18, 18)
                .addComponent(cbo_chon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btn_loc)
                .addContainerGap(759, Short.MAX_VALUE))
            .addComponent(panelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab2PanelLayout.setVerticalGroup(
            tab2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2PanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(tab2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_loc)
                    .addComponent(jLabel1)
                    .addComponent(cbo_chon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_chon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab2", tab2Panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_ngay3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ngay3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ngay3ActionPerformed

    private void btn_chonngay3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chonngay3ActionPerformed
        // TODO add your handling code here:
        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("yyyy-MM-dd");
        int option = JOptionPane.showConfirmDialog(this, chooser, "Chọn ngày", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Date selectedDate = chooser.getDate();
            if (selectedDate != null) {
                txt_ngay3.setText(new SimpleDateFormat("dd-MM-yyyy").format(selectedDate));
            }
        }
    }//GEN-LAST:event_btn_chonngay3ActionPerformed

    private void btn_loc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loc3ActionPerformed
        try {
            String ngayStr = txt_ngay3.getText().trim();
            if (ngayStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trước khi lọc!");
                return;
            }
            // Parse ngày dd-MM-yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(ngayStr);
            LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();

            loadDoanhThuNgay(localDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_loc3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void txt_tongdoanhthuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tongdoanhthuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tongdoanhthuActionPerformed

    private void btn_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_locActionPerformed
        String loai = (String) cbo_item.getSelectedItem();
        int giaTri = Integer.parseInt(cbo_chon.getSelectedItem().toString());

        panelBieuDo.removeAll(); // Xóa biểu đồ cũ

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        JFreeChart chart = null;

        if ("Năm".equals(loai)) {
    // Thống kê theo tháng trong năm
    Map<Integer, Double> rawData = dao.getDoanhThuTheoTungThangTrongNam(giaTri);

    // Dùng LinkedHashMap để duy trì thứ tự tháng
    Map<Integer, Double> data = new LinkedHashMap<>();
    
    // Đảm bảo tất cả 12 tháng đều có giá trị
    for (int thang = 1; thang <= 12; thang++) {
        data.put(thang, rawData.getOrDefault(thang, 0.0));
    }

    // Tạo dataset
    for (Map.Entry<Integer, Double> entry : data.entrySet()) {
        dataset.addValue(entry.getValue(), "Doanh thu", "Tháng " + entry.getKey());
    }

    // Tạo chart
    JFreeChart chart = ChartFactory.createBarChart(
            "Doanh thu theo tháng - Năm " + giaTri,
            "Tháng", "Doanh thu",
            dataset, PlotOrientation.VERTICAL, false, true, false
    );

    // Cấu hình trục X hiển thị đầy đủ nhãn
    CategoryPlot plot = chart.getCategoryPlot();
    CategoryAxis xAxis = plot.getDomainAxis();
    xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    xAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 10));

    // Thêm chart vào panel
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    panelBieuDo.removeAll();
    panelBieuDo.setLayout(new BorderLayout());
    panelBieuDo.add(chartPanel, BorderLayout.CENTER);
    panelBieuDo.revalidate();
    panelBieuDo.repaint();
} else if ("Tháng".equals(loai)) {
    int thang = giaTri;
    int namHienTai = LocalDate.now().getYear();
    
    // Lấy dữ liệu từ DAO
    Map<Integer, Double> rawData = dao.getDoanhThuTheoTungNgayTrongThang(thang, namHienTai);
    
    // Dùng LinkedHashMap để duy trì thứ tự
    Map<Integer, Double> data = new LinkedHashMap<>();
    
    // Lấy số ngày trong tháng
    YearMonth ym = YearMonth.of(namHienTai, thang);
    int soNgay = ym.lengthOfMonth();
    
    // Đảm bảo map có đầy đủ các ngày từ 1 đến soNgay
    for (int ngay = 1; ngay <= soNgay; ngay++) {
        data.put(ngay, rawData.getOrDefault(ngay, 0.0));
    }
    
    System.out.println("Số ngày trong tháng: " + soNgay);
    System.out.println("Dữ liệu: " + data);
    
    // Vẽ biểu đồ
    veBieuDoTongDoanhThu(data,
            "Doanh thu theo ngày - Tháng " + thang + "/" + namHienTai,
            "Ngày",
            "Doanh thu");
}

//        if (chart != null) {
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new Dimension(panelBieuDo.getWidth(), panelBieuDo.getHeight()));
//            panelBieuDo.add(chartPanel, BorderLayout.CENTER);
//        }
//
//        panelBieuDo.revalidate();
//        panelBieuDo.repaint();

    }//GEN-LAST:event_btn_locActionPerformed

    private void txt_tongdoanhthu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tongdoanhthu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tongdoanhthu2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        txt_ngay3.setText("");
        loadDoanhThuNgay(LocalDate.now());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        try {
            // Giả sử bạn nhập tháng-năm kiểu MM-yyyy
            String thangNam = jTextField5.getText().trim();
            if (thangNam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tháng-năm (MM-yyyy)!");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
            Date date = sdf.parse(thangNam);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int month = cal.get(Calendar.MONTH) + 1; // Calendar bắt đầu từ 0
            int year = cal.get(Calendar.YEAR);

            List<ThongketheosanphamDTO> list = dao.thongkeSanPhamTheoThang(month, year);
            fillTableThang(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tháng-năm không hợp lệ (định dạng: MM-yyyy)!");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        loadDoanhThuThangHienTai();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                exportExcel(tbl_ngay, tbl_thang, file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Xuất file thành công!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xuất file thất bại!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cbo_chonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_chonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_chonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chonngay3;
    private javax.swing.JButton btn_loc;
    private javax.swing.JButton btn_loc3;
    private javax.swing.JComboBox<String> cbo_chon;
    private javax.swing.JComboBox<String> cbo_item;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel panelBieuDo;
    private javax.swing.JPanel tab2Panel;
    private javax.swing.JTable tbl_ngay;
    private javax.swing.JTable tbl_thang;
    private javax.swing.JLabel txt_chon;
    private javax.swing.JTextField txt_ngay3;
    private javax.swing.JTextField txt_tongdoanhthu;
    private javax.swing.JTextField txt_tongdoanhthu2;
    // End of variables declaration//GEN-END:variables

    public void veBieuDoTongDoanhThu(Map<Integer, Double> data, String tieuDe, String tenTrucX, String tenTrucY) {
      
       DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Map.Entry<Integer, Double> entry : data.entrySet()) {
        dataset.addValue(entry.getValue(), "Doanh thu", String.valueOf(entry.getKey()));
    }
    
    // Tạo chart
    JFreeChart chart = ChartFactory.createBarChart(
            tieuDe,
            tenTrucX,
            tenTrucY,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
    );
    
    // Tùy chỉnh CategoryPlot để hiển thị tất cả các nhãn trục X
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    CategoryAxis domainAxis = plot.getDomainAxis();
    
    // Hiển thị tất cả các nhãn trên trục X
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Xoay nhãn 45 độ
    domainAxis.setTickLabelsVisible(true);
    domainAxis.setTickMarksVisible(true);
    
    // Nếu có quá nhiều ngày, có thể điều chỉnh font size
    if (data.size() > 20) {
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 10));
    }
    
    // Tạo ChartPanel
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    
    // Xóa chart cũ và thêm chart mới
    panelBieuDo.removeAll();
    panelBieuDo.setLayout(new BorderLayout());
    panelBieuDo.add(chartPanel, BorderLayout.CENTER);
    
    // Quan trọng: dùng revalidate + repaint
    panelBieuDo.revalidate();
    panelBieuDo.repaint();
}

}
