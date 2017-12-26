package responses;

import database.Product;
import database.ProductsDao;

import java.util.List;

public class GetResponse extends Response {
    public GetResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public List<Product> get() {
        return this.productsDao.selectAll();
    }

    public String toHTML() {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        List<Product> products = this.get();
        products.forEach(x -> answer.append(x).append("</br>\n"));
        answer.append("</body></html>\n");
        return answer.toString();
    }
}
