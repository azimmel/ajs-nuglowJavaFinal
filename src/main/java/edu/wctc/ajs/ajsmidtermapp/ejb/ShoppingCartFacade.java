/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.ejb;

import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alyson
 */
@Stateless
public class ShoppingCartFacade extends AbstractFacade<ShoppingCart> {

    @PersistenceContext(unitName = "edu.wctc.ajs_ajsMidTermApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShoppingCartFacade() {
        super(ShoppingCart.class);
    }
    
}
