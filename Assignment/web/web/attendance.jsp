<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/attendance.css">
        <title>Document</title>
    </head>

    <body>
         <a href="javascript:history.back()" class="back-button"><- Back</a>
        <div class="banner">
            <h1>Single Activity Attendance</h1>
        </div>
        <Div class="info-banner">   
            <p>Attendance for <span>${requestScope.timetable.group} - ${requestScope.timetable.courseCode}</span> with lecturer <span>${sessionScope.account.instuctorCode}</span> at slot ${requestScope.timetable.slot} on
                <fmt:parseDate value="${requestScope.timetable.date}" pattern="yyyy-MM-dd" var = "parsedDate" type="date"/>
                <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy"/> in room ${requestScope.timetable.roomName}
        </Div>
        <div>
            <fmt:parseDate value="${requestScope.timetable.takenDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
            <form form method="post" action="Attendance">
                <input type="hidden" name = "timetableCode" value="${requestScope.timetable.timetableCode}">
                <table>
                    <tr>
                        <th>INDEX</th>
                        <th>GROUP</th>
                        <th>CODE</th>
                        <th>NAME</th>
                        <th colspan="2">ATTENDANCE</th>
                        <th>COMMENT</th>
                        <th>TAKER</th>
                        <TH>RECORD TIME</TH>
                    </tr>
                    
                    <c:forEach items="${requestScope.attendanceList}" var="al" varStatus="loop">
                        <tr>
                            <input type="hidden" name = "student" value="${al.student.studentId}">
                            <td>${loop.count}</td>
                            <td><a href="/Assignment/Group">${requestScope.timetable.group}</a></td>
                            <td>${al.student.studentCode}</td>
                            <td>${al.student.fullName}</td>
                            <td>
                                <label for="attended">Attended</label>
                                <input type="radio" name="${requestScope.timetable.timetableCode}_${al.student.studentId}" id="Attended" value="1" 
                                       <c:if test="${al.attended}"> 
                                       checked
                                       </c:if>>
                            </td>
                            <td>
                                <label for="absent">Absent</label>
                                <input type="radio" name="${requestScope.timetable.timetableCode}_${al.student.studentId}" id="Absent" value="0"
                                       <c:if test="${!al.attended}"> 
                                       checked
                                        </c:if>
                                    >
                            </td>
                            <td></td>
                            <td>${sessionScope.account.instuctorCode}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${parsedDateTime}" /></td>
                        </tr>
                    </c:forEach>
                        
                </table>
                <input type="submit" value="Submit">
            </form>
                <c:if test="${requestScope.confirm}">
                        <div class="confirm"">
                            <b>Summit Confirm</b>
                        </div>
                    </c:if>
        </div>

    </body>

</html>