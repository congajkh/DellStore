/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 1. Biến thể sản phẩm (dùng cho thống kê tồn số lượng)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BienTheSanPhamViewModel {
    private int san_pham_id;   // id sản phẩm cha
    private int cpu_id;
    private int gpu_id;
    private int ssd_id;
    private int ram_id;
    private String ten_sp;
    private String cpu;
    private String card;
    private String hang;
    private String ocung;
    private String ram;
    private BigDecimal giaBan;
    private int soLuong; // tổng tồn kho của biến thể này
}