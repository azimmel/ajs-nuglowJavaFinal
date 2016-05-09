/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alyson
 */
@Repository
@Transactional(readOnly = true)
public class UserService {

    private transient final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepo;

    public UserService() {
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(String id) {
        User user = (User) userRepo.findOneByUsername(id);
        return user;
    }

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
