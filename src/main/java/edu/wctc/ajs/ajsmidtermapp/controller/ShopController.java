package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.entity.Order;
import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.service.OrderService;
import edu.wctc.ajs.ajsmidtermapp.service.ProductService;
import edu.wctc.ajs.ajsmidtermapp.service.ShoppingCartService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import edu.wctc.ajs.ajsmidtermapp.util.SpringSecurityCurrentUserInformationHandler;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Shop controller / servlet controlling all pages having to do with the Nuglow
 * shop, shopping cart and ordering process.
 * @author Alyson
 */
@WebServlet(name = "ShopController", urlPatterns = {"/Shop"})
public class ShopController extends HttpServlet {

    private static final String RESULTS_PAGE = "/products.jsp";
    private static final String DETAILS_PAGE = "/productDescription.jsp";
    private static final String ERROR_PAGE = "/errorPage.jsp";
    private static final String SHOPPING_CART = "/shoppingCart.jsp";
    private static final String CHECKOUT_PAGE = "/checkout.jsp";
    private static final String LOGIN = "/login.jsp";
    private static final String ORDER_PROCESSED_PAGE = "/orderConfirmationPage.jsp";
    private static final String ERR = "data cannot be found";
    private static final String SUBMIT = "submit";
    private static final String ACTION = "action";
    private static final String LIST = "list";
    private static final String DETAILS = "details";
    private static final String ADD = "add";
    private static final String CART = "shoppingcart";
    private static final String CHECKOUT = "checkout";
    private static final String PAY = "pay";
    private static final String DELETE = "delete";
    private static final String BACK = "Back";
    private static final String DATE = "date";
    private static final String MSG = "msg";
    private static final String TOTAL_ITEMS = "totalItems";

    private static final String DEFAULT_ERROR = "Error: Huston we have a problem. Something has gone terribly wrong.";

    private static final String ID = "productId";
    private static final String CART_ID = "cartId";
    private static final String PRODUCT = "product";
    private static final String USER = "user";
    private static final String PRODUCT_LIST = "productList";
    private static final String SUBTOTAL = "subtotal";
    private static final String TAX = "tax";
    private static final String TOTAL = "total";
    private static final String USER_ITEMS = "userItems";
    private static final String CURRENCY = "#0.00";
    private static final String CHECKBOX = "checkbox";
    private static final String FIRST_NAME = "firstName";
    private static final String BILLING_FIRST_NAME = "bFirstName";
    private static final String LAST_NAME = "lastName";
    private static final String BILLING_LAST_NAME = "bLastName";
    private static final String ADDRESS1 = "address1";
    private static final String BILLING_ADDRESS1 = "bAddress1";
    private static final String ADDRESS2 = "address2";
    private static final String BILLING_ADDRESS2 = "bAddress2";
    private static final String CITY = "city";
    private static final String BILLING_CITY = "bCity";
    private static final String STATE = "state";
    private static final String BILLING_STATE = "bState";
    private static final String ZIP = "zip";
    private static final String BILLING_ZIP = "bZip";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String CARD_NAME = "cardName";
    private static final String EXP_MONTH = "expMonth";
    private static final String EXP_YEAR = "expYear";
    private static final String CVV = "cvv";
    private static final String NAME = "name";

    private ProductService productService;
    private ShoppingCartService cartService;
    private UserService userService;
    private OrderService orderService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String action = request.getParameter(ACTION);
        String errorMessage;
        String pageDestination = RESULTS_PAGE;
        Product product;
        ShoppingCart cart;
        User user;
        String username = "";

