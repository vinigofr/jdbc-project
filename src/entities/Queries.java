package entities;

public class Queries {
    public static final String SELECT_DEPARTMENTS = "SELECT * FROM department";
    public static final String INSERT_INTO_SELLERS =
            "INSERT INTO seller" +
            "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
            "VALUES (?, ?, ?, ?, ?)";
}
