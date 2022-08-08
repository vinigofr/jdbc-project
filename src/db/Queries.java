package db;

public class Queries {
    public static final String DELETE_SELLER =
            "DELETE FROM seller " +
                    "WHERE Id = ?";

    public static final String GET_SELLER_BY_ID =
            "SELECT seller.*, department.Name as DepName " +
                    "FROM seller " +
                    "INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?";

    public static final String GET_SELLER_BY_DEP_ID =
            "SELECT seller.*, department.Name as DepName " +
                    "FROM seller " +
                    "INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name";

    public static final String GET_ALL_SELLERS =
            "SELECT seller.*, department.Name as DepName " +
                    "FROM seller " +
                    "INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "ORDER BY Name";

    public static final String CREATE_SELLER =
            "INSERT INTO seller" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?)";

    public static final String UPDATE_SELLER =
            "UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ?";
}
