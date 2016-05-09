/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.entity.Authorities;
import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.service.EmailService;
import edu.wctc.ajs.ajsmidtermapp.service.OrderService;
import edu.wctc.ajs.ajsmidtermapp.service.ProductService;
import edu.wctc.ajs.ajsmidtermapp.service.ShoppingCartService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

    private UserService userService;
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
        try (PrintWriter out = response.getWriter()) {
            
            
        }
    }
    public void saveNewRegistation(String userName, String password) {
        // retrieve userName and password from registration form then save new account:
        User user = new User();
        user.setUsername(userName);
        user.setPassword(encodeSha512(password, userName));
        user.setEnabled(false); // don't want enabled until email verified!;

        List<Authorities> auths = new ArrayList<>();
        Authorities auth = new Authorities();
        auth.setAuthority("ROLE_MEMBER"); // or, use any role you want
        auths.add(auth);
        auth.setUsername(user.getUsername());

        user = userService.edit(user); // you need a UserService

        try {
            // you need an email service class
            emailService.sendEmail(user.getUsername(), null);

        } catch (MailException ex) {
            throw new RuntimeException("Sorry, the verification email could not be "
                    + "sent. Please notify the webmaster at "
                    + "webmaster@gmail.com and we'll complete the "
                    + "process for you. Thanks for your patience.");
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
        userService = (UserService) ctx.getBean("userService");;
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
