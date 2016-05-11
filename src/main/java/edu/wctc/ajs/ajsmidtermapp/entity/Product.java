package edu.wctc.ajs.ajsmidtermapp.entity;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class for Product objects
 * @author Alyson
 * @version 1.1
 */
@Entity
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId"),
    @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"),
    @NamedQuery(name = "Product.findByProductType", query = "SELECT p FROM Product p WHERE p.productType = :productType"),
    @NamedQuery(name = "Product.findByProductPrice", query = "SELECT p FROM Product p WHERE p.productPrice = :productPrice"),
    @NamedQuery(name = "Product.findByProductImage", query = "SELECT p FROM Product p WHERE p.productImage = :productImage"),
    @NamedQuery(name = "Product.findByProductDescription", query = "SELECT p FROM Product p WHERE p.productDescription = :productDescription")})
public class Product implements Serializable {

    @OneToMany(mappedBy = "productId")
    private Collection<ShoppingCart> shoppingCartCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "product_name")
    private String productName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "product_type")
    private String productType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_price")
    private double productPrice;
    @Size(max = 160)
    @Column(name = "product_image")
    private String productImage;
    @Size(max = 400)
    @Column(name = "product_description")
    private String productDescription;

    /**
     * Empty Constructor
     */
    public Product() {
    }

    /**
     * Constructor passing productId
     * @param productId Product's Id number.
     */
    public Product(Integer productId) {
        this.productId = productId;
    }

    /**
     * Constructor passing in a productId, name, type and price.
     * @param productId Id of the product
     * @param productName Name of the product
     * @param productType Category type of the product
     * @param productPrice Price of the product
     */
    public Product(Integer productId, String productName, String productType, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
    }

    /**
     * Gets the products Id.
     * @return The products Id.
     */
    public final Integer getProductId() {
        return productId;
    }

    /**
     * Sets the products Id.
     * @param productId products Id number.
     */
    public final void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets the product Id.
     * @return the products Id number.
     */
    public final String getProductName() {
        return productName;
    }

    /**
     * Sets the products name.
     * @param productName the products name.
     */
    public final void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the products Type/category.
     * @return the products category/type.
     */
    public final String getProductType() {
        return productType;
    }

    /**
     * Sets the products type/category.
     * @param productType the products type/category
     */
    public final void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * Gets the products price.
     * @return the products price.
     */
    public final double getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the products price.
     * @param productPrice products price.
     */
    public final void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Gets the products Image URL.
     * @return the products image url.
     */
    public final String getProductImage() {
        return productImage;
    }

    /**
     * Sets the products Image URL.
     * @param productImage the products image url.
     */
    public final void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     * Gets the products description.
     * @return the products description.
     */
    public final String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the products description.
     * @param productDescription the products description.
     */
    public final void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.ajs.ajsmidtermapp.model.Product[ productId=" + productId + " ]";
    }

    @XmlTransient
    public Collection<ShoppingCart> getShoppingCartCollection() {
        return shoppingCartCollection;
    }

    public void setShoppingCartCollection(Collection<ShoppingCart> shoppingCartCollection) {
        this.shoppingCartCollection = shoppingCartCollection;
    }
    
}
