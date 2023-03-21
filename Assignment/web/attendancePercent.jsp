<%-- 
    Document   : attendancePercent
    Created on : Mar 20, 2023, 7:34:22 PM
    Author     : ASUS
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/attendance.css">
        <title>Document</title>
    </head>
    <body>
        <a href="javascript:history.back()" class="back-button">Back</a>
        <div class="banner">
            <h1>Attendance for single group in a course</h1>
        </div>
        <Div class="info-banner">   
            <p>Select a course, then a group...</p>
        </Div>
        <div>

            <fmt:parseDate value="${requestScope.timetable.takenDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
            <form form method="post" action="Attendance">
                <input type="hidden" name = "timetableCode" value="${requestScope.timetable.timetableCode}">

                <table>

                    <tr>
                        <th>ROLLNUMBER</th>
                        <th>STUDENTNAME</th>
                        <th>ABSENT(%)SO FAR</th>
                        <%int i = 1;%>
                            <c:forEach items="${data2}" var="day">
                            <th>${day.getDate()}<br>
                            <%=i%></th>
                            <%i++;%>
                            </c:forEach>


                    </tr>
            
                    <c:forEach items="${data1}" var="c" >
                        <tr>                           
                            <td>${c.getStudent().getStudentCode()}</td>
                            <td>${c.getStudent().getFullName()}</td>
                            <td>${c.getPercentAbsent()}</td>
                            <c:forEach items="${c.getList()}" var="attendance">
                                <c:if test="${attendance.isAttended() == true}">
                                <td> P     </td>  
                                </c:if>
                                  <c:if test="${attendance.isAttended() != true}">
                                <td> A   </td>  
                                </c:if>   
                                    
                                </c:forEach>                 


                        </tr>
                    </c:forEach>

                </table>

            </form>

        </div>

    </body>
</html>
