/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DellStore.controller;

import DellStore.ui.DoiMatKhauJDialog;
import javax.swing.JDialog;
import javax.swing.JFrame;

import DellStore.ui.LoginJDialog;
import DellStore.ui.WelcomeJDialog;

/**
 *
 * @author Gigabyte
 */
public interface DellStoreController {

    void init();

    void showHomeJPanel(JFrame frame);

    void showBanHangJPanel(JFrame frame);
    
    void showSanPhamJPanel(JFrame frame);
    
    void showNhanVienJPanel(JFrame frame);

    void showHoaDonJPanel(JFrame frame);
    
    void showKhuyenMaiJPanel(JFrame frame);
    
    void showKhachHangJPanel(JFrame frame);
    
    void showThongKeJPanel(JFrame frame);
     
    default void showJDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
  
//    default void showLoginDialog(JFrame frame) {
//        this.showJDialog(new LoginJDialog(frame, true));
//    }
//    
//    default void showWelcomeJDialog(JFrame frame){
//    this.showJDialog(new WelcomeJDialog(frame, true));
//    }
}
