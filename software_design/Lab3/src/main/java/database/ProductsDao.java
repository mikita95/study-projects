package database;

import java.util.List;
import java.util.Optional;

public interface ProductsDao {
    public void insert(Product product);
    public List<Product> selectAll();
    public Optional<Product> max();
    public Optional<Product> min();
    public int count();
    public int sum();
}
