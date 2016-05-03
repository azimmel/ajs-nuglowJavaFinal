/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.ejb;

import edu.wctc.ajs.ajsmidtermapp.model.Authorities;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alyson
 */
@Stateless
public class AuthoritiesFacade extends AbstractFacade<Authorities> {

    @PersistenceContext(unitName = "edu.wctc.ajs_ajsMidTermApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthoritiesFacade() {
        super(Authorities.class);
    }
    
}
