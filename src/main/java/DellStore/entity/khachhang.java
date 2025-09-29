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
///**
// *
// * @author Admin
// */
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@ToString
//@Getter
//@Setter
//@Data
public class KhachHang {
     private int id;
    private String ten;
    private boolean gioi_tinh;
    private String dia_chi;
    private String sdt;
    private String email;   

    public KhachHang(int id, String ten, boolean gioi_tinh, String dia_chi, String sdt, String email) {
        this.id = id;
        this.ten = ten;
        this.gioi_tinh = gioi_tinh;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
    }

    public KhachHang(int id, String ten, String sdt, String email) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
    }

    public KhachHang() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(boolean gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
public String toString() {
    return  ten +  sdt ;
}

    
}
