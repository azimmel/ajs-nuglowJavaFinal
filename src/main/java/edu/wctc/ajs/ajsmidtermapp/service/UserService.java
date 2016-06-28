package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service using the UserRepository.
 * @author Alyson
 * @version 1.1
 */
@Repository
@Transactional(readOnly = true)
public class UserService {

    private transient final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepo;

    /**
     * Empty controller.
     */
    public UserService() {
    }

    /**
     * Finds all user records in the database.
     * @return a list of users.
     */
    public List<User> findAll() {
        return userRepo.findAll();
    }

    /**
     * Finds the user by id.
     * @param id Users id.- DO NOT USE THIS
     * @return The user.
     */
    public User findById(String id) {
        User user = (User) userRepo.findOneByUsername(id);
        return user;
    }

    /**
     * Finds the user by it's username.
     * Use this class instead of of the find by id. 
     * @param username Users username.
     * @return User that was found by the username.
     */
    public User findOneByUsername(String username) {
        return (User) userRepo.findOneByUsername(username);
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param user
     */
    @Transactional
    public void remove(User user) {
        LOG.debug("Deleting author: " + user.getUsername());
        userRepo.delete(user);
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param user
     * @return
     */
    @Transactional
    public User edit(User user) {
        return userRepo.saveAndFlush(user);
    }
}
