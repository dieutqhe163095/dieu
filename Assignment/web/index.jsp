<%-- 
    Document   : index
    Created on : Mar 9, 2023, 8:48:55 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <title>Login</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <div class="wrapper">
            <div id="formContent">
                <form action="Login" method="POST">
                    <h1>FPT University Academic Portal</h1>
                    <h3>Login</h3>
                    <input type="text" id="login" name="login" placeholder="login">
                    <input type="password" id="password" name="password" placeholder="password">
                    <input type="submit" value="Log In">
                    <c:if test="${requestScope.errorMess eq 1 }">
                        <div class="errorMess">
                            <b>Login Failed</b>
                            <p>Please try again.</p>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </body>
</html>
