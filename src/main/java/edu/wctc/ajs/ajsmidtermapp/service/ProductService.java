package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.repository.ProductRepository;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Product Service class using the Product repository. 
 * Product Service is fully CRUD enabled. 
 * @author Alyson
 * @version 1.1
 */
@Repository
@Transactional(readOnly = true)
public class ProductService {
   private transient final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ProductRepository productRepo;

    /**
     * Empty productService controller
     */
    public ProductService() {
    }

    /**
     * Finds all Products in the database.
     * @return a list of products. 
     */
    public List<Product> findAll() {
        return productRepo.findAll();
    }
    
    /**
     * Finds a product by it's Id.
     * @param id id of the product
     * @return List of products.
     */
    public Product findById(String id) {
        if(id.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param product 
     */
    @Transactional
    public void remove(Product product) {
        LOG.debug("Deleting author: " + product.getProductName());
        productRepo.delete(product);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param product 
     * @return  
     */
    @Transactional
    public Product edit(Product product) {
        if(product == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productRepo.saveAndFlush(product);
    }
}
