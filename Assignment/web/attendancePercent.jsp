<%-- 
    Document   : attendancePercent
    Created on : Mar 20, 2023, 7:34:22 PM
    Author     : ASUS
--%>

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
                        <th>CAMPUS</th>
                        <th>TERM</th>
                        <th>DEPARTMENT</th>
                        <th>COURSE</th>
<!--                       <th colspan="2">ATTENDANCE</th>-->
                        <th>GROUP</th>
                      
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
