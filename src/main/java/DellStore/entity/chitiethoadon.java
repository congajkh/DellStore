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
public class ChiTietHoaDon {
    private int id ;
    private int hoa_don_id ;
    private int chi_tiet_san_pham_id ;

    private int so_luong ;  
    private BigDecimal don_gia ;
    private BigDecimal giam_gia;
    Integer  chi_tiet_dot_giam_gia_id;   
    private int trang_thai ;
    private int hinh_thuc_id;
}
