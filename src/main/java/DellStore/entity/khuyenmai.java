/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.util.Date;
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

public class khuyenmai {
     private int id;
    private String ma_km;
    private String ten_km;
    private String loai_km;         // "%" hoặc "tiền mặt"
    private double gia_tri;
    private Date ngay_bat_dau;
    private Date ngay_ket_thuc;
    private int trang_thai;
}
