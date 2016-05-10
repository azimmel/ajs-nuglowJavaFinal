<%-- 
    Document   : orderReports
    Created on : May 8, 2016, 8:50:23 AM
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
        <title>Order Reports</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
<sec:authorize access="hasAnyRole('ROLE_MGR')">
        <div class="col-md-4">
            <form method="POST" id="userReport" name="userReport" action="<%= response.encodeRedirectURL("Nuglow?action=userReport")%>">
                <legend>Get All Orders for User</legend>
                <select name="users" id="users">
                    ${users}
                    <c:forEach var="user" items="${users}">
                        <option value="${user}"value="${user}" id="${user}">${user}</option>
                    </c:forEach>
                </select>
                <input type="submit" name="submit" value="Get Orders"/>
            </form>
        </div>
        <div class="col-md-8">
            <form method="POST" id="userReport" name="userReport" action="<%= response.encodeRedirectURL("Nuglow?action=userTimeFrame")%>">
                <legend>Get All Orders Between Dates</legend>
                <input class="col-md-1" type="text" name="startYear" id="startYear" value="" min="1970" max="9999" length="4" placeholder="yyyy" required/>
                <input class="col-md-1" type="text" name="startMonth" id="startMonth" value="" min="01" max="12" length="2" placeholder="mm" required/>
                <input class="col-md-1" type="text" name="startDay" id="startDay" value="" min="01" max="31" length="2" placeholder="dd" required/>
                <div class="col-md-1">To:</div>
                <input class="col-md-1" type="text" name="endYear" id="endYear" value="" min="1970" max="9999" length="4" placeholder="yyyy" required/>
                <input class="col-md-1" type="text" name="endMonth" id="endMonth" value="" min="01" max="12" length="2" placeholder="mm" required/>
                <input class="col-md-1" type="text" name="endDay" id="endDay" value="" min="01" max="31" length="2" placeholder="dd" required/>
                <input type="submit" name="submit" value="Get Orders"/>
            </form>
        </div>
        <div class="col-lg-12" name="results" id="results">
            <table class="col-md-12">
                <thead>
                    <tr>
                        <th>Order Id</th>
                        <th>Order Date</th>
                        <th>User</th>
                        <th>Shipping Address</th>
                        <th>Items Ordered</th>
                        <th>Subtotal</th>
                        <th>Total</th>
                        <th>Billing Address</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${userOrders}">
                        <tr>
                            <td class="col-md-1">${order.orderId}</td>
                            <td class="col-md-2">${order.orderDate}</td>
                            <td class="col-md-1">${order.username}</td>
                            <td class="col-md-2">${order.shippingAddress}</td>
                            <td class="col-md-2">${order.itemsOrdered}</td>
                            <td class="col-md-1">$${order.subtotal}</td>
                            <td class="col-md-1">$${order.total}</td>
                            <td class="col-md-2">${order.billingAddress}</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <jsp:include page="footer.jsp"/>
        </sec:authorize>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>>
