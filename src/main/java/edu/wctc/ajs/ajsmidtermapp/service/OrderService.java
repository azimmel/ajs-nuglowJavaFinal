package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Order;
import edu.wctc.ajs.ajsmidtermapp.repository.OrderRepository;
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
public class OrderService {
     private transient final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private OrderRepository orderRepo;

    public OrderService() {
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
    

    public Order findById(String id) {
        return orderRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param order 
     */
    @Transactional
    public void remove(Order order) {
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
        return orderRepo.saveAndFlush(order);
    }
}
