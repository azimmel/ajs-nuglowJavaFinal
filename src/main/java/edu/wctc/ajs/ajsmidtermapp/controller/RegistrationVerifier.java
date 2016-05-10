/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ajs.ajsmidtermapp.controller;

import edu.wctc.ajs.ajsmidtermapp.entity.User;
import edu.wctc.ajs.ajsmidtermapp.service.AuthoritiesService;
import edu.wctc.ajs.ajsmidtermapp.service.EmailService;
import edu.wctc.ajs.ajsmidtermapp.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Alyson
 */
@WebServlet(name = "RegistrationVerifier", urlPatterns = {"/Verify"})
public class RegistrationVerifier extends HttpServlet {

    UserService userService;
    
    EmailService emailService;
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
        String errMsg = "";
        String destination = "/registrationVerified.jsp";
        
        try {
            String id = request.getParameter("id");
            byte[] decoded = Base64.decode(id.getBytes());
            String username = new String(decoded);
            
            User user = userService.findOneByUsername(username);
            if(user == null) {
                throw new RuntimeException("Sorry, that user is not in our system");
            }
            user.setEnabled(true);
            user.setLastUpdate(new Date());
            userService.edit(user);
                        
        } catch(Exception dae) {
            errMsg = "VERIFICATION ERROR: " + dae.getLocalizedMessage();
            request.setAttribute("errMsg", errMsg);
            destination = "/verificationError.jsp";
        }
        
                    
        RequestDispatcher view =
                    getServletContext().getRequestDispatcher(destination);
                view.forward(request, response);     
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

        @Override
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        userService = (UserService) ctx.getBean("userService");
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

}
