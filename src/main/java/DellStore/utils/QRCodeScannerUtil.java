/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.utils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
public class QRCodeScannerUtil {

    public static void openScannerFrame(Consumer<String> onScanSuccess) {
        JFrame frame = new JFrame("Quét mã QR");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        frame.add(panel, BorderLayout.CENTER);

        // Nút đóng cửa sổ
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> {
            if (webcam.isOpen()) webcam.close();
            frame.dispose();
        });
        frame.add(btnClose, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Quét QR trong luồng riêng
        Thread thread = new Thread(() -> {
            webcam.open();
            try {
                while (true) {
                    BufferedImage image = webcam.getImage();
                    if (image == null) continue;

                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    try {
                        Result result = new MultiFormatReader().decode(bitmap);
                        String qrText = result.getText();

                        // Dừng quét và đóng webcam
                        webcam.close();
                        frame.dispose();

                        SwingUtilities.invokeLater(() -> onScanSuccess.accept(qrText));
                        break;

                    } catch (NotFoundException e) {
                        // Không có mã QR, tiếp tục
                    }

                    Thread.sleep(200);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (webcam.isOpen()) webcam.close();
            }
        });

        thread.start();
    }
}

