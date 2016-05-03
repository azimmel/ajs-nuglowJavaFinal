package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.exception.NullOrEmptyArgumentException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Alyson
 */
@SessionScoped
public class ProductService implements Serializable {

    //@Inject
    //private ProductDaoStrategy dao;
    //test dao
    private ProductDaoStrategy dao = new ProductDao();
    private static final String TABLE_NAME = "product";

    /**
     * Empty productService constructor for implementation.
     */
    public ProductService() {
    }

    /**
     * Gets the DaoStrategy object and returns it.
     *
     * @return DaoStrategy object.
     */
    public ProductDaoStrategy getDao() {
        return dao;
    }

    /**
     * Sets the DaoStrategy object.
     *
     * @param dao DaoStrategy object.
     */
    public void setDao(ProductDaoStrategy dao) {
        if (dao == null) {
            throw new NullOrEmptyArgumentException();
        }
        this.dao = dao;
    }

    /**
     * Connection to the DAO class to retrieve the product list.
     *
     * @return The list of product in the database.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    public List<Product> getProductList() throws DataAccessException {
        return dao.getProductList();
    }

    /**
     * Connection to the dao class to retrieve a single product by its id
     *
     * @param productId id of the other to be retrieved from the database.
     * @return the author being retrieved by the id.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException Custom
     * exception class.
     */
    public Product getProductById(Object productId) throws DataAccessException {
        if (productId == null) {
            throw new NullOrEmptyArgumentException();
        }
        return dao.getProductById(productId);
    }

    /**
     * Connection to the dao class to delete a product from the database by its
     * id
     *
     * @param productId id of a product to be deleted from the database
     * @return product deletion confirmation integer.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException
     */
    public int deleteProductById(Object productId) throws DataAccessException {
        if (productId == null) {
            throw new NullOrEmptyArgumentException();
        }

        return dao.deleteProductById(productId);
    }

    /**
     * Creating a new product in the database. This includes the name of the
     * product, the product type, the product price, the products image link and
     * the products description. The ID is auto incremented in the table.
     *
     * @param productName name of the product being created
     * @param type Category of the product.
     * @param price Price of the product.
     * @param imgUrl relative image URL of the product. This must be located
     * inside a project folder titled "imgs".
     * @param description Products description.
     * @return integer representing if a product was created or not.
     * @throws edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException
     */
    public int createNewProduct(Object productName, Object type, Object price,
            Object imgUrl, Object description) throws DataAccessException {
        if (productName == null || type == null || price == null || imgUrl == null
                || description == null) {
            throw new NullOrEmptyArgumentException();
        }
        int result = dao.createNewProduct(createProductObject(productName, type, price, imgUrl, description));
        return result;
                

    }

    private Product createProductObject(Object productName, Object type, Object price,
            Object imgUrl, Object description) {
        Product product = new Product();
        product.setProductName((String) productName);
        product.setProductType((String) type);
        product.setProductPrice(Double.parseDouble(price.toString()));
        product.setProductUrlRef((String) imgUrl);
        product.setProductDescription((String) description);
        return product;
    }

    /**
     * Updates a current record with new record information. Also checks id
     * against itself to prevent duplicating id's.
     *
     * @param currProductId Pages current product id
     * @param productId product Id submitted.
     * @param productName Product name.
     * @param type Product category/ type.
     * @param price Product price.
     * @param imgUrl Products relative img URL. URL must be located in an imgs
     * folder in your product titled "imgs".
     * @param description Product description.
     * @return An integer is returned representing completion.
     * @throws DataAccessException
     */
    public int updateProductById(Object currProductId, Object productId, Object productName,
            Object type, Object price, Object imgUrl, Object description)
            throws DataAccessException {
        if(currProductId == null || productId == null || productName == null || type == null
                || price == null || imgUrl == null || description == null){
            throw new NullOrEmptyArgumentException();
        }
        return dao.updateProductById(currProductId, productId, productName, type, price, imgUrl, description);
    }
    
    //Testing
//    public static void main(String[] args) throws DataAccessException {
//        ProductService srv = new ProductService();
//        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/nuglow";
//        String userName = "root";
//        String password = "admin";
//        srv.getDao().initDao(driver, url, userName, password);
//        List<Product> products = srv.getProductList();
//        System.out.println(products);
//        // works
//        int msg = srv.deleteProductById(22);
//        //System.out.println(msg);
////        int msg = srv.createNewProduct("1 oz of Misery and Failure", "Failure", 1.50, "imgs/failure.jpg", "Failure is misery. Made from rancid dreams.");
////        int msg = srv.updateProductById(5,22,".05 oz of Misery and Failure", "Fail", 0.99, "imgs/failure.png", "Failure is misery. Made from rancid dreams. Dreams die.");
//        System.out.println(msg);
//        //Product product = srv.getProductById(1);
//        //System.out.println(product);
//        products = srv.getProductList();
//        System.out.println(products);
//    }
}
