/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DellStore.dao.impl;

import DellStore.entity.card;
import DellStore.utils.XJdbc;
import DellStore.utils.XQuery;
import java.util.List;

/**
 *
 * @author docon
 */
public class cardDAOImpl {
    String insertSql = "INSERT INTO card (ten, loai, trang_thai) VALUES (?, ?, ?)";
    String updateSql = "UPDATE card SET ten=?, loai=?, trang_thai=? WHERE id=?";
    String deleteSql = "DELETE FROM card WHERE id=?";
    String findAllSql = "SELECT * FROM card";
    String findByIdSql = "SELECT * FROM card WHERE id=?";

    public card insert(card entity) {
        XJdbc.executeUpdate(insertSql, entity.getTen(), entity.getLoai(), entity.getTrang_thai());
        return entity;
    }

    public void update(card entity) {
        XJdbc.executeUpdate(updateSql, entity.getTen(), entity.getLoai(), entity.getTrang_thai(), entity.getId());
    }

    public void deleteById(int id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    public List<card> findAll() {
        return XQuery.getBeanList(card.class, findAllSql);
    }

    public card findById(int id) {
        return XQuery.getSingleBean(card.class, findByIdSql, id);
    }
}
