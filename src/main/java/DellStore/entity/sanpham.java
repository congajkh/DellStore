/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.entity;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@ToString
//@Getter
//@Setter
//@Data
public class sanpham {
    private int id;
    private String ten;
    private String masp;
    private String mo_ta;
    private int loai_san_pham_id;
    private int hang_id;
    private int trang_thai;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMasp() { return masp; }
    public void setMasp(String masp) { this.masp = masp; }

    public String getMo_ta() { return mo_ta; }
    public void setMo_ta(String mo_ta) { this.mo_ta = mo_ta; }

    public int getLoai_san_pham_id() { return loai_san_pham_id; }
    public void setLoai_san_pham_id(int loai_san_pham_id) { this.loai_san_pham_id = loai_san_pham_id; }

    public int getHang_id() { return hang_id; }
    public void setHang_id(int hang_id) { this.hang_id = hang_id; }

    public int getTrang_thai() { return trang_thai; }
    public void setTrang_thai(int trang_thai) { this.trang_thai = trang_thai; }
}
