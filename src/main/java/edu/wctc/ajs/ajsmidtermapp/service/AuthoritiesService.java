package edu.wctc.ajs.ajsmidtermapp.service;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import edu.wctc.ajs.ajsmidtermapp.repository.AuthoritiesRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alyson
 */
@Repository
@Transactional(readOnly = true)
public class AuthoritiesService {
    private transient final Logger LOG = LoggerFactory.getLogger(AuthoritiesService.class);

    @Inject
    private AuthoritiesRepository authoritiesRepo;

    public AuthoritiesService() {
    }

    public List<Authorities> findAll() {
        return authoritiesRepo.findAll();
    }

    public Authorities findById(String id) {
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
    public void remove(Authorities authorities) {
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
    public Authorities edit(Authorities authority) {
        return authoritiesRepo.saveAndFlush(authority);
    }
}
