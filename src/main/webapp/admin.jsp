<%-- 
    Document   : admin
    Created on : Mar 14, 2016, 7:36:35 AM
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
        <title>NuGlow Admin</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <!--Body content-->
        <form name="productsList" id="productsList" class="forms form-horizontal" method="POST" action="<%= response.encodeURL("Nuglow?action=list")%>" onsubmit="">
            <fieldset>
                <legend>Administration Options</legend>

                <div class='form-group'>
                    <div class="col-lg-10" style="margin-left:1.5%;">
                        <input type="submit" name="submit" value="Products List" class="btn btn-default"/>
                    </div>
                </div>
            </fieldset>
        </form>
        <form method="POST" name="reports" class="form-horizontal" action="<%= response.encodeRedirectURL("Nuglow?action=orderReports")%>">
            <div class='form-group'>
                    <div class="col-lg-10" style="margin-left:1.5%;">
                        <input type="submit" name="submit" value="Order Reports" class="btn btn-default"/>
                    </div>
            </div>
        </form>
        <div class='form-group'>
            <div class="col-lg-10 col-lg-offset-2">
                <button class="btn btn-default"><a href="<%= response.encodeURL("adminPreferences.jsp")%>">Administration Preferences</a></button>
            </div>
        </div>


        <!--/body content-->
        <!--footer and wrapper end-->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
