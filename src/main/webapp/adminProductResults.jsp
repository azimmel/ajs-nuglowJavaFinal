<%-- 
    Document   : productResults
    Created on : Mar 15, 2016, 4:29:19 PM
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
        <title>NuGlow Admin- Results</title>
        <!--js needed at top for hidden field-->
        <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
        <jsp:include page="cssBundle.jsp"/>
    </head>
    <body style="${background}">
        <!--navigation header and wrapper start-->
        <jsp:include page="header.jsp"/>
        <!--Body content-->
        <div class="row col-lg-12">
            <h1 class="col-lg-5">Products</h1>
        </div>
        
        <div>
            <div id="answer">
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#createNewProductModal">Add New Product</button>
                <div class="col-lg-12 message" name="message" id="message">${msg}</div>
                <table class="table table-striped table-hover ">
                    <thead>
                        <tr>
                            <th>Product Id</th>
                            <th>Name- Click to Edit</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Image Url</th>
                            <th>Image</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${productList}">
                            <tr>
                                <td>
                                    <c:out value="${product.productId}"/>
                                </td>
                                <td>
                                    <form method="POST" action="<%=response.encodeRedirectURL("Nuglow?action=details")%>">
                                        <input type="submit" class="submitLink" value="${product.productName}" name="submit"/>
                                        <input type="hidden" id="productId" name="productId" value="${product.productId}"/>
                                    </form>
                                </td>
                                <td>
                                    <c:out value="${product.productType}"/>
                                </td>
                                <td>
                                    <c:out value="${product.productPrice}"/>
                                </td>
                                <td>
                                    <c:out value="${product.productImage}"/>
                                </td>
                                <td>
                                    <img src="${product.productImage}" style="height: 100px; width: 100px"/>                                    
                                </td>
                                <td>
                                    <c:out value="${product.productDescription}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </form>
            </div>
            <div class="col-lg-12 message" name="message" id="message">${msg}</div>
        </div>
        <!--Being createNewProduct modal-->
        <div class="modal fade" tabindex="-1" role="dialog" id="createNewProductModal" aria-labelledby="createNewProductModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">New Product Entry System</h4>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<%=response.encodeRedirectURL("Nuglow?action=create")%>" class="createForm" name="createForm" id="createForm" onsubmit="return validateCreateForm()">
                            <fieldset>
                                <legend></legend>
                                <div class="form-group" onsubmit="return validateCreateForm()">
                                    <label for="authorName" class="control-label">Product Name:</label>
                                    <div onsubmit="return validateCreateForm()">
                                        <input type="text" class="form-control" name="productName" id="productName" value="" autofocus >
                                    </div>
                                </div>
                                <div class="form-group" onsubmit="return validateCreateForm()">
                                    <label for="type" class="control-label">Product Type:</label>
                                    <div onsubmit="return validateCreateForm()">
                                        <input type="text" class="form-control" name="type" id="type" value="" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="price" class="control-label">Product Price:</label>
                                    <div>
                                        <input type="number" class="form-control" name="price" id="price" value="" min="0.01" step=".01" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="imgUrl" class="control-label">Product Relative Img Url:</label>
                                    <div>
                                        <input type="text" class="form-control" name="imgUrl" id="imgUrl" value="" placeholder="imgs/luck.jpg" >
                                        <span class="help-block">Image must be in "imgs" folder within the project. Example URL: imgs/hope.jpg</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="description" class="control-label">Product description:</label>
                                    <div>
                                        <textarea class="form-control" name="description" id="description" value="" cols="1" rows="4" ></textarea>
                                    </div>
                                </div>
                                <input type="submit" value="Create" name="submit" class="btn btn-primary"/>
                            </fieldset>
                        </form>
                    </div>
                    <hr/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <div class="col-lg-12" name="recordsAffected" id="recordsCreated">Total Records Changed in Session: ${recordsAffected}</div>
        <!--/body content-->
        
        <!--footer and wrapper end-->
        <jsp:include page="footer.jsp"/>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>        
        <script src="js/modal.js" type="text/javascript"></script>
        <script src="js/validation.js" type="text/javascript"></script>
    </body>
</html>
