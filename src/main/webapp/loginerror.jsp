<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Error</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">

        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
    <sec:authorize access="isAnonymous()">



        <h3 style="font-weight: 200;color: #ffc400;">Log in error</h3>
        <p class='helpText'>The most likely reason is that you did not logout properly last time. You must logout 
            by clicking the logout link and closing your browser.</p>
        <p class='helpText'>The other possibility is that your credentials could not be verified 
            (inaccurate entry) or are currently being used by someone else (no duplicate logins allowed). 
            Or, you are not authorized to view the content you desire.
        </p>
        <p class='helpText'>
            Try this: (1) re-enter your credentials, or (2) 
            check with your manager and confirm that you are authorized 
            to view the content your desire.
        </p>
        <p class='helpText'>
            <a style="color: #ffc400;" href='<%= this.getServletContext().getContextPath() + "/login.jsp"%>'>Back to Login Page</a>
        </p>



        <jsp:include page="footer.jsp"/>
    </sec:authorize>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
    <script src="js/modal.js" type="text/javascript"></script>
    <script src="js/validation.js" type="text/javascript"></script>
</body>
</html>