/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Student;
import model.Timetable;

/**
 *
 * @author Giang Dong PC
 */
public class AttendanceDBContext extends DBContext<Attendance> {

    public Timetable getInfo(int timetableCode) {
        Timetable timetable = new Timetable();
        try {
            String sql = "select TimetableCode ,\n"
                    + "	g.GroupId, \n"
                    + "	g.GroupCode,\n"
                    + "	r.RoomName,\n"
                    + "	tt.[Date],\n"
                    + "	tt.Slot,\n"
                    + "	i.InstructorId,\n"
                    + "	tt.Taken,\n"
                    + "	tt.TakenDate,\n"
                    + "	c.CourseCode\n"
                    + "	from TimeTable tt \n"
                    + "	inner join Room r \n"
                    + "		on TimetableCode = ?\n"
                    + "		and tt.RoomId = r.RoomId\n"
                    + "	inner join [Group] g\n"
                    + "		on g.GroupId = tt.GroupId\n"
                    + "	inner join Instructor i\n"
                    + "		on i.InstructorId = tt.InstructorId\n"
                    + "	inner join Course c\n"
                    + "		on c.CourseId = g.CourseId";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, timetableCode);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                timetable.setTimetableCode(rs.getInt("TimetableCode"));
                timetable.setDate(LocalDate.parse(rs.getString("Date")));
                timetable.setSlot(rs.getInt("Slot"));
                timetable.setRoomName(rs.getString("RoomName"));
                timetable.setGroup(rs.getString("GroupCode"));
                timetable.setCourseCode(rs.getString("CourseCode"));
                timetable.setTaken(rs.getBoolean("Taken"));
                timetable.setInstructorId(rs.getInt("InstructorId"));
                if (rs.getTimestamp("TakenDate") == null) {
                    timetable.setTakenDate(null);
                } else {
                    timetable.setTakenDate(rs.getTimestamp("TakenDate").toLocalDateTime());
                }
                return timetable;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Attendance> list(int timetableCode) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        try {
            String sql = "select g.GroupId,\n"
                    + "	s.StudentId, \n"
                    + "	s.Code,\n"
                    + "	CONCAT(s.Surname,' ',s.MiddleName,' ',s.GivenName) as FullName,\n"
                    + "	tt.TimetableCode,\n"
                    + "	e.Attended \n"
                    + "	from TimeTable tt\n"
                    + "	inner join [Group] g \n"
                    + "		on  tt.GroupId = g.GroupId\n"
                    + "		and tt.TimetableCode = ?\n"
                    + "	inner join StudentGroupRelation sgr \n"
                    + "		on sgr.GroupId = g.GroupId\n"
                    + "	inner join Student s\n"
                    + "		on sgr.StudentId = s.StudentId\n"
                    + "	inner join Enroll e\n"
                    + "		on e.TimetableCode = tt.TimetableCode\n"
                    + "		and e.StudentId = s.StudentId";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, timetableCode);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setStudent(new Student(rs.getInt("StudentId"), rs.getString("Code"), rs.getString("FullName")));
                attendance.setAttended(rs.getBoolean("Attended"));
                attendances.add(attendance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendances;
    }

    public void update(int timetableCode, int studentId1, boolean b) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [dbo].[Enroll]\n"
                    + "   SET [Attended] = ?\n"
                    + " WHERE [StudentId] = ? \n"
                    + "	and [TimetableCode] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, b);
            stm.setInt(2, studentId1);
            stm.setInt(3, timetableCode);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void updateTime(int timetableCode ) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [dbo].[TimeTable]\n"
                    + "   SET [Taken] = ?\n"
                    + "      ,[TakenDate] = ?\n"
                    + " WHERE TimetableCode = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, true);
            stm.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stm.setInt(3, timetableCode);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
