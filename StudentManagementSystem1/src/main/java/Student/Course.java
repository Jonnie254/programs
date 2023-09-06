package Student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Course {

    Connection conn = MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT MAX(id) FROM course")) {
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
    public boolean getId(int id) {
        try {
            ps = conn.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField10.setText(String.valueOf(rs.getInt(1)));
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"The student does not exist");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public int countSemester(int id){
        int total = 0;
        try {
            ps = conn.prepareStatement("select count(*) as 'total' from course where student_id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);             
            }
            if(total == 8){
                JOptionPane.showMessageDialog(null,"The student has completed all courses");
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    // check whether the student has taken the semester or not
    public boolean isSemesterExists(int sid, int semesterNo) {
        try {
            ps = conn.prepareStatement("select * from course where student_id = ? and semester = ? ");
            ps.setInt(1, sid);
            ps.setInt(2,semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //check whether the student has taken the course or not
    public boolean isCourseExists(int sid, String courseNo, String course) {
       String sql = "select * from course where student_id =? and " + courseNo + "=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2, course);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public void insert(int id, int sid, int semester, String course1, String course2, String course3, String course4, String course5){
        try {
            String sql = "insert into course values (?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setInt(2,sid);
            ps.setInt(3, semester);
            ps.setString(4,course1);
            ps.setString(5,course2);
            ps.setString(6,course3);
            ps.setString(7,course4);
            ps.setString(8,course5);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Courses added successfully");       
            }

        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     //get all the course values from the database course table
     public void getCourseValue(JTable table, String searchValue){
        try {
            String sql= "select * from course where concat(id, student_id, semester)like ? order by id asc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[11];
                row [0] = rs.getInt(1);
                row [1] = rs.getInt(2);
                row [2] = rs.getInt(3);
                row [3] = rs.getString(4);
                row [4] = rs.getString(5);
                row [5] = rs.getString(6);
                row [6] = rs.getString(7);
                row [7] = rs.getString(8);
               
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
} 