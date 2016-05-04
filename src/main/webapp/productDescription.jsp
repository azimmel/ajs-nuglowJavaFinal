<%-- 
    Document   : productDescription
    Created on : May 3, 2016, 7:53:19 PM
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
        <title>${product.productName}</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <form method="POST" action="<%= response.encodeURL("Shop?action=list")%>" class="detailsForm" name="detailsForm" id="detailsForm">
            <input type="submit" value="Back" name="submit" class="btn btn-info"/>
        </form>
        <div class="col-md-12 message" name="message" id="message">${msg}</div>
        <div class="col-md-2"></div>
        <form method="POST" action="<%= response.encodeURL("Shop?action=add")%>" class="detailsForm col-md-8" name="detailsForm" id="detailsForm">
            <fieldset>
                <input type="submit" value="Add To Cart" name="submit" />&nbsp;
                <legend>${product.productName}: Details</legend>
                <input type="hidden" id="productId" name="productId" value="${product.productId}"/>
                <div class="form-group">
                    <img src="${product.productImage}" style="display: block;margin-left: auto; margin-right: auto; height:30%; width:30%;"class="img-responsive productImage"/>
                </div>
                <div class="form-group">
                    <label for="type" class="control-label">Category/Type:</label>
                    <div>
                        <div  name="type" id="type" >${product.productType}</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="control-label">Price:</label>
                    <div>
                        <div  name="price" id="price">$ ${product.productPrice}</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="control-label">Product description:</label>
                    <div>
                        ${product.productDescription}
                    </div>
                </div>
                <input type="submit" value="Add To Cart" name="submit" />
            </fieldset>
        </form>
        <div class="col-md-2"></div>
        <div class="col-md-2"></div>
        <form method="POST" action="<%= response.encodeURL("Shop?action=list")%>" class="detailsForm col-md-8">
            <input type="submit" value="Back" name="submit" class="btn btn-info"/>
        </form>
        <div class="col-md-2"></div>
        <jsp:include page="footer.jsp"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
