<%-- 
    Document   : registration
    Created on : May 8, 2016, 10:05:30 PM
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
        <title>Register</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <sec:authorize access="isAnonymous()">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <form method="POST" action="<%= response.encodeURL("Register?action=register")%>" class="col-md-12">
                        <fieldset>
                            <legend>New User Registration</legend>
                            <div class="form-group">
                                <label for="username" class="col-lg-2 control-label">Email:</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" name="username" id="userName" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-lg-2 control-label">Password:</label>
                                <div class="col-lg-10">
                                    <input type="password" class="form-control" name="password" id="password" value="">
                                </div>
                            </div>
                            <input type="submit" value="Register" name="submit" />
                        </fieldset>
                    </form>
                </div>
                <div class="col-md-1"></div>
            </div>
            <div class="col-md-2"></div>

            <jsp:include page="footer.jsp"/>
        </sec:authorize>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
