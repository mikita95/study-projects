package database;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public static void createFactory() {
        daoFactory = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return daoFactory;
    }

    public ProductsDaoImpl getProductDaoImpl() {
        return new ProductsDaoImpl();
    }
}