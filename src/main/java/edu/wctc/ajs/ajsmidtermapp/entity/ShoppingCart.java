package edu.wctc.ajs.ajsmidtermapp.entity;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for a record of a shopping cart item.
 * @author Alyson
 * @version 1.1
 */
@Entity
@Table(name = "shopping_cart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShoppingCart.findAll", query = "SELECT s FROM ShoppingCart s"),
    @NamedQuery(name = "ShoppingCart.findByCartId", query = "SELECT s FROM ShoppingCart s WHERE s.cartId = :cartId"),
    @NamedQuery(name = "ShoppingCart.findByUser", query = "SELECT s FROM ShoppingCart s WHERE s.username = :username")})
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cart_id")
    private Integer cartId;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private Product productId;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(fetch=FetchType.EAGER)
    private User username;

    /**
     * Empty Constructor
     */
    public ShoppingCart() {
    }

    /**
     * Constructor passing in cartId.
     * @param cartId Carts Id number.
     */
    public ShoppingCart(Integer cartId) {
        this.cartId = cartId;
    }

    /**
     * Gets the carts Id number.
     * @return carts Id number.
     */
    public final Integer getCartId() {
        return cartId;
    }

    /**
     * Sets the carts Id number.
     * @param cartId id of the shopping cart record to find.
     */
    public final void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    /**
     * Gets the product based on the productId.
     * @return Product 
     */
    public final Product getProductId() {
        return productId;
    }

    /**
     * Sets the product based on the productId
     * @param productId Product
     */
    public final void setProductId(Product productId) {
        this.productId = productId;
    }

    /**
     * Gets the user object based on the username
     * @return user object.
     */
    public final User getUsername() {
        return username;
    }

    /**
     * Sets the user object based on the username
     * @param username User object
     */
    public final void setUsername(User username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartId != null ? cartId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart other = (ShoppingCart) object;
        if ((this.cartId == null && other.cartId != null) || (this.cartId != null && !this.cartId.equals(other.cartId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ajs.ajsmidtermapp.model.ShoppingCart[ cartId=" + cartId + " ]";
    }
    
}
