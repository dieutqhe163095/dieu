/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Student {
    private int StudentId;
    private String StudentCode;
    private String FullName;

    public Student() {
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int StudentId) {
        this.StudentId = StudentId;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setStudentCode(String StudentCode) {
        this.StudentCode = StudentCode;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public Student(int StudentId, String StudentCode, String FullName) {
        this.StudentId = StudentId;
        this.StudentCode = StudentCode;
        this.FullName = FullName;
    }
    
}
