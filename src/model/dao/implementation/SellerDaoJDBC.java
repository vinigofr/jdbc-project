package model.dao.implementation;

import db.DB;
import db.DbException;
import db.Queries;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    Queries.CREATE_SELLER,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }

                DB.closeResultSet(resultSet);

            } else {
                throw new DbException("Erro inesperado, nenhuma linha afetada");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Queries.UPDATE_SELLER);

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());
            statement.setInt(6, obj.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    Queries.DELETE_SELLER,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DbException("Seller not found");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(Queries.GET_SELLER_BY_ID);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);

                Seller seller = instantiateSeller(resultSet, department);

                return seller;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    public List<Seller> findByDepartment(Integer depId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(Queries.GET_SELLER_BY_DEP_ID);
            statement.setInt(1, depId);

            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (resultSet.next()) {
                Department department = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), department);
                }

                sellers.add(instantiateSeller(resultSet, department));
            }

            if (sellers.isEmpty()) {
                return null;
            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(Queries.GET_ALL_SELLERS);

            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (resultSet.next()) {
                Department department = departmentMap.get(resultSet.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDepartment(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), department);
                }

                sellers.add(instantiateSeller(resultSet, department));
            }

            if (sellers.isEmpty()) {
                return null;
            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }
}
