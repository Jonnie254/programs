package student;

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

public class Student {

    Connection conn = MyConnection.getConnection();
    PreparedStatement ps;

    // get table max row
    public int getMax() {
        int id = 0;
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT MAX(id) FROM student")) {
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }
     //insert student details into student table
    public void insert(int id, String name, String date_of_birth, String gender, String email, String phone, String father_name, String mother_name, String address1, String address2, String image_path){
        try {
            String sql = "insert into student values (?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,name);
            ps.setString(3, date_of_birth);
            ps.setString(4,gender);
            ps.setString(5,email);
            ps.setString(6,phone);
            ps.setString(7,father_name);
            ps.setString(8,mother_name);
            ps.setString(9,address1);
            ps.setString(10,address2);
            ps.setString(11,image_path);
            
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"New student added successfully");       
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      // check whether email exists
    public boolean isEmailExists(String email) {
        try {
            ps = conn.prepareStatement("select * from student where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean isPhoneExists(String phone) {
        try {
            ps = conn.prepareStatement("select * from student where phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //check if the student exist in the database
    public boolean isIdExists(int id) {
        try {
            ps = conn.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //get all the students from the database from the student table
    public void getStudentValue(JTable table, String searchValue){
        try {
            String sql= "select * from student where concat(id, name, email, phone)like ? order by id asc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[11];
                row [0] = rs.getInt(1);
                row [1] = rs.getString(2);
                row [2] = rs.getString(3);
                row [3] = rs.getString(4);
                row [4] = rs.getString(5);
                row [5] = rs.getString(6);
                row [6] = rs.getString(7);
                row [7] = rs.getString(8);
                row [8] = rs.getString(9);
                row [9] = rs.getString(10);
                row [10] =rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    //update student value
   public void update(int id, String name, String date_of_birth, String gender, String email, String phone, String father_name, String mother_name, String address1, String address2, String image_path) {
    try {
        String sql = "UPDATE student SET name=?, "
                + "date_of_birth=?, "
                + "gender=?, "
                + "email=?, "
                + "phone=?, "
                + "father_name=?, "
                + "mother_name=?, "
                + "address1=?, "
                + "address2=?, "
                + "image_path=? "
                + "WHERE id=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, date_of_birth);
        ps.setString(3, gender);
        ps.setString(4, email);
        ps.setString(5, phone);
        ps.setString(6, father_name);
        ps.setString(7, mother_name);
        ps.setString(8, address1);
        ps.setString(9, address2);
        ps.setString(10, image_path);
        ps.setInt(11, id);
        
        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "Student updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    public void delete(int id) {
        int YesorNo = JOptionPane.showConfirmDialog(null, "Curses and scores will also be deleted", "Student Delete", JOptionPane.OK_CANCEL_OPTION);
        if (YesorNo == JOptionPane.OK_OPTION) {
            try {
                ps = conn.prepareStatement("delete from student where id = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Student data deleted successfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
