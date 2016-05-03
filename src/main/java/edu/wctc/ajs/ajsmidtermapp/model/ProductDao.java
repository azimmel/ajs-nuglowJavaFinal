package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.exception.NullOrEmptyArgumentException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;

/**
 * ProductDAOStrategy class that has connection information and allows for
 * C.R.U.D Capabilities. Exception handling is done through DataAccessException
 * for all methods and validation of parameters is done using primarily
 * NullOrEmptyArgumentException. This class can retrieve a list of products or
 * retrieve one product by it's ID. It can also create a new product, delete a
 * single product and update a single product.
 *
 * @author Alyson
 */
@Dependent
public class ProductDao implements ProductDaoStrategy, Serializable {

    private static final String TABLE_NAME = "product";
    private static final String PRIMARY_KEY_COLUMN_NAME = "product_id";
    private static final String PRODUCT_NAME_COL_NAME = "product_name";
    private static final String PRODUCT_TYPE_COL_NAME = "product_type";
    private static final String PRODUCT_PRICE_COL_NAME = "product_price";
    private static final String PRODUCT_URL_IMG_COL_NAME = "product_image";
    private static final String PRODUCT_DESCRIPTION_COL_NAME = "product_description";

    //live object
    //@Inject
    //private DBStrategy db;
    //testing object
    private DBStrategy db = new DBMySqlStrategy();
    private DataSource ds;

    private String driver;
    private String url;
    private String user;
    private String password;

    /**
     * Empty Product DAO constructor to work with the product DAO
     */
    public ProductDao() {
    }

    /**
     * Initializes the DAO connection parameters using a data source connection.
     *
     * @param ds Data source connection.
     * @throws DataAccessException Custom Exception Class.
     */
    @Override
    public final void initDao(DataSource ds) throws DataAccessException {
        if (ds == null) {
            throw new NullOrEmptyArgumentException();
        }
        setDs(ds);
    }

    /**
     * Initializes the DAO connection parameters.
     *
     * @param driver Driver class.
     * @param url database url.
     * @param user Database user name.
     * @param password Database password.
     */
    @Override
    public final void initDao(String driver, String url, String user, String password) {
        if (driver == null || driver.isEmpty() || url == null || url.isEmpty()
                || user == null || user.isEmpty() || password == null
                || password.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(password);
    }

    /**
     * Gets and returns the database.
     *
     * @return Database.
     */
    @Override
    public final DBStrategy getDb() {
        return db;
    }

    /**
     * Sets the database.
     *
     * @param db database
     */
    @Override
    public final void setDb(DBStrategy db) {
        if (db == null) {
            throw new NullOrEmptyArgumentException();
        }
        this.db = db;
    }

    /**
     * Method opens and closes connection to retrieve full list of products
     *
     * @return List of products in database
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException
     */
    @Override
    public final List<Product> getProductList() throws DataAccessException {
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }

        List<Map<String, Object>> rawData = db.findAllRecordsForTable(TABLE_NAME, 0);
        List<Product> products = new ArrayList<>();
        for (Map rec : rawData) {
            products.add(getRawData(rec));
        }

        db.closeConnection();
        return products;
    }

