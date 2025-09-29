package DellStore.ui.JDialog;

import DellStore.dao.impl.SerialDAOImpl;
import DellStore.dao.impl.chitietsanphamDAO;
import DellStore.dao.impl.sanphamDAO;
import DellStore.entity.Card;
import DellStore.entity.ChiTietSanPham;
import DellStore.entity.Cpu;
import DellStore.entity.Ocung;
import DellStore.entity.Ram;
import DellStore.entity.SanPham;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import DellStore.entity.Serial;
import java.awt.Frame;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author docon
 */
public class SerialNhapJDialog extends javax.swing.JDialog {
    private static final int COL_SERIAL = 3;

    private List<Serial> serialList = new ArrayList<>();
    private int sanPhamId;
    private String tenSanPham;
    private String maSanPham;
    private int soLuong;
    private List<Serial> serialListDaNhap = new ArrayList<>();
//    private List<ChiTietSanPham> listChiTietSP;

    private DefaultTableModel model;
    private SerialDAOImpl serialDAO = new SerialDAOImpl();
    private chitietsanphamDAO ctspDAO = new chitietsanphamDAO();
    private sanphamDAO spDAO = new sanphamDAO();
    private List<Cpu> listCPU;
    private List<Ram> listRAM;
    private List<Ocung> listSSD;
    private List<Card> listGPU;
    private List<SanPham> listSanPham;
    private List<ChiTietSanPham> listChiTietSP = new ArrayList<>();

    /**
     * Creates new form SerialNhapJDialog
     */
   public SerialNhapJDialog(java.awt.Frame parent, boolean modal, List<ChiTietSanPham> listChiTietSP,
        List<SanPham> listSanPham,
        List<Cpu> listCPU,
        List<Ram> listRAM,
        List<Ocung> listSSD,
        List<Card> listGPU) {
    super(parent, modal);
    initComponents(); 
    this.listChiTietSP = listChiTietSP;
    this.listSanPham = listSanPham;  // ✅ THÊM DÒNG NÀY
    this.listCPU = listCPU;
    this.listRAM = listRAM;
    this.listSSD = listSSD;
    this.listGPU = listGPU;
    
    setLocationRelativeTo(null);
    initTable();
    fillTable();    
}


    private void initTable() {
        model = new DefaultTableModel(new Object[]{"STT", "Tên sản phẩm", "Thông số kỹ thuật", "Serial"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Chỉ cho nhập ở cột Serial
            }
        };
        tbl_bang.setModel(model);
    }
private void fillTable1() {
    model.setRowCount(0);
    System.out.println("Gọi fillTable");
    model.addRow(new Object[]{1, "Laptop Dell", "i5 / 8GB / 256GB / GTX1650", ""});
}

