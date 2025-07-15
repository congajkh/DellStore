/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.hang;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class hangDAOImpl {
     String insertSql = "INSERT INTO hang (ten, trang_thai) VALUES (?, ?)";
    String updateSql = "UPDATE hang SET ten=?, trang_thai=? WHERE id=?";
    String deleteSql = "DELETE FROM hang WHERE id=?";
    String findAllSql = "SELECT * FROM hang";
    String findByIdSql = "SELECT * FROM hang WHERE id=?";

    public hang insert(hang entity) {
        XJdbc.executeUpdate(insertSql, entity.getTen(), entity.getTrang_thai());
        return entity;
    }

    public void update(hang entity) {
        XJdbc.executeUpdate(updateSql, entity.getTen(), entity.getTrang_thai(), entity.getId());
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<hang> findAll() {
        return XQuery.getBeanList(hang.class, findAllSql);
    }

    public hang findById(int id) {
        return XQuery.getSingleBean(hang.class, findByIdSql, id);
    }
}
