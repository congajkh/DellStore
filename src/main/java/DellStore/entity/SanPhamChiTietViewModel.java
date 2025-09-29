/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

    @NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Data
public class SanPhamChiTietViewModel {
    private int id;
    private String tenSanPham;
    private String cpu;
    private String card;
    private String hang;
    private String ocung;
    private String ram;
    private String serial;
    private BigDecimal giaBan;
    private int trangThai;
}
