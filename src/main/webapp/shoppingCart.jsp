<%-- 
    Document   : shoppingCart
    Created on : May 7, 2016, 11:27:07 AM
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
        <title>Shopping Cart</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <div class="col-md-8">
            <table>
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${userItems}">
                        <tr>
                            <td>
                                <div class="col-sm-3"><img src="${product.productId.productImage}" alt="${product.productId.productType}" style="height:60%; width:50%;"/></div>
                                <div class="col-sm-4">
                                    <div class="col-sm-12">${product.productId.productName}</div>
                                    <div class="col-sm-12">Category: ${product.productId.productType}</div>
                                </div>
                            </td>
                            <td>
                                ${product.productId.productPrice}
                            </td>
                            <td >
                                <form method="POST" name="remove" id="remove" style="margin-left:20%;"action="<%= response.encodeRedirectURL("Shop?action=delete")%>">
                                    <input type="submit" value="Remove" name="submit" id="delete"class="buy btn-danger"/>
                                    <input type="hidden" id="cartId" name="cartId" value="${product.cartId}"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
        <div class="col-md-4">
            <table>
                <tbody>
                    <tr style="font-size: 1.4em;">
                        <td style="text-align: right;">Subtotal:&nbsp; </td>
                        <td >
                            $${subtotal}
                        </td>
                    </tr>
                    <tr style="font-size: 1.4em;">
                        <td style="text-align: right;">Estimated Tax:&nbsp; </td>
                        <td >
                            $${tax}
                        </td>
                    </tr>
                    <tr style="font-size: 1.4em;">
                        <td style="text-align: right;">Estimated Total:&nbsp; </td>
                        <td >
                            $${total}
                        </td>
                    </tr>
                    <tr style="font-size: 1.4em;">
                        <td></td>
                        <td>
                            <form method="Post" name="checkout" id="checkout" style="text-align: left;" action="<%= response.encodeRedirectURL("Shop?action=checkout")%>">
                                <input type="submit" value="Checkout" name="submit" id="checkout" class="btn btn-success">
                                
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <jsp:include page="footer.jsp"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
