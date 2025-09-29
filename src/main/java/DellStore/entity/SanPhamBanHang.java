/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;
import lombok.*;
/**
 *
 * @author nguyendangtuan
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SanPhamBanHang {
    private String maSanPham;
    private String tenSanPham;
    private String cpu;
    private String card;
    private String hang;
    private String ocung;
    private String ram;
    private BigDecimal giaBan;
    private int soLuong;
    
}
