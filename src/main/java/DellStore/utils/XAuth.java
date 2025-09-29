/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.utils;

import DellStore.entity.NhanVien;

/**
 *
 * @author PC
 */
public class XAuth {
  public static NhanVien user = null;
   public static boolean isLogin() {
        return user != null;
    }
   
    public static void clear() {
        user = null;
    }
    
    
}
