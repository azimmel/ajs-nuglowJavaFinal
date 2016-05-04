<%-- 
    Document   : header
    Created on : Mar 14, 2016, 7:34:39 AM
    Author     : Alyson
--%>
<div class="wrapper">
<nav class='navbar navbar-default'>
    <div class='container-fluid'>
        <div class="navbar-header">
            <!--header stuff-->

            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="index.html" class="navbar-brand" style="text-align: center">Nu-Glow</a>
            <img src="imgs/bottle.png" alt="" style="height:6%; width:6%;"/>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <li>
                    <a href="<%=response.encodeRedirectURL("index.html")%>">Home</a>
                </li>
                <li>
                    <a href="<%=response.encodeRedirectURL("Shop?action=list")%>">Shop</a>
                </li>
                <li>
                    <a href="<%=response.encodeRedirectURL("admin.jsp")%>">Admin</a>                    
                </li>
                <li>
                    <a href="<%=response.encodeRedirectURL("shop?action=shoppingcart")%>"><span class="badge">${totalItems}</span> Cart</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
