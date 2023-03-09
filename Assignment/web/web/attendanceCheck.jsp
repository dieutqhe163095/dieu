<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/attendanceCheck.css">
        <title>Document</title>
    </head>
    <body>
        <div>
            <table>
                <thead>
                    <tr>
                        <th>TERM</th>
                        <th>GROUP</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <c:forEach items="${requestScope.termList}" var="tl">
                                <c:if test="${tl.termId eq requestScope.termSelected}">
                        <li>${tl.termName}</li>
                        </c:if>
                        <c:if test="${tl.termId ne requestScope.termSelected}">
                        <li><a href="/Assignment/AttendanceCheck?term=${tl.termId}">${tl.termName}</a><br></li>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${requestScope.groupList}" var="gl">                               
                        <c:if test="${gl.groupId eq requestScope.groupSelected.groupId}">
                        <li>${gl.groupCode} - ${gl.courseCode}</li>
                        </c:if>
                        <c:if test="${gl.groupId ne requestScope.groupSelected.groupId}">
                        <li><a href="/Assignment/AttendanceCheck?term=${requestScope.termSelected}&group=${gl.groupId}">${gl.groupCode} - ${gl.courseCode}</a><br></li>
                        </c:if>
                    </c:forEach>
                </td>
                </tr>

                </tbody>
            </table>
        </div>
        <c:if test="${requestScope.groupSelected ne null}">
            <div class="info-banner">
                <p>Attentdance Infomation for group ${requestScope.groupSelected.groupCode} course ${requestScope.groupSelected.courseCode} </p>
            </div>
            <div class="scroller" style="overflow-x:auto;">
                <table>
                    <thead>
                        <tr>
                            <th>INDEX</th>
                            <th>GROUP</th>
                            <th>CODE</th>
                            <th>NAME</th>
                                <c:forEach items="${requestScope.timetableList}" var="tt">     
                                <th>
                                    <fmt:parseDate value="${tt.date}" pattern="yyyy-MM-dd" var = "parsedDate" type="date"/>
                                    <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM" />
                                </th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.attendanceCheckList}" var="ac" varStatus="loop">    
                            <tr>
                                <td>${loop.count}</td>
                                <td>${requestScope.groupSelected.groupCode}</td>
                                <td>${ac.student.studentCode}</td>
                                <td>${ac.student.fullName}</td>
                                <c:forEach items="${ac.attentedList}" var="tt">                                      
                                        <c:if test="${tt}">
                                            <td class="attened">Attened</td>   
                                        </c:if>
                                        <c:if test="${!tt}">
                                            <td class="absent">Absent</td>  
                                        </c:if>
                                </c:forEach>
                            </tr>

                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </c:if>


    </body>
</html>