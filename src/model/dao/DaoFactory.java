package model.dao;

import db.DB;
import java.sql.Connection;
import model.dao.implementation.SellerDaoJDBC;
import model.entities.Department;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}
