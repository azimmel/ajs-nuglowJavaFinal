package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.entity.Product;
import edu.wctc.ajs.ajsmidtermapp.entity.ShoppingCart;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.service.ProductService;
import edu.wctc.ajs.ajsmidtermapp.service.ShoppingCartService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import edu.wctc.ajs.ajsmidtermapp.util.SpringSecurityCurrentUserInformationHandler;
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
@WebServlet(name = "NuglowController", urlPatterns = {"/Nuglow"})
public class NuglowController extends HttpServlet {

    private static final String RESULTS_PAGE = "/adminProductResults.jsp";
    private static final String DETAILS_PAGE = "/adminProductDetail.jsp";
    private static final String ERROR_PAGE = "/errorPage.jsp";
    private static final String ADMIN_PREFERENCES = "/adminPreferences.jsp";
    private static final String ERR = "data cannot be found";
    private static final String SUBMIT = "submit";
    private static final String ACTION = "action";
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static final String DETAILS = "details";
    private static final String EDIT_DELETE = "editDelete";
    private static final String EDIT = "Save Edit";
    private static final String DELETE = "Delete";
    private static final String BACK = "Back";
    private static final String PREFERENCES = "preferences";
    private static final String UNICORN = "Change to Unicorn";
    private static final String NORMAL = "Change to SpaceLab";
    private static final String BACKGROUND_STYLE = "background";
    private static final String RECORDS = "recordsAffected";

    //Error Messages
    private static final String NO_RECORDS = "Error: Product was not updated to database.";
    private static final String RECORD_CONFIRMED = "Product list has been updated, change to database confirmed.";
    private static final String RECORDS_ERROR = "Error: Something has gone wrong, too many products have been affected.";
    private static final String DEFAULT_ERROR = "Error: Huston we have a problem. Something has gone terribly wrong.";
    private static final String UNICORN_BACKGROUND = "background-image: url('imgs/unicorn.png');";
    private static final String NORMAL_BACKGROUND = "";
    private static final String INVALID_INPUT = "Input is invalid, please try again.";
    private static final String IMG_SUBSTRING = "imgs/";
    private static final String TOTAL_ITEMS = "totalItems";

    //attributes
    private static final String MSG = "msg";
    private static final String NAME = "productName";
    private static final String TYPE = "type";
    private static final String PRICE = "price";
    private static final String IMG_URL = "imgUrl";
    private static final String DESCRIPTION = "description";
    private static final String ID = "productId";
    private static final String PRODUCT = "product";
    private static final String DATE = "date";

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName;

    private static int recordsAffectedInSession = 0;

