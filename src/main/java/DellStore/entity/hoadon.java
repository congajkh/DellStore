/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;
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
public class HoaDon {
    private int id;
    private String ma;
    private int nhan_vien_id;
    private int khach_hang_id;
    private Date ngay_tao;
    private Double  tong_tien;
    private int trang_thai;
}
