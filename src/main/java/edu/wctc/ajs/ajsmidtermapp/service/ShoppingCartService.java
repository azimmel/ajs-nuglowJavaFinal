package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.repository.ShoppingCartRepository;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Shopping cart service using the ShoppingCartRepository.
 * 
 * @author Alyson
 * @version 1.1
 */
@Repository
@Transactional(readOnly = true)
public class ShoppingCartService {
       private transient final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ShoppingCartRepository shopRepo;

    public ShoppingCartService() {
    }

    /**
     * Finds all records in the database.
     * @return a list of shoppingcarts
     */
    public List<ShoppingCart> findAll() {
        return shopRepo.findAll();
    }
    
    /**
     * Finds the shopping cart record by it's id.
     * @param id Id of the shopping cart.
     * @return a shopping cart record.
     */
    public ShoppingCart findById(int id) {
        if(id == 0){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Integer cartId = id;
        ShoppingCart cart = shopRepo.findOne(cartId);
        return cart;
    }

    /**
     * Finds a list of shopping Carts by the username.
     * @param username users username.
     * @return list of shopping cart records for that username.
     */
    public List<ShoppingCart> findByUser(User username){
        if(username == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return shopRepo.findAllWithUsername(username);
    }
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param item 
     */
    @Transactional
    public void remove(ShoppingCart item) {
        if(item == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        if(item == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return shopRepo.saveAndFlush(item);
    }
}
