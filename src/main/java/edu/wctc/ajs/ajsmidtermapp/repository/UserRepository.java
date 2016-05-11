package edu.wctc.ajs.ajsmidtermapp.repository;

import edu.wctc.ajs.ajsmidtermapp.entity.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * User repository for the UserService.
 * This class has a custom query for finding a record by the username. This should
 * be used instead of find by id.
 * @author Alyson
 * @version 1.1
 */
public interface UserRepository extends JpaRepository<User, Integer>, Serializable{
    @Query("SELECT u FROM User u WHERE u.username = (:username)")
    public Object findOneByUsername(@Param("username") String username);
}
