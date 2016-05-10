/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.service.AuthoritiesService;
import edu.wctc.ajs.ajsmidtermapp.service.EmailService;
import edu.wctc.ajs.ajsmidtermapp.service.OrderService;
import edu.wctc.ajs.ajsmidtermapp.service.ProductService;
import edu.wctc.ajs.ajsmidtermapp.service.ShoppingCartService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Alyson
 */
@WebServlet(name = "RegistrationController", urlPatterns = {"/Register"})
public class RegistrationController extends HttpServlet {

    public static final String REGISTER = "register";
    public static final String ACTION = "action";
    public static final String LOGIN = "/login.jsp";
    public static final String REGISTRATION_PAGE = "/registration.jsp";
    private static final String MSG = "msg";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String EMAIL_ERROR_MSG = "Sorry, the verification email could not be "
            + "sent. Please notify the webmaster at "
            + "webmaster@gmail.com and we'll complete the "
            + "process for you. Thanks for your patience.";

    private UserService userService;
    private AuthoritiesService authService;
    private EmailService emailService;

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
        String action = request.getParameter(ACTION);
        String pageDestination = REGISTRATION_PAGE;
        String errorMessage;
        try {
            switch (action) {
                case REGISTER:
                    String username = request.getParameter(USERNAME);
                    String password = request.getParameter(PASSWORD);
                    try {
                        saveNewRegistration(username, password);
                    } catch (Exception e) {
                        pageDestination = REGISTRATION_PAGE;
                    }
                    pageDestination = LOGIN;

                    break;
                default:
                    pageDestination = REGISTRATION_PAGE;
                    break;
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
            request.setAttribute(MSG, errorMessage);
            pageDestination = REGISTRATION_PAGE;
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(pageDestination));
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            errorMessage = ex.getMessage();
            request.setAttribute(MSG, errorMessage);
        }

    }

    public void saveNewRegistration(String userName, String password) throws Exception {
        // retrieve userName and password from registration form then save new account:
        User user = new User();
        user.setUsername(userName);
        user.setPassword(encodeSha512(password, userName));
        user.setEnabled(false); // don't want enabled until email verified!;

        List<Authorities> auths = new ArrayList<>();
        Authorities auth = new Authorities();
        auth.setAuthority(ROLE_USER);
        auth.setUsername(user.getUsername());

        userService.edit(user); // you need a UserService
        authService.edit(auth);
        try {
            try {
                emailService.sendEmail(user.getUsername());
            } catch (Exception ex) {
                throw new Exception(ex.getMessage() + ", " + ex.getCause());
            }

        } catch (MailException ex) {
            throw new RuntimeException(EMAIL_ERROR_MSG);
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
        userService = (UserService) ctx.getBean("userService");
        authService = (AuthoritiesService) ctx.getBean("authoritiesService");
        emailService = (EmailService) ctx.getBean("emailService");
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

    /*
        * Helper method that creates a salted SHA-512 hash composed of password (pwd) and 
        * salt (username).
     */
    private String encodeSha512(String pwd, String salt) {
        ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
        pe.setIterations(1024);
        String hash = pe.encodePassword(pwd, salt);
        return hash;
    }
}
