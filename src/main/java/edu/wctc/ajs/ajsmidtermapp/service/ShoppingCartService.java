package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import edu.wctc.ajs.ajsmidtermapp.repository.ShoppingCartRepository;
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
public class ShoppingCartService {
       private transient final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ShoppingCartRepository shopRepo;

    public ShoppingCartService() {
    }

    public List<ShoppingCart> findAll() {
        return shopRepo.findAll();
    }
    

    public ShoppingCart findById(String id) {
        return shopRepo.findOne(new Integer(id));
    }

    public List<ShoppingCart> findByUser(String username){
        return shopRepo.findAllWithUsername(username);
    }
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param item 
     */
    @Transactional
    public void remove(ShoppingCart item) {
        LOG.debug("Deleting author: " + item.getProductId().getProductName());
        shopRepo.delete(item);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param item 
     * @return  
     */
    @Transactional
    public ShoppingCart edit(ShoppingCart item) {
        return shopRepo.saveAndFlush(item);
    }
}
