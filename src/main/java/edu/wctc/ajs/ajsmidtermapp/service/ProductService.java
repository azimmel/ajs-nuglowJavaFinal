package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import edu.wctc.ajs.ajsmidtermapp.repository.ProductRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alyson
 */
@Repository
@Transactional(readOnly = true)
public class ProductService {
   private transient final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ProductRepository productRepo;

    public ProductService() {
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }
    

    public Product findById(String id) {
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
        return productRepo.saveAndFlush(product);
    }
}
