/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.service.ProductService;
import edu.wctc.ajs.ajsmidtermapp.service.ShoppingCartService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 *
 * @author Alyson
 */
@WebServlet(name = "ShopController", urlPatterns = {"/Shop"})
public class ShopController extends HttpServlet {

    private static final String RESULTS_PAGE = "/products.jsp";
    private static final String DETAILS_PAGE = "/productDescription.jsp";
    private static final String ERROR_PAGE = "/errorPage.jsp";
    private static final String SHOPPING_CART = "/shoppingCart.jsp";
    private static final String ERR = "data cannot be found";
    private static final String SUBMIT = "submit";
    private static final String ACTION = "action";
    private static final String LIST = "list";
    private static final String DETAILS = "details";
    private static final String ADD = "add";
    private static final String DELETE = "Delete";
    private static final String BACK = "Back";
    private static final String DATE = "date";
    private static final String MSG = "msg";
    private static final String TOTAL_ITEMS = "totalItems";

    private static final String DEFAULT_ERROR = "Error: Huston we have a problem. Something has gone terribly wrong.";

    private static final String ID = "productId";
    private static final String PRODUCT = "product";
    private static final String USER = "username";

    private ProductService productService;
    private ShoppingCartService cartService;
    private UserService userService;

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
        String pageDestination;
        Product product;
        ShoppingCart cart;
        User user;
        int cartItems;
        String cartItemsDisplay;

        try {
            try {
                request.setAttribute(DATE, getYearDate());
            } catch (Exception ex) {
                errorMessage = ex.getMessage();
                request.setAttribute("msg", errorMessage);
            }
            switch (action) {
                case LIST:
                    this.getProductList(request, productService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case DETAILS:
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
                    String username = request.getParameter(USER);
                    if (id == 0) {
                        request.setAttribute(MSG, DEFAULT_ERROR);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    if (username == null) {
                        //TESTING
                        username = "testuser@isp.com";
                    }
                    product = new Product();
                    product = productService.findById(prodId);
                    user = new User();
                    user = userService.findById(username);
                    cart = new ShoppingCart();
                    cart.setProductId(product);
                    cart.setUsername(user);
                    cartService.edit(cart);
                    cartItems = getCartItemNumber(username);
                    if (cartItems > 0) {
                        cartItemsDisplay = cartItems + "";
                    } else {
                        cartItemsDisplay = "";
                    }
                    request.setAttribute(TOTAL_ITEMS, cartItemsDisplay);
                    pageDestination = RESULTS_PAGE;
                    break;
                default:
                    pageDestination = ERROR_PAGE;
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
            request.setAttribute("msg", errorMessage);
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
        request.setAttribute("productList", products);
    }

    private int getCartItemNumber(String username) {
        int cartItems;
        List<ShoppingCart> userCart = cartService.findByUser(username);
        cartItems = 0;
        for (ShoppingCart s : userCart) {
            cartItems++;
        }
        return cartItems;
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

    }
}
