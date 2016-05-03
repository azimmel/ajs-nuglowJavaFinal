package edu.wctc.ajs.ajsmidtermapp.model;

import edu.wctc.ajs.ajsmidtermapp.exception.IllegalUrlReferenceException;
import edu.wctc.ajs.ajsmidtermapp.exception.NumberOutOfRangeException;
import edu.wctc.ajs.ajsmidtermapp.exception.NullOrEmptyArgumentException;

/**
 * Product class that gets and sets all product properties. TODO: Check for null
 * or void params add javadocs.
 *
 * @author Alyson
 */

public final class Product {

    private static final String IMG_REF_FOLDER = "imgs/";
    private int productId;
    private String productName;
    private String productType;
    private double productPrice;
    private String productUrlRef;
    private String productDescription;

    /**
     * Product Empty Constructor for initializing this class.
     */
    public Product() {
    }

    /**
     * Product Constructor passing in the product properties and intializing
     * them.
     *
     * @param productId Unique ID of the product in the database.
     * @param productName Name of the product.
     * @param productType Category Type of the product.
     * @param productPrice Price of the product.
     * @param productUrlRef URL reference of image storage location for product.
     * @param productDescription Description of the product.
     */
    public Product(int productId, String productName, String productType, double productPrice, String productUrlRef, String productDescription) {
        if (productId <= 0 || productName.isEmpty()) {
            throw new IllegalArgumentException();
        }
        setProductId(productId);
        setProductName(productName);
        setProductType(productType);
        setProductPrice(productPrice);
        setProductUrlRef(productUrlRef);
        setProductDescription(productDescription);
    }

    /**
     * Gets the product Id and returns it.
     *
     * @return product Id
     */
    public final int getProductId() {
        return productId;
    }

    /**
     * Sets the product Id.
     *
     * @param productId Unique Id of the product.
     */
    public final void setProductId(int productId) {
        if (productId <= 0) {
            throw new NumberOutOfRangeException();
        }
        this.productId = productId;
    }

    /**
     * Gets the name of the product.
     *
     * @return Name of the product.
     */
    public final String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName Name of the product.
     */
    public final void setProductName(String productName) {
        if (productName.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.productName = productName;
    }

    /**
     * Gets the product type. Type is the category identification of the
     * product.
     *
     * @return Product category/type.
     */
    public final String getProductType() {
        return productType;
    }

    /**
     * sets the product type. Type is the category identification of the
     * product.
     *
     * @param productType Category/Type of the product.
     */
    public final void setProductType(String productType) {
        if (productType == null || productType.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        this.productType = productType;
    }

    /**
     * Gets the price of the product.
     *
     * @return Price of the product.
     */
    public final double getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the price of the product.
     *
     * @param productPrice Price of the product.
     */
    public final void setProductPrice(double productPrice) {
        if (productPrice <= 0) {
            throw new NullOrEmptyArgumentException();
        }
        this.productPrice = productPrice;
    }

    /**
     * Gets the image url reference for the product. The image URL reference is
     * "according the the midterm directions" located within an imgs folder. URL
     * reference will be in the following format: imgs/[imgName].[typeOfImg
     * ex:jpg, png, etc] ex: imgs/hope.jpg
     *
     * @return The URL Reference of the image for the product is returned.
     */
    public final String getProductUrlRef() {
        return productUrlRef;
    }

    /**
     * Set the products image url reference. This url must be a relative url
     * located within the web pages folder inside and imgs folder. The name of
     * the folder that the images are located must be called "imgs" otherwise an
     * exception will occur.
     *
     * @param productUrlRef
     */
    public final void setProductUrlRef(String productUrlRef) {
        if (productUrlRef == null || productUrlRef.isEmpty()) {
            throw new NullOrEmptyArgumentException();
        }
        String refFolder = productUrlRef.substring(0, 5);
        if (!refFolder.equals(IMG_REF_FOLDER)) {
            throw new IllegalUrlReferenceException();
        }
        this.productUrlRef = productUrlRef;
    }

    /**
     *
     * @return
     */
    public final String getProductDescription() {
        return productDescription;
    }

    public final void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.productId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName="
                + productName + ", productType=" + productType + ", productPrice="
                + productPrice + ", productUrlRef=" + productUrlRef + ", "
                + "productDescription=" + productDescription + '}';
    }

    //TESTING
//    public static void main(String[] args) {
//        Product product = new Product(1, "12oz. Bottled Hope", "Hope", 49.50, "imgs/hope.jpg", "The Original 12 oz Bottle of Hope!");
//        System.out.println(product.toString());
//        product.setProductUrlRef("imgs/hope.jpg");
//    }

}
