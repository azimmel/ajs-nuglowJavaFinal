<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login!</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <sec:authorize access="isAnonymous()">




        <form id="signInForm" role="form" method='POST' action="<c:url value='j_spring_security_check' />">
            <sec:csrfInput />


            <div class="col-sm-6">
                <h3 style="font-weight: 200;">Sign in </h3>
                <div class="form-group">
                    <input tabindex="1" class="form-control" id="j_username" name="j_username" placeholder="Email address" type="text" autofocus />
                    <input tabindex="2" class="form-control" id="j_password" name="j_password" type="password" placeholder="password" />
                </div>
                <div class="form-group">
                    <input class="btn btn-warning" name="submit" type="submit" value="Sign in" />
                </div>
            </div>
        </form>


        <jsp:include page="footer.jsp"/>
        </sec:authorize>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>