    /**
     * Opens and Closes the connection and Retrieves a specific product by it's
     * ID.
     *
     * @param id Product ID.
     * @return Returns the Product.
     * @throws DataAccessException Custom exception class.
     */
    @Override
    public final Product getProductById(Object id) throws DataAccessException {
        if (id == null) {
            throw new NullOrEmptyArgumentException();
        }
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }
        Map<String, Object> rawData = db.findRecordById(TABLE_NAME, PRIMARY_KEY_COLUMN_NAME, id);
        Product product = getRawData(rawData);
        db.closeConnection();
        return product;

    }

    /**
     * Deletes an product by specified ID returning the number of records
     * delete. Opens and closes connection within the method.
     *
     * @param productId Id of the product to be deleted.
     * @return Number of records deleted.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    @Override
    public final int deleteProductById(Object productId) throws DataAccessException {
        if (productId == null) {
            throw new NullOrEmptyArgumentException();
        }
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }
        Object primaryKey = productId;

        int result = db.deleteRecordById(TABLE_NAME, PRIMARY_KEY_COLUMN_NAME, primaryKey);
        db.closeConnection();
        return result;

    }

    /**
     * Opens and closes connection within the method. Adds a new product to the
     * database.
     *
     * @param product object to be added to database
     * @return number of records added.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    @Override
    public final int createNewProduct(Product product) throws DataAccessException {
        if (product == null) {
            throw new NullOrEmptyArgumentException();
        }
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }
        List recordData = new ArrayList(Arrays.asList(product.getProductName(),
                product.getProductType(), product.getProductPrice(),
                product.getProductUrlRef(), product.getProductDescription()));
        List colNames = new ArrayList(Arrays.asList(PRODUCT_NAME_COL_NAME,
                PRODUCT_TYPE_COL_NAME, PRODUCT_PRICE_COL_NAME,
                PRODUCT_URL_IMG_COL_NAME, PRODUCT_DESCRIPTION_COL_NAME));
        int result = db.createNewRecordInTable(TABLE_NAME, colNames, recordData);
        db.closeConnection();
        return result;
    }

    /**
     * Open and close connection within the method. updates a current record in
     * the database with the values specified.
     *
     * @param currProductId The current ID of the product before the change.
     * @param productId The product Id to be changed to.
     * @param productName The product name to be changed to.
     * @param productPrice The product price to be changed to.
     * @param productImgUrl The products image URL reference to be changed to.
     * @param productDescription The product description to be changed to.
     * @param productType The product type/category to be changed to.
     * @return number of records updated in database.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    @Override
    public final int updateProductById(Object currProductId, Object productId,
            Object productName, Object productType, Object productPrice,
            Object productImgUrl, Object productDescription) throws DataAccessException {
        if (currProductId == null || productId == null || productName == null
                || productType == null || productPrice == null
                || productImgUrl == null || productDescription == null) {
            throw new NullOrEmptyArgumentException();
        }
        if (ds == null) {
            db.openConnection(driver, url, user, password);
        } else {
            try {
                db.openConnection(ds);
            } catch (Exception ex) {
                throw new DataAccessException(ex.getMessage(), ex.getCause());
            }
        }
        List<String> colNames = new ArrayList<>(Arrays.asList(PRIMARY_KEY_COLUMN_NAME,
                PRODUCT_NAME_COL_NAME, PRODUCT_TYPE_COL_NAME, PRODUCT_PRICE_COL_NAME,
                PRODUCT_URL_IMG_COL_NAME, PRODUCT_DESCRIPTION_COL_NAME));
        List<Object> colValues = new ArrayList<>(Arrays.asList(productId,
                productName, productType, productPrice, productImgUrl, productDescription));
        int result = db.updateRecordById(TABLE_NAME, colNames, colValues,
                PRIMARY_KEY_COLUMN_NAME, currProductId);
        db.closeConnection();
        return result;
    }

    private Product getRawData(Map<String, Object> rawData) {
        Product product = new Product();
        Integer productId = new Integer(rawData.get(PRIMARY_KEY_COLUMN_NAME).toString());
        product.setProductId(productId);
        String name = rawData.get(PRODUCT_NAME_COL_NAME) == null ? "" : rawData.get(PRODUCT_NAME_COL_NAME).toString();
        product.setProductName(name);
        String type = rawData.get(PRODUCT_TYPE_COL_NAME) == null ? "" : rawData.get(PRODUCT_TYPE_COL_NAME).toString();
        product.setProductType(type);
        String price = rawData.get(PRODUCT_PRICE_COL_NAME) == null ? "" : rawData.get(PRODUCT_PRICE_COL_NAME).toString();
        Double doublePrice = Double.parseDouble(price);
        product.setProductPrice(doublePrice);
        String img = rawData.get(PRODUCT_URL_IMG_COL_NAME) == null ? "" : rawData.get(PRODUCT_URL_IMG_COL_NAME).toString();
        product.setProductUrlRef(img);
        String descr = rawData.get(PRODUCT_DESCRIPTION_COL_NAME) == null ? "" : rawData.get(PRODUCT_DESCRIPTION_COL_NAME).toString();
        product.setProductDescription(descr);
        return product;
    }

    /**
     * Returns the driver class.
     *
     * @return Driver class
     */
    @Override
    public final String getDriver() {
        return driver;
    }

    /**
     * Sets the driver class.
     *
     * @param driver Driver class.
     */
    @Override
    public final void setDriver(String driver) {
        if (driver == null || driver.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.driver = driver;
    }

    /**
     * Gets the database URL.
     *
     * @return Database URL.
     */
    @Override
    public final String getUrl() {
        return url;
    }

    /**
     * Sets the database URL.
     *
     * @param url Database URL.
     */
    @Override
    public final void setUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.url = url;
    }

    /**
     * Gets the database user name for log in.
     *
     * @return Database user name.
     */
    @Override
    public final String getUser() {
        return user;
    }

    /**
     * Sets the user name for database log in.
     *
     * @param user Database user name.
     */
    @Override
    public final void setUser(String user) {
        if (user == null || user.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.user = user;
    }

    /**
     * Gets the database password for log in.
     *
     * @return Database password.
     */
    @Override
    public final String getPwd() {
        return password;
    }

    /**
     * Sets the database password for log in.
     *
     * @param password Database password.
     */
    @Override
    public final void setPwd(String password) {
        if (password == null || password.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.password = password;
    }

    /**
     * Gets the data source for connection pooling.
     *
     * @return Current DataSource.
     */
    private DataSource getDs() {
        return ds;
    }

    /**
     * Sets the datasource for connection pooling.
     *
     * @param ds Datasource.
     */
    private void setDs(DataSource ds) {
        if (ds == null) {
            throw new NullOrEmptyArgumentException();
        }
        this.ds = ds;
    }

//    public static void main(String[] args) throws DataAccessException {
//        ProductDaoStrategy dao = new ProductDao();
//        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/nuglow";
//        String userName = "root";
//        String password = "admin";
//
//        dao.initDao(driver, url, userName, password);
//        List<Product> products = dao.getProductList();
//
//        System.out.println(products);
////
////        int deleteComplete = dao.deleteProductById(4);
////
////        System.out.println(deleteComplete);
////        Product ranProduct = new Product();
////        ranProduct.setProductName("1 oz of Failure");
////        ranProduct.setProductType("Failure");
////        ranProduct.setProductPrice(2.99);
////        ranProduct.setProductUrlRef("imgs/failure.jpg");
////        ranProduct.setProductDescription("Failure is misery. Cheap, easy, and the laziest way. Made from rancid dreams.");
////        int result = dao.createNewProduct(ranProduct);
////        System.out.println(result)
////        int results = dao.updateProductById(4,4,"1 oz of Misery and Failure", "Failure", 1.50, "imgs/failure.jpg", "Failure is misery. Made from rancid dreams.");
////        System.out.println(results);
//        Product product = dao.getProductById(1);
//        System.out.println(product);
//        products = dao.getProductList();
//        System.out.println(products);
//    }
}
