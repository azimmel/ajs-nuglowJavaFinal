package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Authorities Service.
 * @author Alyson
 * @version 1.1
 */
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer>, Serializable{
    
}
