package responses;

import database.ProductsDao;

public abstract class Response {
    protected final ProductsDao productsDao;

    public Response(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public abstract String toHTML();

    public static Response parse(String command, ProductsDao productsDao) {
        switch (command) {
            case "max": return new MaxResponse(productsDao);
            case "min": return new MinResponse(productsDao);
            case "count": return new CountResponse(productsDao);
            case "sum": return new SumResponse(productsDao);
            case "get": return new GetResponse(productsDao);
            case "add": return new AddResponse(productsDao);
        }
        return null;
    }

}
