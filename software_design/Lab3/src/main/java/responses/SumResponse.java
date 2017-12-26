package responses;

import database.ProductsDao;

public class SumResponse extends Response {
    public SumResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public int getSum() {
        return this.productsDao.sum();
    }

    public String toHTML() {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        int count = this.getSum();
        answer.append("Summary price: ").append(count).append("\n");
        answer.append("</body></html>\n");
        return answer.toString();
    }
}
