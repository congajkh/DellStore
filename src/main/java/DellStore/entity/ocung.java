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
public class Ocung {

    private int id;
    private String ten;
    private String dung_luong;
    private String loai;
    private int trang_thai;

    @Override
    public String toString() {
        return dung_luong; 
    }
}
