<%-- 
    Document   : orderConfirmationPage
    Created on : May 7, 2016, 10:38:43 PM
    Author     : Alyson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Confirmed</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1>Thank You!</h1>
            <h4>Thank you, ${name}, your order has been placed!</h4>
            <form method="POST" id="back" name="back" action="<%= response.encodeRedirectURL("Shop?action=list")%>">
                <input type="submit" value="Back to Shop" name="submit" id="submit">
            </form>
        </div>
        <div class="col-md-2"></div>

        <jsp:include page="footer.jsp"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
