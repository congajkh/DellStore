/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.ram;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class ramDAOImpl {
    String createSql = "INSERT INTO ram (ten, dung_luong, loai, trang_thai) VALUES (?, ?, ?, ?)";
    String updateSql = "UPDATE ram SET ten = ?, dung_luong = ?, loai = ?, trang_thai = ? WHERE id = ?";
    String deleteSql = "DELETE FROM ram WHERE id = ?";
    String findAllSql = "SELECT * FROM ram";
    String findByIdSql = "SELECT * FROM ram WHERE id = ?";

    public ram create(ram entity) {
        Object[] values = {
            entity.getTen(),
            entity.getDung_luong(),
            entity.getLoai(),
            entity.getTrang_thai()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    public void update(ram entity) {
        Object[] values = {
            entity.getTen(),
            entity.getDung_luong(),
            entity.getLoai(),
            entity.getTrang_thai(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<ram> findAll() {
        return XQuery.getBeanList(ram.class, findAllSql);
    }

    public ram findById(int id) {
        return XQuery.getSingleBean(ram.class, findByIdSql, id);
    }
}
