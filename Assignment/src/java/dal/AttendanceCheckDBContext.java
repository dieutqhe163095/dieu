/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AttendanceCheck;
import model.Group;
import model.Student;
import model.Term;
import model.Timetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;
import model.Timetable;

/**
 *
 * @author ASUS
 */
public class AttendanceCheckDBContext extends DBContext<Object> {

    public ArrayList<Term> termList(int InstuctorId) {
        ArrayList<Term> termsList = new ArrayList<>();
        try {

            String sql = "select t.TermId, t.TermName from \n"
                    + "	(select g.TermId\n"
                    + "	from Instructor i\n"
                    + "	inner join TimeTable tt\n"
                    + "		on tt.InstructorId = i.InstructorId\n"
                    + "		and i.InstructorId = ?\n"
                    + "	inner join [Group] g\n"
                    + "		on g.GroupId = tt.GroupId\n"
                    + "		group by g.TermID) gx\n"
                    + "	inner join Term t\n"
                    + "		on t.TermId = gx.TermID";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, InstuctorId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Term term = new Term();
                term.setTermId(rs.getInt("TermId"));
                term.setTermName(rs.getString("TermName"));
                termsList.add(term);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceCheckDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return termsList;
    }

    public ArrayList<Group> groupList(int instuctorId, int termSelected) {
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "select distinct tt.GroupId, g.GroupCode , c.CourseCode \n"
                    + "from Instructor i\n"
                    + "	inner join TimeTable tt\n"
                    + "		on tt.InstructorId = i.InstructorId\n"
                    + "		and i.InstructorId = ?\n"
                    + "	inner join [Group] g\n"
                    + "		on g.GroupId = tt.GroupId\n"
                    + "	inner join Course c\n"
                    + "		on g.CourseId = c.CourseId\n"
                    + "	inner join Term t\n"
                    + "		on t.TermId = g.TermID\n"
                    + "		and t.TermId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, instuctorId);
            stm.setInt(2, termSelected);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getInt("GroupId"));
                group.setGroupCode(rs.getString("GroupCode"));
                group.setCourseCode(rs.getString("CourseCode"));
                groups.add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceCheckDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }

    public ArrayList<AttendanceCheck> getAttendanceCheck(int groupSelected) {
        ArrayList<AttendanceCheck> attendanceChecks = new ArrayList<>();
        try {
            String sql = "select s.StudentId, \n"
                    + "	s.Code, \n"
                    + "	S.Member,\n"
                    + "	CONCAT(s.Surname,' ',s.MiddleName,' ',s.GivenName) as FullName,	\n"
                    + "	er.Attended, tt.[Date]\n"
                    + "	from [Group] g 			\n"
                    + "	inner join StudentGroupRelation sgr \n"
                    + "		on sgr.GroupId = g.GroupId\n"
                    + "		AND g.GroupId = ?\n"
                    + "	inner join Student s \n"
                    + "		on sgr.StudentId = s.StudentId\n"
                    + "	inner join TimeTable tt\n"
                    + "		on tt.GroupId = g.GroupId  \n"
                    + "	inner join Enroll er\n"
                    + "		on er.StudentId = s.StudentId\n"
                    + "		and tt.TimetableCode = er.TimetableCode";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, groupSelected);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (attendanceChecks.isEmpty()) {
                    AttendanceCheck attendanceCheck = new AttendanceCheck();
                    attendanceCheck.setStudent(new Student());
                    attendanceCheck.getStudent().setStudentId(rs.getInt("StudentId"));
                    attendanceCheck.getStudent().setStudentCode(rs.getString("Code"));
                    attendanceCheck.getStudent().setFullName(rs.getString("FullName"));
                    attendanceCheck.setAttentedList(new ArrayList<>());
                    attendanceChecks.add(attendanceCheck);

                }
                if (attendanceChecks.get(attendanceChecks.size() - 1).getStudent().getStudentId() != rs.getInt("StudentId")) {
                    AttendanceCheck attendanceCheck = new AttendanceCheck();
                    attendanceCheck.setStudent(new Student());
                    attendanceCheck.getStudent().setStudentId(rs.getInt("StudentId"));
                    attendanceCheck.getStudent().setStudentCode(rs.getString("Code"));
                    attendanceCheck.getStudent().setFullName(rs.getString("FullName"));
                    attendanceCheck.setAttentedList(new ArrayList<>());
                    attendanceChecks.add(attendanceCheck);
                }
                attendanceChecks.get(attendanceChecks.size() - 1).getAttentedList().add(rs.getBoolean("Attended"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceCheckDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(attendanceChecks.size());
        return attendanceChecks;
    }

    public ArrayList<Timetable> getTimetable(int groupSelected) {
        ArrayList<Timetable> timetable = new ArrayList<>();
        try {
            String sql = "select tt.TimetableCode , tt.Date , tt.Taken\n"
                    + "	from [Group] g\n"
                    + "	inner join [TimeTable] tt\n"
                    + "		on g.GroupId = tt.GroupId\n"
                    + "		and g.GroupId = ?\n"
                    + "	order by tt.Date";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, groupSelected);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Timetable timetable1 = new Timetable();
                timetable1.setTaken(rs.getBoolean("Taken"));
                timetable1.setDate(LocalDate.parse(rs.getString("Date")));
                timetable1.setTimetableCode(rs.getInt("TimetableCode"));
                timetable.add(timetable1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceCheckDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timetable;
    }
}
