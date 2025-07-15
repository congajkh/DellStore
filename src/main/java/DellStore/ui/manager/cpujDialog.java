/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package DellStore.ui.manager;

import DellStore.dao.impl.cpuDAOImpl;
import DellStore.entity.cpu;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author docon
 */
public class cpujDialog extends javax.swing.JDialog {
private List<cpu> listCPU = new ArrayList<>();
  
    public cpujDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
         DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[] {
        "STT", "Tên", "Tốc Độ", "Loại", "Trạng Thái"
    });
    tbl_bangcpu.setModel(model);

    loadTableCPU(); // Gọi sau khi gán model
    }
private void loadTableCPU() {
    cpuDAOImpl dao = new cpuDAOImpl();
    listCPU = dao.findAll(); // lưu lại danh sách để sử dụng khi sửa

    DefaultTableModel model = (DefaultTableModel) tbl_bangcpu.getModel();
    model.setRowCount(0); // xóa dữ liệu cũ

    int stt = 1;
    for (cpu item : listCPU) {
        model.addRow(new Object[] {
            stt++, // STT
            item.getTen(),
            item.getToc_do(),
            item.getLoai(),
            item.getTrang_thai() == 1 ? "Còn hàng" : "Hết hàng"
        });
    }
}

private void loadDataToForm(int row) {
    DefaultTableModel model = (DefaultTableModel) tbl_bangcpu.getModel();

    // Lấy dữ liệu từ các cột
    String ten = model.getValueAt(row, 1).toString();
    String tocDo = model.getValueAt(row, 2).toString();
    String loai = model.getValueAt(row, 3).toString();
    String trangThai = model.getValueAt(row, 4).toString();

    // Đổ vào các trường nhập liệu
    txt_tencpu.setText(ten);
    txt_tocdo.setText(tocDo);
    txt_loai.setText(loai);

    // Xử lý radio trạng thái
    if ("1".equals(trangThai) || "Còn hàng".equalsIgnoreCase(trangThai)) {
        rdo_conhang.setSelected(true);
    } else {
        rdo_hethang.setSelected(true);
    }
}
private void clearForm() {
    txt_tencpu.setText("");
    txt_tocdo.setText("");
    txt_loai.setText("");
    rdo_hethang.setSelected(true);
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txt_tencpu = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangcpu = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btn_thoat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btn_them = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        txt_tocdo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_loai = new javax.swing.JTextField();
        rdo_conhang = new javax.swing.JRadioButton();
        rdo_hethang = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txt_tencpu.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_tencpu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tencpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tencpuActionPerformed(evt);
            }
        });

        tbl_bangcpu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên", "Tốc Độ", "Loại", "Trạng Thái"
            }
        ));
        tbl_bangcpu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangcpuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bangcpu);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Loại");

        btn_thoat.setText("Thoát");
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Chức Năng"));

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_them)
                    .addComponent(btn_sua))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btn_them)
                .addGap(18, 18, 18)
                .addComponent(btn_sua)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        txt_tocdo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_tocdo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_tocdo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tocdoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Tốc Độ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Trạng Thái");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Tên CPU");

        txt_loai.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txt_loai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_loai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_loaiActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_conhang);
        rdo_conhang.setText("Còn Hàng");

        buttonGroup1.add(rdo_hethang);
        rdo_hethang.setText("Hết Hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_tencpu, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_tocdo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_loai, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(rdo_conhang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdo_hethang, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_thoat)
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_tencpu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_tocdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_loai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btn_thoat)
                    .addComponent(rdo_conhang)
                    .addComponent(rdo_hethang))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tencpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tencpuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tencpuActionPerformed

    private void txt_tocdoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tocdoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tocdoActionPerformed

    private void txt_loaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_loaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_loaiActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // TODO add your handling code here:
    String ten = txt_tencpu.getText().trim();
    String tocDo = txt_tocdo.getText().trim();
    String loai = txt_loai.getText().trim();
    int trangThai = rdo_conhang.isSelected() ? 1 : 0;

    cpu newCPU = new cpu();
    newCPU.setTen(ten);
    newCPU.setToc_do(tocDo);
    newCPU.setLoai(loai);
    newCPU.setTrang_thai(trangThai);

    cpuDAOImpl dao = new cpuDAOImpl();
    dao.insert(newCPU); // hàm insert trong DAO của bạn
    JOptionPane.showMessageDialog(this, "Thêm CPU thành công!");
    loadTableCPU(); // cập nhật lại bảng
    clearForm(); // xóa form nếu muốn
    }//GEN-LAST:event_btn_themActionPerformed

    private void tbl_bangcpuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangcpuMouseClicked
        // TODO add your handling code here:
         int row = tbl_bangcpu.getSelectedRow();
        if (row >= 0) {
            loadDataToForm(row);
        }
    }//GEN-LAST:event_tbl_bangcpuMouseClicked

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
       int row = tbl_bangcpu.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn CPU để sửa!");
        return;
    }

    // Lấy object cpu theo chỉ số dòng được chọn
    cpu cpuUpdate = listCPU.get(row);

    // Lấy dữ liệu từ form
    String ten = txt_tencpu.getText().trim();
    String tocDo = txt_tocdo.getText().trim();
    String loai = txt_loai.getText().trim();
    int trangThai = rdo_conhang.isSelected() ? 1 : 0;

    // Cập nhật thông tin vào object
    cpuUpdate.setTen(ten);
    cpuUpdate.setToc_do(tocDo);
    cpuUpdate.setLoai(loai);
    cpuUpdate.setTrang_thai(trangThai);

    // Gọi DAO để cập nhật
    cpuDAOImpl dao = new cpuDAOImpl();
    dao.update(cpuUpdate);

    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

    loadTableCPU();  // reload bảng sau khi sửa
    clearForm();     // xóa trắng form (nếu có)
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        // TODO add your handling code here:
           this.dispose(); // đóng form hiện tại
    }//GEN-LAST:event_btn_thoatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(cpujDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cpujDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cpujDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cpujDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                cpujDialog dialog = new cpujDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_thoat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_conhang;
    private javax.swing.JRadioButton rdo_hethang;
    private javax.swing.JTable tbl_bangcpu;
    private javax.swing.JTextField txt_loai;
    private javax.swing.JTextField txt_tencpu;
    private javax.swing.JTextField txt_tocdo;
    // End of variables declaration//GEN-END:variables
}
