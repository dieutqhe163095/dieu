/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Timetable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import model.Slot;
import model.Week;

/**
 *
 * @author Giang Dong PC
 */
public class TimetableDBContext extends DBContext<Timetable> {

    public ArrayList<Integer> yearList() {
        try {
            ArrayList<Integer> yearList = new ArrayList<>();
            String sql = "select YEAR([Date]) as [Year] \n"
                    + "	from TimeTable \n"
                    + "	GROUP BY YEAR([Date]) order by [Year];";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer year = rs.getInt("Year");
                yearList.add(year);
            }
            return yearList;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Week> weekList(int year) {
        ArrayList<Week> weekList = new ArrayList<>();
        LocalDate date = LocalDate.parse(year + "-01-01");
        date = date.with(DayOfWeek.MONDAY);
        int i = 0;
        if (date.getYear() != year) {
            date = date.plusDays(7);
        }
        while (date.getYear() == year) {
            Week week = new Week();
            week.setWeekNum(i);
            i++;
            week.setStart(date);
            week.setEnd(date.with(DayOfWeek.SUNDAY));
            date = date.plusDays(7);
            weekList.add(week);
        }
        return weekList;
    }

    public ArrayList<LocalDate> dayList(Week week) {
        ArrayList<LocalDate> dayList = new ArrayList<>();
        LocalDate date = week.getStart();
        for (int i = 0; i < 7; i++) {
            dayList.add(date);
            date = date.plusDays(1);
        }
        return dayList;
    }

    public ArrayList<Slot> slotList() {
        ArrayList<Slot> slots = new ArrayList<>();
        try {
            String time;
            String sql = "SELECT [Slot]\n"
                    + "      ,[From]\n"
                    + "      ,[To]\n"
                    + "  FROM [Assigment].[dbo].[Slot]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlot(rs.getInt("Slot"));
                time = rs.getString("From");
                slot.setFrom(time.substring(0, time.length() - 3));
                time = rs.getString("To");
                slot.setTo(time.substring(0, time.length() - 3));
                slots.add(slot);
            }
            return slots;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Timetable> listTimeTables(Week week, int InstuctorId) {
        ArrayList<Timetable> timetables = new ArrayList<>();
        try {
            String sql = "select TimetableCode ,\n"
                    + "	tt.GroupId, \n"
                    + " \n"
                    + "	g.GroupCode,\n"
                    + "    r.RoomName,\n"
                    + "	tt.[Date],\n"
                    + "    tt.Slot,\n"
                    + "    InstructorId,\n"
                    + "	CourseCode,"
                    + " Taken\n"
                    + "    from TimeTable tt\n"
                    + "		inner join Room r\n"
                    + "			on tt.RoomId = r.RoomId\n"
                    + "		inner join Slot s\n"
                    + "			on s.Slot = tt.Slot\n"
                    + "		inner join [Group] g\n"
                    + "			on tt.GroupId = g.GroupId\n"
                    + "		inner join [Course] c\n"
                    + "			on g.CourseId = c.CourseId\n"
                    + "    where tt.[Date] >= ?\n"
                    + "    and tt.[Date] <= ?\n"
                    + "    and tt.InstructorId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, week.getStart().toString());
            stm.setString(2, week.getEnd().toString());
            stm.setInt(3, InstuctorId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setTimetableCode(rs.getInt("TimetableCode"));
                timetable.setDate(LocalDate.parse(rs.getString("Date")));
                timetable.setSlot(rs.getInt("Slot"));
                timetable.setRoomName(rs.getString("RoomName"));
                timetable.setGroup(rs.getString("GroupCode"));
                timetable.setCourseCode(rs.getString("CourseCode"));
                timetable.setTaken(rs.getBoolean("Taken"));
                timetables.add(timetable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timetables;
    }
}
