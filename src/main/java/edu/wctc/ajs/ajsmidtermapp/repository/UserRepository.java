package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.User;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Alyson
 */
public interface UserRepository extends JpaRepository<User, Integer>, Serializable{
    @Query("SELECT u FROM User u WHERE u.username = (:username)")
    public Object findOneByUsername(@Param("username") String username);
}
