<%-- 
    Document   : footer
    Created on : Mar 14, 2016, 7:39:03 AM
    Author     : Alyson
--%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Date"%>
</div>
<div class="push"></div>
<div class="footer">
    <br/>
    <hr/>
    <div class="col-md-3"><span>Copyright &#169 ${date}</span></div>
    <div class="col-md-6"></div>
    <div class="col-md-3"><ul>
            <li>
                Logged in as: <sec:authentication property="principal.username"></sec:authentication>
            </li>
            <li>
                <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>

            </li>
            <li>
            <sec:authorize access="isAuthenticated('ROLE_MGR')">
                <a href="<%=response.encodeRedirectURL("admin.jsp")%>">Admin</a>                    
                </sec:authorize>
            </li>
        </ul>
    </div>
</div>
