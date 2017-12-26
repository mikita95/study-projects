package responses;

import database.Product;
import database.ProductsDao;

public class AddResponse extends Response {
    public AddResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public void add(Product product) {
        this.productsDao.insert(product);
    }

    public String toHTML() {
        return "OK\n";
    }

    public String toHTML(Product product) {
        this.add(product);
        return this.toHTML();
    }
}
