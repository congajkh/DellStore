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
public class Cpu {
    int id;
    String ten;
    String toc_do;
    String loai;
    int trang_thai;
@Override
public String toString() {
    return ten; // hoặc: return dungLuong + " " + loai;
}
}
