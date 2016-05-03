<%-- 
    Document   : errorPage
    Created on : Mar 18, 2016, 3:22:01 PM
    Author     : Alyson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>404 Error Page</title>
    </head>
    <body>
        <style>
            body{
                background-image: url(404.jpg);
                background-color: #51607A;
            }
            </style>
            <div class="col-lg-12 message" name="message" id="message" style="text-align: center;">${msg}</div>
    </body>
</html>
