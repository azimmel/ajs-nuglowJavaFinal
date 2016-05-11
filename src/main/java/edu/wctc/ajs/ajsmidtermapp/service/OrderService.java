package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Order;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.repository.OrderRepository;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Order service class using the OrderRepository.
 * 
 * @author Alyson
 * @version 1.1
 */
@Repository
@Transactional(readOnly = true)
public class OrderService {
     private transient final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private OrderRepository orderRepo;

    /**
     * Empty constructor for the orderService.
     */
    public OrderService() {
    }

    /**
     * Find All method finds all records
     * @return A list of Orders.
     */
    public List<Order> findAll() {
        return orderRepo.findAll();
    }
    
    /**
     * Find an order by it's Id. 
     * 
     * @param id Id for the order.
     * @return The order from the Id.
     */
    public Order findById(String id) {
        if(id.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orderRepo.findOne(new Integer(id));
    }

    /**
     * Finds the user by the username.
     * Using the users username you can find an order.
     * @param username users username.
     * @return List of orders with the users username,
     */
    public List<Order> findByUser(String username){
        if(username.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orderRepo.findAllWithUsername(username);
    }
    
    /**
     * Find all orders between two dates.
     * @param startDate the date the start looking for. The start date starts at the
     * end of the day.
     * @param endDate the date to end the looks for. The ends date ends at the end of 
     * that date.
     * @return a list of Orders between those dates
     */
    public List<Order> findAllBetweenDates(Date startDate, Date endDate){
        if(startDate == null || endDate == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orderRepo.findAllBetweenDates(startDate, endDate);
    }
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param order 
     */
    @Transactional
    public void remove(Order order) {
        if(order == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LOG.debug("Deleting order: " + order.getOrderId());
        orderRepo.delete(order);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param order 
     * @return  
     */
    @Transactional
    public Order edit(Order order) {
        if(order == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return orderRepo.saveAndFlush(order);
    }
}
