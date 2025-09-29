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
public class Serial {
    int id;
    String ma_serial;
    int ctsp_id;
    int trang_thai;
    
   @Override
    public String toString() {
        return this.ma_serial; // hoặc: return dungLuong + " " + loai;
    }
}
