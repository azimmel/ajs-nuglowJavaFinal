package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Shopping Cart Repository for the ShoppingCartService. 
 * This class has a custom query for locating by the username. 
 * @author Alyson
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>, Serializable {
    @Query("SELECT s FROM ShoppingCart s WHERE s.username = (:username)")
    public List findAllWithUsername(@Param("username") Object username);
}
