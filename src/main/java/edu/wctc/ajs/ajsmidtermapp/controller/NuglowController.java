package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.exception.DataAccessException;
import edu.wctc.ajs.ajsmidtermapp.model.Product;
import edu.wctc.ajs.ajsmidtermapp.model.ProductService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

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

    //attributes
    private static final String MSG = "msg";
    private static final String NAME = "productName";
    private static final String TYPE = "type";
    private static final String PRICE = "price";
    private static final String IMG_URL = "imgUrl";
    private static final String DESCRIPTION = "description";
    private static final String ID = "productId";
    private static final String PRODUCT = "product";
    private static final String CURRENT_PRODUCT_ID = "currProductId";
    private static final String DATE = "date";

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName;

    private static int recordsAffectedInSession = 0;

    @Inject
    private ProductService productService;

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

        try {
            try {
                configDbConnection();
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
                case CREATE:
                    String newProduct = request.getParameter(NAME);
                    if (newProduct == null || newProduct.length() < 3) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String type = request.getParameter(TYPE);
                    if (type == null || type.length() < 3) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String price = request.getParameter(PRICE);
                    Double priceTest = Double.parseDouble(price);
                    if (price == null || priceTest == 0 || priceTest < 0.01 || priceTest > 10000) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String imgUrl = request.getParameter(IMG_URL);
                    String imgUrlSubString = imgUrl.substring(0, 5);
                    if (imgUrl == null || !imgUrlSubString.equals(IMG_SUBSTRING) || imgUrl.length() < 9) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    String description = request.getParameter(DESCRIPTION);
                    if (description == null || description.length() < 10) {
                        errorMessage = INVALID_INPUT;
                        request.setAttribute(MSG, errorMessage);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }

                    //do some validation for null.
                    int recordsCreated = productService.createNewProduct(newProduct, type, price, imgUrl, description);
                    if (recordsCreated <= 0) {
                        errorMessage = NO_RECORDS;
                        request.setAttribute(MSG, errorMessage);
                    } else if (recordsCreated > 1) {
                        errorMessage = RECORDS_ERROR;
                        request.setAttribute(MSG, errorMessage);
                    } else {
                        msg = RECORD_CONFIRMED;
                        request.setAttribute(MSG, msg);
                    }
                    recordsAffectedInSession++;
                    String rc = "" + recordsAffectedInSession;
                    session.setAttribute(RECORDS, rc);
                    this.getProductList(request, productService);
                    pageDestination = RESULTS_PAGE;
                    break;
                case DETAILS:
                    String id = request.getParameter(ID);
                    if (id == null) {
                        request.setAttribute(MSG, DEFAULT_ERROR);
                        pageDestination = RESULTS_PAGE;
                        break;
                    }
                    Product product = productService.getProductById(id);
                    request.setAttribute(PRODUCT, product);
                    pageDestination = DETAILS_PAGE;
                    break;
                case EDIT_DELETE:
                    String subaction = request.getParameter(SUBMIT);
                    switch (subaction) {
                        case EDIT:
                            String currentProductId = request.getParameter(CURRENT_PRODUCT_ID);
                            String newProductId = request.getParameter(ID);
                            int prodIdTest = Integer.parseInt(newProductId);
                            String newProductName = request.getParameter(NAME);
                            String newType = request.getParameter(TYPE);
                            String newPrice = request.getParameter(PRICE);
                            Double priceTestEdit = Double.parseDouble(newPrice);
                            String newImgUrl = request.getParameter(IMG_URL);
                            String urlSubString = newImgUrl.substring(0, 5);
                            String newDescription = request.getParameter(DESCRIPTION);
                            if (newProductId == null || prodIdTest <= 0 || prodIdTest > 10000
                                    || newProductName == null || newProductName.length() < 3
                                    || newType == null || newType.length() < 3
                                    || newPrice == null || priceTestEdit <= 0.01
                                    || priceTestEdit > 10000 || newImgUrl == null
                                    || !urlSubString.equals(IMG_SUBSTRING) || newImgUrl.length() < 9
                                    || newDescription == null || newDescription.length() < 10) {
                                errorMessage = INVALID_INPUT;
                                request.setAttribute(MSG, errorMessage);
                                pageDestination = DETAILS_PAGE;
                                break;
                            }

                            int recordsUpdated = productService.updateProductById(currentProductId,
                                    newProductId, newProductName, newType, newPrice,
                                    newImgUrl, newDescription);
                            if (recordsUpdated == 0) {
                                errorMessage = NO_RECORDS;
                                request.setAttribute(MSG, errorMessage);
                                pageDestination = DETAILS_PAGE;
                            } else if (recordsUpdated > 1) {
                                errorMessage = RECORDS_ERROR;
                                request.setAttribute(MSG, errorMessage);
                                this.getProductList(request, productService);
                                pageDestination = RESULTS_PAGE;
                            } else if (recordsUpdated == 1) {
                                msg = RECORD_CONFIRMED;
                                request.setAttribute(MSG, msg);
                                recordsAffectedInSession++;
                                rc = "" + recordsAffectedInSession;
                                session.setAttribute(RECORDS, rc);
                                this.getProductList(request, productService);
                                pageDestination = RESULTS_PAGE;
                            } else {
                                errorMessage = DEFAULT_ERROR;
                                request.setAttribute(MSG, errorMessage);
                                pageDestination = DETAILS_PAGE;
                            }
                            break;
                        case DELETE:
                            currentProductId = request.getParameter(CURRENT_PRODUCT_ID);
                            recordsUpdated = productService.deleteProductById(currentProductId);
                            if (recordsUpdated == 0) {
                                errorMessage = NO_RECORDS;
                                request.setAttribute(MSG, errorMessage);
                                pageDestination = DETAILS_PAGE;
                            } else if (recordsUpdated > 1) {
                                errorMessage = RECORDS_ERROR;
                                request.setAttribute(MSG, errorMessage);
                                this.getProductList(request, productService);
                                pageDestination = RESULTS_PAGE;
                            } else if (recordsUpdated == 1) {
                                msg = RECORD_CONFIRMED;
                                request.setAttribute(MSG, msg);
                                recordsAffectedInSession++;
                                rc = "" + recordsAffectedInSession;
                                session.setAttribute(RECORDS, rc);
                                this.getProductList(request, productService);
                                pageDestination = RESULTS_PAGE;
                            } else {
                                errorMessage = DEFAULT_ERROR;
                                request.setAttribute(MSG, errorMessage);
                                pageDestination = DETAILS_PAGE;
                            }
                            break;
                        case BACK:
                            this.getProductList(request, productService);
                            pageDestination = RESULTS_PAGE;
                            break;
                        default:
                            errorMessage = DEFAULT_ERROR;
                            request.setAttribute(MSG, errorMessage);
                            pageDestination = DETAILS_PAGE;
                            break;
                    }
                    break;
                case PREFERENCES:
                    subaction = request.getParameter(SUBMIT);
                    switch (subaction) {
                        case UNICORN:
                            session.setAttribute(BACKGROUND_STYLE, UNICORN_BACKGROUND);
                            pageDestination = ADMIN_PREFERENCES;
                            break;
                        case NORMAL:
                            session.setAttribute(BACKGROUND_STYLE, NORMAL_BACKGROUND);
                            pageDestination = ADMIN_PREFERENCES;
                            break;
                        default:
                            pageDestination = ADMIN_PREFERENCES;
                            break;
                    }
                    break;
                default:
                    errorMessage = DEFAULT_ERROR;
                    request.setAttribute(MSG, errorMessage);
                    this.getProductList(request, productService);
                    pageDestination = RESULTS_PAGE;
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
        List<Product> products = ps.getProductList();
        request.setAttribute("productList", products);
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
        String errorMessage;
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            request.setAttribute("msg", errorMessage);
        }
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
        String errorMessage;
        try {
            configDbConnection();
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            request.setAttribute("msg", errorMessage);
        }
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            request.setAttribute("msg", errorMessage);
            RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(RESULTS_PAGE));
            try {
                view.forward(request, response);
            } catch (ServletException | IOException e) {
                errorMessage = e.getMessage();
                request.setAttribute("msg", errorMessage);
            }
        }

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

    private void configDbConnection() throws NamingException, Exception {
        if (dbJndiName == null) {
            productService.getDao().initDao(driverClass, url, userName, password);
        } else {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            productService.getDao().initDao(ds);
        }
    }

    /**
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
    }

}
