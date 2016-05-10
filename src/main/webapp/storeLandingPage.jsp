<%-- 
    Document   : storeLandingPage
    Created on : May 4, 2016, 5:50:27 PM
    Author     : Alyson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shop Nuglow!</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">

        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
    <sec:authorize access="hasAnyRole('ROLE_MGR,ROLE_USER')">
        <form name="productsList" id="productsList" class="forms form-horizontal" method="POST" action="<%= response.encodeURL("Shop?action=list")%>" onsubmit="">
            <fieldset>
                <legend>Store</legend>

                <div class='form-group'>
                    <div class="col-lg-10 col-lg-offset-2">
                        <input type="submit" name="submit" value="Nuglow Products" class="btn btn-default"/>
                    </div>
                </div>
            </fieldset>
        </form>

        <jsp:include page="footer.jsp"/>
    </sec:authorize>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
    <script src="js/modal.js" type="text/javascript"></script>
    <script src="js/validation.js" type="text/javascript"></script>
</body>
</html>
