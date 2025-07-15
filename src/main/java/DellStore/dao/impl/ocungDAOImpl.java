/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.ocung;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class ocungDAOImpl {
    String insertSql = "INSERT INTO ocung (ten, loai, dung_luong, trang_thai) VALUES (?, ?, ?, ?)";
    String updateSql = "UPDATE ocung SET ten=?, dung_luong=?,loai=?, trang_thai=? WHERE id=?";
    String deleteSql = "DELETE FROM ocung WHERE id=?";
    String findAllSql = "SELECT * FROM ocung";
    String findByIdSql = "SELECT * FROM ocung WHERE id=?";

    public ocung insert(ocung entity) {
        XJdbc.executeUpdate(insertSql, entity.getTen(), entity.getDung_luong(), entity.getLoai(), entity.getTrang_thai());
        return entity;
    }

    public void update(ocung entity) {
        XJdbc.executeUpdate(updateSql, entity.getTen(), entity.getDung_luong(), entity.getLoai(), entity.getTrang_thai(), entity.getId());
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<ocung> findAll() {
        return XQuery.getBeanList(ocung.class, findAllSql);
    }

    public ocung findById(int id) {
        return XQuery.getSingleBean(ocung.class, findByIdSql, id);
    }
}
