package responses;

import database.ProductsDao;

public class CountResponse extends Response {
    public CountResponse(ProductsDao productsDao) {
        super(productsDao);
    }

    public int getCount() {
        return this.productsDao.count();
    }

    public String toHTML() {
        StringBuilder answer = new StringBuilder();
        answer.append("<html><body>\n");
        int count = this.getCount();
        answer.append("Number of products: ").append(count).append("\n");
        answer.append("</body></html>\n");
        return answer.toString();
    }
}
