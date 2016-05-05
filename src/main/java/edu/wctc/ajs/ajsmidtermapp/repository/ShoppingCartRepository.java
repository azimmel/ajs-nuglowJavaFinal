package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alyson
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>, Serializable {
    
}
