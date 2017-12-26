package responses;

import database.Product;
import database.ProductsDao;

import java.util.Optional;

public class MaxResponse extends Response {

    public MaxResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public Optional<Product> getMax() {
        return this.productsDao.max();
    }

    public String toHTML() {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        Optional<Product> max = this.getMax();
        max.ifPresent(x -> answer.append("<h1>Product with max price: </h1>\n").append(x).append("</br>\n"));
        answer.append("</body></html>\n");
        return answer.toString();
    }
}
