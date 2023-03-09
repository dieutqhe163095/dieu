/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class Timetable {
    private int slot;
    private int TimetableCode;
    private String roomname;
    private LocalDate Date;
    private String group;
    private String CourseCode;
    private boolean Taken;
    private LocalDateTime takenDate;
    private int InstructorId;
    public String getRoomname() {
        return roomname;
    }
    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getTimetableCode() {
        return TimetableCode;
    }

    public void setTimetableCode(int TimetableCode) {
        this.TimetableCode = TimetableCode;
    }
    
    public String getRoomName() {
        return roomname;
    }

    public void setRoomName(String RoomName) {
        this.roomname = RoomName;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate Date) {
        this.Date = Date;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String CourseCode) {
        this.CourseCode = CourseCode;
    }

    public boolean isTaken() {
        return Taken;
    }

    public void setTaken(boolean Taken) {
        this.Taken = Taken;
    }

    public LocalDateTime getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(LocalDateTime takenDate) {
        this.takenDate = takenDate;
    }

    public int getInstructorId() {
        return InstructorId;
    }

    public void setInstructorId(int InstructorId) {
        this.InstructorId = InstructorId;
    }
    
}
