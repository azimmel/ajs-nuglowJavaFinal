package edu.wctc.ajs.ajsmidtermapp.entity;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for the Order object/ record of an order.
 * @author Alyson
 * @version 1.1
 */
@Entity
@Table(name = "orders")
@XmlRootElement
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id")
    private Integer orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "items_ordered")
    private String itemsOrdered;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal")
    private double subtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private double total;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "billing_address")
    private String billingAddress;

    /**
     * Empty Order Constructor
     */
    public Order() {
    }

    /**
     * Constructor that passes in the order id.
     * @param orderId The id of the order.
     */
    public Order(Integer orderId) {
        if(orderId == null || orderId == 0){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.orderId = orderId;
    }

    /**
     * Constructor that passes in all of the order properties.
     * @param orderId The orders id.
     * @param orderDate The date of the order.
     * @param username The username for the order placed.
     * @param shippingAddress The shipping address for the user
     * @param itemsOrdered Items ordered 
     * @param subtotal Subtotal of the order.
     * @param total Total of the order.
     * @param billingAddress Billing address for the user.
     */
    public Order(Integer orderId, Date orderDate, String username, String shippingAddress, String itemsOrdered, double subtotal, double total, String billingAddress) {
        if(orderId == null || orderId == 0 || orderDate == null || username.isEmpty() || shippingAddress.isEmpty() || subtotal == 0 
                || total == 0 || billingAddress.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.username = username;
        this.shippingAddress = shippingAddress;
        this.itemsOrdered = itemsOrdered;
        this.subtotal = subtotal;
        this.total = total;
        this.billingAddress = billingAddress;
    }

    /**
     * Get the orders Id.
     * @return the orders Id.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Sets the orders Id.
     * @param orderId the orders Id.
     */
    public void setOrderId(Integer orderId) {
        if(orderId == null || orderId == 0){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.orderId = orderId;
    }

    /**
     * Get the date the order was placed.
     * @return the date the order was placed.
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date the order was placed.
     * @param orderDate the date the order was placed.
     */
    public void setOrderDate(Date orderDate) {
        if(orderDate == null){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.orderDate = orderDate;
    }

    /**
     * Gets the users username.
     * @return users username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the users username.
     * @param username users username.
     */
    public void setUsername(String username) {
        if(username.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.username = username;
    }

    /**
     * Gets the shipping address for the users order.
     * @return the shipping address for the users order.
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address for the users order.
     * @param shippingAddress the shipping address for the users order.
     */
    public void setShippingAddress(String shippingAddress) {
        if(shippingAddress.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.shippingAddress = shippingAddress;
    }

    /**
     * Gets the items ordered.
     * @return items that have been ordered.
     */
    public String getItemsOrdered() {
        return itemsOrdered;
    }

    /**
     * Sets the items ordered.
     * @param itemsOrdered items that have been ordered.
     */
    public void setItemsOrdered(String itemsOrdered) {
        if(itemsOrdered.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.itemsOrdered = itemsOrdered;
    }

    /**
     * Gets the subtotal for the order.
     * @return The subtotal for the order.
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Sets the subtotal for the order.
     * @param subtotal The subtotal for the order.
     */
    public void setSubtotal(double subtotal) {
        if(subtotal == 0){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.subtotal = subtotal;
    }

    /**
     * Gets the total for the order.
     * @return The total for the order.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total for the order.
     * @param total the total for the order.
     */
    public void setTotal(double total) {
        if(total == 0){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.total = total;
    }

    /**
     * Gets the billing address for the users order.
     * @return the billing address for the order.
     */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address for the users order.
     * @param billingAddress the billing address for the order.
     */
    public void setBillingAddress(String billingAddress) {
        if(billingAddress.isEmpty()){
            try {
                throw new DataAccessException();
            } catch (DataAccessException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.billingAddress = billingAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ajs.jpaentityconverter.entity.Orders[ orderId=" + orderId + " ]";
    }
    
}
