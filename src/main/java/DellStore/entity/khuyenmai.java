/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
/**
 *
 * @author nguyendangtuan
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class KhuyenMai {
    private int id;
    private String ma_km;
    private String ten_km;
    private String loai_giam;         // "%" hoặc "tiền mặt"
    private BigDecimal giam_gia;
    private Date ngay_bat_dau;
    private Date ngay_ket_thuc;
    private int trang_thai;
}
