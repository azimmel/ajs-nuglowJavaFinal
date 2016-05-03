<%-- 
    Document   : adminPreferences
    Created on : Mar 18, 2016, 5:08:20 PM
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
        <title>Admin Preferences</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <!--Body content-->
        <form name="productsList" id="productsList" class="forms form-horizontal" method="POST" action="<%= response.encodeURL("Nuglow?action=preferences")%>" onsubmit="">
            <fieldset>
                <legend>Admin Preferences</legend>

                <div class='form-group'>
                    <div class="col-lg-10 col-lg-offset-2">
                        <input type="submit" name="submit" value="Change to Unicorn"/>
                        <input type="submit" name="submit" value="Change to SpaceLab"/>
                    </div>
                </div>
                
            </fieldset>
        </form>
        <!--/body content-->
        <!--footer and wrapper end-->
        <jsp:include page="footer.jsp"/>
    </body>
</html>
