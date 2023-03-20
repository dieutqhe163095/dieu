/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slot;
import model.Student;
import model.Term;

/**
 *
 * @author ASUS
 */
public class AttendancePercentDBContext extends DBContext<Term>{
    public ArrayList<Term> termList() {
        ArrayList<Term> terms = new ArrayList<>();
        try {
            String time;
            String sql = "select TermName from Term";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Term term = new Term();
              rs.getString("Term");
                
            }
            return terms;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
