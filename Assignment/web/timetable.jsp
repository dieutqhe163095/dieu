
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/timetable.css">
        <title>View Schedule</title>
    </head>

    <body>
        <a href="javascript:history.back()" class="back-button"><- Back</a>

        <div>
            <Table>
                <tr>
                    <td rowspan="2">
                        Year
                        <form action="Timetable" method="get">
                            <select name="year" id="year" onchange="this.form.submit()">
                                <c:forEach items="${requestScope.yearList}" var="y">
                                    <option
                                        <c:if test="${requestScope.selectYear eq y}"> 
                                            selected="selected"
                                        </c:if>
                                        value="${y}">${y}</option>
                                </c:forEach>
                            </select> <br>
                            Week
                            <select name="week" id="week" onchange="this.form.submit()">
                                <c:forEach items="${requestScope.weekList}" var="w">
                                    <option
                                        <c:if test="${requestScope.selectWeek eq w.weekNum}"> 
                                            selected="selected"
                                        </c:if>                                       
                                        value="${w.weekNum}">
                                        <fmt:parseDate value="${w.start}" pattern="yyyy-MM-dd" var = "parsedDate" type="date"/>
                                        <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM" /> to 
                                        <fmt:parseDate value="${w.end}" pattern="yyyy-MM-dd" var = "parsedDate" type="date"/>
                                        <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM" />
                                    </option>
                                </c:forEach> 
                            </select>
                        </form>

                    </td>
                    <td>MON</td>
                    <td>TUE</td>
                    <td>WED</td>
                    <td>THU</td>
                    <td>FRI</td>
                    <td>SAT</td>
                    <td>SUN</td>
                </tr>
                <tr>
                    <c:forEach items="${requestScope.dayList}" var="d">
                        <td>
                            <fmt:parseDate value="${d}" pattern="yyyy-MM-dd" var = "parsedDate" type="date"/>
                            <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM" /> 
                        </td>
                    </c:forEach>
                </tr>
                <c:forEach items="${requestScope.slotList}" var="slot">
                    <tr>
                        <td>Slot ${slot.slot}</td>
                        <c:forEach items="${requestScope.dayList}" var="d">                          
                            <td>                              
                                <c:forEach items="${requestScope.timetableList}" var="tb">
                                    <c:if test="${(tb.date eq d) and (tb.slot eq slot.slot)}">
                                        <a href="/Assignment/Attendance?timetableCode=${tb.timetableCode}">${tb.group} - ${tb.courseCode}</a><br>
                                        at ${tb.roomName}  <br>
                                        <c:if test="${tb.taken}"><span class="attended">Attended</span></c:if>
                                        <c:if test="${!tb.taken}"><span class="notyet">Not Yet</span></c:if>                 
                                    <li>${slot.from} - ${slot.to}</li>  
                                    </c:if>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>                    
                </c:forEach>
            </Table>
        </div>
    </body>

</html>
