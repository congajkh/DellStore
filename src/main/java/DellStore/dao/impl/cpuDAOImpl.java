/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.cpu;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class cpuDAOImpl {
    String insertSql = "INSERT INTO cpu (ten, toc_do, loai,trang_thai) VALUES (?, ?, ?,?)";
    String deleteSql = "DELETE FROM cpu WHERE id=?";
    String findAllSql = "SELECT * FROM cpu";
    String findByIdSql = "SELECT * FROM cpu WHERE id=?";

    public cpu insert(cpu entity) {
        XJdbc.executeUpdate(insertSql, entity.getTen(), entity.getToc_do(),entity.getLoai(), entity.getTrang_thai());
        return entity;
    }

 public void update(cpu entity) {
    String sql = "UPDATE cpu SET ten=?, toc_do=?, loai=?, trang_thai=? WHERE id=?";
    XJdbc.executeUpdate(sql,
        entity.getTen(),
        entity.getToc_do(),
        entity.getLoai(),
        entity.getTrang_thai(),
        entity.getId()
    );
}


    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<cpu> findAll() {
        return XQuery.getBeanList(cpu.class, findAllSql);
    }

    public cpu findById(int id) {
        return XQuery.getSingleBean(cpu.class, findByIdSql, id);
    }
}
