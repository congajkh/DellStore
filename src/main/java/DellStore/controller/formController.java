/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DellStore.controller;

import javax.swing.JDialog;
import javax.swing.JFrame;

import DellStore.ui.lloginJDialog1;

/**
 *
 * @author Gigabyte
 */
public interface formController {

    void init();

    void showHomeJPanel(JFrame frame);

    void showBanHangJPanel(JFrame frame);

    void showHoaDonJPanel(JFrame frame);
    void showKhachhangJPanel(JFrame frame);
    default void showJDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    default void showloginDialog(JFrame frame) {
        this.showJDialog(new lloginJDialog1(frame, true));
    }
}
