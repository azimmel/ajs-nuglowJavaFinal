<%-- 
    Document   : checkout
    Created on : May 7, 2016, 4:50:06 PM
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
        <title>Checkout</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <script src="js/collapse.js" type="text/javascript"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <sec:authorize access="hasAnyRole('ROLE_MGR,ROLE_USER')">
        <!--Totals ** haven't added in tax per state yet, currently set at .02% but this can be changed.-->
        <table>
            <tbody>
                <tr style="font-size: 1.4em;">
                    <td style="text-align: right;">Subtotal:&nbsp; </td>
                    <td >
                        $${subtotal}
                    </td>
                </tr>
                <tr style="font-size: 1.4em;">
                    <td style="text-align: right;">Tax:&nbsp; </td>
                    <td >
                        $${tax}
                    </td>
                </tr>
                <tr style="font-size: 1.4em;">
                    <td style="text-align: right;">Total:&nbsp; </td>
                    <td >
                        $${total}
                    </td>
                </tr>
            </tbody>
        </table>
        <!--Customer Information-->
        <form method="POST" id="checkout" name="checkout" class="col-md-6"action="<%= response.encodeRedirectURL("Shop?action=pay")%>">
            <fieldset>
                <legend>Customer Information</legend>
                <div class="form-group">
                    <label for="firstName" class="control-label">First Name:</label>
                    <div>
                        <input type="text" class="form-control" name="firstName" id="firstName" value="" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="lastName" class="control-label">Last Name:</label>
                    <div>
                        <input type="text" class="form-control" name="lastName" id="lastName" value="" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="address1" class="control-label">Street Address 1:</label>
                    <div>
                        <input type="text" class="form-control" name="address1" id="address1" value="" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="address2" class="control-label">Street Address 2:</label>
                    <div>
                        <input type="text" class="form-control" name="address2" id="address2" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="city" class="control-label">City:</label>
                    <div>
                        <input type="text" class="form-control" name="city" id="city" value="" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="state" class="control-label">State:</label>
                    <div>
                        <input type="text" class="form-control" name="state" id="state" value="" max-length="2" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="zip" class="control-label">Zip:</label>
                    <div>
                        <input type="text" class="form-control" name="zip" id="zip" value="" max-length="5" min-length="5" required>
                    </div>
                </div>
                <legend>Billing Information</legend>
                <div class="checkbox">
                    <label><input type="checkbox" id="checkbox" name="checkbox" value="1"> Billing address is the same as the shipping address</label>
                </div>
                <div class="billing">
                    <div class="form-group">
                        <label for="bFirstName" class="control-label">First Name:</label>
                        <div>
                            <input type="text" class="form-control" name="bFirstName" id="bFirstName" value="" min-length="2">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bLastName" class="control-label">Last Name:</label>
                        <div>
                            <input type="text" class="form-control" name="bLastName" id="bLastName" value="" min-length="2" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bAddress1" class="control-label">Street Address 1:</label>
                        <div>
                            <input type="text" class="form-control" name="bAddress1" id="bAddress1" value="" min-length="2" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bAddress2" class="control-label">Street Address 2:</label>
                        <div>
                            <input type="text" class="form-control" name="bAddress2" id="bAddress2" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bCity" class="control-label">City:</label>
                        <div>
                            <input type="text" class="form-control" name="bCity" id="bCity" value="" min-length="2" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bState" class="control-label">State:</label>
                        <div>
                            <input type="text" class="form-control" name="bState" id="bState" value="" max-length="2" min-length="2" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bZip" class="control-label">Zip:</label>
                        <div>
                            <input type="text" class="form-control" name="bZip" id="bZip" value="" max-length="5" min-length="5">
                        </div>
                    </div>
                </div>
                <legend>Credit Card Info</legend>
                <div class="form-group">
                    <label for="cardNumber" class="control-label">Credit Card Number:</label>
                    <div>
                        <input type="text" class="form-control" name="cardNumber" id="cardNumber" value="" min-lenght="14" max-length="21" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="cardName" class="control-label">Name on Card:</label>
                    <div>
                        <input type="text" class="form-control" name="cardName" id="cardName" value="" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="expMonth" class="control-label">Expiration Month:</label>
                    <div>
                        <input type="text" class="form-control" name="expMonth" id="expMonth" value="" max-length="2" min-lenght="2" max="12" min="0"required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="expYear" class="control-label">Expiration Year:</label>
                    <div>
                        <input type="text" class="form-control" name="expYear" id="expYear" value="" max-length="2" min-length="2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="cvv" class="control-label">CVV:</label>
                    <div>
                        <input type="text" class="form-control" name="cvv" id="cvv" value="" max-length="3" min-length="3" required>
                    </div>
                </div>
                <input type="submit" value="Checkout" type="submit"/>
            </fieldset>
        </form>




        <!-- CART ITEMS COLLAPSIBLE -->
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Cart <span class="badge">${totalItems}</span>
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                    <div class="panel-body">
                        <table>
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="product" items="${userItems}">
                                    <tr>
                                        <td>
                                            <div class="col-sm-3"><img src="${product.productId.productImage}" alt="${product.productId.productType}" style="height:30%; width:20%;"/></div>
                                            <div class="col-sm-4">
                                                <div class="col-sm-12">${product.productId.productName}</div>
                                                <div class="col-sm-12">Category: ${product.productId.productType}</div>
                                            </div>
                                        </td>
                                        <td>
                                            ${product.productId.productPrice}
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
            </sec:authorize>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
            <script src="js/modal.js" type="text/javascript"></script>
            <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