    private ProductService productService;
    private ShoppingCartService cartService;
    private UserService userService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String action = request.getParameter(ACTION);
        String errorMessage;
        String msg;
        String pageDestination;
        Product product = null;

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
                    getCart(request);
                    pageDestination = RESULTS_PAGE;
                    break;
                case CREATE:
                    String newProduct = request.getParameter(NAME);
                    if (newProduct == null || newProduct.length() < 3) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String type = request.getParameter(TYPE);
                    if (type == null || type.length() < 3) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String price = request.getParameter(PRICE);
                    Double priceTest = Double.parseDouble(price);
                    if (price == null || priceTest == 0 || priceTest < 0.01 || priceTest > 10000) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String imgUrl = request.getParameter(IMG_URL);
                    String imgUrlSubString = imgUrl.substring(0, 5);
                    if (imgUrl == null || !imgUrlSubString.equals(IMG_SUBSTRING) || imgUrl.length() < 9) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String description = request.getParameter(DESCRIPTION);
                    if (description == null || description.length() < 10) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    product = new Product();
                    product.setProductName(newProduct);
                    product.setProductType(type);
                    product.setProductPrice(Double.parseDouble(price));
                    product.setProductImage(imgUrl);
                    product.setProductDescription(description);
                    productService.edit(product);
                    recordsAffectedInSession++;
                    String rc = "" + recordsAffectedInSession;
                    session.setAttribute(RECORDS, rc);
                    this.getProductList(request, productService);
                    getCart(request);
                    pageDestination = RESULTS_PAGE;
                    break;
                case DETAILS:
                    String prodId = request.getParameter(ID);
                    int id = Integer.parseInt(prodId);
                    if (id == 0) {
                        request.setAttribute(MSG, DEFAULT_ERROR);
                        getCart(request);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    product = new Product();
                    product = productService.findById(prodId);
                    request.setAttribute(PRODUCT, product);
                    getCart(request);
                    pageDestination = DETAILS_PAGE;
                    break;
                case EDIT_DELETE:
                    String subaction = request.getParameter(SUBMIT);
                    switch (subaction) {
                        case EDIT:
                            String currentProductId = request.getParameter(ID);
                            String newProductName = request.getParameter(NAME);
                            String newType = request.getParameter(TYPE);
                            String newPrice = request.getParameter(PRICE);
                            Double priceTestEdit = Double.parseDouble(newPrice);
                            String newImgUrl = request.getParameter(IMG_URL);
                            String urlSubString = newImgUrl.substring(0, 5);
                            String newDescription = request.getParameter(DESCRIPTION);
                            if (newProductName == null || newProductName.length() < 3
                                    || newType == null || newType.length() < 3
                                    || newPrice == null || priceTestEdit <= 0.01
                                    || priceTestEdit > 10000 || newImgUrl == null
                                    || !urlSubString.equals(IMG_SUBSTRING) || newImgUrl.length() < 9
                                    || newDescription == null || newDescription.length() < 10) {
                                errorMessage = INVALID_INPUT;
                                request.setAttribute(MSG, errorMessage);
                                getCart(request);
                                pageDestination = DETAILS_PAGE;
                                break;
                            }
                            product = new Product();
                            product.setProductId(Integer.parseInt(currentProductId));
                            product.setProductName(newProductName);
                            product.setProductType(newType);
                            product.setProductPrice(Double.parseDouble(newPrice));
                            product.setProductImage(newImgUrl);
                            product.setProductDescription(newDescription);

                            productService.edit(product);

                            recordsAffectedInSession++;
                            rc = "" + recordsAffectedInSession;
                            session.setAttribute(RECORDS, rc);
                            this.getProductList(request, productService);
                            getCart(request);
                            pageDestination = RESULTS_PAGE;

                            break;
                        case DELETE:
                            currentProductId = request.getParameter(ID);
                            id = Integer.parseInt(currentProductId);
                            product = productService.findById(currentProductId);
                            productService.remove(product);

                            recordsAffectedInSession++;
                            rc = "" + recordsAffectedInSession;
                            session.setAttribute(RECORDS, rc);
                            this.getProductList(request, productService);
                            getCart(request);
                            pageDestination = RESULTS_PAGE;

                            break;
                        case BACK:
                            this.getProductList(request, productService);
                            getCart(request);
                            pageDestination = RESULTS_PAGE;
                            break;
                        default:
                            errorMessage = DEFAULT_ERROR;
                            request.setAttribute(MSG, errorMessage);
                            getCart(request);
                            pageDestination = DETAILS_PAGE;
                            break;
                    }
                    break;
                case PREFERENCES:
                    subaction = request.getParameter(SUBMIT);
                    switch (subaction) {
                        case UNICORN:
                            session.setAttribute(BACKGROUND_STYLE, UNICORN_BACKGROUND);
                            getCart(request);
                            pageDestination = ADMIN_PREFERENCES;
                            break;
                        case NORMAL:
                            session.setAttribute(BACKGROUND_STYLE, NORMAL_BACKGROUND);
                            getCart(request);
                            pageDestination = ADMIN_PREFERENCES;
                            break;
                        default:
                            pageDestination = ADMIN_PREFERENCES;
                            getCart(request);
                            break;
                    }
                    break;
                default:
                    errorMessage = DEFAULT_ERROR;
                    request.setAttribute(MSG, errorMessage);
                    this.getProductList(request, productService);
                    getCart(request);
                    pageDestination = RESULTS_PAGE;
                    break;
            }

        } catch (DataAccessException ex) {
            errorMessage = ex.getMessage();
            request.setAttribute(MSG, errorMessage);
            try {
                this.getProductList(request, productService);
                getCart(request);
                pageDestination = RESULTS_PAGE;
            } catch (DataAccessException ex1) {
                errorMessage = ex1.getMessage();
                request.setAttribute(MSG, errorMessage);
                getCart(request);
                pageDestination = ERROR_PAGE;
            }
        }
        //pageDestination = RESULTS_PAGE;
//        } catch (Exception ex) {
//            errorMessage = errorMessage.concat(ex.getMessage());
//            request.setAttribute("msg", errorMessage);
//        }

        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            errorMessage = ex.getMessage();
            request.setAttribute("msg", errorMessage);
        }
    }

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
        try{
            username = getUsername();
        }catch(Exception e){
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

    public User getUser(String username) {
        User user = userService.findById(username);
        return user;
    }

    public String getUsername() {
        SpringSecurityCurrentUserInformationHandler u;
        u = new SpringSecurityCurrentUserInformationHandler();
        String username = u.getUsername();//get logged in username
        return username;
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
        userService = (UserService) ctx.getBean("userService");
        cartService = (ShoppingCartService) ctx.getBean("shoppingCartService");
    }

}
