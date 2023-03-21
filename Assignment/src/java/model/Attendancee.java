/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class Attendancee {
    private Student student;
    private List<Attendance>list;
    private double percentAbsent;
    public Attendancee() {
    }

    public Attendancee(Student student, List<Attendance> list, double percentAbsent) {
        this.student = student;
        this.list = list;
        this.percentAbsent = percentAbsent;
    }

    public double getPercentAbsent() {
        return percentAbsent;
    }

    public void setPercentAbsent(double percentAbsent) {
        this.percentAbsent = percentAbsent;
    }

   

   

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Attendance> getList() {
        return list;
    }

    public void setList(List<Attendance> list) {
        this.list = list;
    }

    
}
