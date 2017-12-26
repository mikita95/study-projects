package responses;

import database.Product;
import database.ProductsDao;

import java.util.Optional;

public class MinResponse extends Response {
    public MinResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public Optional<Product> getMin() {
        return this.productsDao.min();
    }

    public String toHTML() {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        Optional<Product> max = this.getMin();
        max.ifPresent(x -> answer.append("<h1>Product with min price: </h1>\n").append(x).append("</br>\n"));
        answer.append("</body></html>\n");
        return answer.toString();
    }
}
