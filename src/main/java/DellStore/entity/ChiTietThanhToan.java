/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;
import lombok.*;
/**
 *
 * @author nguyendangtuan
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChiTietThanhToan {
    private int id;
    private int hoa_don_id;
    private int hinh_thuc_thanh_toan_id;
    private double so_tien;
}
