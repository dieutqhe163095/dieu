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
import model.Student;

/**
 *
 * @author ASUS
 */
public class StudentDao extends DBContext {

    public List<Student> fullStudentInGroup(int groupId) {
        List<Student> list = new ArrayList();
        String sql = "Select Student.* from StudentGroupRelation join Student on "
                + "Student.StudentId = StudentGroupRelation.StudentId where "
                + "GroupId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, groupId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("StudentID"));
                student.setFullName(rs.getString("Surname")+" " + rs.getString("MiddleName")+" "+rs.getString("GivenName"));
                student.setStudentCode(rs.getString("Code"));
                list.add(student);
            }
        } catch (SQLException e) {

        }
        return list;
    }
    public static void main(String[] args) {
        StudentDao sd = new StudentDao();
        List<Student> list = sd.fullStudentInGroup(2);
        for (Student student : list) {
            System.out.println(student.getFullName());
        }
    }

}
