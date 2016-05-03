package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Alyson
 */
public interface ProductDaoStrategy {

    public abstract List<Product> getProductList() throws DataAccessException;
    
    public abstract Product getProductById(Object id) throws DataAccessException;
    
    public abstract int deleteProductById(Object productId) throws DataAccessException;
    
    public abstract int createNewProduct(Product product)throws DataAccessException;
    
    public abstract int updateProductById(Object currProductId, Object productId,
            Object productName, Object productType, Object productPrice, 
            Object productUrlRef, Object productDescription)  throws DataAccessException;
    
    public abstract DBStrategy getDb();
    
    public abstract void setDb(DBStrategy db);
    
    public abstract void initDao(DataSource ds) throws DataAccessException;
    
    public abstract void initDao(String driver, String url, String user, String password);
    
    public abstract String getDriver();
    
    public abstract void setDriver(String driver);
    
    public abstract String getUrl();

    public abstract void setUrl(String url);

    public abstract String getUser();

    public abstract void setUser(String user);

    public abstract String getPwd();

    public abstract void setPwd(String pwd);
}
