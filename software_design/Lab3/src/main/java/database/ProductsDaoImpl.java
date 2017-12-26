package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsDaoImpl implements ProductsDao {

    public ProductsDaoImpl() {
    }

    public void create() {
        try {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)";
                Statement statement = connection.createStatement();

                statement.executeUpdate(sql);
                statement.close();
            }
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of table creation", e);
        }
    }

    @Override
    public void insert(Product product) {
        try {
            executeInsert(product);
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of insertion of product", e);
        }

    }

    @Override
    public List<Product> selectAll() {
        try {
            return executeSelectAll();
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of selecting all products", e);
        }
    }

    @Override
    public Optional<Product> max() {
        try {
            return Optional.ofNullable(executeMax());
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of finding max product", e);
        }
    }

    @Override
    public Optional<Product> min() {
        try {
            return Optional.ofNullable(executeMin());
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of finding min product", e);
        }
    }

    @Override
    public int count() {
        try {
            return executeCount();
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of counting products", e);
        }
    }

    @Override
    public int sum() {
        try {
            return executeSum();
        } catch (SQLException e) {
            throw new ProductsDaoException("Error of sum prices of all products", e);
        }
    }

    private void executeInsert(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
            Statement statement = c.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    private List<Product> executeSelectAll() throws SQLException {
        return executeQueryForProductsList("SELECT * FROM PRODUCT");
    }

    private Product executeMax() throws SQLException {
        List<Product> result = executeQueryForProductsList("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        return result.isEmpty() ? null : result.get(result.size() - 1);
    }

    private Product executeMin() throws SQLException {
        List<Product> result = executeQueryForProductsList("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        return result.isEmpty() ? null : result.get(result.size() - 1);
    }

    private List<Product> executeQueryForProductsList(String query) throws SQLException{
        List<Product> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String  name = resultSet.getString("name");
                int price  = resultSet.getInt("price");
                result.add(new Product(name, price));
            }
            resultSet.close();
            statement.close();
        }
        return result;
    }

    private int executeSum() throws SQLException {
        return executeQueryForInt("SELECT SUM(price) FROM PRODUCT");
    }

    private int executeCount() throws SQLException {
        return executeQueryForInt("SELECT COUNT(*) FROM PRODUCT");
    }

    private int executeQueryForInt(String query) throws SQLException {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        }
        return result;

    }
}
