<%-- 
    Document   : products
    Created on : May 3, 2016, 3:49:29 PM
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
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <c:forEach var="product" items="${productList}">
                <div class="well col-md-3" style="text-align:center;display: block;margin-left: auto; margin-right: auto;">
                    <div class="col-md-12" style="display: block;margin-left: auto; margin-right: auto;height:200px;width: 200px;"><img src="${product.productImage}" style="display: block;margin-left: auto; margin-right: auto;height:200px;width: 200px;"class="img-responsive productImage"/></div> 
                    <div class="col-md-12">
                        <form method="POST" name="details" id="details" action="<%= response.encodeRedirectURL("Shop?action=details")%>">
                            <fieldset>
                                <input type="submit" class="submitLink" value="${product.productName}" name="submit"/>
                                <input type="hidden" id="productId" name="productId" value="${product.productId}"/>
                                <input type="hidden" id="user" name="user" value="${users.username}">
                            </fieldset>
                        </form>
                            
                    </div>
                    <div class="col-md-12">Category: ${product.productType}</div>
                    <div class="col-md-12">$ ${product.productPrice}</div>
                    <form method="POST" name="add" id="add" action="<%= response.encodeRedirectURL("Shop?action=add")%>">
                        <input type="submit" value="Add to Cart" name="submit" id="buy"class="buy btn-danger"/>
                        <input type="hidden" id="productId" name="productId" value="${product.productId}"/>
                    </form> 
                </div>
            </c:forEach>
        </div>
        <div class="col-md-1"></div>
        <jsp:include page="footer.jsp"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
