/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AttendanceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Account;
import model.Attendance;
import model.Timetable;

/**
 *
 * @author Giang Dong PC
 */
public class attendanceController extends BaseRequiredAuthenticationController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");  
//        String timetableCodeRaw = request.getParameter("");
//        int timetableCode;
//        try {
//            timetableCode = Integer.parseInt(timetableCodeRaw);
//        } catch (Exception e) {
//            
//        }
//        request.getRequestDispatcher("web/attendance.jsp").forward(request, response);
//    } 
//
//    /** 
//     * Handles the HTTP <code>POST</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        processRequest(request, response);
//    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String timetableCodeRaw = request.getParameter("timetableCode");
        String confirm;
        try {
            confirm = request.getParameter("confirm");
        } catch (Exception e) {
            confirm = null;
        }      
        int timetableCode;
        timetableCode = Integer.parseInt(timetableCodeRaw);
        AttendanceDBContext attendanceDBContext;
        attendanceDBContext = new AttendanceDBContext();
        Timetable timetable = attendanceDBContext.getInfo(timetableCode);
        if (timetable.getInstructorId() != account.getInstuctorId()) {
            response.sendRedirect(request.getContextPath() + "/Timetable");
        } else {
            if(confirm!= null) request.setAttribute("confirm", true);
            request.setAttribute("timetable", timetable);
            ArrayList<Attendance> attendances = attendanceDBContext.list(timetableCode);
            request.setAttribute("attendanceList", attendances);
            request.getRequestDispatcher("web/attendance.jsp").forward(request, response);
        }

    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int timetableCode = Integer.parseInt(request.getParameter("timetableCode"));
        String[] studentId = request.getParameterValues("student");
        String value;
        AttendanceDBContext attendanceDBContext;
        attendanceDBContext = new AttendanceDBContext();
        for (String studentId1 : studentId) {
            value = request.getParameter(timetableCode + "_" + studentId1);    
            if (value.equals("1")) {
                attendanceDBContext.update(timetableCode, Integer.parseInt(studentId1), true);
            } else{
                attendanceDBContext.update(timetableCode, Integer.parseInt(studentId1), false);
            }
        }
            attendanceDBContext.updateTime(timetableCode);
        response.sendRedirect(request.getContextPath() + "/Attendance?timetableCode="+timetableCode+"&confirm=1");
    }    

}
