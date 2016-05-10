<%-- 
    Document   : productDetail
    Created on : Mar 15, 2016, 4:27:23 PM
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
        <title>NuGlow Admin- Details</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <script src="js/validation.js" type="text/javascript"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <sec:authorize access="hasAnyRole('ROLE_MGR')">
        <!--Body content-->
        <form method="POST" action="<%= response.encodeURL("Nuglow?action=list")%>" class="detailsForm" name="detailsForm" id="detailsForm">
            <input type="submit" value="Back" name="submit" class="btn btn-info"/>
        </form>
        <div class="col-lg-12 message" name="message" id="message">${msg}</div>
        <form method="POST" action="<%= response.encodeURL("Nuglow?action=editDelete")%>" class="detailsForm col-lg-8" name="detailsForm" id="detailsForm" onsubmit="return validateDetailForm()">
            <fieldset>
                <input type="submit" value="Save Edit" name="submit" />&nbsp;
                <input type="submit" value="Delete" name="submit" />
                <legend>${product.productName}: Details</legend>
                <div class="form-group">
                    <label for="productId" class="control-label">Product Id:</label>
                    <div>
                        <input type="text" class="form-control" name="productId" id="productId" value="${product.productId}" readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label for="authorName" class="control-label">Product Name:</label>
                    <div>
                        <input type="text" class="form-control" name="productName" id="productName" value="${product.productName}" autofocus>
                    </div>
                </div>
                <div class="form-group">
                    <label for="type" class="control-label">Category/Type:</label>
                    <div>
                        <input type="text" class="form-control" name="type" id="type" value="${product.productType}" >
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="control-label">Product Price:</label>
                    <div>
                        <input type="number" class="form-control" name="price" id="price" value="${product.productPrice}" min="0.01" step=".01" >
                    </div>
                </div>
                <div class="form-group">
                    <label for="imgUrl" class="control-label">Product Relative Img Url:</label>
                    <div>
                        <input type="text" class="form-control" name="imgUrl" id="imgUrl" value="${product.productImage}" placeholder="imgs/luck.jpg" >
                        <span class="help-block">Image must be in "imgs" folder within the project. Example URL: imgs/hope.jpg</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="control-label">Product description:</label>
                    <div>
                        <input class="form-control" name="description" id="description" value="${product.productDescription}" cols="1" rows="4" />
                    </div>
                </div>
                <input type="submit" value="Save Edit" name="submit" />&nbsp;
                <input type="submit" value="Delete" name="submit" />

            </fieldset>
        </form>

        <form method="POST" action="<%= response.encodeURL("Nuglow?action=list")%>" class="detailsForm col-lg-8">
            <input type="submit" value="Back" name="submit" class="btn btn-info"/>
        </form>

        <!--/body content-->
        <!--footer and wrapper end-->
        <jsp:include page="footer.jsp"/>
        </sec:authorize>
        
    </body>
</html>
