/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Attendance {
    private Student student;
    private boolean Attended;
    private String Date;

    public Attendance() {
    }

    public Attendance(Student student, boolean Attended, String Date) {
        this.student = student;
        this.Attended = Attended;
        this.Date = Date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isAttended() {
        return Attended;
    }

    public void setAttended(boolean Attended) {
        this.Attended = Attended;
    }
    
}
