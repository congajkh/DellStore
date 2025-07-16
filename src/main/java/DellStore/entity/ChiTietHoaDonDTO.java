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
public class ChiTietHoaDonDTO {
    private int id;
    private String maHoaDon;
    private String tenSanPham;
    private BigDecimal donGia;
    private int soLuong;
    private BigDecimal tienKhuyenMai;
    private BigDecimal tongTien;
    private String maSerial;  
}
