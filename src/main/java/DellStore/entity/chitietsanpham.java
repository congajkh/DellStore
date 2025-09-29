/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

import java.math.BigDecimal;

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
public class ChiTietSanPham {
    int id;
    int san_pham_id;
    int cpu_id;
    int ram_id;
    int ssd_id;
    int gpu_id;
    BigDecimal gia_ban;
    int trang_thai;
}
