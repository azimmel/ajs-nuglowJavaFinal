package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.repository.AuthoritiesRepository;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Authorities using the AuthoritiesRepository.
 * Full C.R.U.D. functionality within this class is available.
 * To save a record use the .edit() method.
 * @author Alyson
 * @version 1.1
 */
@Repository
@Transactional(readOnly = true)
public class AuthoritiesService {
    private transient final Logger LOG = LoggerFactory.getLogger(AuthoritiesService.class);

    @Inject
    private AuthoritiesRepository authoritiesRepo;

    public AuthoritiesService() {
    }

    public final List<Authorities> findAll() {
        return authoritiesRepo.findAll();
    }

    public final Authorities findById(String id) {
        if(id.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Authorities authority = (Authorities) authoritiesRepo.findOne(new Integer(id));
        return authority;
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param authorities
     */
    @Transactional
    public final void remove(Authorities authorities) {
        if(authorities == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LOG.debug("Deleting author: " + authorities.getUsername());
        authoritiesRepo.delete(authorities);
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param authority
     * @return
     */
    @Transactional
    public final Authorities edit(Authorities authority) {
        if(authority == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                java.util.logging.Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return authoritiesRepo.saveAndFlush(authority);
    }
}
