/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class AttendanceCheck {
   private Student student;
    private ArrayList<Boolean> attentedList;

    public AttendanceCheck() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArrayList<Boolean> getAttentedList() {
        return attentedList;
    }

    public void setAttentedList(ArrayList<Boolean> attentedList) {
        this.attentedList = attentedList;
    } 
}
