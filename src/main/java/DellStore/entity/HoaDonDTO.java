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

/**
 *
 * @author docon
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Data
public class HoaDonDTO {
  private int id;
 private String ma_hoa_don;
    private Date ngay_tao;
    private String ten_nhan_vien;
    private int trang_thai;   
}