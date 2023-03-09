/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TimetableDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Timetable;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Account;
import model.Slot;
import model.Week;

/**
 *
 * @author Giang Dong PC
 */
public class timetableController extends BaseRequiredAuthenticationController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String rawYear = request.getParameter("year");
        String rawWeek = request.getParameter("week");
        int selectYear;
        int selectWeek;
        try {
            if (rawYear == null) {
                throw new Exception();
            }
            selectYear = Integer.parseInt(rawYear);
            selectWeek = Integer.parseInt(rawWeek);
        } catch (Exception e) {
            selectYear = LocalDate.now().getYear();
            selectWeek = 0;
        }
        request.setAttribute("selectYear", selectYear);
        request.setAttribute("selectWeek", selectWeek);
        TimetableDBContext timetableDBContext = new TimetableDBContext();
        ArrayList<Integer> yearList = timetableDBContext.yearList();
        request.setAttribute("yearList", yearList);
        ArrayList<Week> weekList = timetableDBContext.weekList(selectYear);
        request.setAttribute("weekList", weekList);
        ArrayList<LocalDate> dayList = timetableDBContext.dayList(weekList.get(selectWeek));
        request.setAttribute("dayList", dayList);
        ArrayList<Slot> slotList = timetableDBContext.slotList();
        request.setAttribute("slotList", slotList);

        ArrayList<Timetable> timetableList = new ArrayList<>();
        timetableList = timetableDBContext.listTimeTables(weekList.get(selectWeek), account.getInstuctorId());

        request.setAttribute("timetableList", timetableList);
        request.getRequestDispatcher("timetable.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
}