        try {
            try {
                request.setAttribute(DATE, getYearDate());
            } catch (Exception ex) {
                errorMessage = ex.getMessage();
                request.setAttribute(MSG, errorMessage);
            }
            switch (action) {
                case LIST:

                    getCart(request);
                    this.getProductList(request, productService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case DETAILS:
                    getCart(request);
                    String prodId = request.getParameter(ID);
                    int id = Integer.parseInt(prodId);
                    if (id == 0) {
                        request.setAttribute(MSG, DEFAULT_ERROR);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    product = new Product();
                    product = productService.findById(prodId);
                    request.setAttribute(PRODUCT, product);
                    pageDestination = DETAILS_PAGE;
                    break;
                case ADD:
                    prodId = request.getParameter(ID);
                    id = Integer.parseInt(prodId);
                    try {
                        username = getUsername();
                    } catch (Exception e) {
                        request.setAttribute(MSG, e.getCause());
                        this.getProductList(request, productService);
                        pageDestination = RESULTS_PAGE;
                        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
                        try {
                            view.forward(request, response);
                        } catch (ServletException | IOException ex) {
                            errorMessage = ex.getMessage();
                            request.setAttribute(MSG, errorMessage);
                        }
                    }
                    if (id == 0) {
                        request.setAttribute(MSG, DEFAULT_ERROR);
                        this.getProductList(request, productService);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    if (username == null) {
                        pageDestination = LOGIN;
                        break;
                    }
                    product = new Product();
                    product = productService.findById(prodId);
                    user = getUser(username);
                    cart = new ShoppingCart();
                    cart.setProductId(product);
                    cart.setUsername(user);
                    cartService.edit(cart);
                    getCart(request);
                    this.getProductList(request, productService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case CART:
                    createShoppingCartPage(request);
                    pageDestination = SHOPPING_CART;
                    break;
                case DELETE:
                    String cartId = request.getParameter(CART_ID);
                    id = Integer.parseInt(cartId);
                    cart = cartService.findById(id);
                    cartService.remove(cart);
                    createShoppingCartPage(request);
                    pageDestination = SHOPPING_CART;
                    break;
                case CHECKOUT:
                    createShoppingCartPage(request);
                    pageDestination = CHECKOUT_PAGE;
                    break;
                case PAY:
                    try {
                        username = getUsername();
                    } catch (Exception e) {
                        pageDestination = LOGIN;
                        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
                        try {
                            view.forward(request, response);
                        } catch (ServletException | IOException ex) {
                            errorMessage = ex.getMessage();
                            request.setAttribute(MSG, errorMessage);
                        }
                    }
                    String billingCheck = request.getParameter(CHECKBOX);
                    String name = "";
                    if ("0".equals(billingCheck)) {
                        //billing is the same as shipping
                        //order date
                        Order order = new Order();
                        order.setOrderDate(new Date());
                        //user
                        username = getUsername();
                        user = getUser(username);
                        order.setUsername(username);
                        //shipping address
                        String firstName = request.getParameter(FIRST_NAME);
                        String lastName = request.getParameter(LAST_NAME);
                        String address1 = request.getParameter(ADDRESS1);
                        String address2 = request.getParameter(ADDRESS2);
                        String city = request.getParameter(CITY);
                        String state = request.getParameter(STATE);
                        String zip = request.getParameter(ZIP);
                        String shippingAddress = firstName + " " + lastName + ", "
                                + address1 + " " + address2 + ", " + city
                                + " " + state + ", " + zip;
                        order.setShippingAddress(shippingAddress);
                        //users cart
                        List<ShoppingCart> userCart = cartService.findByUser(user);
                        String items = "";
                        for (ShoppingCart s : userCart) {
                            items += (":" + s.getProductId() + ":");
                        }
                        order.setItemsOrdered(items);
                        //subtotal
                        double subtotal = 0;
                        for (ShoppingCart sc : userCart) {
                            subtotal += sc.getProductId().getProductPrice();
                        }
                        order.setSubtotal(subtotal);
                        //total
                        double tax = subtotal * .02;
                        double total = subtotal + tax;
                        order.setTotal(total);
                        //billing address
                        order.setBillingAddress(shippingAddress);
                        //add to database
                        orderService.edit(order);
                        deleteAllCartItems();
                        name = firstName + " " + lastName;
                    } else {
                        //billing is different than shipping
                        //order date
                        Order order = new Order();
                        order.setOrderDate(new Date());
                        //user
                        username = getUsername();
                        user = getUser(username);
                        order.setUsername(username);
                        //shipping address
                        String firstName = request.getParameter(FIRST_NAME);
                        String lastName = request.getParameter(LAST_NAME);
                        String address1 = request.getParameter(ADDRESS1);
                        String address2 = request.getParameter(ADDRESS2);
                        String city = request.getParameter(CITY);
                        String state = request.getParameter(STATE);
                        String zip = request.getParameter(ZIP);
                        String shippingAddress = firstName + " " + lastName + ", "
                                + address1 + " " + address2 + ", " + city
                                + " " + state + ", " + zip;
                        order.setShippingAddress(shippingAddress);
                        //users cart
                        List<ShoppingCart> userCart = cartService.findByUser(user);
                        String items = "";
                        for (ShoppingCart s : userCart) {
                            items += (":" + s.getProductId() + ":");
                        }
                        order.setItemsOrdered(items);
                        //subtotal
                        double subtotal = 0;
                        for (ShoppingCart sc : userCart) {
                            subtotal += sc.getProductId().getProductPrice();
                        }
                        order.setSubtotal(subtotal);
                        //total
                        double tax = subtotal * .02;
                        double total = subtotal + tax;
                        order.setTotal(total);
                        //billing address
                        firstName = request.getParameter(BILLING_FIRST_NAME);
                        lastName = request.getParameter(BILLING_LAST_NAME);
                        address1 = request.getParameter(BILLING_ADDRESS1);
                        address2 = request.getParameter(BILLING_ADDRESS2);
                        city = request.getParameter(BILLING_CITY);
                        state = request.getParameter(BILLING_STATE);
                        zip = request.getParameter(BILLING_ZIP);
                        String billingAddress = firstName + " " + lastName + ", "
                                + address1 + " " + address2 + ", " + city
                                + " " + state + ", " + zip;
                        order.setBillingAddress(billingAddress);
                        deleteAllCartItems();
                        name = firstName + " " + lastName;
                    }

                    request.setAttribute(NAME, name);
                    createShoppingCartPage(request);
                    pageDestination = ORDER_PROCESSED_PAGE;
                    break;
                default:
                    pageDestination = ERROR_PAGE;
                    break;
            }
        } catch (DataAccessException ex) {
            errorMessage = ex.getMessage();
            request.setAttribute(MSG, errorMessage);
            try {
                this.getProductList(request, productService);
                pageDestination = RESULTS_PAGE;
            } catch (DataAccessException ex1) {
                errorMessage = ex1.getMessage();
                request.setAttribute(MSG, errorMessage);
                pageDestination = ERROR_PAGE;
            }
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            errorMessage = ex.getMessage();
            request.setAttribute(MSG, errorMessage);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getYearDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        LocalDate localDate;
        String date;

        localDate = LocalDate.now();
        date = localDate.format(formatter);
        return date;

    }

    private void getProductList(HttpServletRequest request, ProductService ps) throws DataAccessException {
        List<Product> products = ps.findAll();
        request.setAttribute(PRODUCT_LIST, products);
    }

    private int getCartItemNumber(User username) {
        int cartItems;
        List<ShoppingCart> userCart = cartService.findByUser(username);
        cartItems = 0;
        for (ShoppingCart s : userCart) {
            cartItems++;
        }
        return cartItems;
    }

    private void getCart(HttpServletRequest request) {
        String username = "";
        try {
            username = getUsername();
        } catch (Exception e) {
            String cartItemsDisplay = "";
            request.setAttribute(TOTAL_ITEMS, cartItemsDisplay);
        }
        User user = getUser(username);
        int cartItems;
        String cartItemsDisplay;
        cartItems = getCartItemNumber(user);
        if (cartItems > 0) {
            cartItemsDisplay = cartItems + "";
        } else {
            cartItemsDisplay = "";
        }
        request.setAttribute(TOTAL_ITEMS, cartItemsDisplay);
    }

    private void deleteAllCartItems() {
        String username = getUsername();
        User user = getUser(username);
        List<ShoppingCart> userCart = cartService.findByUser(user);
        for (ShoppingCart s : userCart) {
            cartService.remove(s);
        }
    }

    public User getUser(String username) {
        User user = new User();
        user = userService.findById(username);
        return user;
    }

    public String getUsername() {
        SpringSecurityCurrentUserInformationHandler u;
        u = new SpringSecurityCurrentUserInformationHandler();
        String username = u.getUsername();//get logged in username
        return username;
    }

    public void createShoppingCartPage(HttpServletRequest request) {
        String username = getUsername();
        User user = getUser(username);
        List<ShoppingCart> userCart = cartService.findByUser(user);
        double subtotal = 0;
        for (ShoppingCart sc : userCart) {
            subtotal += sc.getProductId().getProductPrice();
        }
        NumberFormat formatter = new DecimalFormat(CURRENCY);
        String sub = formatter.format(subtotal);
        double tax = subtotal * .02;
        String formatTax = formatter.format(tax);
        double total = subtotal + tax;
        String formatTotal = formatter.format(total);
        request.setAttribute(SUBTOTAL, sub);
        request.setAttribute(TAX, formatTax);
        request.setAttribute(TOTAL, formatTotal);
        request.setAttribute(USER_ITEMS, userCart);
        getCart(request);

    }

    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        productService = (ProductService) ctx.getBean("productService");
        cartService = (ShoppingCartService) ctx.getBean("shoppingCartService");
        userService = (UserService) ctx.getBean("userService");
        orderService = (OrderService) ctx.getBean("orderService");
    }
}
