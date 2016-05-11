package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductRepository is a repository for the ProductService
 * @author Alyson
 */
public interface ProductRepository extends JpaRepository<Product, Integer>, Serializable{
    
}
