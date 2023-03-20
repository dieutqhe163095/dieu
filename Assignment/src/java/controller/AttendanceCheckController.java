/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import controller.BaseRequiredAuthenticationController;
import dal.AttendanceCheckDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Account;
import model.AttendanceCheck;
import model.Group;
import model.Term;
import model.Timetable;

/**
 *
 * @author Giang Dong PC
 */
public class AttendanceCheckController extends BaseRequiredAuthenticationController {

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
        AttendanceCheckDBContext attendanceCheckDBContext = new AttendanceCheckDBContext();
        ArrayList<Term> termlList = attendanceCheckDBContext.termList(account.getInstuctorId());
        request.setAttribute("termList", termlList);
        int termSelected = -1;
        int groupSelected;
        try {
            termSelected = Integer.parseInt(request.getParameter("term"));
            request.setAttribute("termSelected", termSelected);
            ArrayList<Group> groupList = attendanceCheckDBContext.groupList(account.getInstuctorId(), termSelected);
            request.setAttribute("groupList", groupList);
            groupSelected = Integer.parseInt(request.getParameter("group"));
            for (Group group : groupList) {
                if (group.getGroupId() == groupSelected) {
                    request.setAttribute("groupSelected", group);
                }
            }           
            ArrayList<Timetable> timetableList = attendanceCheckDBContext.getTimetable(groupSelected);
            request.setAttribute("timetableList", timetableList);
            System.out.println(groupSelected);
            ArrayList<AttendanceCheck> attendanceCheckList = attendanceCheckDBContext.getAttendanceCheck(groupSelected);
            request.setAttribute("attendanceCheckList", attendanceCheckList);          
        } catch (Exception e) {
            
        }
        request.getRequestDispatcher("attendanceCheck.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
