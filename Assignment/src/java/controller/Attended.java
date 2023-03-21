/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AttendanceDBContext;
import dal.StudentDao;
import dal.TimetableDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Attendance;
import model.Attendancee;
import model.Student;
import model.Timetable;

/**
 *
 * @author ASUS
 */
public class Attended extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Attended</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Attended at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDao sd = new StudentDao();
        AttendanceDBContext adbc = new AttendanceDBContext();
        String gId = request.getParameter("gId");
        int groupId = Integer.parseInt(gId);
        List<Attendancee> attendancees = new ArrayList();
        List<Student> students = sd.fullStudentInGroup(groupId);
        List<Double> absentPersent = new ArrayList();
        for (Student student : students) {
            int count = 1;
            List<Attendance> attendances = adbc.getAllStudentListAttendance(student.getStudentId(), groupId);
            for (Attendance attendance1 : attendances) {
                if(!attendance1.isAttended()){
                    count++;
                }
                
            }
            double percent = 100*count/attendances.size();
            absentPersent.add(percent);
            Attendancee attd = new Attendancee(student, attendances,percent);
            attendancees.add(attd);
     
        }
        List<Attendance> attendances = adbc.getAllStudentListAttendance(students.get(0).getStudentId(), groupId);
        request.setAttribute("data2", attendances);

        request.setAttribute("data1", attendancees);
        request.getRequestDispatcher("attendancePercent.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
