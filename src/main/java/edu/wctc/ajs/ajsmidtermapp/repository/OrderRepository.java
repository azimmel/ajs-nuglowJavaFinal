/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.Order;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Alyson
 */
public interface OrderRepository extends JpaRepository<Order, Integer>, Serializable{
    @Query("SELECT o FROM Order o WHERE o.username = (:username)")
    public List findAllWithUsername(@Param("username") Object username);
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN (:startDate) AND (:endDate)")
    public List findAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