    private void fillTable() {
        tbl_bang.setModel(model); // ép lại nếu GUI builder gán lại model khác

        model.setRowCount(0);
         // System.out.println("Tổng số ctsp: " + listChiTietSP.size());  // ✅ Thêm dòng này để debug
        int stt = 1;
//        System.out.println("Danh sách sản phẩm:");
//for (SanPham sp : listSanPham) {
//    System.out.println(sp.getId() + " - " + sp.getTen());
//}

        for (ChiTietSanPham ctsp : listChiTietSP) {
            System.out.println("🔍 Danh sách ID sản phẩm trong listSanPham:");
            for (SanPham sp : listSanPham) {
                System.out.println("  - sp.getId() = " + sp.getId() + ", tên = " + sp.getTen());
            }
            System.out.println("🔍 ctsp.getSan_pham_id() = " + ctsp.getSan_pham_id());

            // Lấy tên sản phẩm theo ID
            String tenSP = listSanPham.stream()
                    .filter(sp -> sp.getId() == ctsp.getSan_pham_id())
                    .map(SanPham::getTen)
                    .findFirst()
                    .orElse("Không rõ");
System.out.println("ctsp.getSan_pham_id() = " + ctsp.getSan_pham_id());

            // Ghép thông số kỹ thuật từ ID
            String cpu = listCPU.stream()
                    .filter(c -> c.getId() == ctsp.getCpu_id())
                    .map(Cpu::getTen)
                    .findFirst()
                    .orElse("?");

            String ram = listRAM.stream()
                    .filter(r -> r.getId() == ctsp.getRam_id())
                    .map(Ram::getDung_luong)
                    .findFirst()
                    .orElse("?");

            String ssd = listSSD.stream()
                    .filter(s -> s.getId() == ctsp.getSsd_id())
                    .map(Ocung::getDung_luong)
                    .findFirst()
                    .orElse("?");

            String gpu = listGPU.stream()
                    .filter(g -> g.getId() == ctsp.getGpu_id())
                    .map(Card::getTen)
                    .findFirst()
                    .orElse("?");
            
            String thongSo = cpu + " / " + ram + " / " + ssd + " / " + gpu;
System.out.println("Thêm dòng: " + tenSP + " - " + thongSo);

            model.addRow(new Object[]{stt++, tenSP, thongSo, ""});
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();
        btn_xacnhan = new javax.swing.JButton();
        btn_huybo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông Số Chi Tiết");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Tên SP", "Thông Số SP", "Serial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_bang);

        btn_xacnhan.setText("✔ Xác nhận & Lưu");
        btn_xacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xacnhanActionPerformed(evt);
            }
        });

        btn_huybo.setText("❌ Hủy bỏ");
        btn_huybo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(314, 314, 314)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(btn_xacnhan)
                                .addGap(147, 147, 147)
                                .addComponent(btn_huybo)))
                        .addGap(0, 298, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_xacnhan)
                    .addComponent(btn_huybo))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_xacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xacnhanActionPerformed
  try {
        Set<String> serialTrongBang = new HashSet<>();

    for (int i = 0; i < tbl_bang.getRowCount(); i++) {
        Object value = tbl_bang.getValueAt(i, 3);
        String maSerial = value != null ? value.toString().trim() : "";
        
        // 1. Kiểm tra rỗng
        if (maSerial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + " chưa nhập mã serial!");
            return;
        }
        System.out.println("Serial dòng " + i + ": " + maSerial);

        // 2. Kiểm tra trùng trong bảng
        if (!serialTrongBang.add(maSerial)) {
            JOptionPane.showMessageDialog(this, "Serial bị trùng trong bảng tại dòng " + (i + 1) + ": " + maSerial);
            return;
        }

        // 3. Kiểm tra trùng trong CSDL
        if (serialDAO.existsBySerial(maSerial)) {
            JOptionPane.showMessageDialog(this, "Serial đã tồn tại trong hệ thống tại dòng " + (i + 1) + ": " + maSerial);
            return;
        }
    }
    // Nếu qua được hết các kiểm tra thì lưu
    for (int i = 0; i < tbl_bang.getRowCount(); i++) {
        String maSerial = tbl_bang.getValueAt(i, 3).toString().trim();

        Serial serial = new Serial();
        serial.setMa_serial(maSerial);
        serial.setCtsp_id(listChiTietSP.get(i).getId());
        serial.setTrang_thai(0); // hoặc theo trạng thái của bạn

//        serialDAO.insert(serial);
        
    }

    luuSerialDaNhap(); // Gọi sau khi đã lưu hết serial vào DB
    JOptionPane.showMessageDialog(this, "Lưu serial thành công!");
    this.dispose();

} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Lỗi khi lưu serial!");
}

    }//GEN-LAST:event_btn_xacnhanActionPerformed
    private void luuSerialDaNhap() {
        serialListDaNhap.clear();
        for (int i = 0; i < tbl_bang.getRowCount(); i++) {
            Object value = tbl_bang.getValueAt(i, 3);
            String serialStr = value != null ? value.toString().trim() : "";
            int ctspId = listChiTietSP.get(i).getId();

            if (serialStr.isEmpty()) {
                continue;
            }

            Serial s = new Serial();
            s.setMa_serial(serialStr);
            s.setCtsp_id(ctspId);
            serialListDaNhap.add(s);
        }
    }

    public List<Serial> getSerialList() {
        return this.serialListDaNhap; // hoặc danh sách bạn đã lưu sau khi xác nhận
    }

    private void btn_huyboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyboActionPerformed
      int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn huỷ nhập?\nNếu đã tạo sản phẩm mới, sản phẩm sẽ bị xoá khỏi hệ thống.",
            "Xác nhận huỷ",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {

        if (listChiTietSP != null && !listChiTietSP.isEmpty()) {
            int sanPhamIdMoi = listChiTietSP.get(0).getSan_pham_id();

            if (sanPhamIdMoi != 0) {
                boolean ok = spDAO.deleteSanPhamFull(sanPhamIdMoi);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Huỷ và xoá sản phẩm thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xoá sản phẩm!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa nhập dữ liệu, không có sản phẩm để xoá!");
        }

        this.dispose();
    }
    }//GEN-LAST:event_btn_huyboActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SerialNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SerialNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SerialNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SerialNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SerialNhapJDialog dialog = new SerialNhapJDialog(new javax.swing.JFrame(), true,int sanPhamId, String tenSanPham, int soLuong);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_huybo;
    private javax.swing.JButton btn_xacnhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_bang;
    // End of variables declaration//GEN-END:variables

}
