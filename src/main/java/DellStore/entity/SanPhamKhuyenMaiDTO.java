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
public class SanPhamKhuyenMaiDTO {
     private String maSp;
    private String tenSp;
    private String cpu;
    private String ram;
    private String hang;
    private String card;
    private String ocung;
    private BigDecimal giaBan;
}
